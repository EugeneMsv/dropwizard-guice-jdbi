package com.demo.dwz;

import io.dropwizard.ConfiguredBundle;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;

public class JDBIBundle implements ConfiguredBundle<AppConfiguration> {

    private Jdbi jdbi;

    private final String dataBaseName;

    public JDBIBundle(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public Jdbi getJdbi() {
        return jdbi;
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) {
        JdbiFactory factory = new JdbiFactory();
        this.jdbi = factory.build(environment, configuration.getDatabase(), dataBaseName);
    }

}
