package me.wordwizard.backend.security.auth.json.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.wordwizard.backend.api.model.auth.UserAuthEmailRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class JsonAuthFilter extends AbstractAuthenticationProcessingFilter {
    private ObjectMapper objectMapper;

    public JsonAuthFilter() {
        super(new AntPathRequestMatcher("/api/login", HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        var httpMethod = request.getMethod();
        if (!HttpMethod.POST.name().equals(httpMethod) || !StringUtils.contains(request.getHeader(HttpHeaders.CONTENT_TYPE), MediaType.APPLICATION_JSON_VALUE)) {
            var msg = String.format("This auth filter accepts HTTP POST method and '%s' content type but got '%s' method and '%s' content type",
                    MediaType.APPLICATION_JSON_VALUE,
                    httpMethod,
                    request.getHeader(HttpHeaders.CONTENT_TYPE));
            throw new AuthenticationServiceException(msg);
        }
        var credential = this.getUserCredential(request);
        var authRequest = new UsernamePasswordAuthenticationToken(credential.getEmail(), credential.getPassword());
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private UserAuthEmailRequest getUserCredential(HttpServletRequest request) {
        try (BufferedReader bufferedReader = request.getReader()) {
            return Optional.ofNullable(objectMapper)
                    .orElseThrow(() -> new AuthenticationServiceException("No jackson mapper instance provided"))
                    .readValue(bufferedReader, UserAuthEmailRequest.class);
        } catch (IOException e) {
            throw new AuthenticationServiceException("Can't read request credential", e);
        }
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
