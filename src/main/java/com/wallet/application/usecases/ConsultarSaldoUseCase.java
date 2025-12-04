package com.wallet.application.usecases;

import com.wallet.application.dtos.CuentaDTO;
import com.wallet.application.mappers.CuentaMapper;
import com.wallet.domain.entities.Cuenta;
import com.wallet.domain.exceptions.CuentaNoEncontradaException;
import com.wallet.domain.repositories.ICuentaRepository;

/**
 * Caso de uso para consultar el saldo de una cuenta.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class ConsultarSaldoUseCase {
    
    private final ICuentaRepository cuentaRepository;
    
    public ConsultarSaldoUseCase(ICuentaRepository cuentaRepository) {
        if (cuentaRepository == null) {
            throw new IllegalArgumentException("El repositorio de cuentas no puede ser nulo");
        }
        this.cuentaRepository = cuentaRepository;
    }
    
    /**
     * Consulta el saldo de una cuenta por su ID.
     * 
     * @param cuentaId ID de la cuenta
     * @return DTO de la cuenta con el saldo actual
     * @throws CuentaNoEncontradaException si la cuenta no existe
     */
    public CuentaDTO ejecutar(String cuentaId) {
        if (cuentaId == null || cuentaId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de la cuenta es requerido");
        }
        
        Cuenta cuenta = cuentaRepository.buscarPorId(cuentaId)
            .orElseThrow(() -> CuentaNoEncontradaException.porId(cuentaId));
        
        return CuentaMapper.toDTO(cuenta);
    }
    
    /**
     * Consulta el saldo de una cuenta por número de cuenta.
     * 
     * @param numeroCuenta número de cuenta
     * @return DTO de la cuenta con el saldo actual
     * @throws CuentaNoEncontradaException si la cuenta no existe
     */
    public CuentaDTO ejecutarPorNumero(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de cuenta es requerido");
        }
        
        Cuenta cuenta = cuentaRepository.buscarPorNumeroCuenta(numeroCuenta)
            .orElseThrow(() -> CuentaNoEncontradaException.porNumero(numeroCuenta));
        
        return CuentaMapper.toDTO(cuenta);
    }
}
