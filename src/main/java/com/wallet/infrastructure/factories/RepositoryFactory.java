package com.wallet.infrastructure.factories;

import com.wallet.domain.repositories.IUsuarioRepository;
import com.wallet.domain.repositories.ICuentaRepository;
import com.wallet.domain.repositories.ITransaccionRepository;
import com.wallet.infrastructure.repositories.UsuarioRepositoryInMemory;
import com.wallet.infrastructure.repositories.CuentaRepositoryInMemory;
import com.wallet.infrastructure.repositories.TransaccionRepositoryInMemory;

/**
 * Fábrica de Repositorios (Factory Pattern).
 * 
 * Centraliza la creación de instancias de repositorios.
 * Utiliza Singleton para garantizar una única instancia de cada repositorio.
 * 
 * Ventajas:
 * - Desacoplamiento: Los clientes no conocen las implementaciones concretas
 * - Reutilización: Una única instancia compartida (memoria)
 * - Flexibilidad: Fácil cambio de implementaciones
 */
public class RepositoryFactory {
    
    // Instancias únicas (Singleton)
    private static final IUsuarioRepository usuarioRepository = new UsuarioRepositoryInMemory();
    private static final ICuentaRepository cuentaRepository = new CuentaRepositoryInMemory();
    private static final ITransaccionRepository transaccionRepository = new TransaccionRepositoryInMemory();
    
    // Constructor privado para evitar instanciación
    private RepositoryFactory() {
        throw new AssertionError("No se debe instanciar RepositoryFactory");
    }
    
    /**
     * Obtiene la instancia del repositorio de usuarios.
     */
    public static IUsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }
    
    /**
     * Obtiene la instancia del repositorio de cuentas.
     */
    public static ICuentaRepository getCuentaRepository() {
        return cuentaRepository;
    }
    
    /**
     * Obtiene la instancia del repositorio de transacciones.
     */
    public static ITransaccionRepository getTransaccionRepository() {
        return transaccionRepository;
    }
    
    /**
     * Limpia todos los repositorios (útil para testing).
     */
    public static void limpiarTodos() {
        if (usuarioRepository instanceof UsuarioRepositoryInMemory) {
            ((UsuarioRepositoryInMemory) usuarioRepository).limpiar();
        }
        if (cuentaRepository instanceof CuentaRepositoryInMemory) {
            ((CuentaRepositoryInMemory) cuentaRepository).limpiar();
        }
        if (transaccionRepository instanceof TransaccionRepositoryInMemory) {
            ((TransaccionRepositoryInMemory) transaccionRepository).limpiar();
        }
    }
}
