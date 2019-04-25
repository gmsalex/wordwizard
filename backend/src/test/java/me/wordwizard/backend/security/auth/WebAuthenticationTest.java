package me.wordwizard.backend.security.auth;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import me.wordwizard.backend.api.model.auth.UserAuthEmailRequest;
import me.wordwizard.backend.api.model.auth.UserAuthSuccessResponse;
import me.wordwizard.backend.security.auth.util.JsonSupport;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureEmbeddedDatabase
@FlywayTest(locationsForMigrate = {"/db/migration", "/WebAuthenticationTest"})
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class WebAuthenticationTest extends JsonSupport {
    @Autowired
    private MockMvc mockMvc;
    private Function<String, MockHttpServletRequestBuilder> f = v -> MockMvcRequestBuilders
            .post("/api/login")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .content(v)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .with(csrf());

    @DirtiesContext
    @Test
    public void testWebAuthSuccessful() throws Exception {
        var json = dataToJson(UserAuthEmailRequest.builder().email("test@gmail.com").password("pass").build());
        var result = mockMvc.perform(f.apply(json))
                .andExpect(status().isOk())
                .andReturn();
        var response = objectMapper.readValue(result.getResponse().getContentAsString(), UserAuthSuccessResponse.class);
        assertThat(response).extracting(UserAuthSuccessResponse::getName).as("logged name").isEqualTo("Test");
    }

    @Test
    public void testWebAuthFail() throws Exception {
        var json = dataToJson(UserAuthEmailRequest.builder().email("test@gmail.com").password("pass1").build());
        mockMvc.perform(f.apply(json)).andExpect(status().isUnauthorized());
    }


    @DirtiesContext
    @Test
    public void testCsrfEmptyJsonFilter() throws Exception {
        var result = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/login")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString();
        assertThat(result.getResponse().getCookie("XSRF-TOKEN")).isNotNull();
        assertThat(response).isEqualTo("{}");
    }

}
