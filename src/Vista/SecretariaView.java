package Vista;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import Modelo.Alumno;
import Controlador.LoginController;

/**
 * Vista para la gestión de alumnos por parte de las secretarias.
 */
public class SecretariaView extends JFrame {
    private static final long serialVersionUID = 1L;
    private final LoginController controller; // Controlador para manejar datos
    private JList<String> alumnosList;
    private JTextArea detallesArea;
    private DefaultListModel<String> listModel;

    public SecretariaView(LoginController controller) {
        this.controller = controller; // Pasar la instancia del controlador
        initComponents(); // Inicializar componentes de la vista
        cargarAlumnos(); // Cargar los alumnos al abrir la vista
    }

    // Inicializar los componentes gráficos
    private void initComponents() {
        setTitle("Sistema de Gestión - Panel de Secretaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Modelo para la lista de alumnos
        listModel = new DefaultListModel<>();
        alumnosList = new JList<>(listModel);
        alumnosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listaScroll = new JScrollPane(alumnosList);
        listaScroll.setPreferredSize(new Dimension(300, 400));

        // Área para mostrar detalles del alumno seleccionado
        detallesArea = new JTextArea();
        detallesArea.setEditable(false);
        JScrollPane detallesScroll = new JScrollPane(detallesArea);

        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout());
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Buscar");
        searchPanel.add(new JLabel("Buscar alumno:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Agregar componentes a la ventana
        add(searchPanel, BorderLayout.NORTH);
        add(listaScroll, BorderLayout.WEST);
        add(detallesScroll, BorderLayout.CENTER);

        // Eventos
        alumnosList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDetallesAlumno();
            }
        });

        searchButton.addActionListener(e -> {
            String busqueda = searchField.getText().toLowerCase();
            filtrarAlumnos(busqueda);
        });

        // Configuración final de la ventana
        pack();
        setLocationRelativeTo(null);
        setSize(800, 600);
    }

    // Método para cargar los alumnos desde el controlador
    private void cargarAlumnos() {
        List<Alumno> alumnos = controller.getAlumnos(); // Obtener alumnos desde LoginController
        listModel.clear(); // Limpiar la lista antes de cargar
        for (Alumno alumno : alumnos) {
            listModel.addElement(alumno.getNombre() + " (" + alumno.getNumeroCuenta() + ")");
        }
    }

    // Mostrar detalles del alumno seleccionado
    private void mostrarDetallesAlumno() {
        int selectedIndex = alumnosList.getSelectedIndex();
        if (selectedIndex != -1) {
            String seleccion = alumnosList.getSelectedValue();
            String numeroCuenta = seleccion.substring(
                seleccion.lastIndexOf("(") + 1,
                seleccion.lastIndexOf(")")
            );
            Alumno alumno = controller.buscarAlumnoPorNumeroCuenta(numeroCuenta);
            if (alumno != null) {
                detallesArea.setText(alumno.toString());
            }
        }
    }

    // Filtrar alumnos por búsqueda
    private void filtrarAlumnos(String busqueda) {
        List<Alumno> alumnos = controller.getAlumnos();
        listModel.clear();
        for (Alumno alumno : alumnos) {
            if (alumno.getNombre().toLowerCase().contains(busqueda) ||
                alumno.getNumeroCuenta().contains(busqueda)) {
                listModel.addElement(alumno.getNombre() + " (" + alumno.getNumeroCuenta() + ")");
            }
        }
    }
}
