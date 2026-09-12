package com.unicauca.taller2.usuarios.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Fábrica de conexiones JDBC para SQLite.
 */
public class ConexionSQLite {

    private static final String URL = "jdbc:sqlite:usuarios.db";

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS usuarios (" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    nombre_usuario TEXT UNIQUE NOT NULL," +
            "    nombre_completo TEXT NOT NULL," +
            "    rol TEXT NOT NULL," +
            "    estado TEXT NOT NULL," +
            "    password_hash TEXT NOT NULL," +
            "    fecha_creacion TEXT NOT NULL" +
            ")";

    /**
     * Constructor por defecto que inicializa el esquema de la base de datos.
     */
    public ConexionSQLite() {
        inicializarEsquema();
    }

    /**
     * Obtiene una nueva conexión a la base de datos SQLite.
     *
     * @return una conexión JDBC activa
     * @throws SQLException si ocurre un error de conexión
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private void inicializarEsquema() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException("Error al inicializar el esquema de la base de datos: " + e.getMessage(), e);
        }
    }
}
