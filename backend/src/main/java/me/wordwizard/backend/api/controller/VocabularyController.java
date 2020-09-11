package me.wordwizard.backend.api.controller;

import me.wordwizard.backend.api.model.vocabulary.entry.VECreateDTO;
import me.wordwizard.backend.api.model.vocabulary.entry.VERemoveDTO;
import me.wordwizard.backend.api.model.vocabulary.entry.VocabularySummaryDTO;
import me.wordwizard.backend.api.model.vocabulary.language.LanguageDTO;
import me.wordwizard.backend.helper.LanguageUtil;
import me.wordwizard.backend.service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "/vocabulary", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RolesAllowed("ROLE_USER")
@Validated
public class VocabularyController {
    private final VocabularyService service;
    private final LanguageUtil languageUtil;

    @Autowired
    public VocabularyController(VocabularyService service, LanguageUtil languageUtil) {
        this.service = service;
        this.languageUtil = languageUtil;
    }

    @PostMapping(value = "")
    public VocabularySummaryDTO createEntry(@RequestBody @NotNull VECreateDTO entry) {
        service.addEntry(entry);
        return service.getVocabularySummary();
    }

    @DeleteMapping(value = "")
    public void removeEntry(@RequestBody @Valid VERemoveDTO dto) {
        service.removeEntry(dto.getVeIds());
    }

    @GetMapping(value = "")
    public VocabularySummaryDTO getVeSummary() {
        return service.getVocabularySummary();
    }


    @GetMapping(value = "/languages")
    public List<LanguageDTO> getLanguages() {
        return languageUtil.getLanguageList();
    }
}
