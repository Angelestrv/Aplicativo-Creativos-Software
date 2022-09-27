package com.DesarrolloCreativo.DesarrolloCreativo.Controller;

import com.DesarrolloCreativo.DesarrolloCreativo.Service.MovimientosService;
import com.DesarrolloCreativo.DesarrolloCreativo.Service.UsuarioService;
import com.DesarrolloCreativo.DesarrolloCreativo.modelos.MovimientoDinero;
import com.DesarrolloCreativo.DesarrolloCreativo.modelos.Usuario;
import com.DesarrolloCreativo.DesarrolloCreativo.repo.MovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControllerMovimientoDinero {

    @Autowired
    MovimientosService movimientosService;

    @Autowired
    MovimientosRepository movimientosRepository;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping ("/Movements")// Controlador que nos lleva al template donde veremos todos los movimientos
    public String verMovimientos (@RequestParam(value="pagina", required=false, defaultValue = "0") int NumeroPagina,
                                  @RequestParam(value="medida", required=false, defaultValue = "5") int medida,
                                  Model model, @ModelAttribute("mensaje") String mensaje){
        Page<MovimientoDinero> paginaMovimientos= movimientosRepository.findAll(PageRequest.of(NumeroPagina,medida));
        model.addAttribute("movlist",paginaMovimientos.getContent());
        model.addAttribute("paginas",new int[paginaMovimientos.getTotalPages()]);
        model.addAttribute("paginaActual", NumeroPagina);
        model.addAttribute("mensaje",mensaje);
        Long sumaMonto=movimientosService.obtenerSumaMontos();
        model.addAttribute("SumaMontos",sumaMonto);//Mandamos la suma de todos los montos a la plantilla
        return "verMovimientos"; //Llamamos al HTML
    }

    @GetMapping("/AgregarMovimiento") //Controlador que nos lleva al template donde podremos crear un nuevo movimiento
    public String nuevoMovimiento(Model model, @ModelAttribute("mensaje") String mensaje){
        MovimientoDinero movimiento= new MovimientoDinero();
        model.addAttribute("mov",movimiento);
        model.addAttribute("mensaje",mensaje);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo= auth.getName();
        Integer IDUsuario =movimientosService.IDPorCorrreo(correo);
        model.addAttribute("IDUsuario", IDUsuario);
        return "agregarMovimiento"; //Llamar HTML
    }

    @PostMapping("/GuardarMovimiento")
    public String guardarMovimiento(MovimientoDinero mov, RedirectAttributes redirectAttributes){
        if(movimientosService.saveOrUpdateMovimiento(mov)){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/Movements";
        }
        redirectAttributes.addFlashAttribute("mensaje","saveError");
        return "redirect:/AgregarMovimiento";
    }

    @GetMapping("/EditarMovimiento/{id}")
    public String editarMovimiento(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje) {
        MovimientoDinero mov = movimientosService.getMovimientoByID(id);
        //Creamos un atributo para el modelo, que se llame igualmente empl y es el que ira al html para llenar o alimentar campos
        model.addAttribute("mov", mov);
        model.addAttribute("mensaje", mensaje);
        List<Usuario> listaEmpleados = usuarioService.getAllUsuario();
        model.addAttribute("emplelist", listaEmpleados);
        return "editarMovimiento";
    }

    @PostMapping("/ActualizarMovimiento")
    public String actualizarMovimiento (@ModelAttribute("mov") MovimientoDinero mov, RedirectAttributes redirectAttributes){
        if(movimientosService.saveOrUpdateMovimiento(mov)){
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/Movements";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect:/EditarMovimiento/"+mov.getID();

    }

    @GetMapping("/EliminarMovement/{id}")
    public String eliminarMovimiento (@PathVariable Integer id, RedirectAttributes redirectAttributes){
        if (movimientosService.deleteMovimiento(id)){
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
            return "redirect:/Movements";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/Movements";
    }

    @GetMapping ("/User/{id}/Movements")  //Filtro de movimientos por usuario
    public String movimientosPorUsuario(@PathVariable("id") Integer ID, Model model){
        List<MovimientoDinero> movlist = movimientosService.obtenerPorUsuario(ID);
        model.addAttribute("movlist", movlist);
        Long sumaMonto= movimientosService.MontosPorUsuario(ID);
        model.addAttribute("SumaMontos", sumaMonto);
        return "verMovimientos"; //Llamamos al HTML
    }

    @GetMapping ("/Enterprises/{id}/Movements")  //Filtro de movimientos por empresa
    public String movimientosPorEmpresa (@PathVariable("id") Integer ID, Model model){
        List<MovimientoDinero> movlist = movimientosService.obtenerPorEmpresa(ID);
        model.addAttribute("movlist", movlist);
        Long sumaMonto = movimientosService.MontosPorEmpresa(ID);
        model.addAttribute("SumaMontos", sumaMonto);
        return "verMovimientos"; //Llammos al HTML
    }

    //Controlador que me lleva al template de No autorizado
    @RequestMapping (value="/Denegado")
    public String accesoDenegado(){
        return "accessDenied";
    }


}
