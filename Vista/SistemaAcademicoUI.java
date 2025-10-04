package Vista;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
 * @author Juan Pablo Figueroa
 * @author Andres Felipe Corredor
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

    /**
     * Constructor de la interfaz gráfica.
     * Inicializa todos los componentes visuales, carga las materias
     * desde archivo, crea un nuevo estudiante y configura los eventos.
     */
    public SistemaAcademicoUI() {
        setTitle("Sistema de Matrícula - Universidad");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        List<Materia> materias = CargaDeElementos.cargarMaterias("C:\\Users\\Juanp\\Documents\\Universidad\\Materias.txt");
        estudiante = Estudiante.nuevoEstudiante();
        matricula = new Matricula(new java.util.Date(), true, 0.0, estudiante);

        materiaModel = new DefaultListModel<>();
        for (Materia materia : materias) {
            materiaModel.addElement(
                materia.getCodigo() + " - " +
                materia.getNombre() + " - Créditos: " +
                materia.getCreditos()
            );
        }

        materiaList = new JList<>(materiaModel);
        JScrollPane scrollCatalogo = new JScrollPane(materiaList);

        JButton btnAgregar = new JButton("Agregar a matrícula");
        btnAgregar.addActionListener(e -> {
            int selectedIndex = materiaList.getSelectedIndex();
            if (selectedIndex != -1) {
                Materia seleccionada = materias.get(selectedIndex);
                matricula.agregarMateria(seleccionada); 
                matriculaArea.append(seleccionada.getNombre() + " - Créditos: " + seleccionada.getCreditos() + " - Código: " + seleccionada.getCodigo() + "\n");
                actualizarTotal();
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
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de'  MMMM 'de' yyyy HH:mm:ss");
            String fechaHora = ahora.format(formatter);
            JOptionPane.showMessageDialog(
                this,
                "¡Matrícula completada exitosamente!\n" +
                "Estudiante: " + estudiante.getName() + "\n" +
                "Código: " + estudiante.getCode() + "\n" +
                totalLabel.getText() +
                "\nFecha y hora: " + fechaHora
            );
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
    }

    /**
     * Actualiza el cálculo del costo total de la matrícula.
     * Suma el valor de todos los créditos de las materias inscritas
     * y actualiza la etiqueta visual del total.
     */
    private void actualizarTotal() {
        double total = 0.0;
        for (Materia m : matricula.getMaterias()) { 
            total += m.getCreditos() * 1000;
        }
        totalLabel.setText("Total: $" + total);
    }
    
    /**
     * Método principal que inicia la aplicación gráfica.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SistemaAcademicoUI().setVisible(true);
        });
    }
}