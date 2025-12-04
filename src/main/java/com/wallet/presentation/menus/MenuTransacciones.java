package com.wallet.presentation.menus;

import com.wallet.presentation.controllers.TransaccionController;
import com.wallet.presentation.utils.ConsoleUtils;

/**
 * Menú de transacciones.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class MenuTransacciones {
    
    private final TransaccionController transaccionController;
    
    public MenuTransacciones(TransaccionController transaccionController) {
        this.transaccionController = transaccionController;
    }
    
    /**
     * Muestra el menú de transacciones.
     */
    public void mostrar() {
        boolean volver = false;
        
        while (!volver) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.printHeader("TRANSACCIONES");
            
            System.out.println("1. Transferir Dinero");
            System.out.println("2. Ver Historial de Transacciones");
            System.out.println("3. Ver Ultimas Transacciones");
            System.out.println("0. Volver al Menu Principal");
            ConsoleUtils.printLine();
            
            int opcion = ConsoleUtils.readIntInRange("Seleccione una opcion: ", 0, 3);
            ConsoleUtils.printLine();
            
            switch (opcion) {
                case 1:
                    transaccionController.transferir();
                    ConsoleUtils.pause();
                    break;
                    
                case 2:
                    transaccionController.consultarHistorial();
                    ConsoleUtils.pause();
                    break;
                    
                case 3:
                    transaccionController.consultarUltimas();
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
