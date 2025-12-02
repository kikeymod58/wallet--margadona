package com.wallet.infrastructure.services;

import com.wallet.application.dtos.TransaccionDTO;
import com.wallet.application.dtos.requests.TransferirDineroRequest;
import com.wallet.application.mappers.TransaccionMapper;
import com.wallet.application.usecases.TransferirDineroUseCase;
import com.wallet.application.usecases.ConsultarHistorialUseCase;
import com.wallet.domain.entities.Transaccion;
import com.wallet.domain.valueobjects.TipoTransaccion;
import com.wallet.infrastructure.factories.RepositoryFactory;
import com.wallet.infrastructure.logging.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de Transacciones (Facade Pattern).
 * 
 * Orquesta los casos de uso relacionados con transacciones.
 * Gestiona transferencias y consultas de historial.
 */
public class TransaccionService {
    
    private final TransferirDineroUseCase transferirDineroUseCase;
    private final ConsultarHistorialUseCase consultarHistorialUseCase;
    
    public TransaccionService() {
        var cuentaRepo = RepositoryFactory.getCuentaRepository();
        var transaccionRepo = RepositoryFactory.getTransaccionRepository();
        
        this.transferirDineroUseCase = new TransferirDineroUseCase(cuentaRepo, transaccionRepo);
        this.consultarHistorialUseCase = new ConsultarHistorialUseCase(cuentaRepo, transaccionRepo);
    }
    
    /**
     * Transfiere dinero entre cuentas.
     * Retorna la transacción de TRANSFERENCIA_SALIDA.
     */
    public List<TransaccionDTO> transferir(TransferirDineroRequest request) {
        Logger.info("Transfiriendo $" + request.getMonto() + 
                   " de " + request.getCuentaOrigenId() + 
                   " a " + request.getCuentaDestinoId());
        
        try {
            List<TransaccionDTO> transacciones = transferirDineroUseCase.ejecutar(request);
            Logger.info("Transferencia exitosa.");
            return transacciones;
        } catch (Exception e) {
            Logger.error("Error al transferir dinero", e);
            throw e;
        }
    }
    
    /**
     * Consulta el historial completo de una cuenta.
     */
    public List<TransaccionDTO> consultarHistorial(String numeroCuenta) {
        Logger.debug("Consultando historial de cuenta: " + numeroCuenta);
        
        // Buscar cuenta por número
        var cuenta = RepositoryFactory.getCuentaRepository()
            .buscarPorNumeroCuenta(numeroCuenta)
            .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
        
        return consultarHistorialUseCase.ejecutar(cuenta.getId());
    }
    
    /**
     * Consulta las últimas N transacciones de una cuenta.
     */
    public List<TransaccionDTO> consultarUltimas(String cuentaId, int limite) {
        Logger.debug("Consultando últimas " + limite + " transacciones de cuenta: " + cuentaId);
        
        return RepositoryFactory.getTransaccionRepository()
            .buscarPorCuentaId(cuentaId)
            .stream()
            .limit(limite)
            .map(TransaccionMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Consulta transacciones por tipo.
     */
    public List<TransaccionDTO> consultarPorTipo(String numeroCuenta, TipoTransaccion tipo) {
        Logger.debug("Consultando transacciones de tipo " + tipo + " para cuenta: " + numeroCuenta);
        
        // Primero buscar la cuenta por número
        var cuenta = RepositoryFactory.getCuentaRepository()
            .buscarPorNumeroCuenta(numeroCuenta)
            .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
        
        return RepositoryFactory.getTransaccionRepository()
            .buscarPorCuentaIdYTipo(cuenta.getId(), tipo)
            .stream()
            .map(TransaccionMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Consulta transacciones en un rango de fechas.
     */
    public List<TransaccionDTO> consultarPorFechas(String cuentaId, 
                                                    LocalDateTime fechaInicio, 
                                                    LocalDateTime fechaFin) {
        Logger.debug("Consultando transacciones entre " + fechaInicio + " y " + fechaFin);
        
        return RepositoryFactory.getTransaccionRepository()
            .obtenerPorCuentaYFechas(cuentaId, fechaInicio, fechaFin)
            .stream()
            .map(TransaccionMapper::toDTO)
            .collect(Collectors.toList());
    }
}
