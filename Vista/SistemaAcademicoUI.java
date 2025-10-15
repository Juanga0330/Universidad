package Vista;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;

import Admision.Matricula;
import Curso.Materia;
import Curso.CargaDeElementos;
import Admision.Estudiante;

/**
 * Interfaz gráfica del sistema de matrícula académica.
 * Proporciona una ventana interactiva para que los estudiantes
 * puedan visualizar materias disponibles y gestionar su inscripción.
 * Utiliza Swing para crear una interfaz amigable con el usuario.
 * 
 * @author Kevin Santiago Costo
 * @version 1.0
 * @since 2025
 */
public class SistemaAcademicoUI extends JFrame {

    /** Lista visual de materias disponibles */
    private JList<String> materiaList;
    
    /** Modelo de datos para la lista de materias */
    private DefaultListModel<String> materiaModel;
    
    /** Área de texto para mostrar materias inscritas */
    private JTextArea matriculaArea;
    
    /** Etiqueta para mostrar el costo total */
    private JLabel totalLabel;
    
    /** Botón para finalizar el proceso de matrícula */
    private JButton btnTerminarMatricula;
    
    /** Objeto matrícula del estudiante actual */
    private Matricula matricula;
    
    /** Lista de materias disponibles */
    private List<Materia> materias;
    
    /** Estudiante que está realizando la matrícula */
    private Estudiante estudiante;
    
    /** Límite máximo de materias que se pueden inscribir */
    private static final int MAX_MATERIAS = 8;
    
    /** Límite máximo de créditos permitidos */
    private static final int MAX_CREDITOS = 24;

