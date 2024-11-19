package Controlador;

import java.io.*;
import java.util.*;
import Modelo.Alumno;
import Modelo.Secretaria;

/**
 * Controlador para manejar el login y la carga de datos del sistema.
 */
public class LoginController {
    private Map<String, Alumno> alumnos; // Almacena los alumnos en memoria
    private Map<String, Secretaria> secretarias; // Almacena las secretarias en memoria
    private static final String ARCHIVO_ALUMNOS = "alumnos.csv";
    private static final String ARCHIVO_SECRETARIAS = "secretarias.csv";

    public LoginController() {
        alumnos = new HashMap<>();
        secretarias = new HashMap<>();
        inicializarArchivos(); // Crear archivos si no existen
        cargarDatos(); // Cargar alumnos y secretarias desde los archivos
    }

    // Inicializar los archivos de alumnos y secretarias si no existen
    private void inicializarArchivos() {
        inicializarArchivo(ARCHIVO_ALUMNOS, "numeroCuenta,nombre,creditos,calificacion,contrasena,numeroInscripcion,semestre");
        inicializarArchivo(ARCHIVO_SECRETARIAS, "nombre,usuario,contrasena");
    }

    private void inicializarArchivo(String archivo, String cabecera) {
        File file = new File(archivo);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println(cabecera); // Escribir la cabecera
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Cargar todos los datos al iniciar el programa
    private void cargarDatos() {
        cargarAlumnos();
        cargarSecretarias();
    }

    // Método para cargar alumnos desde alumnos.csv
    private void cargarAlumnos() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_ALUMNOS))) {
            String line = br.readLine(); // Saltar la cabecera
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) {
                    String numeroCuenta = data[0];
                    Alumno alumno = new Alumno(
                            data[1], // Nombre
                            numeroCuenta, // Número de cuenta
                            Float.parseFloat(data[2]), // Créditos
                            Float.parseFloat(data[3]), // Calificación
                            Integer.parseInt(data[6])  // Semestre
                    );
                    alumno.setContrasena(data[4]);
                    alumno.setNumeroInscripcion(Double.parseDouble(data[5]));
                    alumnos.put(numeroCuenta, alumno); // Agregar al mapa
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo 'alumnos.csv' no encontrado. Se generará uno nuevo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para cargar secretarias desde secretarias.csv
    private void cargarSecretarias() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_SECRETARIAS))) {
            String line = br.readLine(); // Saltar la cabecera
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    Secretaria secretaria = new Secretaria(data[0], data[1], data[2]);
                    secretarias.put(secretaria.getUsuario(), secretaria);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo 'secretarias.csv' no encontrado. Se creará uno nuevo.");
        } catch (IOException e) {
            System.err.println("Error al cargar las secretarias.");
            e.printStackTrace();
        }
    }

    // Registrar una nueva secretaria
    public boolean registrarSecretaria(String nombre, String usuario, String contrasena) {
        if (secretarias.containsKey(usuario)) {
            return false; // No se permite duplicar usuarios
        }

        try (FileWriter fw = new FileWriter(ARCHIVO_SECRETARIAS, true);
             PrintWriter writer = new PrintWriter(fw)) {
            writer.printf("%s,%s,%s%n", nombre, usuario, contrasena); // Escribir en el archivo
            secretarias.put(usuario, new Secretaria(nombre, usuario, contrasena)); // Guardar en memoria
            return true;
        } catch (IOException e) {
            System.err.println("Error al registrar la secretaria.");
            e.printStackTrace();
            return false;
        }
    }

    // Verificar acceso de secretaria
    public boolean accederSecretaria(String usuario, String contrasena) {
        Secretaria secretaria = secretarias.get(usuario);
        return secretaria != null && secretaria.getContrasena().equals(contrasena);
    }

    // Obtener todos los alumnos
    public List<Alumno> getAlumnos() {
        return new ArrayList<>(alumnos.values());
    }

    // Buscar un alumno por su número de cuenta
    public Alumno buscarAlumnoPorNumeroCuenta(String numeroCuenta) {
        return alumnos.get(numeroCuenta);
    }

    // Método para depuración: listar todas las secretarias cargadas
    public void listarSecretarias() {
        System.out.println("Secretarias cargadas:");
        for (Secretaria secretaria : secretarias.values()) {
            System.out.println(secretaria.getUsuario() + " - " + secretaria.getNombre());
        }
    }
}
