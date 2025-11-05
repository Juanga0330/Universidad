package Src.Admision;
import java.util.Date; 
import java.util.ArrayList;
import Src.Curso.Materia;


/**
 * Representa la matrícula académica de un estudiante.
 * Gestiona las materias inscritas, el estado de la matrícula
 * y la información asociada al proceso de inscripción.
 * 
 * @author Kevin Santiago Costo
 * @version 1.0
 * @since 2025
 */
public class Matricula {
    
    /** Lista de materias inscritas en la matrícula */
    private ArrayList<Materia> materias = new ArrayList<>();
    
    /** Fecha en que se realizó la matrícula */
    private Date dateMatricula;
    
    /** Estado de la matrícula (activa/inactiva) */
    private Boolean estado;
    
    /** Semestre académico de la matrícula */
    private String semestre;
    
    /** Costo total de la matrícula */
    private double costo;
    
    /** Estudiante asociado a esta matrícula */
    private Estudiante estudiante;
    
    /** Registro de materias inscritas */
    private String inscripcionMaterias;

    /**
     * Constructor para crear una nueva matrícula.
     * 
     * @param dateMatricula Fecha de realización de la matrícula
     * @param estado Estado inicial de la matrícula
     * @param costo Costo inicial de la matrícula
     * @param estudiante Estudiante que realiza la matrícula
     */
    public Matricula(Date dateMatricula, boolean estado, double costo, Estudiante estudiante) {
        this.dateMatricula = dateMatricula;
        this.estado = estado;
        this.costo = costo;
        this.estudiante = estudiante;
    }
    
    /**
     * Agrega una materia a la lista de materias inscritas.
     * 
     * @param materia Materia a agregar a la matrícula
     */
    public void agregarMateria(Materia materia) {
        materias.add(materia);
    }
    
    /**
     * Obtiene la lista de materias inscritas en la matrícula.
     * 
     * @return ArrayList con todas las materias inscritas
     */
    public ArrayList<Materia> getMaterias() {
        return materias;
    }
}