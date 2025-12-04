package com.wallet.application.usecases;

import com.wallet.application.dtos.TransaccionDTO;
import com.wallet.application.dtos.requests.DepositarDineroRequest;
import com.wallet.application.mappers.TransaccionMapper;
import com.wallet.domain.entities.Cuenta;
import com.wallet.domain.entities.Transaccion;
import com.wallet.domain.exceptions.CuentaNoEncontradaException;
import com.wallet.domain.repositories.ICuentaRepository;
import com.wallet.domain.repositories.ITransaccionRepository;
import com.wallet.domain.valueobjects.Dinero;

/**
 * Caso de uso para depositar dinero en una cuenta.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class DepositarDineroUseCase {
    
    private final ICuentaRepository cuentaRepository;
    private final ITransaccionRepository transaccionRepository;
    
    public DepositarDineroUseCase(ICuentaRepository cuentaRepository,
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
     * Ejecuta el caso de uso para depositar dinero.
     * 
     * @param request datos del depósito
     * @return DTO de la transacción realizada
     * @throws CuentaNoEncontradaException si la cuenta no existe
     */
    public TransaccionDTO ejecutar(DepositarDineroRequest request) {
        validarRequest(request);
        
        // Buscar la cuenta
        Cuenta cuenta = cuentaRepository.buscarPorId(request.getCuentaId())
            .orElseThrow(() -> CuentaNoEncontradaException.porId(request.getCuentaId()));
        
        // Crear el monto
        Dinero monto = Dinero.de(request.getMonto());
        Dinero saldoAnterior = cuenta.getSaldo();
        
        // Realizar el depósito
        cuenta.depositar(monto);
        Dinero saldoNuevo = cuenta.getSaldo();
        
        // Guardar la cuenta actualizada
        cuentaRepository.guardar(cuenta);
        
        // Crear la transacción
        Transaccion transaccion = Transaccion.deposito(
            monto,
            cuenta.getId(),
            request.getDescripcion() != null ? request.getDescripcion() : "Depósito",
            saldoAnterior,
            saldoNuevo
        );
        
        // Guardar la transacción
        Transaccion transaccionGuardada = transaccionRepository.guardar(transaccion);
        
        // Convertir a DTO y retornar
        return TransaccionMapper.toDTO(transaccionGuardada);
    }
    
    private void validarRequest(DepositarDineroRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }
        if (request.getCuentaId() == null || request.getCuentaId().trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de la cuenta es requerido");
        }
        if (request.getMonto() == null) {
            throw new IllegalArgumentException("El monto es requerido");
        }
    }
}
