package edu.casetools.dcase.m2nusmv.evaluation.generators;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class ModelGenerator {
	
	protected FileWriter filestream;
	protected BufferedWriter writer;
	protected Configurations configs;
	
	public ModelGenerator(Configurations configs) throws IOException{
		filestream = new FileWriter(configs.getFilename());
	    writer = new BufferedWriter(filestream);
		this.configs = configs;
	}
	
	public void generate() throws IOException{
		this.write();
	    this.writer.close();
	}
	

	private void write() throws IOException {
	    writeMainModule();
	    writer.append("\n");
	    if(configs.getSip_no() > 0) 
	    	writeStrongImmediatePastModule();
	    if(configs.getWip_no() > 0) 
	    	writeWeakImmediatePastModule();
	    if(configs.getSap_no() > 0) 
	    	writeStrongAbsolutePastModule();
	    if(configs.getWap_no() > 0) 
	    	writeWeakAbsolutePastModule();
	}

	private void writeMainModule() throws IOException {
		writer.append("MODULE main \n");
	    writeVariables();
	    writeValueAssignations();
	    writeSpecifications();
	}


	protected void writeValueAssignations() throws IOException {
		writer.append("ASSIGN\n");
		writer.append("\tinit(time) := 0; \n");	
		generateInitialisations();
		writer.append("\n");
		for(int i=0;i<configs.getRepeat();i++) 
			writeSTR(configs.getAntecedent_no(),i);
		for(int i=0;i<configs.getRepeat();i++) 
			writeNTR((configs.getAntecedent_no()*2)+1,i);
		writer.append("\tnext(time) := case \r\n\t\t\t\t\t (time < "+configs.getMax_iteration()+") : time+1;\r\n\t\t\t\t\t TRUE : "+configs.getMax_iteration()+";\r\n\t\t\t\t  esac;\n");
		
	}


	private void generateInitialisations() throws IOException {
		for(int i=0;i<configs.getRepeat();i++) 
			generateSTRVariableInitialisation(configs.getAntecedent_no(),i);
		for(int i=0;i<configs.getRepeat();i++) 
			generateNTRVariableInitialisation((configs.getAntecedent_no()*2)+1,i);
		
	}

	private void generateSTRVariableInitialisation(int unit, int number) throws IOException {
		writer.append("\tinit(state"+unit+"_"+number+"_aux) := FALSE;\n");	
	}
	
	private void generateNTRVariableInitialisation(int unit, int number) throws IOException {
		writer.append("\tinit(state"+unit+"_"+number+") := FALSE;\n");	
	}


	protected void writeVariables() throws IOException {
		writer.append("VAR\n");
		writer.append("\ttime : 0.."+configs.getMax_iteration()+"; \n\n");
	    for(int i=0;i<configs.getRepeat();i++) 
	    	writeStateVariables(i);
	    writer.append("\n");
	    for(int i=0;i<configs.getRepeat();i++) 
	    	writeAuxiliaryStateVariables(i);
	    writer.append("\n\n");
	    for(int i=0;i<configs.getRepeat();i++) 
	    	writeTemporalOperatorVariables(i);
	    writer.append("\n\n");	
		
	}
	
	protected void writeSpecifications() throws IOException {
		writer.append("\n-- Same Time Rules \n");
		for(int i=0;i<configs.getRepeat()/2;i++) 
			writeSTRSpecifications(configs.getAntecedent_no(),i);
		writer.append("\n-- Next Time Rules \n");
		for(int i=0;i<configs.getRepeat()/2;i++) 
			writeNTRSpecifications((configs.getAntecedent_no()*2)+1,i);
		writer.append("\n");
	}

	private void writeSTRSpecifications(int unit, int number) throws IOException {
		writer.append("\tSPEC \n\t\tAG(");
		for(int i=0; i < unit;i++){
			if(i==0) 
				writer.append("(state"+i+"_"+number+" = TRUE)");	
			else writer.append(" & (state"+i+"_"+number+" = TRUE)");
		}
		writer.append(" -> (state"+unit+"_"+number+" = TRUE) )\n");
	}
	
	private void writeNTRSpecifications(int unit, int number) throws IOException {
		writer.append("\tSPEC \n\t\tAG(");
		for(int i=configs.getAntecedent_no()+1; i < unit;i++){
			if(i==(configs.getAntecedent_no()+1)) 
				writer.append("(state"+i+"_"+number+" = TRUE)");	
			else writer.append(" & (state"+i+"_"+number+" = TRUE)");
		}
		writer.append(" -> AX(state"+unit+"_"+number+" = TRUE) )\n");
	}

	protected void writeNTR(int unit, int number) throws IOException {
		writer.append("\tnext(state"+unit+"_"+number+") := case\n");	
		for(int i=configs.getAntecedent_no()+1; i < unit;i++){
			if(i==(configs.getAntecedent_no()+1)) 
				writer.append("\t\t\t\t\t\t(state"+i+"_"+number+" = TRUE)");	
			else writer.append(" & (state"+i+"_"+number+" = TRUE)");
		}
		writer.append(": TRUE;\n");
		writer.append("\t\t\t\t\t\tTRUE : state"+unit+"_"+number+";\n");
		writer.append("\t\t\t\t    esac;\n\n");
	}

	protected void writeSTR(int unit, int number) throws IOException {
		writer.append("\tstate"+unit+"_"+number+" := case\n");	
		for(int i=0; i < unit;i++){
			if(i==0) 
				writer.append("\t\t\t\t(state"+i+"_"+number+" = TRUE)");	
			else writer.append(" & (state"+i+"_"+number+" = TRUE)");
		}
		writer.append(": TRUE;\n");
		writer.append("\t\t\t\tTRUE : state"+unit+"_"+number+"_aux;\n");
		writer.append("\t\t\t  esac;\n\n");
		writer.append("\tnext(state"+unit+"_"+number+"_aux) := state"+unit+"_"+number+";\n\n");
	}	

	protected void writeTemporalOperatorVariables(int number) throws IOException {
		int stateNumberCounter = 0;

		stateNumberCounter = writeOperator("sip","strong_immediate_past",Integer.toString(configs.getImmediate_operator_length()), stateNumberCounter, number, configs.getSip_no());
		stateNumberCounter = writeOperator("wip","weak_immediate_past",Integer.toString(configs.getImmediate_operator_length()), stateNumberCounter, number,  configs.getWip_no());
		stateNumberCounter = writeOperator("sap","strong_absolute_past",configs.getAbsolute_operator_start()+","+configs.getAbsolute_operator_end()+",time", stateNumberCounter, number,  configs.getSap_no());	
		writeOperator("wap","weak_absolute_past",configs.getAbsolute_operator_start()+","+configs.getAbsolute_operator_end()+",time", stateNumberCounter, number,  configs.getWap_no());	
		writer.append("\n");
	}

	private int writeOperator(String operatorName, String operatorType, String bound, int stateNumberCounter, int number, int operatorNumber) throws IOException {
		int auxStateNumberCounter = stateNumberCounter;
		
		for(int i=0;i<operatorNumber;i++){
		    writer.append("\t"+operatorName+i+"_"+number+" : "+operatorType+"(state"+auxStateNumberCounter+"_"+number+","+bound+"); \n");
		
			if(configs.getAntecedent_no() > auxStateNumberCounter) 
				auxStateNumberCounter++;
		}
		return auxStateNumberCounter;
	}



	protected void writeStateVariables(int number) throws IOException {
		for(int i=0;i<configs.getState_no();i++)
		    writer.append("\tstate"+i+"_"+number+" : boolean; \n");
		writer.append("\n");		
	}
	
	protected void writeAuxiliaryStateVariables(int number) throws IOException {
		int unit = configs.getAntecedent_no();
		while(unit < configs.getState_no()){
		    writer.append("\tstate"+unit+"_"+number+"_aux : boolean; \n");
		    unit = unit + (configs.getAntecedent_no()+1);
		}
		writer.append("\n");			
	}
	
	private void writeStrongImmediatePastModule() throws IOException{
		writer.append("MODULE strong_immediate_past(state,bound)\r\nVAR\r\n  counter : 0..bound;\r\n  live  : boolean;\r\n  \r\nASSIGN\r\n  init(counter) := 0;\r\n  \r\n  live :=\t case\r\n\t\t  \t\t(counter = bound): TRUE;\r\n\t\t  \t\tTRUE: FALSE;\r\n\t\t     esac;\r\n  \r\n  next(counter) :=  case\r\n\t\t  \t\t\t\t(state=TRUE & counter < bound) : counter+1;\r\n\t\t\t\t\t\t(state=TRUE & counter = bound) : bound;\r\n\t\t  \t\t\t\tTRUE: 0;\r\n\t\t\t\t    esac;\n\n\n");
	}
	
	private void writeWeakImmediatePastModule() throws IOException{
		writer.append("MODULE weak_immediate_past(state,bound)\r\nVAR\r\n  counter  : 0..bound;\r\n  live\t: boolean;\r\n  live_aux : boolean;\r\n  \r\nASSIGN\r\n  init(counter) := 0;  \r\n  init(live_aux) := FALSE;\r\n  \r\n  live := case\r\n\t\t\t\t(state=TRUE)  : TRUE;\r\n\t\t\t\t(state=FALSE) & (counter = bound) : FALSE;\r\n\t\t\t\tTRUE: live_aux;\r\n\t\t  esac;\r\n\t\t  \r\n  next(live_aux) := live;\t  \r\n\t\t  \r\n  next(counter) := \tcase\r\n\t\t\t\t\t\t(state = TRUE) : 0;\r\n\t\t  \t\t\t\t(live_aux=TRUE) & (counter < bound) : counter+1;\r\n\t\t  \t\t\t\tTRUE: 0;\r\n\t\t\t\t    esac;\t\n\n\n");
	}

	private void writeStrongAbsolutePastModule() throws IOException{
		writer.append("MODULE strong_absolute_past(state,low_bound,upp_bound,t)\r\nVAR\r\n  veredict\t : boolean;\r\n  veredict_aux  : boolean;\r\n  live \t\t\t: boolean;\r\n  \r\nASSIGN \r\n  init(veredict_aux) := TRUE;\r\n  init(live) := FALSE;\r\n  \r\n  veredict := case\r\n\t\t\t\t((state=FALSE) & (t >= low_bound) & ( t <= upp_bound))  : FALSE;\r\n\t\t\t\tTRUE: veredict_aux;\r\n\t\t\t  esac;  \r\n\n  next(veredict_aux) := veredict;\t  \r\n\n  next(live) := \tcase\r\n\t\t\t\t\t\t(t >= upp_bound) : veredict;\r\n\t\t  \t\t\t\tTRUE: FALSE;\r\n\t\t\t\t\tesac;\n\n\n");
	}
	
	private void writeWeakAbsolutePastModule() throws IOException{
		writer.append("MODULE weak_absolute_past(state,low_bound,upp_bound,t)\r\nVAR\r\n  veredict\t : boolean;\r\n  veredict_aux  : boolean;\r\n  live \t\t\t: boolean;\r\n  \r\nASSIGN\r\n  init(veredict_aux) := FALSE;\r\n  init(live) := FALSE;\r\n  \r\n  veredict := case\r\n\t\t\t\t(state=TRUE) & (t >= low_bound) & ( t <= upp_bound)  : TRUE;\r\n\t\t\t\tTRUE: veredict_aux;\r\n\t\t\t  esac;  \r\n\n  next(veredict_aux) := veredict;\t  \r\n\n  next(live) := \tcase\r\n\t\t\t\t\t\t(upp_bound >= t) : veredict;\r\n\t\t  \t\t\t\tTRUE: FALSE;\r\n\t\t\t\t    esac;\n\n\n");
	}
	
}
