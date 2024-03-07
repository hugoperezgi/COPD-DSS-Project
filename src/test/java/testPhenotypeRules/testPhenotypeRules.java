package testPhenotypeRules;

import entities.MedicalHistory;
import entities.SignsAndSymptoms;
import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import entities.MedicalHistory_Unit;

public class testPhenotypeRules {
    
    private static MedicalHistory_Unit mHistUnit;
    private static RuleUnitInstance<MedicalHistory_Unit> droolsInstance;

    @Before
    public void setUpDrools(){
        mHistUnit = new MedicalHistory_Unit();
        droolsInstance = RuleUnitProvider.get().createRuleUnitInstance(mHistUnit);
    }
    @After
    public void tearDownDrools(){
        droolsInstance.close();
    }

    @Test
    public void testPhenotypeRulesTrue(){
        Assert.assertEquals(true,true);
    }

    @Test
    public void testPhenotypeA(){

        MedicalHistory m = new MedicalHistory();
        SignsAndSymptoms ss = new SignsAndSymptoms();
        ss.setExacerbationCount(1);
        ss.setMixed_asthma(true,false,false,false,false,false);
        ss.setCough(true);
        ss.setChronicExpectoration(true);
        m.setSignsAndSymptoms(ss);

        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals('A',m.getPhenotype());
    }

    @Test
    public void testPhenotypeB1(){

        MedicalHistory m = new MedicalHistory();
        SignsAndSymptoms ss = new SignsAndSymptoms();
        ss.setExacerbationCount(0);
        ss.setMixed_asthma(true,true,false,false,false,false);
        m.setSignsAndSymptoms(ss);

        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals('B',m.getPhenotype());
    }

    @Test
    public void testPhenotypeB2(){

        MedicalHistory m = new MedicalHistory();
        SignsAndSymptoms ss = new SignsAndSymptoms();
        ss.setExacerbationCount(5);
        ss.setTimeBetweenExacerbations(7);
        ss.setMixed_asthma(true,false,false,true,true,false);
        m.setSignsAndSymptoms(ss);

        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals('B',m.getPhenotype());
    }

    @Test
    public void testPhenotypeC(){

        MedicalHistory m = new MedicalHistory();
        SignsAndSymptoms ss = new SignsAndSymptoms();
        ss.setExacerbationCount(5);
        ss.setTimeBetweenExacerbations(2);
        ss.setMixed_asthma(true,false,false,true,false,false);
        ss.setCough(false);
        ss.setChronicExpectoration(false);
        m.setSignsAndSymptoms(ss);

        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals('C',m.getPhenotype());
    }

    @Test
    public void testPhenotypeD(){

        MedicalHistory m = new MedicalHistory();
        SignsAndSymptoms ss = new SignsAndSymptoms();
        ss.setExacerbationCount(4);
        ss.setTimeBetweenExacerbations(2);
        ss.setMixed_asthma(true,false,false,true,false,false);
        ss.setCough(true);
        ss.setChronicExpectoration(true);
        m.setSignsAndSymptoms(ss);

        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals('D',m.getPhenotype());
    }

    @Test
    public void testPhenotypeNonConclusive(){

        MedicalHistory m = new MedicalHistory();
        SignsAndSymptoms ss = new SignsAndSymptoms();
        ss.setExacerbationCount(1);
        ss.setTimeBetweenExacerbations(2);
        ss.setMixed_asthma(true,false,false,true,false,false);
        ss.setCough(true);
        ss.setChronicExpectoration(false);
        m.setSignsAndSymptoms(ss);

        mHistUnit.getMHist().add(m);
        droolsInstance.fire();

        Assert.assertEquals(0,m.getPhenotype());
    }


}
