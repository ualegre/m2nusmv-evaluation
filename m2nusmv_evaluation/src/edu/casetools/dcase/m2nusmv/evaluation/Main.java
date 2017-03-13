package edu.casetools.dcase.m2nusmv.evaluation;

import edu.casetools.dcase.m2nusmv.evaluation.generators.Configurations;
import edu.casetools.dcase.m2nusmv.evaluation.generators.ModelGenerator;

public class Main {
	
	public static void main(String[] args) {
		try{
			Configurations configs = new Configurations();
							configs.setRepeat(3);
							configs.setAntecedent_no(2);
							configs.setState_no(((configs.getAntecedent_no() +1) * 2));
							configs.setStr_no(1);
							configs.setNtr_no(1);
							configs.setSip_no(1);
							configs.setWip_no(1);
							configs.setImmediate_operator_length(3);
							configs.setSap_no(1);
							configs.setWap_no(1);
							configs.setAbsolute_operator_start(5);
							configs.setAbsolute_operator_length(5);
							configs.setAbsolute_operator_end(configs.getAbsolute_operator_start()+configs.getAbsolute_operator_length());
							configs.setMax_upp_bound(5);
							configs.setMax_iteration(8);
							configs.printSetup();

							new ModelGenerator(configs).generate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}