    /**
     * Constructor de la interfaz gráfica.
     * Inicializa todos los componentes visuales, carga las materias
     * desde archivo, crea un nuevo estudiante y configura los eventos.
     * 
     * @throws FileNotFoundException Si no se encuentra el archivo de materias
     * @throws IllegalStateException Si no se pueden cargar las materias
     * @throws NullPointerException Si el estudiante no se puede crear
     */
    public SistemaAcademicoUI() throws FileNotFoundException, IllegalStateException, NullPointerException {
        try {
            setTitle("Sistema de Matrícula - Universidad");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);

            String rutaArchivo = "C:\\Users\\Juanp\\Documents\\Universidad\\Materias.txt";
            
            // Verificar que el archivo existe
            File archivo = new File(rutaArchivo);
            if (!archivo.exists()) {
                throw new FileNotFoundException("No se encontró el archivo de materias en: " + rutaArchivo);
            }
            
            if (!archivo.canRead()) {
                throw new SecurityException("No se tienen permisos de lectura para el archivo: " + rutaArchivo);
            }

            materias = CargaDeElementos.cargarMaterias(rutaArchivo);
            
            if (materias == null) {
                throw new IllegalStateException("Error al cargar las materias: la lista es nula");
            }
            
            if (materias.isEmpty()) {
                throw new IllegalStateException("El archivo de materias está vacío o no tiene datos válidos");
            }

            estudiante = Estudiante.nuevoEstudiante();
            
            if (estudiante == null) {
                throw new NullPointerException("No se pudo crear el estudiante");
            }
            
            if (estudiante.getName() == null || estudiante.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre del estudiante no puede estar vacío");
            }
            
            matricula = new Matricula(new java.util.Date(), true, 0.0, estudiante);

            if (matricula == null) {
                throw new IllegalStateException("No se pudo crear la matrícula");
            }

            materiaModel = new DefaultListModel<>();
            for (Materia materia : materias) {
                if (materia == null) {
                    System.err.println("Advertencia: Se encontró una materia nula en la lista");
                    continue;
                }
                
                try {
                    materiaModel.addElement(
                        materia.getCodigo() + " - " +
                        materia.getNombre() + " - Créditos: " +
                        materia.getCreditos()
                    );
                } catch (NullPointerException e) {
                    System.err.println("Error al agregar materia al modelo: " + e.getMessage());
                }
            }

            materiaList = new JList<>(materiaModel);
            JScrollPane scrollCatalogo = new JScrollPane(materiaList);

            JButton btnAgregar = new JButton("Agregar a matrícula");
            btnAgregar.addActionListener(e -> {
                try {
                    agregarMateria();
                } catch (Exception ex) {
                    mostrarError("Error al agregar materia", ex);
                }
            });

            JPanel panelIzquierdo = new JPanel(new BorderLayout());
            panelIzquierdo.add(new JLabel("Catálogo de Materias"), BorderLayout.NORTH);
            panelIzquierdo.add(scrollCatalogo, BorderLayout.CENTER);
            panelIzquierdo.add(btnAgregar, BorderLayout.SOUTH);

            matriculaArea = new JTextArea();
            matriculaArea.setEditable(false);
            JScrollPane scrollMatricula = new JScrollPane(matriculaArea);

            totalLabel = new JLabel("Total: $0.0");

            btnTerminarMatricula = new JButton("Terminar matrícula");
            btnTerminarMatricula.addActionListener(e -> {
                try {
                    terminarMatricula();
                } catch (Exception ex) {
                    mostrarError("Error al terminar matrícula", ex);
                }
            });

            JPanel panelInferior = new JPanel(new BorderLayout());
            panelInferior.add(totalLabel, BorderLayout.CENTER);
            panelInferior.add(btnTerminarMatricula, BorderLayout.EAST);

            JPanel panelDerecho = new JPanel(new BorderLayout());
            panelDerecho.add(scrollMatricula, BorderLayout.CENTER);
            panelDerecho.add(panelInferior, BorderLayout.SOUTH);

            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
            splitPane.setDividerLocation(400);

            add(splitPane, BorderLayout.CENTER);
            
        } catch (FileNotFoundException e) {
            mostrarError("Archivo no encontrado", e);
            throw e;
        } catch (IllegalStateException e) {
            mostrarError("Error de estado", e);
            throw e;
        } catch (NullPointerException e) {
            mostrarError("Error: valor nulo", e);
            throw e;
        } catch (Exception e) {
            mostrarError("Error inesperado al inicializar la interfaz", e);
            throw new RuntimeException("Error al inicializar la aplicación", e);
        }
    }

    /**
     * Agrega una materia seleccionada a la matrícula del estudiante.
     * Valida que no se exceda el límite de materias ni de créditos,
     * y que no se agreguen materias duplicadas.
     * 
     * @throws IndexOutOfBoundsException Si el índice seleccionado es inválido
     * @throws IllegalStateException Si se excede el límite de materias o créditos
     * @throws IllegalArgumentException Si la materia ya está inscrita
     * @throws NullPointerException Si la materia seleccionada es nula
     */
    private void agregarMateria() throws IndexOutOfBoundsException, IllegalStateException, 
                                         IllegalArgumentException, NullPointerException {
        try {
            int selectedIndex = materiaList.getSelectedIndex();
            
            if (selectedIndex == -1) {
                throw new IllegalArgumentException("Por favor seleccione una materia");
            }
            
            if (selectedIndex < 0 || selectedIndex >= materias.size()) {
                throw new IndexOutOfBoundsException("Índice de materia fuera de rango: " + selectedIndex);
            }
            
            Materia seleccionada = materias.get(selectedIndex);
            
            if (seleccionada == null) {
                throw new NullPointerException("La materia seleccionada es nula");
            }
            
            // Validar límite de materias
            if (matricula.getMaterias().size() >= MAX_MATERIAS) {
                throw new IllegalStateException("No puede inscribir más de " + MAX_MATERIAS + " materias");
            }
            
            // Validar que no esté duplicada
            for (Materia m : matricula.getMaterias()) {
                if (m.getCodigo().equals(seleccionada.getCodigo())) {
                    throw new IllegalArgumentException("La materia '" + seleccionada.getNombre() + 
                                                      "' ya está inscrita");
                }
            }
            
            // Validar límite de créditos
            int creditosActuales = 0;
            for (Materia m : matricula.getMaterias()) {
                creditosActuales += m.getCreditos();
            }
            
            if (creditosActuales + seleccionada.getCreditos() > MAX_CREDITOS) {
                throw new IllegalStateException("No puede inscribir más de " + MAX_CREDITOS + 
                                               " créditos. Actualmente tiene " + creditosActuales + 
                                               " créditos y está intentando agregar " + 
                                               seleccionada.getCreditos() + " más");
            }
            
            // Validar que los créditos sean positivos
            if (seleccionada.getCreditos() <= 0) {
                throw new IllegalArgumentException("La materia tiene un número inválido de créditos: " + 
                                                  seleccionada.getCreditos());
            }
            
            matricula.agregarMateria(seleccionada);
            
            matriculaArea.append(seleccionada.getNombre() + " - Créditos: " + 
                               seleccionada.getCreditos() + " - Código: " + 
                               seleccionada.getCodigo() + "\n");
            
            actualizarTotal();
            
            JOptionPane.showMessageDialog(this, 
                "Materia agregada exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (IllegalArgumentException | IllegalStateException e) {
            JOptionPane.showMessageDialog(this, 
                e.getMessage(), 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
        } catch (IndexOutOfBoundsException e) {
            mostrarError("Error de índice", e);
            throw e;
        } catch (NullPointerException e) {
            mostrarError("Error: elemento nulo", e);
            throw e;
        }
    }

    /**
     * Actualiza el cálculo del costo total de la matrícula.
     * Suma el valor de todos los créditos de las materias inscritas
     * y actualiza la etiqueta visual del total.
     * 
     * @throws ArithmeticException Si ocurre un error en el cálculo
     * @throws NullPointerException Si la lista de materias es nula
     */
    private void actualizarTotal() throws ArithmeticException, NullPointerException {
        try {
            if (matricula == null) {
                throw new NullPointerException("La matrícula es nula");
            }
            
            if (matricula.getMaterias() == null) {
                throw new NullPointerException("La lista de materias es nula");
            }
            
            double total = 0.0;
            for (Materia m : matricula.getMaterias()) {
                if (m == null) {
                    System.err.println("Advertencia: materia nula encontrada al calcular total");
                    continue;
                }
                
                int creditos = m.getCreditos();
                if (creditos < 0) {
                    throw new ArithmeticException("Créditos negativos encontrados: " + creditos);
                }
                
                total += creditos * 1000;
                
                // Validar overflow
                if (Double.isInfinite(total) || Double.isNaN(total)) {
                    throw new ArithmeticException("Error en el cálculo: valor inválido");
                }
            }
            
            totalLabel.setText(String.format("Total: $%.2f", total));
            
        } catch (ArithmeticException e) {
            mostrarError("Error en el cálculo del total", e);
            throw e;
        } catch (NullPointerException e) {
            mostrarError("Error: valor nulo al calcular total", e);
            throw e;
        }
    }
    
    /**
     * Finaliza el proceso de matrícula y muestra un resumen.
     * Valida que se haya inscrito al menos una materia.
     * 
     * @throws IllegalStateException Si no hay materias inscritas
     * @throws NullPointerException Si el estudiante o matrícula son nulos
     */
    private void terminarMatricula() throws IllegalStateException, NullPointerException {
        try {
            if (estudiante == null) {
                throw new NullPointerException("El estudiante es nulo");
            }
            
            if (matricula == null) {
                throw new NullPointerException("La matrícula es nula");
            }
            
            if (matricula.getMaterias() == null) {
                throw new NullPointerException("La lista de materias es nula");
            }
            
            if (matricula.getMaterias().isEmpty()) {
                throw new IllegalStateException("Debe inscribir al menos una materia antes de finalizar");
            }
            
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy HH:mm:ss");
            String fechaHora = ahora.format(formatter);
            
            int totalCreditos = 0;
            for (Materia m : matricula.getMaterias()) {
                totalCreditos += m.getCreditos();
            }
            
            String mensaje = String.format(
                "¡Matrícula completada exitosamente!\n\n" +
                "Estudiante: %s\n" +
                "Código: %s\n" +
                "Email: %s\n" +
                "Materias inscritas: %d\n" +
                "Total créditos: %d\n" +
                "%s\n" +
                "Fecha y hora: %s",
                estudiante.getName(),
                estudiante.getCode(),
                estudiante.getEmail(),
                matricula.getMaterias().size(),
                totalCreditos,
                totalLabel.getText(),
                fechaHora
            );
            
            JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Matrícula Completada",
                JOptionPane.INFORMATION_MESSAGE
            );
            
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, 
                e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException e) {
            mostrarError("Error: datos nulos al finalizar matrícula", e);
            throw e;
        } catch (Exception e) {
            mostrarError("Error inesperado al finalizar matrícula", e);
            throw new RuntimeException("Error al finalizar la matrícula", e);
        }
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje de error detallado.
     * Incluye la excepción original para facilitar la depuración.
     * 
     * @param mensaje Mensaje descriptivo del error
     * @param e Excepción que causó el error
     */
    private void mostrarError(String mensaje, Exception e) {
        JOptionPane.showMessageDialog(
            this,
            mensaje + ": " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
        e.printStackTrace();
    }
}
