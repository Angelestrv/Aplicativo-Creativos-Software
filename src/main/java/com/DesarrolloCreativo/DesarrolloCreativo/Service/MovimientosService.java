package com.DesarrolloCreativo.DesarrolloCreativo.Service;

import com.DesarrolloCreativo.DesarrolloCreativo.modelos.MovimientoDinero;
import com.DesarrolloCreativo.DesarrolloCreativo.repo.MovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovimientosService {

    @Autowired
    MovimientosRepository movimientosRepository;

    //Metodo que muestra todos los movimientos sin filtro.
    public List<MovimientoDinero> getAllMovimientos(){
        List<MovimientoDinero> movimientosList = new ArrayList<>();
        movimientosRepository.findAll().forEach(movimiento -> movimientosList.add(movimiento));
        return movimientosList;
    }

    //Metod Muestrar por ID
    public MovimientoDinero getMovimientoByID (Integer ID){
        return movimientosRepository.findById(ID).get();
    }

    //Metodo para guardar o actualizar objetos de tipo MovimientoDinero
    public boolean saveOrUpdateMovimiento (MovimientoDinero movimientoDinero) {
        MovimientoDinero mov = movimientosRepository.save(movimientoDinero);
        if (movimientosRepository.findById(mov.getID())!= null){
            return true;
        }
        return false;
    }

    //Metodo para eliminar movimientos registrados teniendo el ID
    public boolean deleteMovimiento(Integer ID) {
        movimientosRepository.deleteById(ID); //Elimina usando el metodo que nos ofrece el repositorio
        if(this.movimientosRepository.findById(ID).isPresent()){//Si al buscar el movimeinto lo encontramos, no se eliminó (false)
            return false;
        }
        return true; //Si al buscar el movimiento no lo encontramos, si lo eliminó (true)
    }

    //Metodo para filtar empleados
    public ArrayList<MovimientoDinero> obtenerPorUsuario(Integer ID){ //Obtener teniendo en cuenta el ID del empleado
        return movimientosRepository.findByUsuario(ID);
    }

    //Metodo para filtrar por Empresa
    public ArrayList<MovimientoDinero> obtenerPorEmpresa(Integer ID){ //Obtener movimientos teniendo en cuenta el ID de la empresa a la que pertenecen los empleados que la registrarón
        return movimientosRepository.findByEmpresa(ID);
    }

    //Servicio para ver la suma de todos los montos
    public Long obtenerSumaMontos(){
        return movimientosRepository.SumarMontos();

    }

    //Servicio para ver la suma de los montos por USuario
    public Long MontosPorUsuario(Integer ID){
        return movimientosRepository.MontosPorUsuario(ID);

    }

    //Servicio para ver la suma de los montos por empresa
    public Long MontosPorEmpresa(Integer ID){
        return movimientosRepository.MontosPorEmpresa(ID);

    }

    //Servicio que nos deja conseguir el ID de un Usuario si tenemos su correo
    public Integer IDPorCorrreo(String correo){
        return movimientosRepository.IDporCorreo(correo);
    }




}
