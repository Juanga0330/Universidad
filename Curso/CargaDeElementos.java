package Curso;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de cargar elementos desde archivos, como materias.
 */
public class CargaDeElementos {

    /**
     * Carga las materias desde un archivo de texto.
     * Cada línea del archivo debe tener el formato: nombre,creditos,codigo
     *
     * @param rutaArchivo Ruta del archivo de materias.
     * @return Lista de materias cargadas.
     */
    public static List<Materia> cargarMaterias(String rutaArchivo) {
        List<Materia> materias = new ArrayList<>();

        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return materias; // Si el archivo no existe, retornar lista vacía
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int lineaNumero = 0;

            while ((linea = br.readLine()) != null) {
                lineaNumero++;
                linea = linea.trim();

                // Saltar líneas vacías
                if (linea.isEmpty()) continue;

                String[] partes = linea.split(",");

                if (partes.length != 3) {
                    System.err.println("Línea inválida (" + lineaNumero + "): " + linea);
                    continue;
                }

                try {
                    String nombre = partes[0].trim();
                    int creditos = Integer.parseInt(partes[1].trim());
                    String codigo = partes[2].trim();

                    materias.add(new Materia(nombre, creditos, codigo));

                } catch (NumberFormatException nfe) {
                    System.err.println("Error en línea " + lineaNumero + ": crédito no numérico -> " + partes[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return materias;
    }
}