package com.example.demo.servicios;

import com.example.demo.entidades.Foto;
import com.example.demo.excepciones.MiExcepcion;
import com.example.demo.repositorios.FotoRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {
    
    @Autowired
    private FotoRepositorio fotoRepositorio;
    
    @Transactional
    public Foto guardar(MultipartFile archivo) throws MiExcepcion {
        if(archivo != null && !archivo.isEmpty()){
            try {                
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);            
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }   return null;        
    }
    
    @Transactional
    public Foto actualizar(String idFoto, MultipartFile archivo) throws MiExcepcion {
        if(archivo != null){
            try {                
                Foto foto = new Foto();
                
                if(idFoto != null){
                    Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);
                    if(respuesta.isPresent()){
                        foto = respuesta.get();
                    }
                }
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);            
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }   return null;        
    }
}
