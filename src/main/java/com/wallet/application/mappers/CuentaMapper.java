package com.wallet.application.mappers;

import com.wallet.application.dtos.CuentaDTO;
import com.wallet.domain.entities.Cuenta;
import com.wallet.domain.valueobjects.Dinero;

import java.math.BigDecimal;

/**
 * Mapper para convertir entre Cuenta (Entidad) y CuentaDTO.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class CuentaMapper {
    
    /**
     * Convierte una entidad Cuenta a CuentaDTO.
     * 
     * @param cuenta la entidad Cuenta
     * @return el DTO correspondiente
     */
    public static CuentaDTO toDTO(Cuenta cuenta) {
        if (cuenta == null) {
            return null;
        }
        
        return new CuentaDTO(
            cuenta.getId(),
            cuenta.getNumeroCuenta(),
            cuenta.getUsuarioId(),
            cuenta.getSaldo().getCantidad(),
            cuenta.getSaldo().getMoneda(),
            cuenta.isActiva(),
            cuenta.getFechaCreacion(),
            cuenta.getFechaActualizacion()
        );
    }
    
    /**
     * Convierte un CuentaDTO a entidad Cuenta.
     * 
     * @param dto el DTO
     * @return la entidad Cuenta
     */
    public static Cuenta toEntity(CuentaDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Dinero saldo = new Dinero(dto.getSaldo(), dto.getMoneda());
        
        return new Cuenta(
            dto.getId(),
            dto.getNumeroCuenta(),
            dto.getUsuarioId(),
            saldo,
            dto.getFechaCreacion(),
            dto.getFechaActualizacion(),
            dto.isActiva()
        );
    }
    
    /**
     * Crea una nueva entidad Cuenta desde el ID de usuario.
     * 
     * @param usuarioId ID del usuario propietario
     * @return nueva entidad Cuenta
     */
    public static Cuenta crearCuenta(String usuarioId) {
        return new Cuenta(usuarioId);
    }
}
