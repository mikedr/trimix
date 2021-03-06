package com.trimix.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trimix.model.PersonaBuscar;
import com.trimix.service.PersonaService;

@Controller
public class BusquedaPersonasController {
	
	@Autowired
	private PersonaService personaService;
	
	@RequestMapping(value="/buscar", method = RequestMethod.GET)
	public String buscarPersonaPage(ModelMap model) {
		model.addAttribute("personaBuscar", new PersonaBuscar("",""));
	    List<String> tiposDocumento = new ArrayList<String>();
	    tiposDocumento.add("DNI");
	    tiposDocumento.add("Pasaporte");
	    tiposDocumento.add("Cedula");
	    model.addAttribute("tiposDocumento", tiposDocumento);
		return "buscar";
	}
	
	@RequestMapping(value="/buscar", method = RequestMethod.POST)
	public String buscarPersona(ModelMap model, @Valid PersonaBuscar personaBuscar, 
			BindingResult result) {
		if(result.hasErrors()) {
		    List<String> tiposDocumento = new ArrayList<String>();
		    tiposDocumento.add("DNI");
		    tiposDocumento.add("Pasaporte");
		    tiposDocumento.add("Cedula");
		    model.addAttribute("tiposDocumento", tiposDocumento);
			return "buscar";
		}
		model.clear();
		if(!personaBuscar.getPerNombre().isEmpty() && personaBuscar.getPerTipoDocumento().isEmpty()) {
			model.addAttribute("personas", personaService.getPersonasPorNombre(personaBuscar.getPerNombre()));			
		} else if(personaBuscar.getPerNombre().isEmpty() && !personaBuscar.getPerTipoDocumento().isEmpty()) {
			model.addAttribute("personas", personaService.getPersonasPorTipoDoc(personaBuscar.getPerTipoDocumento()));			
		} else if(!personaBuscar.getPerNombre().isEmpty() && !personaBuscar.getPerTipoDocumento().isEmpty()) {
			model.addAttribute("personas", personaService.getPersonasPorNombreYPorTipoDoc(personaBuscar.getPerNombre(), personaBuscar.getPerTipoDocumento()));			
		}
		else if(personaBuscar.getPerNombre().isEmpty() && personaBuscar.getPerTipoDocumento().isEmpty()) {
			model.addAttribute("personas", personaService.getPersonas());			
		} 

		return "personas";
	}

}
