package com.demo.dwz.resource;

import com.demo.dwz.dao.PlayerDAO;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Api
@Path("/app/player")
@Singleton
public class SimpleResource {

    private PlayerDAO playerDAO;

    @Inject
    public void setPlayerDAO(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    @Path("{uid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Find player", response = PlayerDAO.Player.class)
    public PlayerDAO.Player getPlayer(@PathParam("uid") @NotNull String uid) {
        return playerDAO.findOne(uid);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add new player", response = PlayerDAO.Player.class)
    public PlayerDAO.Player savePlayer(PlayerDAO.Player player) {
        player.setUid(UUID.randomUUID().toString());
        return playerDAO.addPlayer(player);
    }


}
