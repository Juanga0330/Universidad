package Src.Curso;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de cargar elementos desde archivos, como materias.
 */
public class CargaDeElementos {

    /**
     * Carga las materias desde un archivo de texto.
     * Cada línea del archivo debe tener el formato: codigo,nombre,creditos
     *
     * @param rutaArchivo Ruta del archivo de materias.
     * @return Lista de materias cargadas.
     * @throws IOException Si hay un problema al leer el archivo.
     */
    public static List<Materia> cargarMaterias(String rutaArchivo) throws IOException {
        List<Materia> materias = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int lineaNumero = 0;

            while ((linea = br.readLine()) != null) {
                lineaNumero++;
                linea = linea.trim();

                // Saltar líneas vacías
                if (linea.isEmpty()) continue;

                String[] partes = linea.split(",");

                if (partes.length != 3) {
                    System.err.println("Línea inválida en " + lineaNumero + ": \"" + linea + "\". Se esperaban 3 campos separados por coma.");
                    continue;
                }

                try {
                    String codigo = partes[0].trim();
                    String nombre = partes[1].trim();
                    int creditos = Integer.parseInt(partes[2].trim());

                    Materia materia = new Materia(nombre, creditos, codigo);
                    materias.add(materia);

                } catch (NumberFormatException nfe) {
                    System.err.println("Error en línea " + lineaNumero + ": no se pudo convertir a número -> " + partes[2]);
                } catch (Exception e) {
                    System.err.println("Error procesando línea " + lineaNumero + ": " + e.getMessage());
                }
            }
        }

        return materias;
    }
}