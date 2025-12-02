package com.wallet.domain.repositories;

import com.wallet.domain.entities.Usuario;
import com.wallet.domain.valueobjects.Email;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz del repositorio de Usuarios (Port).
 * Define el contrato para persistencia de usuarios.
 * 
 * Principios aplicados:
 * - DIP: La capa de dominio no depende de detalles de implementación
 * - ISP: Interfaz específica para operaciones de usuarios
 */
public interface IUsuarioRepository {
    
    /**
     * Guarda un usuario nuevo o actualiza uno existente.
     * 
     * @param usuario el usuario a guardar
     * @return el usuario guardado
     */
    Usuario guardar(Usuario usuario);
    
    /**
     * Busca un usuario por su ID.
     * 
     * @param id el ID del usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> buscarPorId(String id);
    
    /**
     * Busca un usuario por su email.
     * 
     * @param email el email del usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> buscarPorEmail(Email email);
    
    /**
     * Busca un usuario por su número de documento.
     * 
     * @param numeroDocumento el número del documento
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> buscarPorDocumento(String numeroDocumento);
    
    /**
     * Obtiene todos los usuarios.
     * 
     * @return lista de todos los usuarios
     */
    List<Usuario> obtenerTodos();
    
    /**
     * Obtiene todos los usuarios activos.
     * 
     * @return lista de usuarios activos
     */
    List<Usuario> obtenerActivos();
    
    /**
     * Elimina un usuario por su ID.
     * 
     * @param id el ID del usuario
     * @return true si se eliminó correctamente
     */
    boolean eliminar(String id);
    
    /**
     * Verifica si existe un usuario con el email dado.
     * 
     * @param email el email a verificar
     * @return true si existe
     */
    boolean existePorEmail(Email email);
    
    /**
     * Verifica si existe un usuario con el documento dado.
     * 
     * @param documento el documento a verificar
     * @return true si existe
     */
    boolean existePorDocumento(com.wallet.domain.valueobjects.DocumentoIdentidad documento);
}
