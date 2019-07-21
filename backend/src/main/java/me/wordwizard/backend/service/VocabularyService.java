package me.wordwizard.backend.service;

import me.wordwizard.backend.api.model.vocabulary.entry.VEBaseDTO;
import me.wordwizard.backend.api.model.vocabulary.selection.VSCreationDTO;
import me.wordwizard.backend.model.entity.vocabulary.Repetition;
import me.wordwizard.backend.model.entity.vocabulary.VocabularyEntry;
import me.wordwizard.backend.model.entity.vocabulary.VocabularySelection;
import me.wordwizard.backend.security.auth.util.AuthUtil;
import me.wordwizard.backend.service.repository.RepetitionRepository;
import me.wordwizard.backend.service.repository.VocabularyEntryRepository;
import me.wordwizard.backend.service.repository.VocabularySelectionRepository;
import me.wordwizard.backend.service.util.GroupingFunction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static me.wordwizard.backend.api.mapper.MappingConfiguration.FROM_API_MAPPER;

@Service
@Validated
@RolesAllowed("ROLE_USER")
public class VocabularyService {
    private final VocabularySelectionRepository vsRepository;
    private final VocabularyEntryRepository veRepository;
    private final RepetitionRepository repRepository;
    private final AuthUtil authUtil;
    private final ModelMapper mapper;

    @Autowired
    public VocabularyService(VocabularySelectionRepository vsRepository,
                             VocabularyEntryRepository veRepository,
                             RepetitionRepository repRepository,
                             AuthUtil authUtil,
                             @Qualifier(FROM_API_MAPPER) ModelMapper mapper) {
        this.vsRepository = vsRepository;
        this.veRepository = veRepository;
        this.repRepository = repRepository;
        this.authUtil = authUtil;
        this.mapper = mapper;
    }

    /**
     * Get all vocabulary selections owned by the currently authenticated user
     *
     * @return List of vocabulary selections
     */
    public List<VocabularySelection> getSelectionList() {
        return vsRepository.findAllByUserId(authUtil.getUserId());
    }


    /**
     * Get vocabulary's repetitions
     *
     * @param vsId Vocabulary selection Id
     * @return List of word repetitions
     */
    public List<Repetition> getRepetitionList(long vsId) {
        return repRepository.findBySelectionIdAndSelectionUserIdOrderByCreatedDesc(vsId, authUtil.getUserId());
    }

    /**
     * Create a new vocabulary selection for an authenticated user
     *
     * @param dto Creation params specified
     * @return Created vocabulary selection entity
     * @throws IllegalArgumentException if vocabulary selection already exists
     */
    public VocabularySelection createSelection(@Valid VSCreationDTO dto) {
        vsRepository
                .findByNameAndUserId(dto.getName(), authUtil.getUserId())
                .ifPresent(v -> {
                    throw new IllegalArgumentException("Vocabulary selection already exists " + dto.getName());
                });

        var vs = new VocabularySelection();
        vs.setName(dto.getName());
        vs.setUser(authUtil.getUserDetails().getUser());
        return vsRepository.save(vs);
    }

    /**
     * Add a new vocabulary entry to the database and associate it with vocabulary selection
     *
     * @param vsId Selection id
     * @param dto  Vocabulary entry data
     * @return Repetition object
     */
    @Transactional
    public Repetition addEntry(long vsId, @Valid VEBaseDTO dto) {
        var vs = vsRepository.findByIdAndUserId(vsId, authUtil.getUserId());
        var entry = mapper.map(dto, VocabularyEntry.class);
        entry.setUser(authUtil.getUserDetails().getUser());
        entry = veRepository.save(entry);
        return repRepository.save(new Repetition(entry, vs));
    }

    /**
     * Add vocabulary entries to the studying process by associating vocabulary entries
     * with vocabulary selection
     *
     * @param vsId  Selection id
     * @param veIds Vocabulary entry ids
     */
    @Transactional
    public void addRepetition(long vsId, @NotNull @Size(min = 1, max = 100) Set<Long> veIds) {
        var vs = vsRepository.findByIdAndUserId(vsId, authUtil.getUserId());
        var foundVeIds = veRepository.countByIdInAndUserId(veIds, authUtil.getUserId());
        if (veIds.size() != foundVeIds) {
            throw new IllegalArgumentException("Invalid ve ids supplied by user " + authUtil.getUserId());
        }
        var existingVeIds = repRepository.findByEntryIdIsInAndSelectionId(veIds, vsId)
                .map(rep -> rep.getEntry().getId())
                .collect(Collectors.toSet());
        veIds.stream()
                .filter(veId -> !existingVeIds.contains(veId))
                .map(veRepository::getOne)
                .forEach(ve -> repRepository.save(new Repetition(ve, vs)));
    }


    /**
     * Remove vocabulary entries from studying process by removing association between VE and VS
     * Orphaned VE will be also removed
     *
     * @param repetitionIds Ids of Repetitions for removal
     */
    @Transactional
    public void removeRepetition(@NotNull @Size(min = 1, max = 100) Set<Long> repetitionIds) {
        repRepository
                .findByIdInAndSelectionUserId(repetitionIds, authUtil.getUserId())
                .forEach(v -> {
                    repRepository.delete(v);
                    if (repRepository.countByEntryId(v.getEntry().getId()) == 0) {
                        veRepository.deleteById(v.getEntry().getId());
                    }
                });
    }


    /**
     * Remove vocabulary entries and all their links to VS's
     *
     * @param veIds Ids of Vocabulary entries for removal
     */
    @Transactional
    public void removeEntry(@NotNull @Size(min = 1, max = 100) Set<Long> veIds) {
        veRepository
                .findAllByIdInAndUserId(veIds, authUtil.getUserId())
                .forEach(veRepository::delete);
    }


    /**
     * Remove vocabulary entries and clear corresponding Repetitions
     *
     * @param vsId Vocabulary selection id
     */
    @Transactional
    public void removeSelection(long vsId) {
        var vs = vsRepository.findByIdAndUserId(vsId, authUtil.getUserId());
        vs.getRepetitions().stream()
                .collect(Collectors.groupingBy(new GroupingFunction<>(100)))
                .values()
                .stream()
                .map(partition -> partition.stream().map(Repetition::getId).collect(Collectors.toSet()))
                .forEach(partition -> removeRepetition(new HashSet<>(partition)));
        vsRepository.delete(vs);
    }
}
