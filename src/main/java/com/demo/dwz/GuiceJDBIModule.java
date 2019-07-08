package com.demo.dwz;

import com.demo.dwz.dao.PlayerDAO;
import com.google.inject.AbstractModule;
import org.jdbi.v3.core.Jdbi;

public class GuiceJDBIModule extends AbstractModule {

    private final Jdbi jdbi;

    public GuiceJDBIModule(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    protected void configure() {
        bind(PlayerDAO.class).toInstance(jdbi.onDemand(PlayerDAO.class));
    }
}
