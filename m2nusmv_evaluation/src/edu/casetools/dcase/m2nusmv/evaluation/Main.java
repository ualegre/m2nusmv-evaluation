package edu.casetools.dcase.m2nusmv.evaluation;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import edu.casetools.dcase.m2nusmv.evaluation.generators.ProportionalGenerator;


public class Main {
	
	public static void main(String[] args) {
		try{
			new ProportionalGenerator().generate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}








