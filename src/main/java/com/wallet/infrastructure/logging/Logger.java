package com.wallet.infrastructure.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Sistema de Logging simple para auditoría.
 * 
 * Registra las operaciones importantes del sistema en consola.
 * En una implementación real, podría escribir a archivos o bases de datos.
 */
public class Logger {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static boolean enabled = true;
    
    public enum Nivel {
        INFO, WARNING, ERROR, DEBUG
    }
    
    private Logger() {
        throw new AssertionError("No se debe instanciar Logger");
    }
    
    /**
     * Habilita o deshabilita el logging.
     */
    public static void setEnabled(boolean enabled) {
        Logger.enabled = enabled;
    }
    
    /**
     * Registra un mensaje de información.
     */
    public static void info(String mensaje) {
        log(Nivel.INFO, mensaje);
    }
    
    /**
     * Registra una advertencia.
     */
    public static void warning(String mensaje) {
        log(Nivel.WARNING, mensaje);
    }
    
    /**
     * Registra un error.
     */
    public static void error(String mensaje) {
        log(Nivel.ERROR, mensaje);
    }
    
    /**
     * Registra un error con excepción.
     */
    public static void error(String mensaje, Exception e) {
        log(Nivel.ERROR, mensaje + " - " + e.getClass().getSimpleName() + ": " + e.getMessage());
    }
    
    /**
     * Registra un mensaje de depuración.
     */
    public static void debug(String mensaje) {
        log(Nivel.DEBUG, mensaje);
    }
    
    /**
     * Método interno para formatear y escribir logs.
     */
    private static void log(Nivel nivel, String mensaje) {
        if (!enabled) return;
        
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String nivelStr = String.format("[%-7s]", nivel.name());
        
        System.out.println(timestamp + " " + nivelStr + " " + mensaje);
    }
}
