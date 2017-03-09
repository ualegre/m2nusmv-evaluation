package edu.casetools.dcase.m2nusmv.evaluation.generators;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public abstract class GenericGenerator {

	protected int repeat;
	protected int antecedent_no;
	protected int state_no;
	protected int str_no;
	protected int ntr_no;
	protected int sip_no;
	protected int wip_no;	
	protected int immediate_operator_length;
	protected int sap_no;
	protected int wap_no;
	protected int absolute_operator_start;
	protected int absolute_operator_length;
	protected int absolute_operator_end;
	protected int max_upp_bound;
	protected int max_iteration; 
	protected int length;
	
	protected FileWriter filestream;
	protected BufferedWriter writer;
	protected Random randomGenerator;
	
	protected String name;
	
	public GenericGenerator() throws IOException{
		initialiseValues();
		initialiseName();
		printSetup();
		filestream = new FileWriter(name);
	    writer = new BufferedWriter(filestream);
		randomGenerator = new Random();
 
	}

	protected abstract void initialiseValues();
	protected abstract void initialiseName();

	private void printSetup() throws IOException {
	    print("MAX_ITERATION: "+max_iteration+"\n");
	    print("STATE_NO: "+state_no+"\n");
	    print("STR_NO: "+str_no+"\n");
	    print("NTR_NO: "+ntr_no+"\n");
	    print("Operator Strong Immediate Past: "+sip_no+"\n");
	    print("Operator Weak Immediate Past: "+wip_no+"\n");
	    print("Immediate Operator length: "+immediate_operator_length+"\n");
	    print("Operator Strong Absolute Past: "+sap_no+"\n");
	    print("Operator Weak Absolute Past: "+wap_no+"\n");
	    print("Absolute Operator length: "+absolute_operator_length+"\n");	   
	}
	
	private void print(String s){
		System.out.println(s);
	}
	
	public void generate() throws IOException{
		this.write();
	    this.writer.close();
	}
	

	private void write() throws IOException {
	    writeMainModule();
	    writer.append("\n");
	    if(sip_no > 0) 
	    	writeStrongImmediatePastModule();
	    if(wip_no > 0) 
	    	writeWeakImmediatePastModule();
	    if(sap_no > 0) 
	    	writeStrongAbsolutePastModule();
	    if(wap_no > 0) 
	    	writeWeakAbsolutePastModule();
	}

	private void writeMainModule() throws IOException {
		writer.append("MODULE main \n");
	    writeVariables();
	    writeValueAssignations();
	}

	protected abstract void writeValueAssignations() throws IOException;

	protected void writeNTR(int unit, int number) throws IOException {
		writer.append("\tnext(state"+unit+"_"+number+") := case\n");	
		for(int i=antecedent_no+1; i < unit;i++){
			if(i==(antecedent_no+1)) 
				writer.append("\t\t\t\t\t\t(state"+i+"_"+number+" = "+getRandomStatus()+")");	
			else writer.append(" & (state"+i+"_"+number+" = "+getRandomStatus()+")");
		}
		writer.append(": "+getRandomStatus()+";\n");
		writer.append("\t\t\t\t\t\tTRUE : state"+unit+"_"+number+";\n");
		writer.append("\t\t\t\t    esac;\n\n");
	}

	protected void writeSTR(int unit, int number) throws IOException {
		writer.append("\tstate"+unit+"_"+number+" := case\n");	
		for(int i=0; i < unit;i++){
			if(i==0) 
				writer.append("\t\t\t\t(state"+i+"_"+number+" = "+getRandomStatus()+")");	
			else writer.append(" & (state"+i+"_"+number+" = "+getRandomStatus()+")");
		}
		writer.append(": "+getRandomStatus()+";\n");
		writer.append("\t\t\t\tTRUE : state"+unit+"_"+number+"_aux;\n");
		writer.append("\t\t\t  esac;\n\n");
		writer.append("\tnext(state"+unit+"_"+number+"_aux) := state"+unit+"_"+number+";\n\n");
	}
	
	private String getRandomStatus(){
		if(randomGenerator.nextBoolean()){
			return "TRUE";
		} else return "FALSE";
	}

	protected abstract void writeVariables() throws IOException;

	protected void writeTemporalOperatorVariables(int number) throws IOException {
		for(int i=0;i<sip_no;i++)
		    writer.append("\tsip"+i+"_"+number+" : strong_immediate_past(state0_"+number+","+immediate_operator_length+"); \n");
		for(int i=0;i<wip_no;i++)
		    writer.append("\twip"+i+"_"+number+" : boolean; \n");
		for(int i=0;i<sap_no;i++)
		    writer.append("\tsap"+i+"_"+number+" : boolean; \n");
		for(int i=0;i<wap_no;i++)
		    writer.append("\twap"+i+"_"+number+" : boolean; \n");
		
	}

	protected void writeStateVariables(int number) throws IOException {
		for(int i=0;i<state_no;i++)
		    writer.append("\tstate"+i+"_"+number+" : boolean; \n");
				
	}
	
	protected void writeAuxiliaryStateVariables(int number) throws IOException {
		int unit = antecedent_no;
		while(unit < state_no){
		    writer.append("\tstate"+unit+"_"+number+"_aux : boolean; \n");
		    unit = unit + (antecedent_no+1);
		}
				
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
