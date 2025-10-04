import Admision.Estudiante;
import Admision.Matricula;
import Curso.Materia;
import Curso.CargaDeElementos;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

/**
 * Clase principal del sistema de matrícula académica.
 * Proporciona una interfaz de consola para que los estudiantes puedan
 * inscribir materias y gestionar su matrícula.
 * 
 * @author Juan Pablo Figueroa
 * @author Andres Felipe Corredor
 * @author Kevin Santiago Costo
 * @version 1.0
 * @since 2025
 */
public class Main {
    
    /**
     * Método principal que ejecuta el sistema de matrícula.
     * Permite al estudiante:
     * <ul>
     *   <li>Registrar sus datos personales</li>
     *   <li>Visualizar el catálogo de materias disponibles</li>
     *   <li>Seleccionar e inscribir materias</li>
     *   <li>Ver el resumen y costo total de la matrícula</li>
     * </ul>
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Estudiante estudiante = Estudiante.nuevoEstudiante();
        Matricula matricula = new Matricula(new Date(), true, 0.0, estudiante);
        
        System.out.println("\n=== INFORMACIÓN DEL ESTUDIANTE ===");
        System.out.println("Nombre: " + estudiante.getName() +
            "\nCódigo: " + estudiante.getCode() +
            "\nEmail: " + estudiante.getEmail() +
            "\nFecha de Ingreso: " + estudiante.getIngreso() +
            "\nFecha de Nacimiento: " + estudiante.getFechaNacimiento() +
            "\nSemestre: " + estudiante.getSemestre());

        List<Materia> materias = CargaDeElementos.cargarMaterias("C:\\Users\\Juanp\\Documents\\Universidad\\Materias.txt");

        System.out.println("\n=== CATÁLOGO DE MATERIAS ===");
        System.out.println("Materias.txt");
        for (int i = 0; i < materias.size(); i++) {
            Materia m = materias.get(i);
            System.out.println((i + 1) + ". " + m.getNombre() + " - Créditos: " + m.getCreditos() + " - Código: " + m.getCodigo());
        }
        System.out.println("0. Finalizar inscripción");

        int opcion;
        do {
            System.out.print("\nSeleccione una materia (0 para finalizar): ");
            opcion = sc.nextInt();

            if (opcion == 0) {
                System.out.println("\nInscripción finalizada.");
            } else if (opcion >= 1 && opcion <= materias.size()) {
                Materia seleccionada = materias.get(opcion - 1);
                matricula.agregarMateria(seleccionada);
                System.out.println(seleccionada.getNombre() + " agregada a la matrícula.");
            } else {
                System.out.println(" Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 0);

        System.out.println("\n=== MATERIAS INSCRITAS ===");
        if (matricula.getMaterias().isEmpty()) {
            System.out.println("No se inscribieron materias.");
        } else {
            for (Materia m : matricula.getMaterias()) {
                System.out.println("• " + m.getNombre() + " - Créditos: " + m.getCreditos() + " - Código: " + m.getCodigo());
            }
        }

        double costoTotal = 0.0;
        for (Materia m : matricula.getMaterias()) {
            costoTotal += m.getCreditos() * 50000;
        }
        
        System.out.println("\n=== RESUMEN DE MATRÍCULA ===");
        System.out.println("Total de materias: " + matricula.getMaterias().size());
        System.out.println("Total a pagar: $" + String.format("%.0f", costoTotal));
        System.out.println("\n¡Gracias por usar el sistema de matrícula!");
        
        sc.close();
    }
}