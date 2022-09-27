package com.DesarrolloCreativo.DesarrolloCreativo.Service;

import com.DesarrolloCreativo.DesarrolloCreativo.modelos.Empresa;
import com.DesarrolloCreativo.DesarrolloCreativo.repo.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpresaService {

    @Autowired //conectamos esta clase con el repositorio empresa
    EmpresaRepository empresaRepository;

    //Metodo que retornará la lista de empresas usando metodos heredados del Jparpository
    public List<Empresa> getAllEmpresas(){
        List<Empresa> empresaList = new ArrayList<>();
        empresaRepository.findAll().forEach(empresa -> empresaList.add(empresa));  //Recorremos el iterable que regresa el metodo findAll del Jpa y lo guardamos en la lista creada
        return empresaList;
    }

    //Metodo que me trae un objeto de tipo Empresa cuando cuento con el id de la misma
    public Empresa getEmpresaById(Integer ID){
        return empresaRepository.findById(ID).get();
    }

    //Metodo para guardar o actualizar objetos de tipo Empresa
    public boolean saveOrUpdateEmpresa(Empresa empresa){
        Empresa emp=empresaRepository.save(empresa);
        if (empresaRepository.findById(emp.getID())!=null){
            return true;
        }
        return false;
    }

    //Metodo para eliminar empresas registradas teniendo el id
    public boolean deleteEmpresa(Integer ID){
        empresaRepository.deleteById(ID);  //Eliminar
        if (empresaRepository.findById(ID)!=null){  //Verificacion del servicio eliminacion
            return true;
        }
        return false;
    }
}