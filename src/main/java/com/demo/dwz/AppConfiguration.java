package com.demo.dwz;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AppConfiguration extends Configuration {

    @NotEmpty
    private String applicationName;

    @NotEmpty
    private String defaultPlayer;

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    private SwaggerBundleConfiguration swagger;

    public String getApplicationName() {
        return applicationName;
    }

    public String getDefaultPlayer() {
        return defaultPlayer;
    }

    public DataSourceFactory getDatabase() {
        return database;
    }

    public SwaggerBundleConfiguration getSwagger() {
        return swagger;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AppConfiguration{");
        sb.append("applicationName='").append(applicationName).append('\'');
        sb.append(", defaultPlayer='").append(defaultPlayer).append('\'');
        sb.append(", database=").append(database);
        sb.append(", swagger=").append(swagger);
        sb.append('}');
        return sb.toString();
    }
}
