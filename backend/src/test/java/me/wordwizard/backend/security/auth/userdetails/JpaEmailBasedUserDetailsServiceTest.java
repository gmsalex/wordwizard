package me.wordwizard.backend.security.auth.userdetails;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import me.wordwizard.backend.security.auth.userdetails.principal.WWUserDetails;
import me.wordwizard.backend.service.repository.UserWithEmailRepository;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(SpringRunner.class)
@AutoConfigureEmbeddedDatabase
@SpringBootTest
@FlywayTest
public class JpaEmailBasedUserDetailsServiceTest {
    @Autowired
    private UserWithEmailRepository repository;
    private JpaEmailBasedUserDetailsService service;

    @Before
    public void setUp() throws Exception {
        service = new JpaEmailBasedUserDetailsService(repository);
    }

    @FlywayTest(locationsForMigrate = {"/JpaEmailBasedUserDetailsServiceTest/"})
    @Test
    public void loadUserByUsername() {
        assertThat(service.loadUserByUsername("test@gmail.com"))
                .isNotNull()
                .isInstanceOf(WWUserDetails.class)
                .extracting(v -> tuple(v.getUsername(), v.getPassword()))
                .as("Db Username/Password hash")
                .isEqualTo(tuple("test@gmail.com", "HASH"));
    }


    @Test(expected = ConstraintViolationException.class)
    public void loadUserByUsernameNotEmail() {
        service.loadUserByUsername("NOT_EMAIL");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameUnknownUser() {
        service.loadUserByUsername("unknown@gmail.com");
    }

}