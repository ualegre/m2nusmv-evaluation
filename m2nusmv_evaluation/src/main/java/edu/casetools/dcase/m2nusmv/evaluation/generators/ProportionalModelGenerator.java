/*
 * 	 This file is part of M2NuSMV_Evaluation.
 *
 *   M2NuSMV_Evaluation is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License.
 *
 *   M2NuSMV_Evaluation is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with M2NuSMV_Evaluation.  If not, see <http://www.gnu.org/licenses/>.
 *   
 */
package edu.casetools.dcase.m2nusmv.evaluation.generators;
import java.io.IOException;

import edu.casetools.dcase.m2nusmv.M2NuSMV;
import edu.casetools.dcase.m2nusmv.data.MData;
import edu.casetools.dcase.m2nusmv.data.elements.BoundedOperator;
import edu.casetools.dcase.m2nusmv.data.elements.BoundedOperator.BOP_TYPE;
import edu.casetools.dcase.m2nusmv.data.elements.Rule;
import edu.casetools.dcase.m2nusmv.data.elements.RuleElement;
import edu.casetools.dcase.m2nusmv.data.elements.Specification;
import edu.casetools.dcase.m2nusmv.data.elements.Specification.TYPE;
import edu.casetools.dcase.m2nusmv.data.elements.State;


public class ProportionalModelGenerator {
	
	private Configurations configs;
	private MData data;
	private M2NuSMV translator;
	private int strId;
	private int ntrId;
	
	public ProportionalModelGenerator(Configurations configs) throws IOException{
		this.configs = configs;
		data = new MData();
		translator = new M2NuSMV();
		strId = 0;
		ntrId = 0;
	}
	
	public void generate() throws IOException{
		generateData();
		translator.writeModel(data);
	}
	

	private void generateData() throws IOException {
		data = new MData();
		data.setMaxIteration(configs.getMaxIteration());
		data.setFilePath(configs.getFilename());
	    generateStates();
	    writeValueAssignations();
	    writeSpecifications();
	}


	protected void writeValueAssignations() throws IOException {

		for(int i=0;i<configs.getRepeat();i++) {
			data.getStrs().add(generateRule(configs.getAntecedentNo(),i,0,strId));
			strId++;
		}
		for(int i=0;i<configs.getRepeat();i++) {
			data.getNtrs().add(generateRule((configs.getAntecedentNo()*2)+1,i,configs.getAntecedentNo()+1,ntrId));
			ntrId++;
		}
		

	}

	protected void generateStates() throws IOException {

	    for(int i=0;i<configs.getRepeat();i++) 
	    	generateStates(i);

	    for(int i=0;i<configs.getRepeat();i++) 
	    	configureDependentStates();

	    for(int i=0;i<configs.getRepeat();i++) 
	    	generateTemporalOperators(i);

		
	}
	
	protected void writeSpecifications() throws IOException {
		
		for(int i=0;i<configs.getRepeat()/2;i++) {
			Specification spec = writeSTRSpecifications(configs.getAntecedentNo(),i);
			data.getSpecifications().add(spec);
		}


		for(int i=0;i<configs.getRepeat()/2;i++) 
			data.getSpecifications().add(writeNTRSpecifications((configs.getAntecedentNo()*2)+1,i));

	}

	private Specification writeSTRSpecifications(int unit, int number) throws IOException {
		Specification specification = new Specification();
		specification.setId(Integer.toString(number));
		String specificationText = "AG(";
		for(int i=0; i < unit;i++){
			if(i==0) 
				specificationText = specificationText + "(state"+i+"_"+number+" = TRUE)";	
			else specificationText = specificationText + " & (state"+i+"_"+number+" = TRUE)";
		}
		specificationText = specificationText + " -> (state"+unit+"_"+number+" = TRUE) )\n";
		specification.setSpec(specificationText);
		specification.setType(TYPE.CTL);
		return specification;
	}
	
