package cesur.examen.core.worker;

import cesur.examen.core.common.DAO;
import cesur.examen.core.common.JDBCUtils;
import lombok.extern.java.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

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
public class WorkerDAO implements DAO<Worker> {

    private final String QUERY_ORDER_BY = "SELECT * FROM trabajador ORDER BY desde";
    private final String QUERY_BY_DNI = "SELECT * FROM trabajador WHERE dni=?";
    private final String UPDATE_BY_ID = "UPDATE trabajador SET nombre=?, dni=?, desde=? WHERE id=?";

    @Override
    public Worker save(Worker worker) {
        // Implementar lógica para guardar un nuevo trabajador
        return null;
    }

    @Override
    public Worker update(Worker worker) {
        Worker out = null;

        try (PreparedStatement st = JDBCUtils.getConn().prepareStatement(UPDATE_BY_ID)) {
            // Log SQL update statement
            log.log(Level.INFO, "UPDATE SQL: " + UPDATE_BY_ID);

            st.setString(1, worker.getName());
            st.setString(2, worker.getDni());
            st.setDate(3, JDBCUtils.dateUtilToSQL(new Date(System.currentTimeMillis())));
            st.setLong(4, worker.getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                out = worker;
                // Log success
                log.log(Level.INFO, "Worker updated successfully.");
            } else {
                // Log failure
                log.log(Level.WARNING, "Failed to update worker or worker does not exist.");
            }

        } catch (SQLException e) {
            // Log exception
            log.log(Level.SEVERE, "Error in update(): " + e.getMessage());
            throw new RuntimeException(e);
        }

        return out;
    }

    @Override
    public boolean remove(Worker worker) {
        return false;
    }

    @Override
    public Worker get(Long id) {
        return null;
    }

    public Worker getWorkerByDNI(String dni) {
        Worker out = null;

        try (PreparedStatement st = JDBCUtils.getConn().prepareStatement(QUERY_BY_DNI)) {
            st.setString(1, dni);
            ResultSet rs = st.executeQuery();

            // Log SQL query
            log.log(Level.INFO, "QUERY SQL: " + QUERY_BY_DNI);

            if (rs.next()) {
                Worker w = new Worker();
                w.setId(rs.getLong("id"));
                w.setName(rs.getString("nombre"));
                w.setDni(rs.getString("dni"));
                w.setFrom(rs.getDate("desde"));
                out = w;
            }
        } catch (SQLException e) {
            // Log exception
            log.log(Level.SEVERE, "Error in getWorkerByDNI(): " + e.getMessage());
            throw new RuntimeException(e);
        }
        return out;
    }

    @Override
    public List<Worker> getAll() {
        return null;
    }

    public List<Worker> getAllOrderByFrom() {
        ArrayList<Worker> out = new ArrayList<>(0);

        try (Statement st = JDBCUtils.getConn().createStatement()) {
            ResultSet rs = st.executeQuery(QUERY_ORDER_BY);

            // Log SQL query
            log.log(Level.INFO, "QUERY SQL: " + QUERY_ORDER_BY);

            while (rs.next()) {
                Worker w = new Worker();
                w.setId(rs.getLong("id"));
                w.setName(rs.getString("nombre"));
                w.setDni(rs.getString("dni"));
                w.setFrom(rs.getDate("desde"));
                out.add(w);
            }
        } catch (SQLException e) {
            // Log exception
            log.log(Level.SEVERE, "Error in getAllOrderByFrom(): " + e.getMessage());
            throw new RuntimeException(e);
        }

        return out;
    }
}


