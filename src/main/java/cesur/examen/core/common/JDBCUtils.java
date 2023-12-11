package cesur.examen.core.common;

import lombok.extern.java.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * EXAMEN DE ACCESO A DATOS
 * Diciembre 2023
 *
 * Nombre del alumno: Fernando Perez de Ayala
 * Fecha: 11/12/2023
 *
 * No se permite escribir en consola desde las clases DAO, Service y Utils usando System.out.
 * En su lugar, usa log.info(), log.warning() y log.severe() para mostrar información interna
 * o para seguir la traza de ejecución.
 */
@Log
public class JDBCUtils {

    private static final Connection conn;

    static {
        try {
            Properties properties = new Properties();
            properties.load(JDBCUtils.class.getClassLoader().getResourceAsStream("db.properties"));

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            conn = DriverManager.getConnection(url, user, password);

            if (conn == null) {
                log.info("JDBCUtils Not implemented yet!");
            } else {
                log.info("Successfully connected!");
            }
        } catch (Exception ex) {
            log.severe("Error connecting to database: " + ex.getMessage());
            throw new RuntimeException("Error connecting to database", ex);
        }
    }

    public static Connection getConn() {
        return conn;
    }

    public static java.sql.Date dateUtilToSQL(java.util.Date d) {
        return new java.sql.Date(d.getTime());
    }
}

