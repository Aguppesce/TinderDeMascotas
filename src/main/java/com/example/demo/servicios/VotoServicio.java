package com.example.demo.servicios;


import com.example.demo.entidades.Mascota;
import com.example.demo.entidades.Voto;
import com.example.demo.excepciones.MiExcepcion;
import com.example.demo.repositorios.MascotaRepositorio;
import com.example.demo.repositorios.VotoRepositorio;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotoServicio {
//    @Autowired
//    private NotificacionServicio notificacionServicio;
    
    @Autowired
    private VotoRepositorio votoRepositorio;
    
    @Autowired
    private MascotaRepositorio mascotaRepositorio;
    
    public void votar(String idUsuario, String idMascota1, String idMascota2) throws MiExcepcion{
        Voto voto = new Voto();
        voto.setFecha(new Date());
        
        if(idMascota1.equals(idMascota2)){
            throw new MiExcepcion("No puede votarse a si mismo");
        }
        
        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota1);
        if(respuesta.isPresent()){
            Mascota mascota1 = respuesta.get();
            if(mascota1.getUsuario().getId().equals(idUsuario)){
                voto.setMascota1(mascota1);                                
            } else {
                throw new MiExcepcion("No tiene permisos para realizar la operación solicitada.");
            }
        } else {
            throw new MiExcepcion("No existe una mascota vinculada a ese identificador.");
        }
        
        Optional<Mascota> respuesta2 = mascotaRepositorio.findById(idMascota2);
        if(respuesta2.isPresent()){
            Mascota mascota2 = respuesta2.get();
            voto.setMascota2(mascota2);
            
            //notificacionServicio.enviar("Tu mascota ha sido votada", "Tinder de Mascota", mascota2.getUsuario().getMail());
        } else {
            throw new MiExcepcion("No existe una mascota vinculada a ese identificador.");
        }
        
        votoRepositorio.save(voto);
    }
    
    public void responder(String idUsuario, String idVoto) throws MiExcepcion{
        Optional<Voto> respuesta = votoRepositorio.findById(idVoto);
        if(respuesta.isPresent()){
            Voto voto = respuesta.get();
            voto.setRespuesta(new Date());
            
            if(voto.getMascota2().getUsuario().getId().equals(idUsuario)){
                //notificacionServicio.enviar("Tu voto fue correspondido", "Tinder de Mascota", voto.getMascota1().getUsuario().getMail());
                votoRepositorio.save(voto);
            } else {
                throw new MiExcepcion("No tiene permiso para realizar la operación.");
            }
        } else {
            throw new MiExcepcion("No existe el voto solicitado.");
        }
    }
}
