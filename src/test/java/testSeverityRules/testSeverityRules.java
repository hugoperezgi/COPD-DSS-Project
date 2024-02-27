package testSeverityRules;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import entities.*;

public class testSeverityRules {

    private static KieServices ksSever;
    private static KieContainer kcSever;
    private static KieBase kbSever;
    private static KieSession ksessionSever;

    @Before
    public void setUpDrools(){
        ksSever = KieServices.Factory.get();
        kcSever = ksSever.getKieClasspathContainer();
        kbSever = kcSever.getKieBase();
        ksessionSever = kbSever.newKieSession();
    }
    @After
    public void tearDownDrools(){
        ksessionSever.dispose();
    }

    @Test
    public void testSeverityRulesTrue(){
        Assert.assertEquals(true,true);
    }
    
}
