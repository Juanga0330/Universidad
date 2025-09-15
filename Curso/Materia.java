package Curso;

public class Materia {
    private String nombre;
    private int creditos;  
    private String codigo;

    public Materia(String nombre, int creditos, String codigo) {
        this.nombre = nombre;
        this.creditos = creditos;
        this.codigo = codigo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public int getCreditos() {
        return creditos;
    }
    
    public String getCodigo() {
        return codigo;
    }
}