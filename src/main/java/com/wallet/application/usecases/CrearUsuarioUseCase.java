package com.wallet.application.usecases;

import com.wallet.application.dtos.UsuarioDTO;
import com.wallet.application.dtos.requests.CrearUsuarioRequest;
import com.wallet.application.mappers.UsuarioMapper;
import com.wallet.domain.entities.Usuario;
import com.wallet.domain.exceptions.OperacionNoValidaException;
import com.wallet.domain.repositories.IUsuarioRepository;
import com.wallet.domain.valueobjects.DocumentoIdentidad;
import com.wallet.domain.valueobjects.Email;

/**
 * Caso de uso para crear un nuevo usuario.
 * Aplica principios SOLID:
 * - SRP: Responsabilidad única de crear usuarios
 * - DIP: Depende de abstracción (IUsuarioRepository)
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class CrearUsuarioUseCase {
    
    private final IUsuarioRepository usuarioRepository;
    
    public CrearUsuarioUseCase(IUsuarioRepository usuarioRepository) {
        if (usuarioRepository == null) {
            throw new IllegalArgumentException("El repositorio de usuarios no puede ser nulo");
        }
        this.usuarioRepository = usuarioRepository;
    }
    
    /**
     * Ejecuta el caso de uso para crear un nuevo usuario.
     * 
     * @param request datos del usuario a crear
     * @return DTO del usuario creado
     * @throws OperacionNoValidaException si ya existe un usuario con el mismo email o documento
     */
    public UsuarioDTO ejecutar(CrearUsuarioRequest request) {
        validarRequest(request);
        
        // Crear value objects
        Email email = new Email(request.getEmail());
        DocumentoIdentidad documento = new DocumentoIdentidad(
            request.getNumeroDocumento(),
            DocumentoIdentidad.TipoDocumento.valueOf(request.getTipoDocumento())
        );
        
        // Validar que no exista usuario con el mismo email
        if (usuarioRepository.existePorEmail(email)) {
            throw OperacionNoValidaException.porque("Ya existe un usuario con el email: " + email.getValor());
        }
        
        // Validar que no exista usuario con el mismo documento
        if (usuarioRepository.existePorDocumento(documento)) {
            throw OperacionNoValidaException.porque("Ya existe un usuario con el documento: " + documento.getNumero());
        }
        
        // Crear la entidad Usuario
        Usuario usuario = new Usuario(
            request.getNombre(),
            request.getApellido(),
            email,
            documento
        );
        
        // Guardar en el repositorio
        Usuario usuarioGuardado = usuarioRepository.guardar(usuario);
        
        // Convertir a DTO y retornar
        return UsuarioMapper.toDTO(usuarioGuardado);
    }
    
    private void validarRequest(CrearUsuarioRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula");
        }
        if (request.getNombre() == null || request.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido");
        }
        if (request.getApellido() == null || request.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido es requerido");
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email es requerido");
        }
        if (request.getTipoDocumento() == null || request.getTipoDocumento().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de documento es requerido");
        }
        if (request.getNumeroDocumento() == null || request.getNumeroDocumento().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de documento es requerido");
        }
    }
}
