package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {
	public static final Logger logger = Logger.getLogger(EmployeServiceImpl.class);

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	//firas chbinou
	public int ajouterEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe.getId();
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Employe employe = employeRepository.findById(employeId).get();
		employe.setEmail(email);
		employeRepository.save(employe);

	}

	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		Departement depManagedEntity = deptRepoistory.findById(depId).get();
		Employe employeManagedEntity = employeRepository.findById(employeId).get();

		if(depManagedEntity.getEmployes() == null){

			List<Employe> employes = new ArrayList<>();
			employes.add(employeManagedEntity);
			depManagedEntity.setEmployes(employes);
		}else{

			depManagedEntity.getEmployes().add(employeManagedEntity);

		}

	}
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		Departement dep = deptRepoistory.findById(depId).get();

		int employeNb = dep.getEmployes().size();
		for(int index = 0; index < employeNb; index++){
			if(dep.getEmployes().get(index).getId() == employeId){
				dep.getEmployes().remove(index);
				break;//a revoir
			}
		}
	}

	public int ajouterContrat(Contrat contrat) {
		contratRepoistory.save(contrat);
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
		Employe employeManagedEntity = employeRepository.findById(employeId).get();

		contratManagedEntity.setEmploye(employeManagedEntity);
		contratRepoistory.save(contratManagedEntity);
		
	}


	public String getEmployePrenomById(int employeId) {
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
		return employeManagedEntity.getPrenom();
	}


	//fares
	

	public void deleteEmployeById(int employeId)
	{
		Optional<Employe> employe = employeRepository.findById(employeId);
		
		

		//Desaffecter l'employe de tous les departements
		//c'est le bout master qui permet de mettre a jour
		//la table d'association
		if(employe.isPresent()) {
			logger.info("In deleteEmployeById():");
			logger.debug("debut d'effacement de l'employe: " +  employe.get().getNom());
			for(Departement dep : employe.get().getDepartements()){
				dep.getEmployes().remove(employe.get());
			}

			employeRepository.delete(employe.get());
			logger.debug("l'employe: " + employe.get().getNom() + " de l'id: " + employe.get().getId() + " effacé avec succé");

		}
		logger.info("out of deleteEmployeById()");
	}

	
	public void deleteContratById(int contratId) {
		
		Optional<Contrat> contratManagedEntity = contratRepoistory.findById(contratId);
		
		
		
		if(contratManagedEntity.isPresent()) {
			logger.info("In deleteContratById():");
			logger.debug("debut d'effacement du contrat: " +  contratManagedEntity.get().getReference());
			contratRepoistory.delete(contratManagedEntity.get());
			logger.debug("le contrat: " + contratManagedEntity.get().getReference() + " effacé avec succé");

		}
		
		logger.info("out of deleteContratById()");

	}

	public int getNombreEmployeJPQL() {
		logger.info("In getNombreEmployeJPQL():");
		return employeRepository.countemp();
		
	}
	
	public List<String> getAllEmployeNamesJPQL() {
		logger.info("In getAllEmployeNamesJPQL():");
		return employeRepository.employeNames();

	}
	
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		logger.info("In getAllEmployeByEntreprise():");
		logger.debug("l'entreprise: " + entreprise.getName());
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		logger.info("In mettreAjourEmailByEmployeIdJPQL():");
		logger.debug("Mettre a jour l'email: " + email + "avec l'employeID"+ employeId);
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}

	//chiheb
	public void deleteAllContratJPQL() {
         employeRepository.deleteAllContratJPQL();
	}


	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}
	
	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
				return (List<Employe>) employeRepository.findAll();
	}

}
