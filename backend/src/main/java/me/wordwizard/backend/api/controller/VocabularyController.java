package me.wordwizard.backend.api.controller;

import me.wordwizard.backend.api.model.vocabulary.entry.VEAssociateDTO;
import me.wordwizard.backend.api.model.vocabulary.entry.VEDisAssociateDTO;
import me.wordwizard.backend.api.model.vocabulary.entry.VERemovalDTO;
import me.wordwizard.backend.api.model.vocabulary.entry.VocabularyEntryDTO;
import me.wordwizard.backend.api.model.vocabulary.repetition.RepetitionDTO;
import me.wordwizard.backend.api.model.vocabulary.selection.VSCreationDTO;
import me.wordwizard.backend.api.model.vocabulary.selection.VocabularySelectionDTO;
import me.wordwizard.backend.service.VocabularyService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Type;
import java.util.List;

import static me.wordwizard.backend.api.mapper.MappingConfiguration.TO_API_MAPPER;

@RestController
@RequestMapping(path = "/vocabulary", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RolesAllowed("ROLE_USER")
@Validated
public class VocabularyController {
    private final VocabularyService service;
    private final ModelMapper mapper;

    @Autowired
    public VocabularyController(VocabularyService service, @Qualifier(TO_API_MAPPER) ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping(value = "")
    public List<VocabularySelectionDTO> getSelectionList() {
        Type token = new TypeToken<List<VocabularySelectionDTO>>() {
        }.getType();
        return mapper.map(service.getSelectionList(), token);
    }

    @PostMapping(value = "")
    public VocabularySelectionDTO createVs(@RequestBody @NotNull VSCreationDTO dto) {
        return mapper.map(service.createSelection(dto), VocabularySelectionDTO.class);
    }

    @PostMapping(value = "/{id}/entry")
    public RepetitionDTO createEntry(@PathVariable("id") long vsId, @RequestBody @NotNull VocabularyEntryDTO entry) {
        return mapper.map(service.addEntry(vsId, entry), RepetitionDTO.class);
    }

    @GetMapping(value = "/{id}/repetition")
    public List<RepetitionDTO> getRepetitionList(@PathVariable("id") long vsId) {
        Type token = new TypeToken<List<RepetitionDTO>>() {
        }.getType();
        return mapper.map(service.getRepetitionList(vsId), token);
    }

    @PostMapping(value = "/{id}/repetition")
    public void addRepetition(@PathVariable("id") long vsId, @RequestBody @NotNull VEAssociateDTO dto) {
        service.addRepetition(vsId, dto.getVeIds());
    }

    @DeleteMapping(value = "/repetition")
    public void removeRepetition(@RequestBody @Valid VEDisAssociateDTO dto) {
        service.removeRepetition(dto.getRepetitionIds());
    }

    @DeleteMapping(value = "/entry")
    public void removeEntry(@RequestBody @Valid VERemovalDTO dto) {
        service.removeEntry(dto.getVeIds());
    }
}
