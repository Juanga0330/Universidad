package Test.Curso;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import Src.Curso.Materia;
public class MateriaTest {
    
    private Materia materia;
    
    @BeforeEach
    void setUp() {
        materia = new Materia("Calculo Diferencial", 4, "MAT101");
    }
    
    @Test
    void testConstructor() {
        assertNotNull(materia);
    }
    
    @Test
    void testGetNombre() {
        assertEquals("Calculo Diferencial", materia.getNombre());
    }
    
    @Test
    void testGetCreditos() {
        assertEquals(4, materia.getCreditos());
    }
    
    @Test
    void testGetCodigo() {
        assertEquals("MAT101", materia.getCodigo());
    }
    
    @Test
    void testCreditosPositivos() {
        assertTrue(materia.getCreditos() > 0);
    }
    
    @Test
    void testCodigoNoVacio() {
        assertFalse(materia.getCodigo().isEmpty());
    }
    
    @Test
    void testNombreNoVacio() {
        assertFalse(materia.getNombre().isEmpty());
    }
    
    @Test
    void testMateriaDiferente() {
        Materia materia2 = new Materia("Fisica I", 3, "FIS101");
        assertNotEquals(materia.getCodigo(), materia2.getCodigo());
    }
    
    @Test
    void testCreditosRangoValido() {
        assertTrue(materia.getCreditos() >= 1 && materia.getCreditos() <= 5);
    }
}