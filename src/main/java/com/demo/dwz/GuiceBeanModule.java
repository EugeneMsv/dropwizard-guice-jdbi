package com.demo.dwz;

import com.demo.dwz.hook.ApplicationManagedHook;
import com.demo.dwz.hook.CompositeApplicationManagedHook;
import com.demo.dwz.hook.PlayerDaoPreparingApplicationManagedHook;
import com.demo.dwz.resource.SimpleResource;
import com.google.inject.AbstractModule;

/**
 * DI module
 */
public class GuiceBeanModule extends AbstractModule {

    public GuiceBeanModule() {

    }

    @Override
    protected void configure() {
        bind(SimpleResource.class);
        bind(ApplicationManagedHook.class).to(CompositeApplicationManagedHook.class);
        bind(PlayerDaoPreparingApplicationManagedHook.class);
    }
}
