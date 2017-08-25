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
package edu.casetools.dcase.m2nusmv.evaluation;

import edu.casetools.dcase.m2nusmv.evaluation.generators.Configurations;
import edu.casetools.dcase.m2nusmv.evaluation.generators.ProportionalModelGenerator;

public class Main {
	
	private Main(){
		
	}
	
	public static void main(String[] args) {
		try{
			Configurations configs = new Configurations();
							configs.setRepeat(6);
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
							configs.setMaxIteration(10000000);
							configs.printSetup();

							new ProportionalModelGenerator(configs).generate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}








