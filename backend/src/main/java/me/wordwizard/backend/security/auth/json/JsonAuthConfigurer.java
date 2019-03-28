package me.wordwizard.backend.security.auth.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.wordwizard.backend.security.auth.json.filter.JsonAuthFilter;
import me.wordwizard.backend.security.auth.json.handler.JsonAuthenticationFailureHandler;
import me.wordwizard.backend.security.auth.json.handler.JsonAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JsonAuthConfigurer extends AbstractAuthenticationFilterConfigurer<HttpSecurity, JsonAuthConfigurer, JsonAuthFilter> {
    @Autowired
    private JsonAuthenticationSuccessHandler jsonSuccessHandler;
    @Autowired
    private JsonAuthenticationFailureHandler defaultFailureHandler;
    @Autowired
    private ObjectMapper objectMapper;

    public JsonAuthConfigurer() {
        super(new JsonAuthFilter(), null);
    }

    @Override
    public JsonAuthConfigurer loginProcessingUrl(String loginProcessingUrl) {
        return this;
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return null;
    }

    @Override
    public void configure(HttpSecurity http) {
        JsonAuthFilter authFilter = getAuthenticationFilter();
        authFilter.setObjectMapper(objectMapper);

        authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        authFilter.setAuthenticationSuccessHandler(jsonSuccessHandler);
        authFilter.setAuthenticationFailureHandler(defaultFailureHandler);

        Optional.ofNullable(http.getSharedObject(RememberMeServices.class))
                .ifPresent(authFilter::setRememberMeServices);

        JsonAuthFilter filter = postProcess(authFilter);
        http.addFilterAfter(filter, CsrfFilter.class);
    }
}
