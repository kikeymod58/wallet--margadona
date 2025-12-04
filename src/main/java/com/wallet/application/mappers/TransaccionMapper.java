package com.wallet.application.mappers;

import com.wallet.application.dtos.TransaccionDTO;
import com.wallet.domain.entities.Transaccion;
import com.wallet.domain.valueobjects.Dinero;
import com.wallet.domain.valueobjects.TipoTransaccion;

/**
 * Mapper para convertir entre Transaccion (Entidad) y TransaccionDTO.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class TransaccionMapper {
    
    /**
     * Convierte una entidad Transaccion a TransaccionDTO.
     * 
     * @param transaccion la entidad Transaccion
     * @return el DTO correspondiente
     */
    public static TransaccionDTO toDTO(Transaccion transaccion) {
        if (transaccion == null) {
            return null;
        }
        
        return new TransaccionDTO(
            transaccion.getId(),
            transaccion.getCuentaOrigenId(),
            transaccion.getTipo().name(),
            transaccion.getMonto().getCantidad(),
            transaccion.getMonto().getMoneda(),
            transaccion.getDescripcion(),
            transaccion.getFecha(),
            transaccion.getCuentaDestinoId()
        );
    }
    
    /**
     * Convierte un TransaccionDTO a entidad Transaccion.
     * 
     * @param dto el DTO
     * @return la entidad Transaccion
     */
    public static Transaccion toEntity(TransaccionDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Dinero monto = new Dinero(dto.getMonto(), dto.getMoneda());
        TipoTransaccion tipo = TipoTransaccion.valueOf(dto.getTipo());
        
        return new Transaccion(
            dto.getId(),
            tipo,
            monto,
            dto.getCuentaId(),
            dto.getCuentaDestinoId(),
            dto.getDescripcion(),
            dto.getFecha(),
            Dinero.CERO,
            monto
        );
    }
}
