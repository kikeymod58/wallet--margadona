package com.wallet.infrastructure.services;

import com.wallet.application.dtos.CuentaDTO;
import com.wallet.application.dtos.DepositarDineroRequest;
import com.wallet.application.dtos.RetirarDineroRequest;
import com.wallet.application.mappers.CuentaMapper;
import com.wallet.application.usecases.*;
import com.wallet.domain.entities.Cuenta;
import com.wallet.infrastructure.factories.RepositoryFactory;
import com.wallet.infrastructure.logging.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio de Cuentas (Facade Pattern).
 * 
 * Orquesta los casos de uso relacionados con cuentas.
 * Gestiona operaciones de creación, depósito, retiro y consulta de saldo.
 */
public class CuentaService {
    
    private final CrearCuentaUseCase crearCuentaUseCase;
    private final DepositarDineroUseCase depositarDineroUseCase;
    private final RetirarDineroUseCase retirarDineroUseCase;
    private final ConsultarSaldoUseCase consultarSaldoUseCase;
    
    public CuentaService() {
        var usuarioRepo = RepositoryFactory.getUsuarioRepository();
        var cuentaRepo = RepositoryFactory.getCuentaRepository();
        var transaccionRepo = RepositoryFactory.getTransaccionRepository();
        
        this.crearCuentaUseCase = new CrearCuentaUseCase(usuarioRepo, cuentaRepo);
        this.depositarDineroUseCase = new DepositarDineroUseCase(cuentaRepo, transaccionRepo);
        this.retirarDineroUseCase = new RetirarDineroUseCase(cuentaRepo, transaccionRepo);
        this.consultarSaldoUseCase = new ConsultarSaldoUseCase(cuentaRepo);
    }
    
    /**
     * Crea una nueva cuenta para un usuario.
     */
    public CuentaDTO crearCuenta(String usuarioId) {
        Logger.info("Creando cuenta para usuario: " + usuarioId);
        
        try {
            Cuenta cuenta = crearCuentaUseCase.ejecutar(usuarioId);
            Logger.info("Cuenta creada exitosamente: " + cuenta.getNumeroCuenta());
            return CuentaMapper.toDTO(cuenta);
        } catch (Exception e) {
            Logger.error("Error al crear cuenta", e);
            throw e;
        }
    }
    
    /**
     * Deposita dinero en una cuenta.
     */
    public CuentaDTO depositar(DepositarDineroRequest request) {
        Logger.info("Depositando $" + request.monto() + " en cuenta: " + request.numeroCuenta());
        
        try {
            Cuenta cuenta = depositarDineroUseCase.ejecutar(request);
            Logger.info("Depósito exitoso. Nuevo saldo: $" + cuenta.getSaldo().getMonto());
            return CuentaMapper.toDTO(cuenta);
        } catch (Exception e) {
            Logger.error("Error al depositar dinero", e);
            throw e;
        }
    }
    
    /**
     * Retira dinero de una cuenta.
     */
    public CuentaDTO retirar(RetirarDineroRequest request) {
        Logger.info("Retirando $" + request.monto() + " de cuenta: " + request.numeroCuenta());
        
        try {
            Cuenta cuenta = retirarDineroUseCase.ejecutar(request);
            Logger.info("Retiro exitoso. Nuevo saldo: $" + cuenta.getSaldo().getMonto());
            return CuentaMapper.toDTO(cuenta);
        } catch (Exception e) {
            Logger.error("Error al retirar dinero", e);
            throw e;
        }
    }
    
    /**
     * Consulta el saldo de una cuenta.
     */
    public BigDecimal consultarSaldo(String numeroCuenta) {
        Logger.debug("Consultando saldo de cuenta: " + numeroCuenta);
        
        return consultarSaldoUseCase.ejecutar(numeroCuenta);
    }
    
    /**
     * Busca una cuenta por número.
     */
    public Optional<CuentaDTO> buscarPorNumeroCuenta(String numeroCuenta) {
        Logger.debug("Buscando cuenta: " + numeroCuenta);
        
        Optional<Cuenta> cuenta = RepositoryFactory.getCuentaRepository()
            .buscarPorNumeroCuenta(numeroCuenta);
        return cuenta.map(CuentaMapper::toDTO);
    }
    
    /**
     * Busca cuentas por usuario.
     */
    public List<CuentaDTO> buscarPorUsuario(String usuarioId) {
        Logger.debug("Buscando cuentas del usuario: " + usuarioId);
        
        return RepositoryFactory.getCuentaRepository()
            .buscarPorUsuarioId(usuarioId)
            .stream()
            .map(CuentaMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene todas las cuentas activas de un usuario.
     */
    public List<CuentaDTO> obtenerCuentasActivas(String usuarioId) {
        Logger.debug("Obteniendo cuentas activas del usuario: " + usuarioId);
        
        return RepositoryFactory.getCuentaRepository()
            .obtenerActivasPorUsuario(usuarioId)
            .stream()
            .map(CuentaMapper::toDTO)
            .collect(Collectors.toList());
    }
}
