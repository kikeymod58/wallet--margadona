package com.wallet.application.usecases;

import com.wallet.application.dtos.TransaccionDTO;
import com.wallet.application.dtos.requests.TransferirDineroRequest;
import com.wallet.application.mappers.TransaccionMapper;
import com.wallet.domain.entities.Cuenta;
import com.wallet.domain.entities.Transaccion;
import com.wallet.domain.exceptions.CuentaNoEncontradaException;
import com.wallet.domain.exceptions.OperacionNoValidaException;
import com.wallet.domain.repositories.ICuentaRepository;
import com.wallet.domain.repositories.ITransaccionRepository;
import com.wallet.domain.valueobjects.Dinero;

import java.util.ArrayList;
import java.util.List;

/**
 * Caso de uso para transferir dinero entre cuentas.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class TransferirDineroUseCase {
    
    private final ICuentaRepository cuentaRepository;
    private final ITransaccionRepository transaccionRepository;
    
    public TransferirDineroUseCase(ICuentaRepository cuentaRepository,
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
     * Ejecuta el caso de uso para transferir dinero.
     * 
     * @param request datos de la transferencia
     * @return lista con ambas transacciones (envío y recepción)
     * @throws CuentaNoEncontradaException si alguna cuenta no existe
     * @throws OperacionNoValidaException si las cuentas son iguales o diferentes monedas
     */
    public List<TransaccionDTO> ejecutar(TransferirDineroRequest request) {
        validarRequest(request);
        
        // Validar que las cuentas no sean la misma
        if (request.getCuentaOrigenId().equals(request.getCuentaDestinoId())) {
            throw OperacionNoValidaException.porque(
                "No se puede transferir a la misma cuenta");
        }
        
        // Buscar ambas cuentas
        Cuenta cuentaOrigen = cuentaRepository.buscarPorId(request.getCuentaOrigenId())
            .orElseThrow(() -> CuentaNoEncontradaException.porId(request.getCuentaOrigenId()));
        
        Cuenta cuentaDestino = cuentaRepository.buscarPorId(request.getCuentaDestinoId())
            .orElseThrow(() -> CuentaNoEncontradaException.porId(request.getCuentaDestinoId()));
        
        // Validar que ambas cuentas tengan la misma moneda
        if (!cuentaOrigen.getSaldo().getMoneda().equals(cuentaDestino.getSaldo().getMoneda())) {
            throw OperacionNoValidaException.porque(
                "Las cuentas deben tener la misma moneda para realizar transferencias");
        }
        
        // Crear el monto
        Dinero monto = Dinero.de(request.getMonto());
        
        // Guardar saldos anteriores
        Dinero saldoAnteriorOrigen = cuentaOrigen.getSaldo();
        Dinero saldoAnteriorDestino = cuentaDestino.getSaldo();
        
        // Realizar la transferencia (retiro de origen, depósito en destino)
        cuentaOrigen.retirar(monto);
        cuentaDestino.depositar(monto);
        
        // Guardar saldos nuevos
        Dinero saldoNuevoOrigen = cuentaOrigen.getSaldo();
        Dinero saldoNuevoDestino = cuentaDestino.getSaldo();
        
        // Guardar las cuentas actualizadas
        cuentaRepository.guardar(cuentaOrigen);
        cuentaRepository.guardar(cuentaDestino);
        
        // Crear las transacciones
        String descripcion = request.getDescripcion() != null ? 
            request.getDescripcion() : "Transferencia";
        
        Transaccion transaccionEnviada = Transaccion.transferenciaEnviada(
            monto,
            cuentaOrigen.getId(),
            cuentaDestino.getId(),
            descripcion,
            saldoAnteriorOrigen,
            saldoNuevoOrigen
        );
        
        Transaccion transaccionRecibida = Transaccion.transferenciaRecibida(
            monto,
            cuentaDestino.getId(),
            cuentaOrigen.getId(),
            descripcion,
            saldoAnteriorDestino,
            saldoNuevoDestino
        );
        
        // Guardar las transacciones
        Transaccion enviada = transaccionRepository.guardar(transaccionEnviada);
        Transaccion recibida = transaccionRepository.guardar(transaccionRecibida);
        
        // Convertir a DTOs y retornar
        List<TransaccionDTO> resultado = new ArrayList<>();
        resultado.add(TransaccionMapper.toDTO(enviada));
        resultado.add(TransaccionMapper.toDTO(recibida));
        
        return resultado;
    }
    
    private void validarRequest(TransferirDineroRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }
        if (request.getCuentaOrigenId() == null || request.getCuentaOrigenId().trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de la cuenta origen es requerido");
        }
        if (request.getCuentaDestinoId() == null || request.getCuentaDestinoId().trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de la cuenta destino es requerido");
        }
        if (request.getMonto() == null) {
            throw new IllegalArgumentException("El monto es requerido");
        }
    }
}
