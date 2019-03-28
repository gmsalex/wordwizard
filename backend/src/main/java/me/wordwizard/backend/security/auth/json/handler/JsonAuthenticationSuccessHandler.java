package me.wordwizard.backend.security.auth.json.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.wordwizard.backend.model.api.auth.UserAuthSuccessResponse;
import me.wordwizard.backend.model.mapper.MappingConfiguration;
import me.wordwizard.backend.security.auth.userdetails.principal.WWUserPrincipal;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JsonAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    @Qualifier(MappingConfiguration.TO_CLIENT_MAPPER)
    private ModelMapper modelMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        var user = (WWUserPrincipal) authentication.getPrincipal();
        objectMapper.writeValue(response.getWriter(), modelMapper.map(user, UserAuthSuccessResponse.class));
        clearAuthenticationAttributes(request);
    }
}
