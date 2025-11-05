package Test.Curso;

import Src.Curso.CargaDeElementos;
import Src.Curso.Materia;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

class CargaDeElementosTest {
    
    private String archivoTemporal;
    
    @BeforeEach
    void setUp() {
        archivoTemporal = "test_materias.txt";
    }
    
    @AfterEach
    void tearDown() {
        File file = new File(archivoTemporal);
        if (file.exists()) {
            file.delete();
        }
    }
    
    @Test
    void testCargarMateriasDesdeArchivo() throws IOException {
        crearArchivoTest("1,Cálculo Diferencial,4\n2,Programación I,3\n3,Física I,4");
        List<Materia> materias = CargaDeElementos.cargarMaterias(archivoTemporal);
        assertNotNull(materias);
        assertEquals(3, materias.size());
    }
    
    @Test
    void testContenidoMateriaCargada() throws IOException {
        crearArchivoTest("101,Cálculo,4");
        List<Materia> materias = CargaDeElementos.cargarMaterias(archivoTemporal);
        Materia materia = materias.get(0);
        assertEquals("Cálculo", materia.getNombre());
        assertEquals(4, materia.getCreditos());
        assertEquals("101", materia.getCodigo());
    }
    
    @Test
    void testArchivoVacio() throws IOException {
        crearArchivoTest("");
        List<Materia> materias = CargaDeElementos.cargarMaterias(archivoTemporal);
        assertNotNull(materias);
        assertTrue(materias.isEmpty());
    }
    
    @Test
    void testArchivoConUnaMateria() throws IOException {
        crearArchivoTest("27,Inglés I,2");
        List<Materia> materias = CargaDeElementos.cargarMaterias(archivoTemporal);
        assertEquals(1, materias.size());
        assertEquals("Inglés I", materias.get(0).getNombre());
    }
    
    @Test
    void testArchivoConVariasMaterias() throws IOException {
        crearArchivoTest("1,Materia1,3\n2,Materia2,4\n3,Materia3,2\n4,Materia4,3\n5,Materia5,4");
        List<Materia> materias = CargaDeElementos.cargarMaterias(archivoTemporal);
        assertEquals(5, materias.size());
    }
    
    @Test
    void testArchivoInexistente() {
        assertThrows(IOException.class, () -> {
            CargaDeElementos.cargarMaterias("archivo_no_existe.txt");
        });
    }
    
    @Test
    void testCreditosDiferentes() throws IOException {
        crearArchivoTest("1,Materia2Creditos,2\n2,Materia3Creditos,3\n3,Materia4Creditos,4");
        List<Materia> materias = CargaDeElementos.cargarMaterias(archivoTemporal);
        assertEquals(2, materias.get(0).getCreditos());
        assertEquals(3, materias.get(1).getCreditos());
        assertEquals(4, materias.get(2).getCreditos());
    }
    
 @Test
void testNombresConEspacios() throws IOException {  // ← AGREGAR ESTO
    crearArchivoTest("1,Cálculo Diferencial,4");
    List<Materia> materias = CargaDeElementos.cargarMaterias(archivoTemporal);
    assertEquals("Cálculo Diferencial", materias.get(0).getNombre());
}

@Test
void testCodigosUnicos() throws IOException {  // ← AGREGAR ESTO
    crearArchivoTest("COD001,Materia1,3\nCOD002,Materia2,3\nCOD003,Materia3,3");
    List<Materia> materias = CargaDeElementos.cargarMaterias(archivoTemporal);
    assertEquals("COD001", materias.get(0).getCodigo());
    assertEquals("COD002", materias.get(1).getCodigo());
    assertEquals("COD003", materias.get(2).getCodigo());
}

@Test
void testOrdenDeCarga() throws IOException {  // ← AGREGAR ESTO
    crearArchivoTest("P01,Primera,3\nP02,Segunda,3\nP03,Tercera,3");
    List<Materia> materias = CargaDeElementos.cargarMaterias(archivoTemporal);
    assertEquals("Primera", materias.get(0).getNombre());
    assertEquals("Segunda", materias.get(1).getNombre());
    assertEquals("Tercera", materias.get(2).getNombre());
}

@Test
void testLineasVaciasIgnoradas() throws IOException {  // ← AGREGAR ESTO
    crearArchivoTest("1,Materia1,3\n\n2,Materia2,4\n\n3,Materia3,2");
    List<Materia> materias = CargaDeElementos.cargarMaterias(archivoTemporal);
    assertEquals(3, materias.size());
}
    
    private void crearArchivoTest(String contenido) throws IOException {
        FileWriter writer = new FileWriter(archivoTemporal);
        writer.write(contenido);
        writer.close();
    }
}