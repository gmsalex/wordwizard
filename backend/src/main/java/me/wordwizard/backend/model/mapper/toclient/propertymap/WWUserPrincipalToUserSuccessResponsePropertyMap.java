package me.wordwizard.backend.model.mapper.toclient.propertymap;

import me.wordwizard.backend.model.api.auth.UserAuthSuccessResponse;
import me.wordwizard.backend.model.mapper.MappingConfiguration;
import me.wordwizard.backend.security.auth.userdetails.principal.WWUserPrincipal;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Qualifier(MappingConfiguration.TO_CLIENT_MAPPER)
@Component
public class WWUserPrincipalToUserSuccessResponsePropertyMap extends PropertyMap<WWUserPrincipal, UserAuthSuccessResponse> {
    @Override
    protected void configure() {
        map(source.getUser().getName(), destination.getName());
    }
}
