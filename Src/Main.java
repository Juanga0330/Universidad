package Src;

import Src.Admision.Estudiante;
import Src.Admision.Matricula;
import Src.Curso.Materia;
import Src.Curso.CargaDeElementos;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        try {
            Estudiante estudiante = Estudiante.nuevoEstudiante();
            Matricula matricula = new Matricula(new Date(), true, 0.0, estudiante);
            
            System.out.println("\n=== INFORMACIÓN DEL ESTUDIANTE ===");
            System.out.println("Nombre: " + estudiante.getName() +
                "\nCódigo: " + estudiante.getCode() +
                "\nEmail: " + estudiante.getEmail() +
                "\nFecha de Ingreso: " + estudiante.getIngreso() +
                "\nFecha de Nacimiento: " + estudiante.getFechaNacimiento() +
                "\nSemestre: " + estudiante.getSemestre());

            List<Materia> materias = null;
            try {
                materias = CargaDeElementos.cargarMaterias("Materias.txt");
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                return;
            }

            System.out.println("\n=== CATÁLOGO DE MATERIAS ===");
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
                    System.out.println("Opción inválida. Intente de nuevo.");
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
            
        } finally {
            sc.close();
        }
    }
}