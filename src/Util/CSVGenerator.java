/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

/**
 *
 * @author Derek
 */
import java.io.*;
import java.util.*;
import Modelo.Alumno;
import Modelo.Secretaria;

public class CSVGenerator {
    private static final String[] NOMBRES = {
        "Juan", "María", "Pedro", "Ana", "Luis", "Carmen", "José", "Isabel", "Miguel", "Sofia",
        "Carlos", "Laura", "Antonio", "Patricia", "Francisco", "Elena", "David", "Monica", "Daniel", "Lucia"
    };
    
    private static final String[] APELLIDOS = {
        "García", "Rodríguez", "González", "Fernández", "López", "Martínez", "Sánchez", "Pérez",
        "Gómez", "Martin", "Jiménez", "Ruiz", "Hernández", "Díaz", "Moreno", "Muñoz", "Álvarez"
    };
    
    public static void generarArchivosCSV() {
        // Eliminar archivos existentes
        File alumnosFile = new File("alumnos.csv");
        File secretariasFile = new File("secretarias.csv");
        
        if (alumnosFile.exists()) alumnosFile.delete();
        if (secretariasFile.exists()) secretariasFile.delete();
        
        generarAlumnosCSV("alumnos.csv");
        generarSecretariasCSV("secretarias.csv");
    }
    
    private static String generarNumeroCuenta() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }
    
    private static void generarAlumnosCSV(String archivo) {
        try {
            List<Alumno> alumnos = new ArrayList<>();
            Random rand = new Random();
            Set<String> numerosUsados = new HashSet<>();
            
            // Generar alumnos
            for (int i = 0; i < 1000; i++) {
                String numeroCuenta;
                do {
                    numeroCuenta = generarNumeroCuenta();
                } while (numerosUsados.contains(numeroCuenta));
                
                numerosUsados.add(numeroCuenta);
                
                String nombre = NOMBRES[rand.nextInt(NOMBRES.length)] + " " + 
                               APELLIDOS[rand.nextInt(APELLIDOS.length)] + " " +
                               APELLIDOS[rand.nextInt(APELLIDOS.length)];
                float creditos = 20 + rand.nextFloat() * 380;
                float calificacion = 6 + rand.nextFloat() * 4;
                int semestre = 1 + rand.nextInt(10);
                
                alumnos.add(new Alumno(nombre, numeroCuenta, creditos, calificacion, semestre));
            }
            
            // Ordenar por número de inscripción
            Collections.sort(alumnos, (a1, a2) -> 
                Double.compare(a1.getNumeroInscripcion(), a2.getNumeroInscripcion()));
            
            // Escribir al CSV
            try (PrintWriter writer = new PrintWriter(new File(archivo))) {
                writer.println("numeroCuenta,nombre,creditos,calificacion,contrasena,numeroInscripcion,semestre");
                
                for (Alumno alumno : alumnos) {
                    writer.printf("%s,%s,%.2f,%.2f,%s,%.4f,%d%n",
                        alumno.getNumeroCuenta(),
                        alumno.getNombre(),
                        alumno.getCreditos(),
                        alumno.getCalificacion(),
                        alumno.getContrasena(),
                        alumno.getNumeroInscripcion(),
                        alumno.getSemestre());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error al crear el archivo CSV de alumnos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void generarSecretariasCSV(String archivo) {
        try (PrintWriter writer = new PrintWriter(new File(archivo))) {
            writer.println("nombre,usuario,contrasena");
            // Agregar secretarias por defecto
            writer.println("María López,maria.lopez,secretaria123");
            writer.println("Juan Pérez,juan.perez,secretaria456");
        } catch (FileNotFoundException e) {
            System.err.println("Error al crear el archivo CSV de secretarias: " + e.getMessage());
            e.printStackTrace();
        }
    }
}