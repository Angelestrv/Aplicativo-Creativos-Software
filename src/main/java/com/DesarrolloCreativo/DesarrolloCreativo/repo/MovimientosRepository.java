package com.DesarrolloCreativo.DesarrolloCreativo.repo;

import com.DesarrolloCreativo.DesarrolloCreativo.modelos.MovimientoDinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MovimientosRepository extends JpaRepository <MovimientoDinero, Integer> {

    //Metodo para filtrar movimientos por Usuario
    @Query(value= "SELECT * FROM movimiento_dinero where usuario_id = ?1", nativeQuery = true)
    public abstract ArrayList<MovimientoDinero> findByUsuario(Integer ID);

    //Metodo para filtrar movimeintos por empresa
    @Query (value= "SELECT * FROM movimiento_dinero where usuario_id in (SELECT id FROM usuario where empresa_id = ?1)", nativeQuery = true)
    public abstract ArrayList<MovimientoDinero> findByEmpresa(Integer ID);

    //Metodos para Sumar todos los movimientos de dinero (montos).
    @Query (value= "SELECT SUM (monto) FROM movimiento_dinero", nativeQuery = true)
    public abstract Long SumarMontos();

    //Metodo para ver la suma de los montos por Usuario
    @Query(value= "SELECT SUM (monto) FROM movimiento_dinero where usuario_id=?1", nativeQuery = true)
    public abstract Long MontosPorUsuario(Integer ID); //ID del Usuario

    //Metodo para ver la suma de los montos por empresa
    @Query (value= "SELECT SUM (monto) FROM movimiento_dinero where usuario_id in (SELECT id FROM usuario WHERE empresa_id = ?1)", nativeQuery = true)
    public abstract Long MontosPorEmpresa (Integer ID);

    //Metodo que me trae el ID de un Usuario cuando tengo su correo
    @Query (value="select ID from usuario where correo =?1", nativeQuery = true)
    public abstract Integer IDporCorreo (String correo);
}
