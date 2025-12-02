package com.wallet.domain.repositories;

import com.wallet.domain.entities.Transaccion;
import com.wallet.domain.valueobjects.TipoTransaccion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz del repositorio de Transacciones (Port).
 * Define el contrato para persistencia de transacciones.
 * 
 * Principios aplicados:
 * - DIP: Inversión de dependencias
 * - ISP: Interfaz segregada
 */
public interface ITransaccionRepository {
    
    /**
     * Guarda una nueva transacción.
     * 
     * @param transaccion la transacción a guardar
     * @return la transacción guardada
     */
    Transaccion guardar(Transaccion transaccion);
    
    /**
     * Busca una transacción por su ID.
     * 
     * @param id el ID de la transacción
     * @return Optional con la transacción si existe
     */
    Optional<Transaccion> buscarPorId(String id);
    
    /**
     * Obtiene todas las transacciones de una cuenta.
     * 
     * @param cuentaId el ID de la cuenta
     * @return lista de transacciones
     */
    List<Transaccion> buscarPorCuentaId(String cuentaId);
    
    /**
     * Obtiene transacciones de una cuenta por tipo.
     * 
     * @param cuentaId el ID de la cuenta
     * @param tipo el tipo de transacción
     * @return lista de transacciones filtradas
     */
    List<Transaccion> buscarPorCuentaIdYTipo(String cuentaId, TipoTransaccion tipo);
    
    /**
     * Obtiene transacciones de una cuenta en un rango de fechas.
     * 
     * @param cuentaId el ID de la cuenta
     * @param fechaInicio fecha de inicio
     * @param fechaFin fecha de fin
     * @return lista de transacciones en el rango
     */
    List<Transaccion> obtenerPorCuentaYFechas(String cuentaId, 
                                              LocalDateTime fechaInicio, 
                                              LocalDateTime fechaFin);
    
    /**
     * Obtiene todas las transacciones.
     * 
     * @return lista de todas las transacciones
     */
    List<Transaccion> obtenerTodas();
    
    /**
     * Obtiene las últimas N transacciones de una cuenta.
     * 
     * @param cuentaId el ID de la cuenta
     * @param limite número máximo de transacciones
     * @return lista de últimas transacciones
     */
    List<Transaccion> obtenerUltimasPorCuenta(String cuentaId, int limite);
}
