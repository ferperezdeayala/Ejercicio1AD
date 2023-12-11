package cesur.examen.core.common;

import cesur.examen.core.worker.Worker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
public class FileUtils {

    public static void toCSV(String fileName, List<Worker> workers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Worker worker : workers) {
                String line = String.format("%s, %s, %s\n", worker.getName(), worker.getDni(), worker.getFrom());
                writer.write(line);
            }
            // Logging the successful write
            // log.info("Workers data exported to CSV successfully.");

        } catch (IOException e) {
            // Logging the exception
            // log.severe("Error exporting workers data to CSV: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

