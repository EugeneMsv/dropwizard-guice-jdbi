package com.demo.dwz.hook;

import com.demo.dwz.AppConfiguration;
import com.demo.dwz.dao.PlayerDAO;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Singleton
public class PlayerDaoPreparingApplicationManagedHook implements ApplicationManagedHook {

    private static final Logger logger = LoggerFactory.getLogger(PlayerDaoPreparingApplicationManagedHook.class);

    private final PlayerDAO playerDAO;

    private String defaultPlayer;

    @Inject
    public PlayerDaoPreparingApplicationManagedHook(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    @Inject
    public void setDefaultPlayer(@Named("defaultPlayer") String defaultPlayer) {
        this.defaultPlayer = defaultPlayer;
    }

    @Override
    public void start(AppConfiguration configuration, Environment environment) {
        if (playerDAO.existsTable()) {
            logger.info("Table for playerDAO already exists, skip creation");
            return;
        }
        playerDAO.createPlayerTable();
        PlayerDAO.Player defaultPlayer = new PlayerDAO.Player();
        defaultPlayer.setUid(UUID.randomUUID().toString());
        defaultPlayer.setName(this.defaultPlayer);
        playerDAO.addPlayer(defaultPlayer);
    }

    @Override
    public void stop(AppConfiguration configuration, Environment environment) {

    }
}
