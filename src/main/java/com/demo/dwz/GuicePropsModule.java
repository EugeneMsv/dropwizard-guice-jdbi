package com.demo.dwz;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * DI module
 */
public class GuicePropsModule extends AbstractModule {

    private final AppConfiguration configuration;

    public GuicePropsModule(AppConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(String.class).annotatedWith(Names.named("defaultPlayer")).toInstance(configuration.getDefaultPlayer());
    }
}
