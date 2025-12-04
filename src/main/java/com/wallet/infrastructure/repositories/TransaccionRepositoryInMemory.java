package com.wallet.infrastructure.repositories;

import com.wallet.domain.entities.Transaccion;
import com.wallet.domain.repositories.ITransaccionRepository;
import com.wallet.domain.valueobjects.TipoTransaccion;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implementación en memoria del repositorio de transacciones.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class TransaccionRepositoryInMemory implements ITransaccionRepository {
    
    private final Map<String, Transaccion> transacciones;
    
    public TransaccionRepositoryInMemory() {
        this.transacciones = new ConcurrentHashMap<>();
    }
    
    @Override
    public Transaccion guardar(Transaccion transaccion) {
        if (transaccion == null) {
            throw new IllegalArgumentException("La transacción no puede ser nula");
        }
        transacciones.put(transaccion.getId(), transaccion);
        return transaccion;
    }
    
    @Override
    public Optional<Transaccion> buscarPorId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(transacciones.get(id));
    }
    
    @Override
    public List<Transaccion> buscarPorCuentaId(String cuentaId) {
        if (cuentaId == null || cuentaId.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return transacciones.values().stream()
            .filter(t -> t.getCuentaOrigenId().equals(cuentaId) || 
                        (t.getCuentaDestinoId() != null && t.getCuentaDestinoId().equals(cuentaId)))
            .sorted(Comparator.comparing(Transaccion::getFecha).reversed())
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Transaccion> buscarPorCuentaIdYTipo(String cuentaId, TipoTransaccion tipo) {
        if (cuentaId == null || cuentaId.trim().isEmpty() || tipo == null) {
            return Collections.emptyList();
        }
        return transacciones.values().stream()
            .filter(t -> (t.getCuentaOrigenId().equals(cuentaId) || 
                         (t.getCuentaDestinoId() != null && t.getCuentaDestinoId().equals(cuentaId)))
                         && t.getTipo() == tipo)
            .sorted(Comparator.comparing(Transaccion::getFecha).reversed())
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Transaccion> obtenerPorCuentaYFechas(String cuentaId, 
                                                      LocalDateTime fechaInicio, 
                                                      LocalDateTime fechaFin) {
        if (cuentaId == null || cuentaId.trim().isEmpty() || 
            fechaInicio == null || fechaFin == null) {
            return Collections.emptyList();
        }
        return transacciones.values().stream()
            .filter(t -> (t.getCuentaOrigenId().equals(cuentaId) || 
                         (t.getCuentaDestinoId() != null && t.getCuentaDestinoId().equals(cuentaId)))
                         && !t.getFecha().isBefore(fechaInicio)
                         && !t.getFecha().isAfter(fechaFin))
            .sorted(Comparator.comparing(Transaccion::getFecha).reversed())
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Transaccion> obtenerTodas() {
        return transacciones.values().stream()
            .sorted(Comparator.comparing(Transaccion::getFecha).reversed())
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene las últimas N transacciones de una cuenta.
     */
    @Override
    public List<Transaccion> obtenerUltimasPorCuenta(String cuentaId, int limite) {
        if (cuentaId == null || cuentaId.trim().isEmpty() || limite <= 0) {
            return Collections.emptyList();
        }
        return buscarPorCuentaId(cuentaId).stream()
            .limit(limite)
            .collect(Collectors.toList());
    }
    
    /**
     * Limpia todas las transacciones (útil para testing).
     */
    public void limpiar() {
        transacciones.clear();
    }
    
    /**
     * Retorna el número de transacciones almacenadas.
     */
    public int contar() {
        return transacciones.size();
    }
}
