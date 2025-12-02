package com.wallet.infrastructure.services;

import com.wallet.application.dtos.CuentaDTO;
import com.wallet.application.dtos.TransaccionDTO;
import com.wallet.application.dtos.requests.DepositarDineroRequest;
import com.wallet.application.dtos.requests.RetirarDineroRequest;
import com.wallet.application.mappers.CuentaMapper;
import com.wallet.application.usecases.*;
import com.wallet.domain.entities.Cuenta;
import com.wallet.domain.entities.Usuario;
import com.wallet.domain.exceptions.UsuarioNoEncontradoException;
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
        
        this.crearCuentaUseCase = new CrearCuentaUseCase(cuentaRepo, usuarioRepo);
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
            CuentaDTO cuenta = crearCuentaUseCase.ejecutar(usuarioId);
            Logger.info("Cuenta creada exitosamente: " + cuenta.getNumeroCuenta());
            return cuenta;
        } catch (Exception e) {
            Logger.error("Error al crear cuenta", e);
            throw e;
        }
    }
    
    /**
     * Crea una nueva cuenta buscando usuario por email.
     */
    public CuentaDTO crearCuentaPorEmail(String email) {
        Logger.info("Creando cuenta para usuario con email: " + email);
        
        try {
            // Buscar usuario por email
            Optional<Usuario> usuarioOpt = RepositoryFactory.getUsuarioRepository()
                .buscarPorEmail(new com.wallet.domain.valueobjects.Email(email));
            
            if (!usuarioOpt.isPresent()) {
                throw new UsuarioNoEncontradoException("No existe un usuario con el email: " + email);
            }
            
            Usuario usuario = usuarioOpt.get();
            CuentaDTO cuenta = crearCuentaUseCase.ejecutar(usuario.getId());
            Logger.info("Cuenta creada exitosamente: " + cuenta.getNumeroCuenta());
            return cuenta;
        } catch (Exception e) {
            Logger.error("Error al crear cuenta", e);
            throw e;
        }
    }
    
    /**
     * Deposita dinero en una cuenta.
     */
    public TransaccionDTO depositar(DepositarDineroRequest request) {
        Logger.info("Depositando $" + request.getMonto() + " en cuenta: " + request.getCuentaId());
        
        try {
            TransaccionDTO transaccion = depositarDineroUseCase.ejecutar(request);
            Logger.info("Deposito exitoso. Saldo actualizado.");
            return transaccion;
        } catch (Exception e) {
            Logger.error("Error al depositar dinero", e);
            throw e;
        }
    }
    
    /**
     * Deposita dinero en una cuenta usando el número de cuenta.
     */
    public TransaccionDTO depositarPorNumero(String numeroCuenta, BigDecimal monto, String descripcion) {
        Logger.info("Depositando $" + monto + " en cuenta: " + numeroCuenta);
        
        try {
            // Buscar cuenta por número para obtener el ID
            Optional<Cuenta> cuentaOpt = RepositoryFactory.getCuentaRepository()
                .buscarPorNumeroCuenta(numeroCuenta);
            
            if (!cuentaOpt.isPresent()) {
                throw new com.wallet.domain.exceptions.CuentaNoEncontradaException(
                    "No existe una cuenta con el número: " + numeroCuenta
                );
            }
            
            Cuenta cuenta = cuentaOpt.get();
            DepositarDineroRequest request = new DepositarDineroRequest(
                cuenta.getId(), monto, descripcion
            );
            
            TransaccionDTO transaccion = depositarDineroUseCase.ejecutar(request);
            Logger.info("Deposito exitoso. Saldo actualizado.");
            return transaccion;
        } catch (Exception e) {
            Logger.error("Error al depositar dinero", e);
            throw e;
        }
    }
    
    /**
     * Retira dinero de una cuenta.
     */
    public TransaccionDTO retirar(RetirarDineroRequest request) {
        Logger.info("Retirando $" + request.getMonto() + " de cuenta: " + request.getCuentaId());
        
        try {
            TransaccionDTO transaccion = retirarDineroUseCase.ejecutar(request);
            Logger.info("Retiro exitoso. Saldo actualizado.");
            return transaccion;
        } catch (Exception e) {
            Logger.error("Error al retirar dinero", e);
            throw e;
        }
    }
    
    /**
     * Retira dinero de una cuenta usando el número de cuenta.
     */
    public TransaccionDTO retirarPorNumero(String numeroCuenta, BigDecimal monto, String descripcion) {
        Logger.info("Retirando $" + monto + " de cuenta: " + numeroCuenta);
        
        try {
            // Buscar cuenta por número para obtener el ID
            Optional<Cuenta> cuentaOpt = RepositoryFactory.getCuentaRepository()
                .buscarPorNumeroCuenta(numeroCuenta);
            
            if (!cuentaOpt.isPresent()) {
                throw new com.wallet.domain.exceptions.CuentaNoEncontradaException(
                    "No existe una cuenta con el número: " + numeroCuenta
                );
            }
            
            Cuenta cuenta = cuentaOpt.get();
            RetirarDineroRequest request = new RetirarDineroRequest(
                cuenta.getId(), monto, descripcion
            );
            
            TransaccionDTO transaccion = retirarDineroUseCase.ejecutar(request);
            Logger.info("Retiro exitoso. Saldo actualizado.");
            return transaccion;
        } catch (Exception e) {
            Logger.error("Error al retirar dinero", e);
            throw e;
        }
    }
    
    /**
     * Consulta el saldo de una cuenta.
     */
    public CuentaDTO consultarSaldo(String numeroCuenta) {
        Logger.debug("Consultando saldo de cuenta: " + numeroCuenta);
        
        return consultarSaldoUseCase.ejecutarPorNumero(numeroCuenta);
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
