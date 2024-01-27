package me.defender.cosmetics.database;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

// TODO migrate load and save method to this
public interface IDatabase {

    void connect();
    void createTable();
    HikariDataSource getDataSource();
    Connection getConnection();
}
