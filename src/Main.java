import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import entities.Patient;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * main
 */
public class Main /*extends Application*/ {

    public static void main(String[] args) {
        System.out.println("Hello there");

        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        
        KieSession ksession = kc.newKieSession("treatmentRules");

        
        Patient p = new Patient();
        p.setPhenotype('A');
        p.setSeverityLevel(1);
        ksession.insert(p);
        ksession.fireAllRules();
        System.out.println(p.getSuggestedTreatment());
    }

    // @Override
    // public void start(Stage arg0) throws Exception {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'start'");
    // }
    
}

