package com.demo.dwz.hook;

import com.demo.dwz.AppConfiguration;
import io.dropwizard.setup.Environment;

import java.util.LinkedList;
import java.util.List;

public class CompositeApplicationManagedHook implements ApplicationManagedHook {

    private List<ApplicationManagedHook> hooks = new LinkedList<>();


    public synchronized void add(ApplicationManagedHook hook) {
        hooks.add(hook);
    }

    public synchronized void remove(ApplicationManagedHook hook) {
        hooks.remove(hook);
    }


    @Override
    public void start(AppConfiguration configuration, Environment environment) {
        hooks.forEach(hook -> hook.start(configuration, environment));
    }

    @Override
    public void stop(AppConfiguration configuration, Environment environment) {
        hooks.forEach(hook -> hook.stop(configuration, environment));
    }
}
