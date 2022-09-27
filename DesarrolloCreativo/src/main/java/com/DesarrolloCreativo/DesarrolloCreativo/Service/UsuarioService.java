package com.DesarrolloCreativo.DesarrolloCreativo.Service;

import com.DesarrolloCreativo.DesarrolloCreativo.modelos.Empresa;
import com.DesarrolloCreativo.DesarrolloCreativo.modelos.Usuario;
import com.DesarrolloCreativo.DesarrolloCreativo.repo.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    //Metodo para ver todos los usuarios
    public List<Usuario> getAllUsuario(){
        List<Usuario> usuarioList =new ArrayList<>();
        usuarioRepository.findAll().forEach(usuario -> usuarioList.add(usuario));
        return usuarioList;
    }

    //Metodo para buscar usuarios por ID
    public Optional<Usuario> getUsuarioByID (Integer ID){
        return usuarioRepository.findById(ID);
    }

    //Metodo para buscar usuarios por empresa
    public ArrayList<Usuario> obtenerPorEmpresa (Integer ID){
        return usuarioRepository.findByEmpresa(ID);
    }

    //Metodo para guardar o actualizar registros de Usuarios
    public boolean saveOrUpdateUsuario (Usuario usua) {
       Usuario usu = usuarioRepository.save(usua);
       if(usuarioRepository.findById(usu.getID()) !=null){
           return true;
       }
           return false;
    }

    //Metodo para eliminar registros de usuarios registrados teniendo el id
    public boolean deleteUsuario(Integer ID){
        usuarioRepository.deleteById(ID); //Eliminar registro
        if(usuarioRepository.findById(ID).isPresent()){//Verificacion del servicio eliminacion
            return false;
        }
        return true;
    }

}
