package edu.casetools.dcase.m2nusmv.evaluation.generators;
import java.io.IOException;


public class ProportionalGenerator extends GenericGenerator{
	
	public ProportionalGenerator() throws IOException{
		super();
	}
	
	@Override
	protected void initialiseValues() {
		repeat   = 5;
		antecedent_no =2;
		state_no = ((antecedent_no +1) * 2)*repeat;
		str_no   = 1;
		ntr_no   = 1;
		sip_no   = 1;
		wip_no   = 1;	
		immediate_operator_length = 3;
		sap_no   = 1;
		wap_no   = 1;
		absolute_operator_start = 5;
		absolute_operator_length = 5;
		absolute_operator_end = absolute_operator_start + absolute_operator_length;
		max_upp_bound = 5;
		max_iteration = 20; 
		length = repeat;

	}
	
	@Override
	protected void initialiseName() {
		name = "M_Proportional_Length"+length+"_State_"+state_no+".smv";
		
	}
	
	@Override
	protected void writeValueAssignations() throws IOException {
		writer.append("ASSIGN\n");
		writer.append("\tinit(time) := 0; \n");	
		writer.append("\n");
		for(int i=0;i<repeat;i++) writeSTR(antecedent_no,i);
		for(int i=0;i<repeat;i++) writeNTR(((antecedent_no*2)+1),i);
		writer.append("\tnext(time) := case \r\n\t\t\t\t\t (time < "+max_iteration+") : time+1;\r\n\t\t\t\t\t TRUE : "+max_iteration+";\r\n\t\t\t\t  esac;\n");
		
	}

	@Override
	protected void writeVariables() throws IOException {
		writer.append("VAR\n");
		writer.append("\ttime : 0.."+max_iteration+"; \n\n");
	    for(int i=0;i<repeat;i++) writeStateVariables(i);
	    	writer.append("\n");
	    for(int i=0;i<repeat;i++) writeAuxiliaryStateVariables(i);
	    	writer.append("\n\n");
	    for(int i=0;i<repeat;i++) writeTemporalOperatorVariables(i);
	    	writer.append("\n\n");	
		
	}

}
