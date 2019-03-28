package me.wordwizard.backend.security.auth.provider;

import me.wordwizard.backend.model.entity.user.User;
import me.wordwizard.backend.model.entity.user.UserAuthEmail;
import me.wordwizard.backend.security.auth.userdetails.JpaEmailBasedUserDetailsService;
import me.wordwizard.backend.security.auth.userdetails.principal.WWUserEmailPrincipal;
import me.wordwizard.backend.security.auth.userdetails.principal.WWUserPrincipal;
import me.wordwizard.backend.security.auth.util.AbstractJsonTest;
import me.wordwizard.backend.security.auth.util.NoJpaConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@NoJpaConfiguration
@SpringBootTest
public class WWDaoAuthenticationProviderTest extends AbstractJsonTest {
    @MockBean
    private JpaEmailBasedUserDetailsService userDetailsService;
    private WWDaoAuthenticationProvider authenticationProvider;

    @Before
    public void setUp() throws Exception {
        authenticationProvider = new WWDaoAuthenticationProvider(userDetailsService);
    }

    @Test
    public void testAuthenticateSuccessful() throws IOException {
        var user = getDataFromJsonFileSource("user.json", UserAuthEmail.class);
        when(userDetailsService.loadUserByUsername(user.getEmail()))
                .thenReturn(new WWUserEmailPrincipal(user));

        var result = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), "pass"));
        assertThat(result.isAuthenticated()).isTrue();
        assertThat(result.getPrincipal())
                .isInstanceOf(WWUserPrincipal.class)
                .extracting(v -> ((WWUserPrincipal) v).getUser())
                .extracting(v -> tuple(((User) v).getId(), ((User) v).getName()))
                .as("User id/name")
                .isEqualTo(tuple(1L, "name"));
        verify(userDetailsService).loadUserByUsername(user.getEmail());
    }


    @Test(expected = BadCredentialsException.class)
    public void testAuthenticateFailed() throws IOException {
        var user = getDataFromJsonFileSource("user.json", UserAuthEmail.class);
        when(userDetailsService.loadUserByUsername(user.getEmail()))
                .thenReturn(new WWUserEmailPrincipal(user));
        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), "invalidpass"));
    }

    @Test(expected = InternalAuthenticationServiceException.class)
    public void testAuthenticateFailedUnknownUser() throws IOException {
        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken("unknown@gmail.com", "invalidpass"));
    }
}