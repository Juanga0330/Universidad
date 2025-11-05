package Test.Admision;
import Src.Admision.Estudiante;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para la clase Estudiante.
 */
public class EstudianteTest {
    
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
    }
    
    @Test
    void testGetName() {
        assertEquals("Juan Perez", estudiante.getName());
    }
    
    @Test
    void testGetCode() {
        assertEquals("202510123", estudiante.getCode());
    }
    
    @Test
    void testGetEmail() {
        assertEquals("juan.perez@uptc.edu.co", estudiante.getEmail());
    }
    
    @Test
    void testGetIngreso() {
        assertEquals("01/02/2025", estudiante.getIngreso());
    }
    
    @Test
    void testGetFechaNacimiento() {
        assertEquals("15/03/2005", estudiante.getFechaNacimiento());
    }
    
    @Test
    void testGetSemestre() {
        assertEquals(1, estudiante.getSemestre());
    }
    
    @Test
    void testConstructorConDatosValidos() {
        Estudiante est = new Estudiante(
            "Maria Lopez",
            "202520456",
            "maria.lopez@uptc.edu.co",
            "10/08/2025",
            "20/05/2004",
            2
        );
        
        assertNotNull(est);
        assertEquals("Maria Lopez", est.getName());
        assertEquals("202520456", est.getCode());
        assertEquals("maria.lopez@uptc.edu.co", est.getEmail());
        assertEquals("10/08/2025", est.getIngreso());
        assertEquals("20/05/2004", est.getFechaNacimiento());
        assertEquals(2, est.getSemestre());
    }
    
    @Test
    void testNombreConEspacios() {
        Estudiante est = new Estudiante(
            "Ana Maria Rodriguez Gomez",
            "202510789",
            "ana.maria.rodriguez.gomez@uptc.edu.co",
            "01/02/2025",
            "10/10/2003",
            1
        );
        
        assertEquals("Ana Maria Rodriguez Gomez", est.getName());
        assertEquals("ana.maria.rodriguez.gomez@uptc.edu.co", est.getEmail());
    }
    
    @Test
    void testCodigoUnico() {
        Estudiante est1 = new Estudiante("Pedro", "202510111", "pedro@uptc.edu.co", "01/02/2025", "01/01/2000", 1);
        Estudiante est2 = new Estudiante("Luis", "202510222", "luis@uptc.edu.co", "01/02/2025", "01/01/2001", 1);
        
        assertNotEquals(est1.getCode(), est2.getCode());
    }
    
    @Test
    void testSemestrePrimero() {
        Estudiante est = new Estudiante("Test", "202510100", "test@uptc.edu.co", "01/02/2025", "01/01/2000", 1);
        assertEquals(1, est.getSemestre());
    }
    
    @Test
    void testSemestreSegundo() {
        Estudiante est = new Estudiante("Test", "202520100", "test@uptc.edu.co", "01/08/2025", "01/01/2000", 2);
        assertEquals(2, est.getSemestre());
    }
    
    @Test
    void testFormatoEmail() {
        String email = estudiante.getEmail();
        assertTrue(email.contains("@uptc.edu.co"));
        assertTrue(email.endsWith("@uptc.edu.co"));
    }
}