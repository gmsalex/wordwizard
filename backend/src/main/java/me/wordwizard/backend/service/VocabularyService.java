package me.wordwizard.backend.service;

import me.wordwizard.backend.api.model.vocabulary.entry.VECreateDTO;
import me.wordwizard.backend.api.model.vocabulary.entry.VocabularyEntryDTO;
import me.wordwizard.backend.api.model.vocabulary.entry.VocabularySummaryDTO;
import me.wordwizard.backend.api.model.vocabulary.tag.VocabularyTagDTO;
import me.wordwizard.backend.model.entity.vocabulary.Repetition;
import me.wordwizard.backend.model.entity.vocabulary.VocabularyEntry;
import me.wordwizard.backend.security.auth.util.AuthUtil;
import me.wordwizard.backend.service.repository.RepetitionRepository;
import me.wordwizard.backend.service.repository.TagRepository;
import me.wordwizard.backend.service.repository.VocabularyEntryRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

import static me.wordwizard.backend.api.mapper.MappingConfiguration.FROM_API_MAPPER;
import static me.wordwizard.backend.api.mapper.MappingConfiguration.TO_API_MAPPER;

@Service
@Validated
@RolesAllowed("ROLE_USER")
public class VocabularyService {
    private final VocabularyEntryRepository veRepository;
    private final RepetitionRepository repRepository;
    private final TagRepository tagRepository;
    private final AuthUtil authUtil;
    private final ModelMapper fromApiMapper;
    private final ModelMapper toApiMapper;
    private final VocabularyTagService tagService;

    @Autowired
    public VocabularyService(VocabularyEntryRepository veRepository,
                             RepetitionRepository repRepository, TagRepository tagRepository, AuthUtil authUtil,
                             @Qualifier(FROM_API_MAPPER) ModelMapper fromApiMapper,
                             @Qualifier(TO_API_MAPPER) ModelMapper toApiMapper,
                             VocabularyTagService tagService) {
        this.veRepository = veRepository;
        this.repRepository = repRepository;
        this.tagRepository = tagRepository;
        this.authUtil = authUtil;
        this.fromApiMapper = fromApiMapper;
        this.toApiMapper = toApiMapper;
        this.tagService = tagService;
    }

    /**
     * Add a new vocabulary entry to the database
     *
     * @param dto Vocabulary entry data
     * @return Entry DTO
     */
    @Transactional
    public VocabularyEntryDTO addEntry(@Valid VECreateDTO dto) {
        var entry = fromApiMapper.map(dto.getEntry(), VocabularyEntry.class);
        entry.setId(null);
        entry.setUser(authUtil.getUserDetails().getUser());
        var veEntity = veRepository.save(entry);

        tagService
                .createMissingTags(dto.getTagList())
                .forEach(tag -> repRepository.save(new Repetition(veEntity, tag)));
        return toApiMapper.map(veEntity, VocabularyEntryDTO.class);
    }

    /**
     * Remove vocabulary entries
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
     * Get vocabulary summary of the authenticated user
     *
     * @return Summary object
     */
    @Transactional
    public VocabularySummaryDTO getVocabularySummary() {
        var userId = authUtil.getUserId();
        var veList = veRepository
                .findAllByUserId(userId)
                .map(v -> toApiMapper.map(v, VocabularyEntryDTO.class))
                .collect(Collectors.toList());
        var tagList = tagRepository
                .findAllByUserId(userId)
                .map(v -> toApiMapper.map(v, VocabularyTagDTO.class))
                .collect(Collectors.toList());
        return new VocabularySummaryDTO(veList, tagList);
    }
}
