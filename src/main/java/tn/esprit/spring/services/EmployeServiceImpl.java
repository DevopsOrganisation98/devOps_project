package tn.esprit.spring.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
		try {
			logger.info("In ajouterEmploye():");
			logger.debug("debut d'ajout de l'employe: " + employe.getNom());
			employeRepository.save(employe);
			logger.info("out of ajouterEmploye()");
			logger.debug("l'employé: " + employe.getNom() + " de l'identifiant: " + employe.getId() + " ajoutée avec succé");
		}catch(Exception e){
			logger.error("erreur a ajouterEmploye(): "+e);
		}
		return employe.getId();
	}

	public int mettreAjourEmailByEmployeId(String email, int employeId) {
		int id =0;
		Optional<Employe> value = this.employeRepository.findById(employeId);
		Employe employe=new Employe();
		try {
			logger.info("In mettreAjourEmailByEmployeId()");
			if(value.isPresent()){
				employe = value.get();
			}
			logger.debug("debut de modification de l'email de l'employe: "+employe.getNom());
			employe.setEmail(email);
			employeRepository.save(employe);
			logger.info("Out of mettreAjourEmailByEmployeId()");
			logger.debug("l'employe: "+employe.getNom()+" de l'id: "+employe.getId()+" a été modifié");
			if(value.isPresent()){
				id=value.get().getId();
			}
		}catch(Exception e){
			logger.error("erreur a mettreAjourEmailByEmployeId: "+e);
		}
		return id;

	}

	@Transactional	
	public int affecterEmployeADepartement(int employeId, int depId) {
		logger.info("In affecterEmployeADepartement()");
		logger.debug("début de l'affectation de l'emplye de l'id: "+employeId+" au departement de l'id: "+depId);
		Optional<Departement> depManaged = deptRepoistory.findById(depId);
		Optional<Employe> employeManaged = employeRepository.findById(employeId);
		int id = 0;
		try {

			Departement depManagedEntity = new Departement();
			Employe employeManagedEntity =new Employe();
			if(depManaged.isPresent()){
				depManagedEntity=depManaged.get();
			}
			if(employeManaged.isPresent()){
				 employeManagedEntity = employeManaged.get();
			}


			if (depManagedEntity.getEmployes() == null) {

				List<Employe> employes = new ArrayList<>();
				employes.add(employeManagedEntity);
				depManagedEntity.setEmployes(employes);
			} else {

				depManagedEntity.getEmployes().add(employeManagedEntity);

			}
			logger.info("Out of affecterEmployeADepartement()");
			logger.debug("l'affectation a été effectué avec succé");
		}catch(Exception e){
			logger.error("erreur a affecterEmployeADepartement(): "+e);
		}
		if(employeManaged.isPresent()){
			id=employeManaged.get().getId();
		}
		return id;

	}

	@Transactional
	public int desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		logger.info("In desaffecterEmployeDuDepartement()");
		logger.debug("début de la desaffectation de l'emplye de l'id: "+employeId+" au departement de l'id: "+depId);
		Optional<Departement> departement = deptRepoistory.findById(depId);
		Departement dep = new Departement();
		try {
			if(departement.isPresent()){
				dep = departement.get();
			}


			int employeNb = dep.getEmployes().size();
			for (int index = 0; index < employeNb; index++) {
				if (dep.getEmployes().get(index).getId() == employeId) {
					dep.getEmployes().remove(index);
					break;//a revoir
				}
			}
			logger.info("Out of desaffecterEmployeDuDepartement()");
			logger.debug("la desaffectation a été effectué avec succé");
		}catch(Exception e){
			logger.error("erreur a desaffecterEmployeDuDepartement(): "+e);
		}
		return dep.getId();
	}

	public int ajouterContrat(Contrat contrat) {
		try{
			logger.info("in ajouterContrat()");
			logger.debug("début d'ajout du contrat num: "+contrat.getReference());
			contratRepoistory.save(contrat);
			logger.info("Out of ajouterContrat()");
			logger.debug("fin d'ajout du contrat num: "+contrat.getReference());
		}catch(Exception e){
			logger.error("erreur a ajouterContrat(): "+e);
		}

		return contrat.getReference();
	}

	public int affecterContratAEmploye(int contratId, int employeId) {
		logger.info("in affecterContratAEmploye()");
		logger.debug("début d'affectation du contrat num: "+contratId +" a l'emplye: "+employeId);
		Contrat contratManagedEntity = new Contrat();
		Employe employeManagedEntity = new Employe();
		Optional<Contrat> contrat = contratRepoistory.findById(contratId);
		Optional<Employe> employe = employeRepository.findById(contratId);
		int id=0;
		try{
			if(contrat.isPresent()){
				contratManagedEntity = contrat.get();
			}
			if(employe.isPresent()){
				 employeManagedEntity = employe.get();
			}
			contratManagedEntity.setEmploye(employeManagedEntity);
			contratRepoistory.save(contratManagedEntity);
			logger.info("Out of affecterContratAEmploye()");
			logger.debug("fin d'affectation du contrat num: "+contratId +" a l'emplye: "+employeId);
		}catch(Exception e){
			logger.error("erreur a affecterContratAEmploye(): "+e);
		}
		if(contrat.isPresent()){
			id = contrat.get().getReference();
		}
		return id;
		
	}


	public String getEmployePrenomById(int employeId) {
		Employe employeManagedEntity=new Employe();
		Optional<Employe> emp = employeRepository.findById(employeId);
		try{
			logger.info("in getEmployePrenomById()");
			logger.debug("debut de l'opperation");
			if(emp.isPresent()){
				employeManagedEntity = emp.get();
			}

			logger.info("Out of getEmployePrenomById()");
			logger.debug("employe récupéré sans echec: "+employeManagedEntity.getPrenom());

		}catch(Exception e) {
			logger.error("erreur a getEmployePrenomById(): " + e);
		}
		return employeManagedEntity.getPrenom();
	}


	//fares
	public void deleteEmployeById(int employeId)
	{
		Optional<Employe> employe = employeRepository.findById(employeId);
		
		

		
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
