🧪 Pruebas Unitarias Completas
📄 Test/Admision/EstudianteTest.java
javapackage Admision;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para la clase Estudiante.
 */
class EstudianteTest {
    
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

📄 Test/Admision/MatriculaTest.java
javapackage Admision;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;
import Curso.Materia;

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

📄 Test/Curso/CargaDeElementosTest.java
javapackage Curso;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Clase de pruebas unitarias para la clase CargaDeElementos.
 */
class CargaDeElementosTest {
    
    private String archivoTemporal;
    
    @BeforeEach
    void setUp() throws IOException {
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
        crearArchivoTest(
            "Cálculo Diferencial,4,MAT101\n" +
            "Programación I,3,PRG101\n" +
            "Física I,4,FIS101"
        );
        
        List<Materia> materias = CargaDeElementos.cargarMaterias(archivoTemporal);
        
        assertNotNull(materias);
        assertEquals(3, materias.size());
    }
    
    @Test
    void testContenidoMateriaCargada() throws IOException {
        crearArchivoTest("Cálculo,4,MAT101");
        
        List<Materia> materias = CargaDeElementos.cargarMaterias(archivoTemporal);
        
        Materia materia = materias.get(0);
        assertEquals("Cálculo", materia.getNombre());
        assertEquals(4, materia.getCreditos());
        assertEquals("MAT101", materia.getCodigo());
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
        crearArchivoTest("Inglés I,2,ING101");
        
        List<Materia> materias = CargaDeElementos.cargarMaterias(archivoTemporal);
        
        assertEquals(1, materias.size());
        assertEquals("Inglés I", materias.get(0).getNombre());
    }
    
    @Test
    void testArchivoConVariasMaterias() throws IOException {
        crearArchivoTest(
            "Materia1,3,M01\n" +
            "Materia2,4,M02\n" +
            "Materia3,2,M03\n" +
            "Materia4,3,M04\n" +
            "Materia5,4,M05"
        );
        
        List<Materia> materias = CargaDeElementos.cargarMaterias(archivoTemporal);
        
        assertEquals(5, materias.size());
    }
    
    @Test
    void testArchivoInexistente() {
        List<Materia> materias = CargaDeElementos.cargarMaterias("archivo_no_existe.txt");
        
        assertNotNull(materias);
        assertTrue(materias.isEmpty());
    }
    
    @Test
    void testCreditosDiferentes() throws IOException {
        crearArchivoTest(
            "Materia2Creditos,2,M2\n" +
            "Materia3Creditos,3,M3\n" +
            "Materia4Creditos,4,M4"
        );
        
        List<Materia> materias = CargaDeElementos.cargarMaterias(archivoTemporal);
        
        assertEquals(2, materias.get(0).getCreditos());
        assertEquals(3, materias.get(1).getCreditos());
        assertEquals(4, materias.get(2).getCreditos());
    }
    
    @Test
    void testNombresConEspacios() throws IOException {
        crearArchivoTest("Cálculo Diferencial,4,MAT101");
        
        List<Materia> materias = CargaDeElementos.cargarMaterias(archivoTemporal);
        
        assertEquals("Cálculo Diferencial", materias.get(0).getNombre());
    }
    
    @Test
    void testCodigosUnicos() throws IOException {
        crearArchivoTest(
            "Materia1,3,COD001\n" +
            "Materia2,3,COD002\n" +
            "Materia3,3,COD003"
        );
        
        List<Materia> materias = CargaDeElementos.cargarMaterias(archivoTemporal);
        
        assertEquals("COD001", materias.get(0).getCodigo());
        assertEquals("COD002", materias.get(1).getCodigo());
        assertEquals("COD003", materias.get(2).getCodigo());
    }
    
    @Test
    void testOrdenDeCarga() throws IOException {
        crearArchivoTest(
            "Primera,3,P01\n" +
            "Segunda,3,P02\n" +
            "Tercera,3,P03"
        );
        
        List<Materia> materias = CargaDeElementos.cargarMaterias(archivoTemporal);
        
        assertEquals("Primera", materias.get(0).getNombre());
        assertEquals("Segunda", materias.get(1).getNombre());
        assertEquals("Tercera", materias.get(2).getNombre());
    }
    
    private void crearArchivoTest(String contenido) throws IOException {
        FileWriter writer = new FileWriter(archivoTemporal);
        writer.write(contenido);
        writer.close();
    }
}