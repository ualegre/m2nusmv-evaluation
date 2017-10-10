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

public class Configurations {

	private int repeat;
	private int antecedentNo;
	private int stateNo;
	private int strNo;
	private int ntrNo;
	private int sipNo;
	private int wipNo;	
	private int immediateOperatorLength;
	private int sapNo;
	private int wapNo;
	private int absoluteOperatorStart;
	private int absoluteOperatorLength;
	private int absoluteOperatorEnd;
	private int maxIteration; 
	private String filename;
	
	private void initialiseName() {
		filename = "M_Proportional_Length"+repeat+"_State_"+(stateNo*repeat)+"_time_"+maxIteration+".smv";		
	}
	
	public void printSetup() {
	    print("MAX_ITERATION: "+maxIteration+"\n");
	    print("STATE_NO: "+stateNo+"\n");
	    print("STR_NO: "+strNo+"\n");
	    print("NTR_NO: "+ntrNo+"\n");
	    print("Operator Strong Immediate Past: "+sipNo+"\n");
	    print("Operator Weak Immediate Past: "+wipNo+"\n");
	    print("Immediate Operator length: "+immediateOperatorLength+"\n");
	    print("Operator Strong Absolute Past: "+sapNo+"\n");
	    print("Operator Weak Absolute Past: "+wapNo+"\n");
	    print("Absolute Operator length: "+absoluteOperatorLength+"\n");	   
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

	public String getFilename() {
		initialiseName();
		return filename;
	}

	public int getAntecedentNo() {
		return antecedentNo;
	}

	public void setAntecedentNo(int antecedentNo) {
		this.antecedentNo = antecedentNo;
	}

	public int getStateNo() {
		return stateNo;
	}

	public void setStateNo(int stateNo) {
		this.stateNo = stateNo;
	}

	public int getStrNo() {
		return strNo;
	}

	public void setStrNo(int strNo) {
		this.strNo = strNo;
	}

	public int getNtrNo() {
		return ntrNo;
	}

	public void setNtrNo(int ntrNo) {
		this.ntrNo = ntrNo;
	}

	public int getSipNo() {
		return sipNo;
	}

	public void setSipNo(int sipNo) {
		this.sipNo = sipNo;
	}

	public int getWipNo() {
		return wipNo;
	}

	public void setWipNo(int wipNo) {
		this.wipNo = wipNo;
	}

	public int getImmediateOperatorLength() {
		return immediateOperatorLength;
	}

	public void setImmediateOperatorLength(int immediateOperatorLength) {
		this.immediateOperatorLength = immediateOperatorLength;
	}

	public int getSapNo() {
		return sapNo;
	}

	public void setSapNo(int sapNo) {
		this.sapNo = sapNo;
	}

	public int getWapNo() {
		return wapNo;
	}

	public void setWapNo(int wapNo) {
		this.wapNo = wapNo;
	}

	public int getAbsoluteOperatorStart() {
		return absoluteOperatorStart;
	}

	public void setAbsoluteOperatorStart(int absoluteOperatorStart) {
		this.absoluteOperatorStart = absoluteOperatorStart;
	}

	public int getAbsoluteOperatorLength() {
		return absoluteOperatorLength;
	}

	public void setAbsoluteOperatorLength(int absoluteOperatorLength) {
		this.absoluteOperatorLength = absoluteOperatorLength;
	}

	public int getAbsoluteOperatorEnd() {
		return absoluteOperatorEnd;
	}

	public void setAbsoluteOperatorEnd(int absoluteOperatorEnd) {
		this.absoluteOperatorEnd = absoluteOperatorEnd;
	}

	public int getMaxIteration() {
		return maxIteration;
	}

	public void setMaxIteration(int maxIteration) {
		this.maxIteration = maxIteration;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	
	
	
}
