package Admision;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.*;

/**
 * Representa un estudiante en el sistema académico.
 * Contiene la información personal y académica del estudiante,
 * incluyendo datos de identificación y matrícula.
 * 
 * @author Andres Felipe Corredor
 * @version 1.0
 * @since 2025
 */
public class Estudiante {

    /** Nombre completo del estudiante */
    private String name;
    
    /** Código único del estudiante generado automáticamente */
    private String code;
    
    /** Correo electrónico institucional del estudiante */
    private String email;
    
    /** Fecha de ingreso del estudiante a la universidad */
    private String ingreso;
    
    /** Fecha de nacimiento del estudiante */
    private String fechaNacimiento;
    
    /** Semestre actual del estudiante (1 o 2) */
    private int semestre;

    /**
     * Constructor para crear un nuevo estudiante.
     * 
     * @param name Nombre completo del estudiante
     * @param code Código único del estudiante
     * @param email Correo electrónico institucional
     * @param ingreso Fecha de ingreso a la universidad
     * @param fechaNacimiento Fecha de nacimiento
     * @param semestre Semestre académico actual
     */
    public Estudiante(String name, String code, String email, String ingreso, String fechaNacimiento, int semestre) {
        this.name = name;
        this.code = code;
        this.email = email;
        this.ingreso = ingreso;
        this.fechaNacimiento = fechaNacimiento;
        this.semestre = semestre;
    }

    /**
     * Obtiene el nombre del estudiante.
     * 
     * @return Nombre completo del estudiante
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene el código único del estudiante.
     * 
     * @return Código del estudiante
     */
    public String getCode() {
        return code;
    }

    /**
     * Obtiene el correo electrónico institucional.
     * 
     * @return Email del estudiante
     */
    public String getEmail() {
        return email;
    }

    /**
     * Obtiene la fecha de ingreso a la universidad.
     * 
     * @return Fecha de ingreso en formato DD/MM/YYYY
     */
    public String getIngreso() {
        return ingreso;
    }
    
    /**
     * Obtiene la fecha de nacimiento del estudiante.
     * 
     * @return Fecha de nacimiento en formato DD/MM/YYYY
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    /**
     * Obtiene el semestre académico actual.
     * 
     * @return Número de semestre (1 o 2)
     */
    public int getSemestre() {
        return semestre;
    }

    /**
     * Crea un nuevo estudiante mediante entrada de datos por consola.
     * Genera automáticamente:
     * <ul>
     *   <li>Código único basado en año, semestre y número aleatorio</li>
     *   <li>Correo electrónico institucional</li>
     *   <li>Fecha de ingreso actual</li>
     *   <li>Semestre según el mes actual</li>
     * </ul>
     * 
     * @return Nuevo objeto Estudiante con los datos ingresados
     */
    public static Estudiante nuevoEstudiante() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nombre del aspirante:");
        String name = sc.nextLine();
        LocalDateTime hoy = LocalDateTime.now();
        int semestre = (hoy.getMonthValue() <= 6) ? 1 : 2;
        String año = String.valueOf(hoy.getYear());
        String sem = String.valueOf(semestre);
        String aleatorio = String.valueOf((int)((Math.random()*900)+100));
        String code = año + sem+"0"+ aleatorio;
        String email = name.toLowerCase().replace(" ", ".") + "@uptc.edu.co";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String ingreso = hoy.format(formato);
        System.out.println("Ingrese la fecha de nacimiento del aspirante: DD/MM/AAAA");
        String fechaNacimiento = sc.nextLine();
        return new Estudiante(name, code, email, ingreso, fechaNacimiento, semestre);
    }
}