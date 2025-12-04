package com.wallet.application.usecases;

import com.wallet.application.dtos.CuentaDTO;
import com.wallet.application.mappers.CuentaMapper;
import com.wallet.domain.entities.Cuenta;
import com.wallet.domain.exceptions.OperacionNoValidaException;
import com.wallet.domain.exceptions.UsuarioNoEncontradoException;
import com.wallet.domain.repositories.ICuentaRepository;
import com.wallet.domain.repositories.IUsuarioRepository;

/**
 * Caso de uso para crear una nueva cuenta bancaria.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class CrearCuentaUseCase {
    
    private final ICuentaRepository cuentaRepository;
    private final IUsuarioRepository usuarioRepository;
    
    public CrearCuentaUseCase(ICuentaRepository cuentaRepository,
                              IUsuarioRepository usuarioRepository) {
        if (cuentaRepository == null) {
            throw new IllegalArgumentException("El repositorio de cuentas no puede ser nulo");
        }
        if (usuarioRepository == null) {
            throw new IllegalArgumentException("El repositorio de usuarios no puede ser nulo");
        }
        this.cuentaRepository = cuentaRepository;
        this.usuarioRepository = usuarioRepository;
    }
    
    /**
     * Ejecuta el caso de uso para crear una nueva cuenta.
     * 
     * @param usuarioId ID del usuario propietario de la cuenta
     * @return DTO de la cuenta creada
     * @throws UsuarioNoEncontradoException si el usuario no existe
     * @throws OperacionNoValidaException si el usuario ya tiene una cuenta
     */
    public CuentaDTO ejecutar(String usuarioId) {
        if (usuarioId == null || usuarioId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario es requerido");
        }
        
        // Verificar que el usuario existe
        if (!usuarioRepository.buscarPorId(usuarioId).isPresent()) {
            throw UsuarioNoEncontradoException.porId(usuarioId);
        }
        
        // Verificar que el usuario no tenga ya una cuenta
        if (!cuentaRepository.buscarPorUsuarioId(usuarioId).isEmpty()) {
            throw OperacionNoValidaException.porque(
                "El usuario ya tiene una cuenta asociada");
        }
        
        // Crear la cuenta
        Cuenta cuenta = new Cuenta(usuarioId);
        
        // Guardar en el repositorio
        Cuenta cuentaGuardada = cuentaRepository.guardar(cuenta);
        
        // Convertir a DTO y retornar
        return CuentaMapper.toDTO(cuentaGuardada);
    }
}
