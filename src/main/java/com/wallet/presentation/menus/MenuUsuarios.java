package com.wallet.presentation.menus;

import com.wallet.application.dtos.UsuarioDTO;
import com.wallet.presentation.controllers.UsuarioController;
import com.wallet.presentation.utils.ConsoleUtils;

/**
 * Menú de gestión de usuarios.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class MenuUsuarios {
    
    private final UsuarioController usuarioController;
    
    public MenuUsuarios(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }
    
    /**
     * Muestra el menú de usuarios.
     */
    public void mostrar() {
        boolean volver = false;
        
        while (!volver) {
            ConsoleUtils.clearScreen();
            ConsoleUtils.printHeader("GESTION DE USUARIOS");
            
            System.out.println("1. Registrar Nuevo Usuario");
            System.out.println("2. Listar Usuarios Activos");
            System.out.println("3. Buscar Usuario por Email");
            System.out.println("0. Volver al Menu Principal");
            ConsoleUtils.printLine();
            
            int opcion = ConsoleUtils.readIntInRange("Seleccione una opcion: ", 0, 3);
            ConsoleUtils.printLine();
            
            switch (opcion) {
                case 1:
                    UsuarioDTO usuario = usuarioController.registrarUsuario();
                    if (usuario != null) {
                        ConsoleUtils.printLine();
                        if (ConsoleUtils.readConfirmation("Desea crear una cuenta para este usuario")) {
                            // Se implementará en MenuCuentas
                            ConsoleUtils.printInfo("Funcionalidad disponible en Gestion de Cuentas.");
                        }
                    }
                    ConsoleUtils.pause();
                    break;
                    
                case 2:
                    usuarioController.listarUsuarios();
                    ConsoleUtils.pause();
                    break;
                    
                case 3:
                    usuarioController.buscarPorEmail();
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
