package testPhenotypeRules;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import entities.*;

public class testPhenotypeRules {

    private static KieServices ksPheno;
    private static KieContainer kcPheno;
    private static KieBase kbPheno;
    private static KieSession ksessionPheno;

    @Before
    public void setUpDrools(){
        ksPheno = KieServices.Factory.get();
        kcPheno = ksPheno.getKieClasspathContainer();
        kbPheno = kcPheno.getKieBase();
        ksessionPheno = kbPheno.newKieSession();
    }
    @After
    public void tearDownDrools(){
        ksessionPheno.dispose();
    }

    @Test
    public void testPhenotypeRulesTrue(){
        Assert.assertEquals(true,true);
    }

}
