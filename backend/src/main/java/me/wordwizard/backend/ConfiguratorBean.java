package me.wordwizard.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

@Configuration
public class ConfiguratorBean {

    @Bean
    public javax.validation.Validator localValidatorFactoryBean(@Autowired AutowireCapableBeanFactory beanFactory) {
        var constraintFactory = new SpringConstraintValidatorFactory(beanFactory);
        var validatorFactory = new LocalValidatorFactoryBean();
        validatorFactory.setConstraintValidatorFactory(constraintFactory);
        return validatorFactory;
    }

    @Bean
    @Lazy
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(@Autowired javax.validation.Validator validator) {
        return hibernateProperties -> hibernateProperties.put("javax.persistence.validation.factory", validator);
    }

    @Bean
    @Profile("dev")
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        return flyway -> {
            flyway.clean();
            flyway.migrate();
        };
    }
}
