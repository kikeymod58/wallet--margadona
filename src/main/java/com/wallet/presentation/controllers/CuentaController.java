package com.wallet.presentation.controllers;

import com.wallet.application.dtos.CuentaDTO;
import com.wallet.application.dtos.TransaccionDTO;
import com.wallet.application.dtos.requests.DepositarDineroRequest;
import com.wallet.application.dtos.requests.RetirarDineroRequest;
import com.wallet.infrastructure.services.CuentaService;
import com.wallet.presentation.utils.ConsoleUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para operaciones de cuentas.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class CuentaController {
    
    private final CuentaService cuentaService;
    
    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }
    
    /**
     * Crea una nueva cuenta para un usuario.
     */
    public CuentaDTO crearCuenta(String usuarioId) {
        ConsoleUtils.printHeader("CREAR NUEVA CUENTA");
        
        try {
            CuentaDTO cuenta = cuentaService.crearCuenta(usuarioId);
            ConsoleUtils.printSuccess("Cuenta creada exitosamente!");
            mostrarCuenta(cuenta);
            return cuenta;
            
        } catch (Exception e) {
            ConsoleUtils.printError("Error al crear cuenta: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Crea una nueva cuenta buscando usuario por email.
     */
    public CuentaDTO crearCuentaPorEmail() {
        ConsoleUtils.printHeader("CREAR NUEVA CUENTA");
        
        String email = ConsoleUtils.readLine("Email del usuario: ");
        
        try {
            CuentaDTO cuenta = cuentaService.crearCuentaPorEmail(email);
            ConsoleUtils.printSuccess("Cuenta creada exitosamente!");
            mostrarCuenta(cuenta);
            return cuenta;
            
        } catch (Exception e) {
            ConsoleUtils.printError("Error al crear cuenta: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Realiza un depósito en una cuenta.
     */
    public TransaccionDTO depositar() {
        ConsoleUtils.printHeader("DEPOSITAR DINERO");
        
        String numeroCuenta = ConsoleUtils.readLine("Numero de cuenta: ");
        BigDecimal monto = ConsoleUtils.readBigDecimal("Monto a depositar: $");
        String descripcion = ConsoleUtils.readLine("Descripcion (opcional): ");
        
        if (descripcion.isEmpty()) {
            descripcion = "Deposito";
        }
        
        if (!ConsoleUtils.readConfirmation("Confirmar deposito de " + ConsoleUtils.formatMoney(monto))) {
            ConsoleUtils.printWarning("Operacion cancelada.");
            return null;
        }
        
        try {
            TransaccionDTO transaccion = cuentaService.depositarPorNumero(numeroCuenta, monto, descripcion);
            ConsoleUtils.printSuccess("Deposito realizado exitosamente!");
            ConsoleUtils.printInfo("Monto: " + ConsoleUtils.formatMoney(transaccion.getMonto()));
            return transaccion;
            
        } catch (Exception e) {
            ConsoleUtils.printError("Error al depositar: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Realiza un retiro de una cuenta.
     */
    public TransaccionDTO retirar() {
        ConsoleUtils.printHeader("RETIRAR DINERO");
        
        String numeroCuenta = ConsoleUtils.readLine("Numero de cuenta: ");
        BigDecimal monto = ConsoleUtils.readBigDecimal("Monto a retirar: $");
        String descripcion = ConsoleUtils.readLine("Descripcion (opcional): ");
        
        if (descripcion.isEmpty()) {
            descripcion = "Retiro";
        }
        
        if (!ConsoleUtils.readConfirmation("Confirmar retiro de " + ConsoleUtils.formatMoney(monto))) {
            ConsoleUtils.printWarning("Operacion cancelada.");
            return null;
        }
        
        try {
            TransaccionDTO transaccion = cuentaService.retirarPorNumero(numeroCuenta, monto, descripcion);
            ConsoleUtils.printSuccess("Retiro realizado exitosamente!");
            ConsoleUtils.printInfo("Monto: " + ConsoleUtils.formatMoney(transaccion.getMonto()));
            return transaccion;
            
        } catch (Exception e) {
            ConsoleUtils.printError("Error al retirar: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Consulta el saldo de una cuenta.
     */
    public void consultarSaldo() {
        ConsoleUtils.printHeader("CONSULTAR SALDO");
        
        String numeroCuenta = ConsoleUtils.readLine("Numero de cuenta: ");
        
        try {
            CuentaDTO cuenta = cuentaService.consultarSaldo(numeroCuenta);
            ConsoleUtils.printLine();
            ConsoleUtils.printSuccess("Cuenta: " + cuenta.getNumeroCuenta());
            System.out.println("  Saldo actual: " + ConsoleUtils.formatMoney(cuenta.getSaldo()));
            System.out.println("  Moneda: " + cuenta.getMoneda());
            System.out.println("  Estado: " + (cuenta.isActiva() ? "Activa" : "Inactiva"));
            ConsoleUtils.printLine();
            
        } catch (Exception e) {
            ConsoleUtils.printError("Error al consultar saldo: " + e.getMessage());
        }
    }
    
    /**
     * Lista las cuentas de un usuario.
     */
    public void listarCuentasUsuario(String usuarioId) {
        ConsoleUtils.printHeader("CUENTAS DEL USUARIO");
        
        List<CuentaDTO> cuentas = cuentaService.buscarPorUsuario(usuarioId);
        
        if (cuentas.isEmpty()) {
            ConsoleUtils.printInfo("El usuario no tiene cuentas.");
            return;
        }
        
        System.out.println("Total de cuentas: " + cuentas.size());
        ConsoleUtils.printLine();
        
        for (int i = 0; i < cuentas.size(); i++) {
            CuentaDTO c = cuentas.get(i);
            System.out.println((i + 1) + ". Cuenta: " + c.getNumeroCuenta());
            System.out.println("   ID: " + c.getId());
            System.out.println("   Saldo: " + ConsoleUtils.formatMoney(c.getSaldo()) + " " + c.getMoneda());
            System.out.println("   Estado: " + (c.isActiva() ? "Activa" : "Inactiva"));
            ConsoleUtils.printLine();
        }
    }
    
    /**
     * Busca una cuenta por número.
     */
    public CuentaDTO buscarPorNumero() {
        String numeroCuenta = ConsoleUtils.readLine("Numero de cuenta: ");
        
        Optional<CuentaDTO> cuenta = cuentaService.buscarPorNumeroCuenta(numeroCuenta);
        
        if (cuenta.isPresent()) {
            mostrarCuenta(cuenta.get());
            return cuenta.get();
        } else {
            ConsoleUtils.printWarning("Cuenta no encontrada.");
            return null;
        }
    }
    
    /**
     * Muestra los detalles de una cuenta.
     */
    private void mostrarCuenta(CuentaDTO cuenta) {
        ConsoleUtils.printLine();
        System.out.println("Detalles de la cuenta:");
        System.out.println("  ID: " + cuenta.getId());
        System.out.println("  Numero: " + cuenta.getNumeroCuenta());
        System.out.println("  Saldo: " + ConsoleUtils.formatMoney(cuenta.getSaldo()));
        System.out.println("  Moneda: " + cuenta.getMoneda());
        System.out.println("  Estado: " + (cuenta.isActiva() ? "Activa" : "Inactiva"));
        ConsoleUtils.printLine();
    }
}
