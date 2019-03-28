package me.wordwizard.backend.security;

import me.wordwizard.backend.security.auth.json.JsonAuthConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.savedrequest.NullRequestCache;

@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    private JsonAuthConfigurer jsonAuthConfigurer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .cors()
                .and()
                .logout().logoutUrl("/api/logout").clearAuthentication(true).invalidateHttpSession(true)
                .and()
                .sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false).and()
                .and()
                .apply(jsonAuthConfigurer)
                .and()
                .requestCache().requestCache(new NullRequestCache());
        http.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, DaoAuthenticationProvider daoAuthenticationProvider) {
        auth.authenticationProvider(daoAuthenticationProvider);
        auth.eraseCredentials(true);
    }
}
