package com.wallet.domain.repositories;

import com.wallet.domain.entities.Cuenta;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz del repositorio de Cuentas (Port).
 * Define el contrato para persistencia de cuentas.
 * 
 * Principios aplicados:
 * - DIP: Dependencia hacia abstracción
 * - ISP: Interfaz específica y cohesiva
 */
public interface ICuentaRepository {
    
    /**
     * Guarda una cuenta nueva o actualiza una existente.
     * 
     * @param cuenta la cuenta a guardar
     * @return la cuenta guardada
     */
    Cuenta guardar(Cuenta cuenta);
    
    /**
     * Busca una cuenta por su ID.
     * 
     * @param id el ID de la cuenta
     * @return Optional con la cuenta si existe
     */
    Optional<Cuenta> buscarPorId(String id);
    
    /**
     * Busca una cuenta por su número.
     * 
     * @param numeroCuenta el número de cuenta
     * @return Optional con la cuenta si existe
     */
    Optional<Cuenta> buscarPorNumeroCuenta(String numeroCuenta);
    
    /**
     * Obtiene todas las cuentas de un usuario.
     * 
     * @param usuarioId el ID del usuario
     * @return lista de cuentas del usuario
     */
    List<Cuenta> buscarPorUsuarioId(String usuarioId);
    
    /**
     * Obtiene todas las cuentas activas de un usuario.
     * 
     * @param usuarioId el ID del usuario
     * @return lista de cuentas activas
     */
    List<Cuenta> obtenerActivasPorUsuario(String usuarioId);
    
    /**
     * Obtiene todas las cuentas.
     * 
     * @return lista de todas las cuentas
     */
    List<Cuenta> obtenerTodas();
    
    /**
     * Elimina una cuenta por su ID.
     * 
     * @param id el ID de la cuenta
     * @return true si se eliminó correctamente
     */
    boolean eliminar(String id);
    
    /**
     * Verifica si existe una cuenta con el número dado.
     * 
     * @param numeroCuenta el número de cuenta
     * @return true si existe
     */
    boolean existeNumeroCuenta(String numeroCuenta);
}
