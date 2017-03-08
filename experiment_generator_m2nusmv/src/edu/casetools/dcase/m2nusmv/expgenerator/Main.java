package edu.casetools.dcase.m2nusmv.expgenerator;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class Main {
	private Random randomGenerator;
	private final int repeat   = 10;
	private final int antecedent_no =2;
	private final int state_no = ((antecedent_no +1) * 2)*repeat;
	private final int str_no   = 1;
	private final int ntr_no   = 1;
	private final int sip_no   = 1;
	private final int wip_no   = 1;	
	private final int sap_no   = 1;
	private final int wap_no   = 1;
	private final int max_upp_bound = 5;
	private final int max_iteration = 20; //max_upp_bound + 2;
	
	private FileWriter filestream;
	private BufferedWriter writer;
	
	public Main() throws IOException{
		randomGenerator = new Random();
	    startWriter();
	    System.out.println("MAX_ITERATION: "+max_iteration+"\n");
	    System.out.println("STATE_NO: "+state_no+"\n");
	    System.out.println("STR_NO: "+str_no+"\n");
	    System.out.println("NTR_NO: "+ntr_no+"\n");
	    System.out.println("Operator Strong Immediate Past: "+sip_no+"\n");
	    System.out.println("Operator Weak Immediate Past: "+wip_no+"\n");
	    System.out.println("Operator Strong Absolute Past: "+sap_no+"\n");
	    System.out.println("Operator Weak Absolute Past: "+wap_no+"\n");
	}

	private void startWriter() throws IOException {
		filestream = new FileWriter("M_"+state_no+".smv");
	    writer = new BufferedWriter(filestream);
	}
	
	public static void main(String[] args) {
		try{
			Main m = new Main();
			m.write();
			m.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void restartWriter() throws IOException{
	    writer.close();
	    startWriter();
	}
	
	public void write() throws IOException {
	    writeMainModule();

	}

	private void writeMainModule() throws IOException {
		writer.append("MODULE main \n");
	    writeVariables();
	    writeValueAssignations();
	}

	private void writeValueAssignations() throws IOException {
		writer.append("ASSIGN\n");
		writer.append("\tinit(time) := 0; \n");	
		writer.append("\n");
		for(int i=0;i<repeat;i++){ writeSTR(antecedent_no,i);
		writer.append("\n");}
		for(int i=0;i<repeat;i++){writeNTR(((antecedent_no*2)+1),i);
		writer.append("\n");}
		writer.append("\tnext(time) := case \r\n\t\t\t\t\t (time < "+max_iteration+") : time+1;\r\n\t\t\t\t\t TRUE : "+max_iteration+";\r\n\t\t\t\t  esac;\n");
	}

	private void writeNTR(int unit, int number) throws IOException {
		writer.append("\tnext(state"+unit+"_"+number+") := case\n");	
		for(int i=antecedent_no+1; i < unit;i++){
			if(i==(antecedent_no+1)) writer.append("\t\t\t\t\t\t(state"+i+"_"+number+" = "+getRandomStatus()+")");	
			else writer.append(" & (state"+i+"_"+number+" = "+getRandomStatus()+")");
		}
		writer.append(": "+getRandomStatus()+";\n");
		writer.append("\t\t\t\t\t\tTRUE : state"+unit+"_"+number+";\n");
		writer.append("\t\t\t\t    esac;\n");
	}

	private void writeSTR(int unit, int number) throws IOException {
		writer.append("\tstate"+unit+"_"+number+" := case\n");	
		for(int i=0; i < unit;i++){
			if(i==0) writer.append("\t\t\t\t(state"+i+"_"+number+" = "+getRandomStatus()+")");	
			else writer.append(" & (state"+i+"_"+number+" = "+getRandomStatus()+")");
		}
		writer.append(": "+getRandomStatus()+";\n");
		writer.append("\t\t\t\tTRUE : state"+unit+"_"+number+"_aux;\n");
		writer.append("\t\t\t  esac;\n\n");
		writer.append("\tnext(state"+unit+"_"+number+"_aux) := state"+unit+"_"+number+";\n");
	}
	
	private String getRandomStatus(){
		if(randomGenerator.nextBoolean()){
			return "TRUE";
		} else return "FALSE";
	}

	private void writeVariables() throws IOException {
		writer.append("VAR\n");
		writer.append("\ttime : 0.."+max_iteration+"; \n\n");
	    for(int i=0;i<repeat;i++) writeStateVariables(i);
	    writer.append("\n");
	    for(int i=0;i<repeat;i++) writeAuxiliaryStateVariables(i);
	    writer.append("\n");
	}

	private void writeStateVariables(int number) throws IOException {
		for(int i=0;i<state_no;i++)
		    writer.append("\tstate"+i+"_"+number+" : boolean; \n");
				
	}
	
	private void writeAuxiliaryStateVariables(int number) throws IOException {
		int unit = antecedent_no;
		while(unit < state_no){
		    writer.append("\tstate"+unit+"_"+number+"_aux : boolean; \n");
		    unit = unit + (antecedent_no+1);
		}
				
	}

	private void close() throws IOException{
	    writer.close();
	}
	

}
