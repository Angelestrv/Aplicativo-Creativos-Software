package com.DesarrolloCreativo.DesarrolloCreativo;

import com.DesarrolloCreativo.DesarrolloCreativo.modelos.Empresa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@SpringBootApplication (exclude={SecurityAutoConfiguration.class})
public class DesarrolloCreativoApplication {

	@GetMapping({"/"})
	public String Inicio(){
		return "Home";
	}


	@GetMapping("/test")
	public String test(){
		Empresa CreativosS = new Empresa ("Creativos Software SAS", "Calle Colombia", "62515656", "4569321-8");
		return CreativosS.getNombre();
	}

	public static void main(String[] args) {
		SpringApplication.run(DesarrolloCreativoApplication.class, args);
	}

}
