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

public class ShopNowIU extends JFrame {

    private JList<String> materiaList;
    private DefaultListModel<String> materiaModel;
    private JTextArea matriculaArea;
    private JLabel totalLabel;
    private JButton btnTerminarMatricula;
    private Matricula matricula;
    private List<Materia> materias;
    private Estudiante estudiante;


    public ShopNowIU() {
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

    private void actualizarTotal() {
        double total = 0.0;
        for (Materia m : matricula.getMaterias()) { 
            total += m.getCreditos() * 1000;
        }
        totalLabel.setText("Total: $" + total);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ShopNowIU().setVisible(true);
        });
    }
}