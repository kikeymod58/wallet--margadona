package com.wallet.infrastructure.repositories;

import com.wallet.domain.entities.Cuenta;
import com.wallet.domain.repositories.ICuentaRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implementación en memoria del repositorio de cuentas.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class CuentaRepositoryInMemory implements ICuentaRepository {
    
    private final Map<String, Cuenta> cuentas;
    
    public CuentaRepositoryInMemory() {
        this.cuentas = new ConcurrentHashMap<>();
    }
    
    @Override
    public Cuenta guardar(Cuenta cuenta) {
        if (cuenta == null) {
            throw new IllegalArgumentException("La cuenta no puede ser nula");
        }
        cuentas.put(cuenta.getId(), cuenta);
        return cuenta;
    }
    
    @Override
    public Optional<Cuenta> buscarPorId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(cuentas.get(id));
    }
    
    @Override
    public Optional<Cuenta> buscarPorNumeroCuenta(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.trim().isEmpty()) {
            return Optional.empty();
        }
        return cuentas.values().stream()
            .filter(c -> c.getNumeroCuenta().equals(numeroCuenta))
            .findFirst();
    }
    
    @Override
    public List<Cuenta> buscarPorUsuarioId(String usuarioId) {
        if (usuarioId == null || usuarioId.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return cuentas.values().stream()
            .filter(c -> c.getUsuarioId().equals(usuarioId))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Cuenta> obtenerActivasPorUsuario(String usuarioId) {
        if (usuarioId == null || usuarioId.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return cuentas.values().stream()
            .filter(c -> c.getUsuarioId().equals(usuarioId) && c.isActiva())
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Cuenta> obtenerTodas() {
        return new ArrayList<>(cuentas.values());
    }
    
    @Override
    public boolean eliminar(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        return cuentas.remove(id) != null;
    }
    
    @Override
    public boolean existeNumeroCuenta(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.trim().isEmpty()) {
            return false;
        }
        return cuentas.values().stream()
            .anyMatch(c -> c.getNumeroCuenta().equals(numeroCuenta));
    }
    
    /**
     * Limpia todas las cuentas (útil para testing).
     */
    public void limpiar() {
        cuentas.clear();
    }
    
    /**
     * Retorna el número de cuentas almacenadas.
     */
    public int contar() {
        return cuentas.size();
    }
}
