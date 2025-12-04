package com.wallet.presentation.menus;

import com.wallet.application.dtos.CuentaDTO;
import com.wallet.presentation.controllers.CuentaController;
import com.wallet.presentation.utils.ConsoleUtils;

/**
 * Menú de gestión de cuentas.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class MenuCuentas {
    
    private final CuentaController cuentaController;
    
    public MenuCuentas(CuentaController cuentaController) {
        this.cuentaController = cuentaController;
    }
    
    /**
     * Muestra el menú de cuentas.
     */
    public void mostrar() {
        boolean volver = false;
        
        while (!volver) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.printHeader("GESTION DE CUENTAS");
            
            System.out.println("1. Crear Nueva Cuenta");
            System.out.println("2. Depositar Dinero");
            System.out.println("3. Retirar Dinero");
            System.out.println("4. Consultar Saldo");
            System.out.println("5. Listar Cuentas de Usuario");
            System.out.println("6. Buscar Cuenta por Numero");
            System.out.println("0. Volver al Menu Principal");
            ConsoleUtils.printLine();
            
            int opcion = ConsoleUtils.readIntInRange("Seleccione una opcion: ", 0, 6);
            ConsoleUtils.printLine();
            
            switch (opcion) {
                case 1:
                    cuentaController.crearCuentaPorEmail();
                    ConsoleUtils.pause();
                    break;
                    
                case 2:
                    cuentaController.depositar();
                    ConsoleUtils.pause();
                    break;
                    
                case 3:
                    cuentaController.retirar();
                    ConsoleUtils.pause();
                    break;
                    
                case 4:
                    cuentaController.consultarSaldo();
                    ConsoleUtils.pause();
                    break;
                    
                case 5:
                    String userId = ConsoleUtils.readLine("ID del usuario: ");
                    cuentaController.listarCuentasUsuario(userId);
                    ConsoleUtils.pause();
                    break;
                    
                case 6:
                    cuentaController.buscarPorNumero();
                    ConsoleUtils.pause();
                    break;
                    
                case 0:
                    volver = true;
                    break;
                    
                default:
                    ConsoleUtils.printError("Opcion no valida.");
                    ConsoleUtils.pause();
            }
        }
    }
}
