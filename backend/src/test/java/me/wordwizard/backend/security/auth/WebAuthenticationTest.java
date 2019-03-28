package me.wordwizard.backend.security.auth;

import me.wordwizard.backend.model.api.auth.UserAuthEmailRequest;
import me.wordwizard.backend.model.api.auth.UserAuthSuccessResponse;
import me.wordwizard.backend.security.auth.util.AbstractJsonTest;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@FlywayTest(locationsForMigrate = {"/db/migration", "/WebAuthenticationTest"})
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class WebAuthenticationTest extends AbstractJsonTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void name() throws Exception {
        var json = dataToJson(UserAuthEmailRequest.builder().email("test@gmail.com").password("pass").build());
        var result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/login")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();
        var response = getDataFromJsonString(result.getResponse().getContentAsString(), UserAuthSuccessResponse.class);
        assertThat(response).extracting(UserAuthSuccessResponse::getName).as("logged name").isEqualTo("Test");
    }
}