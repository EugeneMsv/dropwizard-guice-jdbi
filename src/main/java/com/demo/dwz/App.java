package com.demo.dwz;

import com.demo.dwz.hook.CompositeApplicationManagedHook;
import com.demo.dwz.hook.PlayerDaoPreparingApplicationManagedHook;
import com.demo.dwz.resource.SimpleResource;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application<AppConfiguration> {

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public final JDBIBundle postgresJdbiBundle = new JDBIBundle("postgresql");

    private GuiceBundle guiceBundle;

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                ));
        guiceBundle = new GuiceBundle(GuiceBeanModule.class, GuicePropsModule.class);
        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(postgresJdbiBundle);
        bootstrap.addBundle(new SwaggerBundle<AppConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AppConfiguration configuration) {
                return configuration.getSwagger();
            }
        });
    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) {
        logger.info("Starting with configuration = {}", configuration);

        Jdbi jdbi = postgresJdbiBundle.getJdbi();
        guiceBundle.addModules(new GuiceJDBIModule(jdbi));
        Injector guiceInjector = guiceBundle.createInjector();

        environment.jersey().register(guiceInjector.getInstance(SimpleResource.class));
        configureHooks(configuration, environment, guiceInjector);
    }

    private void configureHooks(AppConfiguration configuration, Environment environment, Injector guiceInjector) {
        CompositeApplicationManagedHook applicationManagedHook =
                guiceInjector.getInstance(CompositeApplicationManagedHook.class);
        applicationManagedHook.add(guiceInjector.getInstance(PlayerDaoPreparingApplicationManagedHook.class));
        environment.lifecycle().manage(new Managed() {
            @Override
            public void start() {
                applicationManagedHook.start(configuration, environment);
            }

            @Override
            public void stop() {
                applicationManagedHook.stop(configuration, environment);
            }
        });
    }

}