	private Specification writeNTRSpecifications(int unit, int number) throws IOException {
		Specification specification = new Specification();
		specification.setId(Integer.toString(number));
		String specificationText = "AG(";
		for(int i=configs.getAntecedentNo()+1; i < unit;i++){
			if(i==(configs.getAntecedentNo()+1)) 
				specificationText = specificationText + "(state"+i+"_"+number+" = TRUE)";	
			else specificationText = specificationText + " & (state"+i+"_"+number+" = TRUE)";
		}
		specificationText = specificationText + " -> AX(state"+unit+"_"+number+" = TRUE) )\n";
		specification.setSpec(specificationText);
		specification.setType(TYPE.CTL);
		return specification;
	}


	protected Rule generateRule(int unit, int number, int start, int id) throws IOException {
		Rule str = new Rule(); 
		str.setId(Integer.toString(id));
		for(int i=start; i < unit;i++){
			RuleElement antecedent = new RuleElement();
			antecedent.setName("state"+i+"_"+number);
			antecedent.setStatus("TRUE");
			str.getAntecedents().add(antecedent);
		}

		RuleElement consequent = new RuleElement();
		consequent.setName("state"+unit+"_"+number);
		consequent.setStatus("TRUE");
		str.setConsequent(consequent);
		
		return str;
	}	

	protected void generateTemporalOperators(int number) throws IOException {
		int stateNumberCounter = 0;
		
		stateNumberCounter = generateTemporalOperator("sip", stateNumberCounter, number, configs.getSipNo(), BOP_TYPE.STRONG_IMMEDIATE_PAST, configs.getImmediateOperatorLength(), 0);
		stateNumberCounter = generateTemporalOperator("wip", stateNumberCounter, number, configs.getWipNo(), BOP_TYPE.WEAK_IMMEDIATE_PAST, configs.getImmediateOperatorLength(), 0);
		stateNumberCounter = generateTemporalOperator("sap", stateNumberCounter, number, configs.getSapNo(), BOP_TYPE.STRONG_ABSOLUTE_PAST, configs.getAbsoluteOperatorStart(), configs.getAbsoluteOperatorEnd());		
							 generateTemporalOperator("wap", stateNumberCounter, number, configs.getWapNo(), BOP_TYPE.WEAK_ABSOLUTE_PAST, configs.getAbsoluteOperatorStart(), configs.getAbsoluteOperatorEnd());		

	}

	private int generateTemporalOperator(String operatorName, int stateNumberCounter, int number, int operatorNumber, BOP_TYPE type, int lowBound, int uppBound) throws IOException {
		int auxStateNumberCounter = stateNumberCounter;

		for(int i=0;i<operatorNumber;i++){
			BoundedOperator bop = new BoundedOperator();
			bop.setId(Integer.toString(number));
			bop.setOperatorName(operatorName+i+"_"+number);
			bop.setStateName("state"+auxStateNumberCounter+"_"+number);
			bop.setStatus("TRUE");
			bop.setType(type);
			bop.setLowBound(Integer.toString(lowBound));
			bop.setUppBound(Integer.toString(uppBound));
			data.getBops().add(bop);
		    
			if(configs.getAntecedentNo() > auxStateNumberCounter) 
				auxStateNumberCounter++;
		}
		return auxStateNumberCounter;
	}



	protected void generateStates(int number) throws IOException {
		for(int i=0;i<configs.getStateNo();i++){
			State state = new State();
			state.setName("state"+i+"_"+number);
			state.setIndepedence(true);
			data.getStates().add(state);
		}
	}
	
	protected void configureDependentStates() throws IOException {
		int unit = configs.getAntecedentNo();
		while(unit < (configs.getStateNo()*configs.getRepeat())){
			data.getStates().get(unit).setIndepedence(false);
		    unit = unit + (configs.getAntecedentNo()+1);
		}		
	}
	
}
