package com.wallet.domain.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Value Object que representa una cantidad de dinero.
 * Inmutable y con aritmética segura usando BigDecimal.
 * 
 * Principios aplicados:
 * - SRP: Solo responsable de operaciones monetarias
 * - Inmutabilidad: Operaciones retornan nuevas instancias
 * - Precisión: Usa BigDecimal para evitar errores de redondeo
 */
public final class Dinero {
    
    private static final int ESCALA_DECIMAL = 2;
    private static final RoundingMode MODO_REDONDEO = RoundingMode.HALF_UP;
    
    public static final Dinero CERO = new Dinero(BigDecimal.ZERO);
    
    private final BigDecimal cantidad;
    private final String moneda;
    
    /**
     * Crea un nuevo objeto Dinero con la moneda por defecto (CLP).
     * 
     * @param cantidad la cantidad de dinero
     */
    public Dinero(BigDecimal cantidad) {
        this(cantidad, "CLP");
    }
    
    /**
     * Crea un nuevo objeto Dinero.
     * 
     * @param cantidad la cantidad de dinero
     * @param moneda el código de la moneda (ISO 4217)
     * @throws IllegalArgumentException si los datos son inválidos
     */
    public Dinero(BigDecimal cantidad, String moneda) {
        validar(cantidad, moneda);
        this.cantidad = cantidad.setScale(ESCALA_DECIMAL, MODO_REDONDEO);
        this.moneda = moneda.toUpperCase();
    }
    
    /**
     * Crea un Dinero desde un double.
     * 
     * @param cantidad la cantidad como double
     * @return nuevo objeto Dinero
     */
    public static Dinero de(double cantidad) {
        return new Dinero(BigDecimal.valueOf(cantidad));
    }
    
    /**
     * Crea un Dinero desde un string.
     * 
     * @param cantidad la cantidad como string
     * @return nuevo objeto Dinero
     */
    public static Dinero de(String cantidad) {
        return new Dinero(new BigDecimal(cantidad));
    }
    
    /**
     * Crea un Dinero desde un BigDecimal.
     * 
     * @param cantidad la cantidad como BigDecimal
     * @return nuevo objeto Dinero
     */
    public static Dinero de(BigDecimal cantidad) {
        return new Dinero(cantidad);
    }
    
    private void validar(BigDecimal cantidad, String moneda) {
        if (cantidad == null) {
            throw new IllegalArgumentException("La cantidad no puede ser nula");
        }
        
        if (moneda == null || moneda.trim().isEmpty()) {
            throw new IllegalArgumentException("La moneda no puede estar vacía");
        }
        
        if (moneda.length() != 3) {
            throw new IllegalArgumentException("El código de moneda debe tener 3 caracteres (ISO 4217)");
        }
    }
    
    /**
     * Suma este dinero con otro.
     * 
     * @param otro el dinero a sumar
     * @return nuevo Dinero con la suma
     * @throws IllegalArgumentException si las monedas son diferentes
     */
    public Dinero sumar(Dinero otro) {
        validarMismaMoneda(otro);
        return new Dinero(this.cantidad.add(otro.cantidad), this.moneda);
    }
    
    /**
     * Resta otro dinero de este.
     * 
     * @param otro el dinero a restar
     * @return nuevo Dinero con la resta
     * @throws IllegalArgumentException si las monedas son diferentes
     */
    public Dinero restar(Dinero otro) {
        validarMismaMoneda(otro);
        return new Dinero(this.cantidad.subtract(otro.cantidad), this.moneda);
    }
    
    /**
     * Multiplica este dinero por un factor.
     * 
     * @param factor el factor de multiplicación
     * @return nuevo Dinero multiplicado
     */
    public Dinero multiplicar(double factor) {
        return new Dinero(this.cantidad.multiply(BigDecimal.valueOf(factor)), this.moneda);
    }
    
    /**
     * Verifica si este dinero es mayor que otro.
     * 
     * @param otro el dinero a comparar
     * @return true si es mayor
     */
    public boolean esMayorQue(Dinero otro) {
        validarMismaMoneda(otro);
        return this.cantidad.compareTo(otro.cantidad) > 0;
    }
    
    /**
     * Verifica si este dinero es mayor o igual que otro.
     * 
     * @param otro el dinero a comparar
     * @return true si es mayor o igual
     */
    public boolean esMayorOIgualQue(Dinero otro) {
        validarMismaMoneda(otro);
        return this.cantidad.compareTo(otro.cantidad) >= 0;
    }
    
    /**
     * Verifica si este dinero es menor que otro.
     * 
     * @param otro el dinero a comparar
     * @return true si es menor
     */
    public boolean esMenorQue(Dinero otro) {
        validarMismaMoneda(otro);
        return this.cantidad.compareTo(otro.cantidad) < 0;
    }
    
    /**
     * Verifica si la cantidad es positiva (mayor que cero).
     * 
     * @return true si es positivo
     */
    public boolean esPositivo() {
        return this.cantidad.compareTo(BigDecimal.ZERO) > 0;
    }
    
    /**
     * Verifica si la cantidad es negativa (menor que cero).
     * 
     * @return true si es negativo
     */
    public boolean esNegativo() {
        return this.cantidad.compareTo(BigDecimal.ZERO) < 0;
    }
    
    /**
     * Verifica si la cantidad es cero.
     * 
     * @return true si es cero
     */
    public boolean esCero() {
        return this.cantidad.compareTo(BigDecimal.ZERO) == 0;
    }
    
    private void validarMismaMoneda(Dinero otro) {
        if (!this.moneda.equals(otro.moneda)) {
            throw new IllegalArgumentException(
                String.format("No se pueden operar monedas diferentes: %s y %s", 
                    this.moneda, otro.moneda)
            );
        }
    }
    
    public BigDecimal getCantidad() {
        return cantidad;
    }
    
    public String getMoneda() {
        return moneda;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dinero dinero = (Dinero) o;
        return cantidad.compareTo(dinero.cantidad) == 0 && Objects.equals(moneda, dinero.moneda);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(cantidad, moneda);
    }
    
    @Override
    public String toString() {
        return String.format("%s %.2f", moneda, cantidad);
    }
}
