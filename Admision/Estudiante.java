package Admision;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.*;

public class Estudiante {

    private String name;
    private String code;
    private String email;
    private String ingreso;
    private String fechaNacimiento;
    private int semestre;

    public Estudiante(String name, String code, String email, String ingreso, String fechaNacimiento, int semestre) {
        this.name = name;
        this.code = code;
        this.email = email;
        this.ingreso = ingreso;
        this.fechaNacimiento = fechaNacimiento;
        this.semestre = semestre;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getEmail() {
        return email;
    }

    public String getIngreso() {
        return ingreso;
    }
    
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public int getSemestre() {
        return semestre;
    }

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