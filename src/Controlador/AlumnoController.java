/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Controlador;

/**
 *
 * @author Derek
 */
import Modelo.Alumno;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlumnoController {
    private List<Alumno> alumnos;
    
    public AlumnoController() {
        this.alumnos = new ArrayList<>();
    }
    
    public boolean anadirAlumno(String nombre, int numeroCuenta, float creditos, float calificacion) {
        if (buscarAlumno(numeroCuenta).isPresent()) {
            return false;
        }
        return alumnos.add(new Alumno(nombre, numeroCuenta, creditos, calificacion));
    }
    
    public boolean eliminarAlumno(int numeroCuenta) {
        return alumnos.removeIf(alumno -> alumno.getNumeroCuenta() == numeroCuenta);
    }
    
    public boolean editarAlumno(int numeroCuenta, String nombre, float creditos, float calificacion) {
        Optional<Alumno> alumnoOpt = buscarAlumno(numeroCuenta);
        if (alumnoOpt.isPresent()) {
            Alumno alumno = alumnoOpt.get();
            alumno.setNombre(nombre);
            alumno.setCreditos(creditos);
            alumno.setCalificacion(calificacion);
            return true;
        }
        return false;
    }
    
    public Optional<Alumno> buscarAlumno(int numeroCuenta) {
        return alumnos.stream()
                     .filter(alumno -> alumno.getNumeroCuenta() == numeroCuenta)
                     .findFirst();
    }
    
    public List<Alumno> listarAlumnos() {
        return new ArrayList<>(alumnos);
    }
}