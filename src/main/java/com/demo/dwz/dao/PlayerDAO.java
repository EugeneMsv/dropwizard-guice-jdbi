package com.demo.dwz.dao;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface PlayerDAO {

    @SqlQuery("SELECT EXISTS (SELECT 1 FROM information_schema.tables  WHERE table_name = 'player')")
    @GetGeneratedKeys
    boolean existsTable();

    @SqlUpdate("create table player (uid varchar(128) primary key, name varchar(128))")
    void createPlayerTable();

    @SqlUpdate("insert into player (uid, name) values (:uid, :name)")
    @GetGeneratedKeys
    @RegisterBeanMapper(Player.class)
    Player addPlayer(@BindBean Player player);

    @SqlQuery("select uid, name from player where uid=:uid")
    @RegisterBeanMapper(Player.class)
    Player findOne(@Bind("uid") String uid);

    class Player {

        private String name;

        private String uid;

        public String getName() {
            return name;
        }

        public String getUid() {
            return uid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
