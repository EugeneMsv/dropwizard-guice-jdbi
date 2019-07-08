package com.demo.dwz;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GuiceBundle implements ConfiguredBundle<AppConfiguration> {

    private Injector injector;

    private Class<AbstractModule>[] modulesClasses;

    private List<AbstractModule> modules = new LinkedList<>();

    public GuiceBundle(Class<?>... modulesClasses) {
        this.modulesClasses = (Class<AbstractModule>[]) modulesClasses;
    }

    public Injector getInjector() {
        return injector;
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) {
        for (Class<AbstractModule> moduleClass : modulesClasses) {
            try {
                Constructor<?>[] declaredConstructors = moduleClass.getDeclaredConstructors();
                Constructor<?> moduleConstructor = null;
                for (Constructor<?> declaredConstructor : declaredConstructors) {
                    declaredConstructor.setAccessible(true);
                    if (declaredConstructor.getParameterCount() == 1
                            && declaredConstructor.getParameterTypes()[0] == AppConfiguration.class) {
                        moduleConstructor = declaredConstructor;
                        break;
                    }
                }
                AbstractModule module;
                if (moduleConstructor == null) {
                    moduleConstructor = moduleClass.getDeclaredConstructor();
                    module = (AbstractModule) moduleConstructor.newInstance();
                } else {
                    module = (AbstractModule) moduleConstructor.newInstance(configuration);
                }
                modules.add(module);

            } catch (Exception e) {
                throw new RuntimeException("Fail during guice module initialization = " + moduleClass);
            }
        }
    }

    public void addModules(AbstractModule... modules){
        this.modules.addAll(Arrays.asList(modules));
    }

    public Injector createInjector(){
        this.injector = Guice.createInjector(modules);
        return this.injector;
    }


}
