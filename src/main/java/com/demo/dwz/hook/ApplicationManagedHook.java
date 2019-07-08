package com.demo.dwz.hook;

import com.demo.dwz.AppConfiguration;
import io.dropwizard.setup.Environment;

/**
 * Execute anything after application start
 */
public interface ApplicationManagedHook {


    void start(AppConfiguration configuration, Environment environment);

    void stop(AppConfiguration configuration, Environment environment);
}
