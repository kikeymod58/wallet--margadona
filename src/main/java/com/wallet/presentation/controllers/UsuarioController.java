package com.wallet.presentation.controllers;

import com.wallet.application.dtos.UsuarioDTO;
import com.wallet.application.dtos.requests.CrearUsuarioRequest;
import com.wallet.infrastructure.services.UsuarioService;
import com.wallet.presentation.utils.ConsoleUtils;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para operaciones de usuarios.
 * Actúa como intermediario entre la UI y el servicio de usuarios.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    /**
     * Registra un nuevo usuario interactivamente.
     */
    public UsuarioDTO registrarUsuario() {
        ConsoleUtils.printHeader("REGISTRO DE NUEVO USUARIO");
        
        // Solicitar datos
        String nombre = ConsoleUtils.readLine("Nombre: ");
        while (!ConsoleUtils.isNotEmpty(nombre)) {
            ConsoleUtils.printError("El nombre es requerido.");
            nombre = ConsoleUtils.readLine("Nombre: ");
        }
        
        String apellido = ConsoleUtils.readLine("Apellido: ");
        while (!ConsoleUtils.isNotEmpty(apellido)) {
            ConsoleUtils.printError("El apellido es requerido.");
            apellido = ConsoleUtils.readLine("Apellido: ");
        }
        
        String email = ConsoleUtils.readLine("Email: ");
        while (!ConsoleUtils.isValidEmail(email)) {
            ConsoleUtils.printError("Email invalido. Debe contener @ y .");
            email = ConsoleUtils.readLine("Email: ");
        }
        
        // Tipo de documento
        System.out.println("\nTipo de Documento:");
        System.out.println("1. DNI");
        System.out.println("2. PASAPORTE");
        System.out.println("3. CEDULA");
        int tipoOpcion = ConsoleUtils.readIntInRange("Seleccione opcion (1-3): ", 1, 3);
        
        String tipoDocumento;
        switch (tipoOpcion) {
            case 1: tipoDocumento = "DNI"; break;
            case 2: tipoDocumento = "PASAPORTE"; break;
            case 3: tipoDocumento = "CEDULA"; break;
            default: tipoDocumento = "DNI";
        }
        
        String numeroDocumento = ConsoleUtils.readLine("Numero de Documento: ");
        while (!ConsoleUtils.isNotEmpty(numeroDocumento)) {
            ConsoleUtils.printError("El numero de documento es requerido.");
            numeroDocumento = ConsoleUtils.readLine("Numero de Documento: ");
        }
        
        // Confirmar datos
        ConsoleUtils.printLine();
        System.out.println("Datos ingresados:");
        System.out.println("  Nombre: " + nombre + " " + apellido);
        System.out.println("  Email: " + email);
        System.out.println("  Documento: " + tipoDocumento + " - " + numeroDocumento);
        ConsoleUtils.printLine();
        
        if (!ConsoleUtils.readConfirmation("Confirmar registro")) {
            ConsoleUtils.printWarning("Registro cancelado.");
            return null;
        }
        
        // Crear usuario
        try {
            CrearUsuarioRequest request = new CrearUsuarioRequest(
                nombre, apellido, email, tipoDocumento, numeroDocumento
            );
            
            UsuarioDTO usuario = usuarioService.crearUsuario(request);
            ConsoleUtils.printSuccess("Usuario registrado exitosamente!");
            ConsoleUtils.printInfo("ID: " + usuario.getId());
            return usuario;
            
        } catch (Exception e) {
            ConsoleUtils.printError("Error al registrar usuario: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Busca un usuario por email.
     */
    public UsuarioDTO buscarPorEmail() {
        String email = ConsoleUtils.readLine("Ingrese email del usuario: ");
        
        Optional<UsuarioDTO> usuario = usuarioService.buscarPorEmail(email);
        
        if (usuario.isPresent()) {
            mostrarUsuario(usuario.get());
            return usuario.get();
        } else {
            ConsoleUtils.printWarning("Usuario no encontrado.");
            return null;
        }
    }
    
    /**
     * Lista todos los usuarios activos.
     */
    public void listarUsuarios() {
        ConsoleUtils.printHeader("LISTA DE USUARIOS ACTIVOS");
        
        List<UsuarioDTO> usuarios = usuarioService.obtenerUsuariosActivos();
        
        if (usuarios.isEmpty()) {
            ConsoleUtils.printInfo("No hay usuarios registrados.");
            return;
        }
        
        System.out.println("Total de usuarios: " + usuarios.size());
        ConsoleUtils.printLine();
        
        for (int i = 0; i < usuarios.size(); i++) {
            UsuarioDTO u = usuarios.get(i);
            System.out.println((i + 1) + ". " + u.getNombre() + " " + u.getApellido());
            System.out.println("   ID: " + u.getId());
            System.out.println("   Email: " + u.getEmail());
            System.out.println("   Documento: " + u.getTipoDocumento() + " - " + u.getNumeroDocumento());
            ConsoleUtils.printLine();
        }
    }
    
    /**
     * Muestra los detalles de un usuario.
     */
    private void mostrarUsuario(UsuarioDTO usuario) {
        ConsoleUtils.printLine();
        System.out.println("Usuario encontrado:");
        System.out.println("  ID: " + usuario.getId());
        System.out.println("  Nombre: " + usuario.getNombre() + " " + usuario.getApellido());
        System.out.println("  Email: " + usuario.getEmail());
        System.out.println("  Documento: " + usuario.getTipoDocumento() + " - " + usuario.getNumeroDocumento());
        System.out.println("  Activo: " + (usuario.isActivo() ? "Si" : "No"));
        ConsoleUtils.printLine();
    }
}
