package com.DesarrolloCreativo.DesarrolloCreativo.Controller;

import com.DesarrolloCreativo.DesarrolloCreativo.Service.EmpresaService;
import com.DesarrolloCreativo.DesarrolloCreativo.Service.UsuarioService;
import com.DesarrolloCreativo.DesarrolloCreativo.modelos.Empresa;
import com.DesarrolloCreativo.DesarrolloCreativo.modelos.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class ControllerUsuario {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EmpresaService empresaService;


    //Usuario
    @GetMapping("/Users")
    public String viewUsuarios(Model modelo, @ModelAttribute("mensaje") String mensaje) {
        List<Usuario> listaUsuario = usuarioService.getAllUsuario();
        modelo.addAttribute("usulist", listaUsuario);
        modelo.addAttribute("mensaje", mensaje);
        return "verUsuarios";

    }

    @GetMapping("/AgregarUsuario")
    public String nuevoUsuario(Model modelo, @ModelAttribute("mensaje") String mensaje) {
        Usuario usu = new Usuario();
        modelo.addAttribute("usu", usu);
        modelo.addAttribute("mensaje", mensaje);
        List<Empresa> listaEmpresas= empresaService.getAllEmpresas();
        modelo.addAttribute("emprelist", listaEmpresas);
        return "agregarUsuario";
    }

    @PostMapping("/GuardarUsuario")
    public String guardarUsuario(Usuario usu, RedirectAttributes redirectAttributes) {
        String passEncriptado = passwordEncoder().encode(usu.getPassword());
        usu.setPassword(passEncriptado);
        if (usuarioService.saveOrUpdateUsuario(usu) == true) {
            redirectAttributes.addFlashAttribute("mensaje", "saveOK");
            return "redirect:/Users";
        }
        redirectAttributes.addFlashAttribute("mensaje", "saveError");
        return "redirect:/AgregarUsuario";

    }

    @GetMapping("/EditarUsuario/{id}")
    public String editarUsuario(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje) {
        Usuario usu = usuarioService.getUsuarioByID(id).get();
        model.addAttribute("usu", usu);
        model.addAttribute("mensaje", mensaje);
        List<Empresa> listaEmpresas = empresaService.getAllEmpresas();
        model.addAttribute("emprelist", listaEmpresas);
        return "editarUsuario";
    }

    @PostMapping("/ActualizarUsuario")
    public String updateUsuario(@ModelAttribute("usu") Usuario usu, RedirectAttributes redirectAttributes) {
        Integer id = usu.getID();
        String Oldpass = usuarioService.getUsuarioByID(id).get().getPassword();
        if (!usu.getPassword().equals(Oldpass)){
            String passEncriptado =passwordEncoder().encode(usu.getPassword());
            usu.setPassword(passEncriptado);
        }
        if (usuarioService.saveOrUpdateUsuario(usu)){
            redirectAttributes.addFlashAttribute("mensaje", "updateOK!");
            return "redirect:/Users";
        }
        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/EditarUsuario/" + usu.getID();
    }

    @GetMapping("/EliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if (usuarioService.deleteUsuario(id)){
            redirectAttributes.addFlashAttribute("mensaje", "deleteOK!");
            return "redirect:/Users";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/Users";
    }

    @GetMapping("/Enterprises/{id}/Users")//Filtro usuarios por empresa
    public String verUsuarioPorEmpresa(@PathVariable("id") Integer id, Model model) {
        List<Usuario> listaUsuarios = usuarioService.obtenerPorEmpresa(id);
        model.addAttribute("usulist", listaUsuarios);
        return "verUsuarios";// Call HTML con usuarioList
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
