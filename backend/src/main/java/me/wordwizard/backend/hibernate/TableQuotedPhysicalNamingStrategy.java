package me.wordwizard.backend.hibernate;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.Optional;

/**
 * Naming strategy that quotes table names.
 */
public class TableQuotedPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {
    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return Optional.ofNullable(name).map(Identifier::quote).orElse(null);
    }
}
