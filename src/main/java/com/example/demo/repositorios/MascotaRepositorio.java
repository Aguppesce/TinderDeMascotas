package com.example.demo.repositorios;

import com.example.demo.entidades.Mascota;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota, String>{
    
    @Query("SELECT c FROM Mascota c WHERE c.usuario.id = :id AND c.baja IS NULL")
    public List<Mascota> buscarMascotasPorUsuario(@Param("id") String id);
    
    @Query("SELECT c FROM Mascota c WHERE c.nombre = :nombre")
    public Mascota buscarPorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT c FROM Mascota c WHERE c.id = :id")
    public Mascota buscarPorId(@Param("id") String id);
    
}
