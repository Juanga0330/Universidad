package Curso;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CargaDeElementos {

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