package com.wallet;

import com.wallet.presentation.menus.MenuPrincipal;

/**
 * Clase principal de la aplicación Wallet.
 * Punto de entrada del sistema de billetera digital.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class Main {
    
    /**
     * Método principal que inicia la aplicación.
     * 
     * @param args argumentos de línea de comandos (no utilizados actualmente)
     */
    public static void main(String[] args) {
        // Iniciar interfaz de usuario interactiva
        MenuPrincipal menu = new MenuPrincipal();
        menu.iniciar();
    }
}

