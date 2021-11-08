

package tn.esprit.spring;


import org.junit.Test;
import org.junit.Assert;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimesheetSpringBootCoreDataJpaMvcRest1ApplicationTests {
    @Autowired
    private IEntrepriseService servEntreprise;

    @Autowired
    private EntrepriseRepository repEnt;
    
    @Autowired
    private IEmployeService servEmploye;
    
    @Autowired
    private EntrepriseRepository empEnt;

    @Transactional
    @Test
    public void testAjouterEntreprise(){
        Entreprise ent= new Entreprise("telecom tunis","telelcomunication");
        int id=servEntreprise.ajouterEntreprise((ent));

        Assert.assertNotNull(repEnt.findById(id).get());
    }

    @Transactional
    @Test
    public void getNombreEmployeJPQL(){

        if(servEmploye.getNombreEmployeJPQL()!= 0){
    		Assert.assertNotNull(servEmploye.getNombreEmployeJPQL());
		}
    }
    
    @Transactional
    @Test
    public void getAllEmployeNamesJPQL(){

        Assert.assertNotNull(servEmploye.getAllEmployeNamesJPQL());
    }
    
    @Transactional
    @Test
    public void getAllEmployeByEntreprise(){
    	
    	Entreprise ent= new Entreprise("orange","telelcomunication");
    	servEntreprise.ajouterEntreprise((ent));

        Assert.assertNotNull(servEmploye.getAllEmployeByEntreprise(ent));
    }

}