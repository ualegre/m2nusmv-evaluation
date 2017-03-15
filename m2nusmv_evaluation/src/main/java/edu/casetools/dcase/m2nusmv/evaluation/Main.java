package edu.casetools.dcase.m2nusmv.evaluation;

import edu.casetools.dcase.m2nusmv.evaluation.generators.Configurations;
import edu.casetools.dcase.m2nusmv.evaluation.generators.ProportionalModelGenerator;

public class Main {
	
	private Main(){
		
	}
	
	public static void main(String[] args) {
		try{
			Configurations configs = new Configurations();
							configs.setRepeat(10);
							configs.setAntecedentNo(2);
							configs.setStateNo((configs.getAntecedentNo() +1) * 2);
							configs.setStrNo(1);
							configs.setNtrNo(1);
							configs.setSipNo(1);
							configs.setWipNo(1);
							configs.setImmediateOperatorLength(3);
							configs.setSapNo(1);
							configs.setWapNo(1);
							configs.setAbsoluteOperatorStart(5);
							configs.setAbsoluteOperatorLength(5);
							configs.setAbsoluteOperatorEnd(configs.getAbsoluteOperatorStart()+configs.getAbsoluteOperatorLength());
							configs.setMaxUppBound(5);
							configs.setMaxIteration(8);
							configs.printSetup();

							new ProportionalModelGenerator(configs).generate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}








