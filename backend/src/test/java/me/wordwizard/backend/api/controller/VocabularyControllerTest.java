package me.wordwizard.backend.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import me.wordwizard.backend.api.model.vocabulary.entry.VEAssociateDTO;
import me.wordwizard.backend.api.model.vocabulary.entry.VEDisAssociateDTO;
import me.wordwizard.backend.api.model.vocabulary.entry.VERemovalDTO;
import me.wordwizard.backend.api.model.vocabulary.entry.VocabularyEntryDTO;
import me.wordwizard.backend.api.model.vocabulary.repetition.RepetitionDTO;
import me.wordwizard.backend.api.model.vocabulary.selection.VSCreationDTO;
import me.wordwizard.backend.api.model.vocabulary.selection.VocabularySelectionDTO;
import me.wordwizard.backend.model.entity.vocabulary.Repetition;
import me.wordwizard.backend.model.entity.vocabulary.VocabularySelection;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureEmbeddedDatabase
@AutoConfigureMockMvc
public class VocabularyControllerTest extends JsonSupport {
    private final static TypeReference<List<VocabularySelectionDTO>> dtoRef = new TypeReference<List<VocabularySelectionDTO>>() {
    };
    private final static TypeReference<List<VocabularySelection>> selectionRef = new TypeReference<List<VocabularySelection>>() {
    };
    @MockBean
    private VocabularyService service;
    @Autowired
    private MockMvc mockMvc;

    @WithMockUser
    @Test
    public void getList() throws Exception {
        var src = getDataFromJsonFileSource("getList/service.json", selectionRef);
        when(service.getSelectionsList()).thenReturn(src);
        var result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/vocabulary")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();
        var actual = objectMapper.readValue(result.getResponse().getContentAsString(), dtoRef);
        verify(service).getSelectionsList();
        var expected = getDataFromJsonFileSource("getList/expected.json", dtoRef);
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }


    @WithMockUser
    @Test
    public void createVs() throws Exception {
        var request = getJsonAsStringFromFileSource("createVs/request.json");
        var argsExpected = objectMapper.readValue(request, VSCreationDTO.class);
        var serviceResult = getDataFromJsonFileSource("createVs/serviceResult.json", VocabularySelection.class);
        var expected = getDataFromJsonFileSource("createVs/response.json", VocabularySelectionDTO.class);

        when(service.createSelection(
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

        var actual = objectMapper.readValue(result.getResponse().getContentAsString(), VocabularySelectionDTO.class);
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);

        verify(service).createSelection(argThat(v -> {
            assertThat(v).isEqualToComparingFieldByFieldRecursively(expected);
            return true;
        }));
    }

    @WithMockUser
    @Test
    public void createEntry() throws Exception {
        var request = getJsonAsStringFromFileSource("createEntry/request.json");
        var argsExpected = objectMapper.readValue(request, VocabularyEntryDTO.class);
        var serviceResult = getDataFromJsonFileSource("createEntry/serviceResult.json", Repetition.class);
        var responseExpected = getDataFromJsonFileSource("createEntry/response.json", RepetitionDTO.class);

        when(service.addEntry(eq(1L),
                argThat(argsActual -> {
                    assertThat(argsActual).isEqualToComparingFieldByFieldRecursively(argsExpected);
                    return true;
                })
        )).thenReturn(serviceResult);

        var result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/vocabulary/1/entry")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        var actual = objectMapper.readValue(result.getResponse().getContentAsString(), RepetitionDTO.class);
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(responseExpected);

        verify(service).addEntry(eq(1L),
                argThat(argsActual -> {
                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(responseExpected);
                    return true;
                })
        );
    }


    @WithMockUser
    @Test
    public void addRepetition() throws Exception {
        var request = getJsonAsStringFromFileSource("addRepetition/request.json");
        var argsExpected = objectMapper.readValue(request, VEAssociateDTO.class);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/vocabulary/1/repetition")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .with(csrf()))
                .andExpect(status().isOk());

        verify(service).addRepetition(1L, argsExpected.getVeIds());
    }


    @WithMockUser
    @Test
    public void removeRepetition() throws Exception {
        var request = getJsonAsStringFromFileSource("removeRepetition/request.json");
        var argsExpected = objectMapper.readValue(request, VEDisAssociateDTO.class);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/vocabulary/repetition")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .with(csrf()))
                .andExpect(status().isOk());

        verify(service).removeRepetition(argsExpected.getRepetitionIds());
    }

    @WithMockUser
    @Test
    public void removeEntry() throws Exception {
        var request = getJsonAsStringFromFileSource("removeEntry/request.json");
        var argsExpected = objectMapper.readValue(request, VERemovalDTO.class);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/vocabulary/entry")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .with(csrf()))
                .andExpect(status().isOk());

        verify(service).removeEntry(argsExpected.getVeIds());
    }

}
