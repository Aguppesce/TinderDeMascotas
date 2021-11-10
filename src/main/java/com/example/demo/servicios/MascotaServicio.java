package com.example.demo.servicios;

import com.example.demo.entidades.Foto;
import com.example.demo.entidades.Mascota;
import com.example.demo.entidades.Usuario;
import com.example.demo.enumeracion.Sexo;
import com.example.demo.enumeracion.Tipo;
import com.example.demo.excepciones.MiExcepcion;
import com.example.demo.repositorios.MascotaRepositorio;
import com.example.demo.repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MascotaServicio {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private MascotaRepositorio mascotaRepositorio;
    
    @Autowired
    private FotoServicio fotoServicio;
    
    @Transactional
    public void agregarMascota(MultipartFile archivo, String idUsuario, String nombre, Sexo sexo, Tipo tipo) throws MiExcepcion{
        
        Usuario usuario = usuarioRepositorio.findById(idUsuario).get();
        
        validar(nombre, sexo);
        
        Mascota mascota = new Mascota();
        mascota.setNombre(nombre); 
        mascota.setSexo(sexo);
        mascota.setAlta(new Date());
        mascota.setTipo(tipo);
        
        Foto foto = fotoServicio.guardar(archivo);
        mascota.setFoto(foto);
        
        mascotaRepositorio.save(mascota);
    }
    
    @Transactional
    public void modificar(MultipartFile archivo, String idUsuario, String idMascota, String nombre, Sexo sexo, Tipo tipo) throws MiExcepcion {
        validar(nombre, sexo);
        
        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);
        if(respuesta.isPresent()){
            Mascota mascota = respuesta.get();
            if(mascota.getUsuario().getId().equals(idUsuario)){
                mascota.setNombre(nombre);
                mascota.setSexo(sexo);                
                
                String idFoto = null;
                if(mascota.getFoto() != null){
                    idFoto = mascota.getFoto().getId();
                }
                
                Foto foto = fotoServicio.actualizar(idFoto, archivo);
                mascota.setFoto(foto);
                mascota.setTipo(tipo);
                
                mascotaRepositorio.save(mascota);
            } else {
                throw new MiExcepcion("No tiene permisos suficientes para realizar la operación");
            }
        }else{
            throw new MiExcepcion("No existe una mascota con el identificador solicitado");
        }
    }
    
    @Transactional
    public void eliminar(String idUsuario, String idMascota) throws MiExcepcion{
        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);
        if(respuesta.isPresent()){
            Mascota mascota = respuesta.get();
            if(mascota.getUsuario().getId().equals(idUsuario)){
                mascota.setBaja(new Date());
                mascotaRepositorio.save(mascota);
            }
        } else{
            throw new MiExcepcion("No existe una mascota con el identificador solicitado");
        }
    }
    
    public void validar(String nombre, Sexo sexo) throws MiExcepcion{
        if(nombre == null || nombre.isEmpty()){
            throw new MiExcepcion("El nombre de la mascota no puede ser nulo o vacío.");
        }
        
        if(sexo == null){
            throw new MiExcepcion("El sexo de la mascota no puede ser nulo");
        }
    }
    
    public Mascota buscarPorId(String id) throws MiExcepcion{
	Optional<Mascota> respuesta = mascotaRepositorio.findById(id);
        if(respuesta.isPresent()){
            return respuesta.get();
        } else {
            throw new MiExcepcion("La mascota solicitada no existe.");
        }       
    }
    
    public List<Mascota> buscarMascotasPorUsuario(String id) {
	return mascotaRepositorio.buscarMascotasPorUsuario(id);
    }
}
