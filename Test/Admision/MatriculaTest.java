package Test.Admision;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
import Src.Curso.Materia;
import Src.Admision.Matricula;
import Src.Admision.Estudiante;

/**
 * Clase de pruebas unitarias para la clase Matricula.
 */
class MatriculaTest {
    
    private Matricula matricula;
    private Estudiante estudiante;
    
    @BeforeEach
    void setUp() {
        estudiante = new Estudiante(
            "Juan Perez",
            "202510123",
            "juan.perez@uptc.edu.co",
            "01/02/2025",
            "15/03/2005",
            1
        );
        matricula = new Matricula(new Date(), true, 0.0, estudiante);
    }
    
    @Test
    void testAgregarMateria() {
        Materia materia = new Materia("Cálculo", 4, "MAT101");
        matricula.agregarMateria(materia);
        
        assertEquals(1, matricula.getMaterias().size());
        assertTrue(matricula.getMaterias().contains(materia));
    }
    
    @Test
    void testAgregarVariasMaterias() {
        Materia mat1 = new Materia("Cálculo", 4, "MAT101");
        Materia mat2 = new Materia("Física", 4, "FIS101");
        Materia mat3 = new Materia("Programación", 3, "PRG101");
        
        matricula.agregarMateria(mat1);
        matricula.agregarMateria(mat2);
        matricula.agregarMateria(mat3);
        
        assertEquals(3, matricula.getMaterias().size());
    }
    
    @Test
    void testGetMateriasVacia() {
        assertNotNull(matricula.getMaterias());
        assertEquals(0, matricula.getMaterias().size());
        assertTrue(matricula.getMaterias().isEmpty());
    }
    
    @Test
    void testGetMaterias() {
        Materia materia = new Materia("Inglés", 2, "ING101");
        matricula.agregarMateria(materia);
        
        assertNotNull(matricula.getMaterias());
        assertFalse(matricula.getMaterias().isEmpty());
        assertEquals(materia, matricula.getMaterias().get(0));
    }
    
    @Test
    void testOrdenDeInsercion() {
        Materia mat1 = new Materia("Primera", 3, "M01");
        Materia mat2 = new Materia("Segunda", 3, "M02");
        Materia mat3 = new Materia("Tercera", 3, "M03");
        
        matricula.agregarMateria(mat1);
        matricula.agregarMateria(mat2);
        matricula.agregarMateria(mat3);
        
        assertEquals(mat1, matricula.getMaterias().get(0));
        assertEquals(mat2, matricula.getMaterias().get(1));
        assertEquals(mat3, matricula.getMaterias().get(2));
    }
    
    @Test
    void testConstructorConEstudiante() {
        Date fecha = new Date();
        Matricula mat = new Matricula(fecha, true, 0.0, estudiante);
        
        assertNotNull(mat);
        assertNotNull(mat.getMaterias());
        assertTrue(mat.getMaterias().isEmpty());
    }
    
    @Test
    void testAgregarMateriaDuplicada() {
        Materia materia = new Materia("Test", 3, "TST101");
        
        matricula.agregarMateria(materia);
        matricula.agregarMateria(materia);
        
        assertEquals(2, matricula.getMaterias().size());
    }
    
    @Test
    void testMatriculaConMaximoMaterias() {
        for (int i = 1; i <= 8; i++) {
            matricula.agregarMateria(new Materia("Materia " + i, 3, "M" + i));
        }
        
        assertEquals(8, matricula.getMaterias().size());
    }
    
    @Test
    void testListaMateriasNoNula() {
        assertNotNull(matricula.getMaterias());
    }
}