package me.wordwizard.backend.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import me.wordwizard.backend.api.model.vocabulary.entry.VERemoveDTO;
import me.wordwizard.backend.api.model.vocabulary.entry.VocabularyEntryDTO;
import me.wordwizard.backend.api.model.vocabulary.entry.VocabularySummaryDTO;
import me.wordwizard.backend.api.model.vocabulary.language.LanguageDTO;
import me.wordwizard.backend.helper.LanguageUtil;
import me.wordwizard.backend.security.auth.util.JsonSupport;
import me.wordwizard.backend.service.VocabularyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureEmbeddedDatabase
@AutoConfigureMockMvc
public class VocabularyControllerTest extends JsonSupport {
    private final static TypeReference<List<LanguageDTO>> languageDtoRef = new TypeReference<>() {
    };

    @MockBean
    private LanguageUtil languageUtil;
    @MockBean
    private VocabularyService service;
    @Autowired
    private MockMvc mockMvc;


    @WithMockUser
    @Test
    public void createEntry() throws Exception {
        var request = getJsonAsStringFromFileSource("createEntry/request.json");
        var argsExpected = objectMapper.readValue(request, VocabularyEntryDTO.class);
        var serviceResult = getDataFromJsonFileSource("createEntry/serviceResult.json", VocabularyEntryDTO.class);
        var responseExpected = getDataFromJsonFileSource("createEntry/response.json", VocabularyEntryDTO.class);

        when(service.addEntry(
                argThat(argsActual -> {
                    assertThat(argsActual).isEqualToComparingFieldByFieldRecursively(argsExpected);
                    return true;
                })
        )).thenReturn(serviceResult);

        var result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/vocabulary")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        var actual = objectMapper.readValue(result.getResponse().getContentAsString(), VocabularyEntryDTO.class);
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(responseExpected);

        verify(service).addEntry(
                argThat(argsActual -> {
                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(responseExpected);
                    return true;
                })
        );
    }

    @WithMockUser
    @Test
    public void getVocabularySummaryDTO() throws Exception {
        var serviceResult = getDataFromJsonFileSource("getVocabularySummaryDTO/serviceResult.json", VocabularySummaryDTO.class);
        var responseExpected = getDataFromJsonFileSource("getVocabularySummaryDTO/response.json", VocabularySummaryDTO.class);

        when(service.getVocabularySummary()).thenReturn(serviceResult);

        var result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/vocabulary")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        var actual = objectMapper.readValue(result.getResponse().getContentAsString(), VocabularySummaryDTO.class);
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(responseExpected);
        verify(service).getVocabularySummary();
    }


    @WithMockUser
    @Test
    public void removeEntry() throws Exception {
        var request = getJsonAsStringFromFileSource("removeEntry/request.json");
        var argsExpected = objectMapper.readValue(request, VERemoveDTO.class);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/vocabulary")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .with(csrf()))
                .andExpect(status().isOk());

        verify(service).removeEntry(argsExpected.getVeIds());
    }


    @WithMockUser
    @Test
    public void getLanguages() throws Exception {
        List<LanguageDTO> languageList = Arrays.asList(new LanguageDTO("TEST_CODE", "TEST_NAME", "TEST NATIVE NAME"));
        when(languageUtil.getLanguageList()).thenReturn(languageList);

        var result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/vocabulary/languages")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();
        var actual = objectMapper.readValue(result.getResponse().getContentAsString(), languageDtoRef);
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(languageList);

        verify(languageUtil).getLanguageList();
    }

}
