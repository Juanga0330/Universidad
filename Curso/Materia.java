package Curso;

/**
 * Representa una materia académica del plan de estudios.
 * Contiene la información básica de identificación y
 * los créditos académicos asociados.
 * 
 * @author Juan Pablo Figueroa
 * @version 1.0
 * @since 2025
 */
public class Materia {
    
    /** Nombre de la materia */
    private String nombre;
    
    /** Número de créditos académicos de la materia */
    private int creditos;
    
    /** Código único de identificación de la materia */
    private String codigo;

    /**
     * Constructor para crear una nueva materia.
     * 
     * @param nombre Nombre de la materia
     * @param creditos Número de créditos académicos
     * @param codigo Código único de la materia
     */
    public Materia(String nombre, int creditos, String codigo) {
        this.nombre = nombre;
        this.creditos = creditos;
        this.codigo = codigo;
    }
    
    /**
     * Obtiene el nombre de la materia.
     * 
     * @return Nombre de la materia
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene el número de créditos de la materia.
     * 
     * @return Créditos académicos
     */
    public int getCreditos() {
        return creditos;
    }
    
    /**
     * Obtiene el código de la materia.
     * 
     * @return Código único de identificación
     */
    public String getCodigo() {
        return codigo;
    }
}