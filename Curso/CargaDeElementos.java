package Curso;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase utilitaria para cargar elementos académicos desde archivos.
 * Proporciona métodos para leer y parsear archivos de datos
 * y convertirlos en objetos del sistema.
 * 
 * @author Andres Felipe Corredor
 * @version 1.0
 * @since 2025
 */
public class CargaDeElementos {

    /**
     * Carga materias desde un archivo de texto.
     * El archivo debe tener formato CSV con la estructura:
     * nombre,creditos,codigo
     * 
     * @param pathfile Ruta completa del archivo de materias
     * @return Lista de materias cargadas desde el archivo
     * @throws IOException Si ocurre un error al leer el archivo
     */
    public static List<Materia> cargarMaterias(String pathfile) {
        List<Materia> materias = new ArrayList<>();
        try (BufferedReader bufferReader = new BufferedReader(new FileReader(pathfile))) {
            String linea;
            while ((linea = bufferReader.readLine()) != null) {
                String[] valores = linea.split(",");
                String nombre = valores[0];
                int creditos = Integer.parseInt(valores[1]);
                String codigo = valores[2];
                materias.add(new Materia(nombre, creditos, codigo));
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return materias;
    }
}