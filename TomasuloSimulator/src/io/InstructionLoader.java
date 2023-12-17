package io;

import model.Instruction;
import model.instructionData;
import simulator.Simulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class InstructionLoader {

	public List<Instruction> loadFromFile(String filePath) throws IOException {
		List<Instruction> instructions = new LinkedList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				Instruction instruction = parseInstruction(line);
				if (instruction != null) {
					instructions.add(instruction);
					if(instruction.getOperation().equalsIgnoreCase("L.D")|| instruction.getOperation().equalsIgnoreCase("S.D") ) {
						Simulator.instructionTable.add(new instructionData(instruction.getOperation(), instruction.getDest(), instruction.getImmediate(), null , 0));

					}else if(instruction.getOperation().equalsIgnoreCase("ADDI")|| instruction.getOperation().equalsIgnoreCase("SUBI")) {
						Simulator.instructionTable.add(new instructionData(instruction.getOperation(), instruction.getDest(), instruction.getSrcRegister1(), instruction.getImmediate() , 0));

					}else if(instruction.getOperation().equalsIgnoreCase("BNEZ")) {
						Simulator.instructionTable.add(new instructionData(instruction.getOperation(), null , instruction.getSrcRegister1(), instruction.getImmediate() , 0));

					}
					else {
					Simulator.instructionTable.add(new instructionData(instruction.getOperation(), instruction.getDest(), instruction.getSrcRegister1(), instruction.getSrcRegister2() , 0));
				}
				}
			}
		}
		return instructions;

	}
	
	private Instruction parseInstruction(String line) {
		String[] split = line.trim().split("\\s+"); //splits by spaces
		String op = split[0];
		String dest = "";
		String srcReg1 = "";
		String srcReg2 = "";
		String immediate ="";

		switch (op) {
		case "FADD":
		case "FSUB":
		case "FMUL":
		case "FDIV":
		case "DADD":
			if(split.length != 4) {
				System.out.println("invalid operation format"+ op + ":"+ line);
				return null;
			}
		dest = split[1].trim();
		srcReg1 = split[2].trim();
		srcReg2 = split[3].trim();
		break;
		
		
		case "ADDI":
		case "SUBI":
			if(split.length != 4) {
				System.out.println("invalid operation format"+ op + ":"+ line);
				return null;
			}
			dest = split[1].trim();
			srcReg1 = split[2].trim();
			immediate = split[3].trim();
			break;
			
		case "L.D":
		case "S.D":
			if(split.length != 3) {
				System.out.println("invalid operation format"+ op + ":"+ line);
				
				return null;
			}
			dest = split[1].trim();
			immediate = split[2].trim();
			break;
			
			
		case "BNEZ":
			if(split.length != 3) {
				System.out.println("invalid operation format"+ op + ":"+ line);
				
				return null;
			}
			srcReg1 = split[1].trim();
			immediate = split[2].trim();
			break;
			
		default:
            System.out.println("Unknown operation: " + op);
            return null;



		}
	
		Instruction inst = new Instruction(op, dest, srcReg1, srcReg2, immediate);
		return inst;
	}
}