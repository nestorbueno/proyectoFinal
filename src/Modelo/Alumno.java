package Modelo;

/**
 * Representa un alumno del sistema.
 */
public class Alumno {
    private static int nextInscriptionNumber = 1; // Números de inscripción empiezan en 1
    private int inscriptionNumber; // Número de inscripción único
    private String nombre;
    private String numeroCuenta;
    private float creditos;
    private float calificacion;
    private String contrasena;
    private double numeroInscripcion; // Ahora es secuencial
    private int semestre;

    public Alumno() {
        this.inscriptionNumber = nextInscriptionNumber++;
    }

    public Alumno(String nombre, String numeroCuenta, float creditos, float calificacion, int semestre) {
        this.inscriptionNumber = nextInscriptionNumber++;
        this.nombre = nombre;
        this.numeroCuenta = numeroCuenta;
        this.creditos = creditos;
        this.calificacion = calificacion;
        this.semestre = semestre;
        this.contrasena = "pass" + numeroCuenta;
        this.numeroInscripcion = this.inscriptionNumber; // Asignar número secuencial
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public float getCreditos() {
        return creditos;
    }

    public void setCreditos(float creditos) {
        this.creditos = creditos;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public double getNumeroInscripcion() {
        return numeroInscripcion;
    }

    public void setNumeroInscripcion(double numeroInscripcion) {
        this.numeroInscripcion = numeroInscripcion;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    @Override
    public String toString() {
        return String.format("""
                Nombre: %s
                Número de Cuenta: %s
                Semestre: %d
                Créditos: %.2f
                Calificación: %.2f
                Número de Inscripción: %.0f
                """, nombre, numeroCuenta, semestre, creditos, calificacion, numeroInscripcion);
    }
}
