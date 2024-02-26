package test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import entities.*;

public class testTreatmentRules {
    
    
    // private static KieServices ks;
    // private static KieContainer kc;
    // private static KieSession ksession;

    // @Before
    // public void setUpDrools(){
    //     ks = KieServices.Factory.get();
    //     kc = ks.getKieClasspathContainer();
        
    //     ksession = kc.newKieSession("treatmentRules");
    // }
    // @After
    // public void tearDownDrools(){
    //     ksession.dispose();
    // }

    @Test
    public void testTreatmentRulesTrue(){
        Assert.assertEquals(true,true);
    }


    @Test
    public void testPhenotypeASeverity1(){
        
        Patient p = new Patient();
        p.setPhenotype('A');
        p.setSeverityLevel(1);
        ksession.insert(p);
        ksession.fireAllRules();
        Assert.assertEquals(p.getSuggestedTreatment(),"SABA / SAMA or LAMA / LABA");
    }
    @Test
    public void testPhenotypeASeverity2(){
        Patient p = new Patient();
        p.setPhenotype('A');
        p.setSeverityLevel(2);
        ksession.insert(p);
        ksession.fireAllRules();

        Assert.assertEquals(p.getSuggestedTreatment(),"LAMA / LABA or LAMA + LABA");
    }
    @Test
    public void testPhenotypeASeverity3(){
        Patient p = new Patient();
        p.setPhenotype('A');
        p.setSeverityLevel(3);
        ksession.insert(p);
        ksession.fireAllRules();

        Assert.assertEquals(p.getSuggestedTreatment(),"LAMA + LABA");
    }
    @Test
    public void testPhenotypeASeverity4(){
        Patient p = new Patient();
        p.setPhenotype('A');
        p.setSeverityLevel(4);
        ksession.insert(p);
        ksession.fireAllRules();

        Assert.assertEquals(p.getSuggestedTreatment(),"LAMA + LABA or LAMA + LABA + Teofiline");
    }
    @Test
    public void testPhenotypeBSeverity1(){
        Patient p = new Patient();
        p.setPhenotype('B');
        p.setSeverityLevel(1);
        ksession.insert(p);
        ksession.fireAllRules();

        Assert.assertEquals(p.getSuggestedTreatment(),"LABA + CI");
    }
    @Test
    public void testPhenotypeBSeverity2(){
        Patient p = new Patient();
        p.setPhenotype('B');
        p.setSeverityLevel(2);
        ksession.insert(p);
        ksession.fireAllRules();

        Assert.assertEquals(p.getSuggestedTreatment(),"LABA + CI");
    }
    @Test
    public void testPhenotypeBSeverity3(){
        Patient p = new Patient();
        p.setPhenotype('B');
        p.setSeverityLevel(3);
        ksession.insert(p);
        ksession.fireAllRules();

        Assert.assertEquals(p.getSuggestedTreatment(),"LAMA + LABA + CI");
    }
    @Test
    public void testPhenotypeBSeverity4(){
        Patient p = new Patient();
        p.setPhenotype('B');
        p.setSeverityLevel(4);
        ksession.insert(p);
        ksession.fireAllRules();

        Assert.assertEquals(p.getSuggestedTreatment(),"LAMA + LABA + CI, Consider adding Teofiline/IPE4 with expectoration");
    }
    @Test
    public void testPhenotypeCSeverity1(){
        Patient p = new Patient();
        p.setPhenotype('C');
        p.setSeverityLevel(1);
        ksession.insert(p);
        ksession.fireAllRules();

        Assert.assertEquals(p.getSuggestedTreatment(),"LAMA / LABA");
    }
    @Test
    public void testPhenotypeCSeverity2(){
        Patient p = new Patient();
        p.setPhenotype('C');
        p.setSeverityLevel(2);
        ksession.insert(p);
        ksession.fireAllRules();

        Assert.assertEquals(p.getSuggestedTreatment(),"(LAMA / LABA) + CI or LAMA + LABA or LAMA/LABA");
    }
    @Test
    public void testPhenotypeCSeverity3(){
        Patient p = new Patient();
        p.setPhenotype('C');
        p.setSeverityLevel(3);
        ksession.insert(p);
        ksession.fireAllRules();

        Assert.assertEquals(p.getSuggestedTreatment(),"LAMA + LABA + CI");
    }
    @Test
    public void testPhenotypeCSeverity4(){
        Patient p = new Patient();
        p.setPhenotype('C');
        p.setSeverityLevel(4);
        ksession.insert(p);
        ksession.fireAllRules();

        Assert.assertEquals(p.getSuggestedTreatment(),"LAMA + LABA + CI, Consider adding Teofiline");
    }
    @Test
    public void testPhenotypeDSeverity1(){
        Patient p = new Patient();
        p.setPhenotype('D');
        p.setSeverityLevel(1);
        ksession.insert(p);
        ksession.fireAllRules();

        Assert.assertEquals(p.getSuggestedTreatment(),"LAMA / LABA");
    }
    @Test
    public void testPhenotypeDSeverity2(){
        Patient p = new Patient();
        p.setPhenotype('D');
        p.setSeverityLevel(2);
        ksession.insert(p);
        ksession.fireAllRules();

        Assert.assertEquals(p.getSuggestedTreatment(),"(LAMA / LABA) + (CI / IPE4) or LAMA + LABA or  LAMA / LABA");
    }
    @Test
    public void testPhenotypeDSeverity3(){
        Patient p = new Patient();
        p.setPhenotype('D');
        p.setSeverityLevel(3);
        ksession.insert(p);
        ksession.fireAllRules();

        Assert.assertEquals(p.getSuggestedTreatment(),"LAMA + LABA + (CI / IPE4) or (LAMA / LABA) + CI + IPE4, and consider adding Carbocisteine");
    }
    @Test
    public void testPhenotypeDSeverity4(){
        Patient p = new Patient();
        p.setPhenotype('D');
        p.setSeverityLevel(4);
        ksession.insert(p);
        ksession.fireAllRules();

        Assert.assertEquals(p.getSuggestedTreatment(),"LAMA + LABA + (CI / IPE4) or LAMA + LABA + CI + IPE4, and consider adding Carbocisteine/Antibiotics");
    }
    
}
