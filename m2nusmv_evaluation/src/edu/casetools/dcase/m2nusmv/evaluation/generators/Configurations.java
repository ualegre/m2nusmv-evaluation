package edu.casetools.dcase.m2nusmv.evaluation.generators;

public class Configurations {

	private int repeat;
	private int antecedent_no;
	private int state_no;
	private int str_no;
	private int ntr_no;
	private int sip_no;
	private int wip_no;	
	private int immediate_operator_length;
	private int sap_no;
	private int wap_no;
	private int absolute_operator_start;
	private int absolute_operator_length;
	private int absolute_operator_end;
	private int max_upp_bound;
	private int max_iteration; 
	private String filename;
	
	private void initialiseName() {
		filename = "M_Proportional_Length"+repeat+"_State_"+(state_no*repeat)+".smv";		
	}
	
	public void printSetup() {
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

	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	public int getAntecedent_no() {
		return antecedent_no;
	}

	public void setAntecedent_no(int antecedent_no) {
		this.antecedent_no = antecedent_no;
	}

	public int getState_no() {
		return state_no;
	}

	public void setState_no(int state_no) {
		this.state_no = state_no;
	}

	public int getStr_no() {
		return str_no;
	}

	public void setStr_no(int str_no) {
		this.str_no = str_no;
	}

	public int getNtr_no() {
		return ntr_no;
	}

	public void setNtr_no(int ntr_no) {
		this.ntr_no = ntr_no;
	}

	public int getSip_no() {
		return sip_no;
	}

	public void setSip_no(int sip_no) {
		this.sip_no = sip_no;
	}

	public int getWip_no() {
		return wip_no;
	}

	public void setWip_no(int wip_no) {
		this.wip_no = wip_no;
	}

	public int getImmediate_operator_length() {
		return immediate_operator_length;
	}

	public void setImmediate_operator_length(int immediate_operator_length) {
		this.immediate_operator_length = immediate_operator_length;
	}

	public int getSap_no() {
		return sap_no;
	}

	public void setSap_no(int sap_no) {
		this.sap_no = sap_no;
	}

	public int getWap_no() {
		return wap_no;
	}

	public void setWap_no(int wap_no) {
		this.wap_no = wap_no;
	}

	public int getAbsolute_operator_start() {
		return absolute_operator_start;
	}

	public void setAbsolute_operator_start(int absolute_operator_start) {
		this.absolute_operator_start = absolute_operator_start;
	}

	public int getAbsolute_operator_length() {
		return absolute_operator_length;
	}

	public void setAbsolute_operator_length(int absolute_operator_length) {
		this.absolute_operator_length = absolute_operator_length;
	}

	public int getAbsolute_operator_end() {
		return absolute_operator_end;
	}

	public void setAbsolute_operator_end(int absolute_operator_end) {
		this.absolute_operator_end = absolute_operator_end;
	}

	public int getMax_upp_bound() {
		return max_upp_bound;
	}

	public void setMax_upp_bound(int max_upp_bound) {
		this.max_upp_bound = max_upp_bound;
	}

	public int getMax_iteration() {
		return max_iteration;
	}

	public void setMax_iteration(int max_iteration) {
		this.max_iteration = max_iteration;
	}

	public String getFilename() {
		initialiseName();
		return filename;
	}
	
	
	
}
