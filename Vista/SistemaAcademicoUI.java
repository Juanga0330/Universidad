package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
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
 * 
 * @author Kevin Santiago Costo
 * @version 1.0
 * @since 2025
 */
public class SistemaAcademicoUI extends JFrame {

    private JList<String> materiaList;
    private DefaultListModel<String> materiaModel;
    private JTextArea matriculaArea;
    private JLabel totalLabel;
    private JButton btnTerminarMatricula;
    private Matricula matricula;
    private List<Materia> materias;
    private Estudiante estudiante;
    
    private static final int MAX_MATERIAS = 8;
    private static final int MAX_CREDITOS = 24;

    /**
     * Crea un JTextField que solo acepta letras y espacios.
     * Bloquea números y caracteres especiales en tiempo real.
     */
    private JTextField crearCampoSoloLetras() {
        JTextField campo = new JTextField();
        campo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                // Si NO es letra, NO es espacio y NO es backspace
                if (!Character.isLetter(c) && c != ' ' && c != KeyEvent.VK_BACK_SPACE) {
                    evt.consume(); // BLOQUEA la tecla - no permite escribir
                    Toolkit.getDefaultToolkit().beep(); // Sonido de error
                }
            }
        });
        return campo;
    }

    /**
     * Crea un JTextField que solo acepta números.
     * Bloquea letras y caracteres especiales en tiempo real.
     */
    private JTextField crearCampoSoloNumeros() {
        JTextField campo = new JTextField();
        campo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                // Si NO es dígito y NO es backspace
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    evt.consume(); // BLOQUEA la tecla - no permite escribir
                    Toolkit.getDefaultToolkit().beep(); // Sonido de error
                }
            }
        });
        return campo;
    }

    /**
     * Crea un JTextField que solo acepta números y formato de fecha (/).
     * Bloquea letras en tiempo real.
     */
    private JTextField crearCampoFecha() {
        JTextField campo = new JTextField();
        campo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                // Si NO es dígito, NO es '/' y NO es backspace
                if (!Character.isDigit(c) && c != '/' && c != KeyEvent.VK_BACK_SPACE) {
                    evt.consume(); // BLOQUEA la tecla - no permite escribir
                    Toolkit.getDefaultToolkit().beep(); // Sonido de error
                }
            }
        });
        return campo;
    }

    /**
     * Valida que un texto solo contenga letras y espacios.
     */
    private boolean validarSoloLetras(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }

    /**
     * Valida que un texto solo contenga números.
     */
    private boolean validarSoloNumeros(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }
        return texto.matches("\\d+");
    }

    /**
     * Valida formato de fecha (DD/MM/AAAA).
     */
    private boolean validarFormatoFecha(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            return false;
        }
        // Formato DD/MM/AAAA
        return fecha.matches("(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}");
    }

    /**
     * Constructor de la interfaz gráfica.
     * Inicializa todos los componentes visuales y carga los datos.
     */
    public SistemaAcademicoUI() throws Exception {
        try {
            // Configuración inicial de la ventana
            setTitle("Sistema de Matrícula - Universidad");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);

            // Cargar archivo de materias
            String rutaArchivo = obtenerRutaArchivo();
            if (rutaArchivo == null) {
                throw new FileNotFoundException("No se seleccionó ningún archivo");
            }
            
            File archivo = new File(rutaArchivo);
            if (!archivo.exists() || !archivo.canRead()) {
                throw new FileNotFoundException("No se puede acceder al archivo: " + rutaArchivo);
            }

            // Cargar datos
            materias = CargaDeElementos.cargarMaterias(rutaArchivo);
            if (materias == null || materias.isEmpty()) {
                throw new IllegalStateException("No se pudieron cargar las materias del archivo");
            }

            // Crear estudiante con validaciones
            estudiante = crearEstudianteConValidacion();
            if (estudiante == null) {
                throw new NullPointerException("No se pudo crear el estudiante correctamente");
            }
            
            matricula = new Matricula(new java.util.Date(), true, 0.0, estudiante);
            if (matricula == null) {
                throw new IllegalStateException("No se pudo crear la matrícula");
            }

            // Crear modelo de lista de materias
            materiaModel = new DefaultListModel<>();
            for (Materia materia : materias) {
                if (materia != null) {
                    materiaModel.addElement(
                        materia.getCodigo() + " - " +
                        materia.getNombre() + " - Créditos: " +
                        materia.getCreditos()
                    );
                }
            }

            // Panel izquierdo - Catálogo de materias
            materiaList = new JList<>(materiaModel);
            JScrollPane scrollCatalogo = new JScrollPane(materiaList);

            JButton btnAgregar = new JButton("Agregar a matrícula");
            btnAgregar.addActionListener(e -> agregarMateria());

            JPanel panelIzquierdo = new JPanel(new BorderLayout());
            panelIzquierdo.add(new JLabel("Catálogo de Materias"), BorderLayout.NORTH);
            panelIzquierdo.add(scrollCatalogo, BorderLayout.CENTER);
            panelIzquierdo.add(btnAgregar, BorderLayout.SOUTH);

            // Panel derecho - Materias inscritas
            matriculaArea = new JTextArea();
            matriculaArea.setEditable(false);
            JScrollPane scrollMatricula = new JScrollPane(matriculaArea);

            totalLabel = new JLabel("Total: $0.0");

            btnTerminarMatricula = new JButton("Terminar matrícula");
            btnTerminarMatricula.addActionListener(e -> terminarMatricula());

            JPanel panelInferior = new JPanel(new BorderLayout());
            panelInferior.add(totalLabel, BorderLayout.CENTER);
            panelInferior.add(btnTerminarMatricula, BorderLayout.EAST);

            JPanel panelDerecho = new JPanel(new BorderLayout());
            panelDerecho.add(scrollMatricula, BorderLayout.CENTER);
            panelDerecho.add(panelInferior, BorderLayout.SOUTH);

            // Unir paneles
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
            splitPane.setDividerLocation(400);

            add(splitPane, BorderLayout.CENTER);
            
        } catch (Exception e) {
            mostrarError("Error al inicializar la aplicación", e);
            throw e;
        }
    }

    /**
     * Obtiene la ruta del archivo de materias.
     * Intenta con rutas comunes o solicita al usuario.
     */
    private String obtenerRutaArchivo() {
        String[] rutasPosibles = {
            "Materias.txt",
            "src/Materias.txt",
            "recursos/Materias.txt"
        };
        
        for (String ruta : rutasPosibles) {
            File archivo = new File(ruta);
            if (archivo.exists()) {
                return ruta;
            }
        }
        
        // Si no encuentra, pide al usuario
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione el archivo Materias.txt");
        int result = fileChooser.showOpenDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        
        return null;
    }

    /**
     * Crea un estudiante con validaciones de entrada personalizadas.
     * Usa campos con restricciones en tiempo real.
     */
    private Estudiante crearEstudianteConValidacion() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        
        // Crear campos con validaciones en tiempo real
        JTextField campoNombre = crearCampoSoloLetras();
        JTextField campoCodigo = crearCampoSoloNumeros();
        JTextField campoFechaNacimiento = crearCampoFecha();
        JTextField campoEmail = new JTextField();
        
        panel.add(new JLabel("Nombre completo (solo letras):"));
        panel.add(campoNombre);
        panel.add(new JLabel("Código (solo números):"));
        panel.add(campoCodigo);
        panel.add(new JLabel("Fecha de nacimiento (DD/MM/AAAA):"));
        panel.add(campoFechaNacimiento);
        panel.add(new JLabel("Email:"));
        panel.add(campoEmail);
        
        boolean datosValidos = false;
        Estudiante nuevoEstudiante = null;
        
        while (!datosValidos) {
            int result = JOptionPane.showConfirmDialog(
                null, 
                panel, 
                "Registro de Estudiante", 
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );
            
            if (result != JOptionPane.OK_OPTION) {
                return null; // Usuario canceló
            }
            
            String nombre = campoNombre.getText().trim();
            String codigo = campoCodigo.getText().trim();
            String fechaNac = campoFechaNacimiento.getText().trim();
            String email = campoEmail.getText().trim();
            
            // Validaciones finales
            if (!validarSoloLetras(nombre)) {
                JOptionPane.showMessageDialog(null, 
                    "El nombre solo puede contener letras y espacios", 
                    "Error de validación", 
                    JOptionPane.ERROR_MESSAGE);
                continue;
            }
            
            if (!validarSoloNumeros(codigo)) {
                JOptionPane.showMessageDialog(null, 
                    "El código solo puede contener números", 
                    "Error de validación", 
                    JOptionPane.ERROR_MESSAGE);
                continue;
            }
            
            if (!validarFormatoFecha(fechaNac)) {
                JOptionPane.showMessageDialog(null, 
                    "La fecha debe tener el formato DD/MM/AAAA y solo contener números", 
                    "Error de validación", 
                    JOptionPane.ERROR_MESSAGE);
                continue;
            }
            
            if (email.isEmpty() || !email.contains("@")) {
                JOptionPane.showMessageDialog(null, 
                    "El email debe ser válido", 
                    "Error de validación", 
                    JOptionPane.ERROR_MESSAGE);
                continue;
            }
            
            // Si todas las validaciones pasan, crear el estudiante
            try {
                nuevoEstudiante = new Estudiante(
                    nombre, 
                    codigo, 
                    email, 
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), 
                    fechaNac, 
                    1
                );
                datosValidos = true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Error al crear el estudiante: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        
        return nuevoEstudiante;
    }

    /**
     * Agrega una materia seleccionada a la matrícula del estudiante.
     */
    private void agregarMateria() {
        try {
            // Validar selección
            int selectedIndex = materiaList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor seleccione una materia", 
                    "Advertencia", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Materia seleccionada = materias.get(selectedIndex);
            
            // Validar límite de materias
            if (matricula.getMaterias().size() >= MAX_MATERIAS) {
                JOptionPane.showMessageDialog(this, 
                    "No puede inscribir más de " + MAX_MATERIAS + " materias", 
                    "Límite alcanzado", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Validar duplicados
            for (Materia m : matricula.getMaterias()) {
                if (m.getCodigo().equals(seleccionada.getCodigo())) {
                    JOptionPane.showMessageDialog(this, 
                        "La materia '" + seleccionada.getNombre() + "' ya está inscrita", 
                        "Materia duplicada", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            
            // Validar límite de créditos
            int creditosActuales = 0;
            for (Materia m : matricula.getMaterias()) {
                creditosActuales += m.getCreditos();
            }
            
            if (creditosActuales + seleccionada.getCreditos() > MAX_CREDITOS) {
                JOptionPane.showMessageDialog(this, 
                    String.format("No puede inscribir más de %d créditos.\nActuales: %d\nIntentando agregar: %d", 
                        MAX_CREDITOS, creditosActuales, seleccionada.getCreditos()),
                    "Límite de créditos", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Agregar materia
            matricula.agregarMateria(seleccionada);
            matriculaArea.append(String.format("%s - Créditos: %d - Código: %s\n",
                seleccionada.getNombre(), 
                seleccionada.getCreditos(), 
                seleccionada.getCodigo()));
            
            actualizarTotal();
            
            JOptionPane.showMessageDialog(this, 
                "Materia agregada exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            mostrarError("Error al agregar materia", e);
        }
    }

    /**
     * Actualiza el cálculo del costo total de la matrícula.
     */
    private void actualizarTotal() {
        try {
            double total = 0.0;
            for (Materia m : matricula.getMaterias()) {
                if (m != null) {
                    total += m.getCreditos() * 1000;
                }
            }
            totalLabel.setText(String.format("Total: $%.2f", total));
        } catch (Exception e) {
            mostrarError("Error al calcular el total", e);
        }
    }
    
    /**
     * Finaliza el proceso de matrícula y muestra un resumen.
     */
    private void terminarMatricula() {
        try {
            // Validar que haya materias inscritas
            if (matricula.getMaterias().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Debe inscribir al menos una materia antes de finalizar", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Calcular totales
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy HH:mm:ss");
            String fechaHora = ahora.format(formatter);
            
            int totalCreditos = 0;
            for (Materia m : matricula.getMaterias()) {
                totalCreditos += m.getCreditos();
            }
            
            // Mostrar resumen
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
            
        } catch (Exception e) {
            mostrarError("Error al finalizar matrícula", e);
        }
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje de error.
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

    /**
     * Método principal para ejecutar la aplicación.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                SistemaAcademicoUI frame = new SistemaAcademicoUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Error al iniciar la aplicación: " + e.getMessage(),
                    "Error Fatal",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}