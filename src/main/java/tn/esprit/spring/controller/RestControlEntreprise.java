package tn.esprit.spring.controller;




import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.dto.DepartementDto;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IEntrepriseService;


@RestController
public class RestControlEntreprise {


	@Autowired
	IEntrepriseService ientrepriseservice;

	@Autowired
	ModelMapper modelMapper;




    @PutMapping(value = "/affecterDepartementAEntreprise/{iddept}/{identreprise}") 
	public void affecterDepartementAEntreprise(@PathVariable("iddept")int depId, @PathVariable("identreprise")int entrepriseId) {
		ientrepriseservice.affecterDepartementAEntreprise(depId, entrepriseId);
	}
    

    @DeleteMapping("/deleteEntrepriseById/{identreprise}") 
	@ResponseBody 
	public void deleteEntrepriseById(@PathVariable("identreprise")int entrepriseId)
	{
		ientrepriseservice.deleteEntrepriseById(entrepriseId);
	}
    

    @GetMapping(value = "getEntrepriseById/{identreprise}")
    @ResponseBody
	public Entreprise getEntrepriseById(@PathVariable("identreprise") int entrepriseId) {

		return ientrepriseservice.getEntrepriseById(entrepriseId);
	}
    

 	@PostMapping("/ajouterDepartement")
 	@ResponseBody
	public int ajouterDepartement(@RequestBody DepartementDto depDto) {
 		Departement dep=modelMapper.map(depDto, Departement.class);
		return ientrepriseservice.ajouterDepartement(dep);
	}
	


    // URL : http://localhost:8081/SpringMVC/servlet/deleteDepartementById/3
    @DeleteMapping("/deleteDepartementById/{iddept}") 
	@ResponseBody 
	public void deleteDepartementById(@PathVariable("iddept") int depId) {
		ientrepriseservice.deleteDepartementById(depId);

	}
}
