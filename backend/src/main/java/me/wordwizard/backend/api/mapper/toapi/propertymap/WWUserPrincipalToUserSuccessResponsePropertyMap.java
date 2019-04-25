package me.wordwizard.backend.api.mapper.toapi.propertymap;

import me.wordwizard.backend.api.mapper.MappingConfiguration;
import me.wordwizard.backend.api.model.auth.UserAuthSuccessResponse;
import me.wordwizard.backend.security.auth.userdetails.principal.WWUserDetails;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Qualifier(MappingConfiguration.TO_API_MAPPER)
@Component
public class WWUserPrincipalToUserSuccessResponsePropertyMap extends PropertyMap<WWUserDetails, UserAuthSuccessResponse> {
    @Override
    protected void configure() {
        map(source.getUser().getName(), destination.getName());
    }
}
