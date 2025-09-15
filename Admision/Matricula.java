package Admision;
import java.util.Date; 
import java.util.ArrayList;
import Curso.Materia; 

public class Matricula {
    private ArrayList<Materia> materias = new ArrayList<>();
    private Date dateMatricula;
    private Boolean estado;
    private String semestre;
    private double costo;
    private Estudiante estudiante;
    private String inscripcionMaterias;

    public Matricula(Date dateMatricula, boolean estado, double costo, Estudiante estudiante) {
        this.dateMatricula = dateMatricula;
        this.estado = estado;
        this.costo = costo;
        this.estudiante = estudiante;
    }
    
    public void agregarMateria(Materia materia) {
        materias.add(materia);
    }
    
    public ArrayList<Materia> getMaterias() {
        return materias;
    }
}