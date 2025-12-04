package com.wallet.application.mappers;

import com.wallet.application.dtos.UsuarioDTO;
import com.wallet.domain.entities.Usuario;
import com.wallet.domain.valueobjects.DocumentoIdentidad;
import com.wallet.domain.valueobjects.Email;

/**
 * Mapper para convertir entre Usuario (Entidad) y UsuarioDTO.
 * Implementa el patrón Mapper para separar capas.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class UsuarioMapper {
    
    /**
     * Convierte una entidad Usuario a UsuarioDTO.
     * 
     * @param usuario la entidad Usuario
     * @return el DTO correspondiente
     */
    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        return new UsuarioDTO(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getEmail().getValor(),
            usuario.getDocumentoIdentidad().getTipo().name(),
            usuario.getDocumentoIdentidad().getNumero(),
            usuario.isActivo(),
            usuario.getFechaCreacion(),
            usuario.getFechaActualizacion()
        );
    }
    
    /**
     * Convierte un UsuarioDTO a entidad Usuario.
     * 
     * @param dto el DTO
     * @return la entidad Usuario
     */
    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Email email = new Email(dto.getEmail());
        DocumentoIdentidad documento = new DocumentoIdentidad(
            dto.getNumeroDocumento(),
            DocumentoIdentidad.TipoDocumento.valueOf(dto.getTipoDocumento())
        );
        
        return new Usuario(
            dto.getId(),
            dto.getNombre(),
            dto.getApellido(),
            email,
            documento,
            dto.getFechaCreacion(),
            dto.getFechaActualizacion(),
            dto.isActivo()
        );
    }
    
    /**
     * Crea una nueva entidad Usuario desde datos básicos.
     * 
     * @param nombre nombre del usuario
     * @param apellido apellido del usuario
     * @param email email del usuario
     * @param tipoDocumento tipo de documento
     * @param numeroDocumento número de documento
     * @return nueva entidad Usuario
     */
    public static Usuario crearUsuario(String nombre, String apellido, String email,
                                       String tipoDocumento, String numeroDocumento) {
        Email emailVO = new Email(email);
        DocumentoIdentidad documentoVO = new DocumentoIdentidad(
            numeroDocumento,
            DocumentoIdentidad.TipoDocumento.valueOf(tipoDocumento)
        );
        
        return new Usuario(nombre, apellido, emailVO, documentoVO);
    }
}
