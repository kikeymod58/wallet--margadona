package com.wallet.application.usecases;

import com.wallet.application.dtos.TransaccionDTO;
import com.wallet.application.mappers.TransaccionMapper;
import com.wallet.domain.entities.Transaccion;
import com.wallet.domain.exceptions.CuentaNoEncontradaException;
import com.wallet.domain.repositories.ICuentaRepository;
import com.wallet.domain.repositories.ITransaccionRepository;
import com.wallet.domain.valueobjects.TipoTransaccion;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Caso de uso para consultar el historial de transacciones de una cuenta.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class ConsultarHistorialUseCase {
    
    private final ICuentaRepository cuentaRepository;
    private final ITransaccionRepository transaccionRepository;
    
    public ConsultarHistorialUseCase(ICuentaRepository cuentaRepository,
                                     ITransaccionRepository transaccionRepository) {
        if (cuentaRepository == null) {
            throw new IllegalArgumentException("El repositorio de cuentas no puede ser nulo");
        }
        if (transaccionRepository == null) {
            throw new IllegalArgumentException("El repositorio de transacciones no puede ser nulo");
        }
        this.cuentaRepository = cuentaRepository;
        this.transaccionRepository = transaccionRepository;
    }
    
    /**
     * Consulta todas las transacciones de una cuenta.
     * 
     * @param cuentaId ID de la cuenta
     * @return lista de DTOs de transacciones
     * @throws CuentaNoEncontradaException si la cuenta no existe
     */
    public List<TransaccionDTO> ejecutar(String cuentaId) {
        if (cuentaId == null || cuentaId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de la cuenta es requerido");
        }
        
        // Verificar que la cuenta existe
        if (!cuentaRepository.buscarPorId(cuentaId).isPresent()) {
            throw CuentaNoEncontradaException.porId(cuentaId);
        }
        
        // Obtener todas las transacciones de la cuenta
        List<Transaccion> transacciones = transaccionRepository.buscarPorCuentaId(cuentaId);
        
        // Convertir a DTOs
        return transacciones.stream()
            .map(TransaccionMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Consulta las transacciones de una cuenta filtradas por tipo.
     * 
     * @param cuentaId ID de la cuenta
     * @param tipo tipo de transacción (DEPOSITO, RETIRO, etc.)
     * @return lista de DTOs de transacciones del tipo especificado
     * @throws CuentaNoEncontradaException si la cuenta no existe
     */
    public List<TransaccionDTO> ejecutarPorTipo(String cuentaId, String tipo) {
        if (cuentaId == null || cuentaId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de la cuenta es requerido");
        }
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de transacción es requerido");
        }
        
        // Verificar que la cuenta existe
        if (!cuentaRepository.buscarPorId(cuentaId).isPresent()) {
            throw CuentaNoEncontradaException.porId(cuentaId);
        }
        
        // Convertir string a enum
        TipoTransaccion tipoTransaccion = TipoTransaccion.valueOf(tipo);
        
        // Obtener transacciones filtradas
        List<Transaccion> transacciones = transaccionRepository
            .buscarPorCuentaIdYTipo(cuentaId, tipoTransaccion);
        
        // Convertir a DTOs
        return transacciones.stream()
            .map(TransaccionMapper::toDTO)
            .collect(Collectors.toList());
    }
}
