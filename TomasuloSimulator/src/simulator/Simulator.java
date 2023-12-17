package simulator;

import model.*;

import java.util.Queue;
import java.util.Scanner;

import Exceptions.UndefinedInstructionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import data.CommonDataBus;
import io.InstructionLoader;

import instruction.*;
//if an instruction is stalled all other instructions will be stalled except the ones that were executing already

public class Simulator {
	public static Queue<Instruction> instructionQueue = new LinkedList<>();
	private RegisterFile registerFile;
	public static Execute executionUnit;
	public static CommonDataBus bus = new CommonDataBus();
	private boolean status = false;

	// public Issuing issue = new Issuing();

	public static ReservationStation[] FAdder;
	public static ReservationStation[] FMultiplier;
	public static ReservationStation[] IAdder;
	public static ReservationStation[] IMultiplier;
	public static LoadBuffer[] loadBuffer = new LoadBuffer[3];
	public static StoreBuffer[] storeBuffer = new StoreBuffer[3];
	public static RegisterFile floatRF = new RegisterFile();
	public static RegisterFile intRF = new RegisterFile();
	public static Queue<CommonDataBus> busQueue = new LinkedList<>();
	public static ArrayList<instructionData> instructionTable = new ArrayList<>();
	public static ArrayList<instructionData> doneTable = new ArrayList<>();

	public static boolean stall = false;
	public static int stallCycle;
	public static int cycle = 1;
	public static int addLatency;
	public static int mulLatency;
	public static int loadLatency;
	public static int storeLatency;
	public static Memory memory = new Memory();
	public static boolean branch = false;
	public static int branchCycle = 0;
	public static int branchIndex = -1;
	public static int floatAddResStation;
	public static int intAddResStation;
	public static int floatMulResStations;
	public static int intMulResStations;
	public static int loadBuffers;
	public static int storeBuffers;

	



	public Simulator() {
		this.registerFile = new RegisterFile();
		this.executionUnit = new Execute();
			

		memory.setDataValue(50, 100);


	}

	public void loadInstructions(Queue<Instruction> instructions) {
		this.instructionQueue.addAll(instructions);
	}

	public static void configure(int addLatency, int multiplyLatency,int loadLatency, int storeLatency,
			int floatAddResStation,int intAddResStation,
			int floatMulResStations,int intMulResStations, 
			int loadBuffers, int storeBuffers) {
		Simulator.addLatency = addLatency;
		Simulator.mulLatency = multiplyLatency;
		Simulator.loadLatency = loadLatency;
		Simulator.storeLatency = storeLatency;
		
	    FAdder = new ReservationStation[floatAddResStation];
	    IAdder = new ReservationStation[intAddResStation];

	    FMultiplier = new ReservationStation[floatMulResStations];
	    IMultiplier = new ReservationStation[intMulResStations];

	    loadBuffer = new LoadBuffer[loadBuffers];
	    storeBuffer = new StoreBuffer[storeBuffers];
	    
		for (int i = 0; i < floatAddResStation; i++) {
			FAdder[i] = new ReservationStation("A" + i);

		}
		for (int i = 0; i < intAddResStation; i++) {
			IAdder[i] = new ReservationStation("Ai" + i);
		}
		for (int i = 0; i < loadBuffers; i++) {
			loadBuffer[i] = new LoadBuffer("L" + i);
			}
		for (int i = 0; i < storeBuffers; i++) {
			storeBuffer[i] = new StoreBuffer("S" + i);
			}
		for (int i = 0; i < intMulResStations; i++) {
			IMultiplier[i] = new ReservationStation("Mi" + i);
			}
		for (int i = 0; i < floatMulResStations; i++) {
			FMultiplier[i] = new ReservationStation("M" + i);

		}
	}

//	public static boolean done() {
//		boolean output = false ;
//		boolean doneWB = false ;
//		for(int i = 0 ; i < instructionTable.size() ;i++) {
//			if(instructionTable.get(i).getEndExecution()==0 && instructionTable.get(i).getEndExecution() > cycle  ) {
//				System.out.println("IN DONE : " + instructionTable.get(i));
//				doneWB =  false;
//			}else {
//				doneWB = true;
//			}
//		}
//		for(int i = 0 ; i < FAdder.length ; i ++) {
//			//System.out.println(FAdder[i].toString());
//			if(FAdder[i].isBusy()) {
//				System.out.println("IN DONE : " + FAdder[i].toString());
//
//				output = false;
//			}else {
//				output = true;
//			}
//		}
//		for(int i = 0 ; i < FMultiplier.length ; i ++) {
//			if(FMultiplier[i].isBusy()) {
//				System.out.println("IN DONE : " + FMultiplier[i].toString());
//				output = false;
//			}else {
//				output = true;
//			}
//		}
//		
//		for(int i = 0 ; i < loadBuffer.length ; i++) {
//			if(loadBuffer[i].getBusy()) {
//				System.out.println("IN DONE : " + loadBuffer[i].toString());
//				output = false;
//			}else {
//				output = true;
//			}
//		}
//		
//		for(int i = 0 ; i < storeBuffer.length ; i++) {
//			if(storeBuffer[i].getBusy()) {
//				System.out.println("IN DONE : " + storeBuffer[i].toString());
//				output = false;
//			}else {
//				output = true;
//			}
//		}
//		
//		
//		for(int i = 0 ; i < IAdder.length ; i++) {
//			if(IAdder[i].isBusy()) {
//				System.out.println("IN DONE : " + IAdder[i].toString());
//				output = false;
//			}else {
//				output = true;
//			}
//		}
//		 
//		return output || doneWB;
//	}

	public static boolean done() {
		for (int i = 0; i < instructionTable.size(); i++) {
			// TODO if branch then check if executed
			if (instructionTable.get(i).getStartExecution() == 0 || (instructionTable.get(i).getEndExecution() != 0
					&& instructionTable.get(i).getEndExecution() >= cycle  )) {
				return false;
			}
		}
		return true;
	}

	public void runSimulation() throws UndefinedInstructionException {

		while ((!done() || !busQueue.isEmpty() || (bus.getTag() != null))) {
			System.out.println("DONE TABLE : \n" + doneTable.toString());
			branch = false;
			if (Simulator.bus.canWrite()) {
				Simulator.bus.clear();
				if (!Simulator.busQueue.isEmpty()) {
					CommonDataBus cdb = new CommonDataBus();
					cdb = Simulator.busQueue.poll();
					Simulator.bus.broadcast(cdb.getTag(), cdb.getValue());
					Execute.fillRegisters();
					Simulator.bus.setBusCycle(Simulator.cycle);
				}
			}

			for (int i = 0; i < instructionTable.size(); i++) {
				// System.out.println((instructionTable.get(i).getIssue() > 0 ) + " FOR
				// INSTRUCTION " + instructionTable.get(i));
				if ((instructionTable.get(i).getIssue() > 0 && instructionTable.get(i).getWriteBack() == 0)) {
					Instruction instruction = null;
					if (instructionTable.get(i).getOperation().equals("L.D")
							|| instructionTable.get(i).getOperation().equals("S.D")) {
						instruction = new Instruction(instructionTable.get(i).getOperation(),
								instructionTable.get(i).getDest(), null, null, instructionTable.get(i).getOperand1());
					} else if (instructionTable.get(i).getOperation().equals("ADDI")
							|| instructionTable.get(i).getOperation().equals("SUBI")) {
						instruction = new Instruction(instructionTable.get(i).getOperation(),
								instructionTable.get(i).getDest(), instructionTable.get(i).getOperand1(), null,
								instructionTable.get(i).getOperand2());

					} else if (instructionTable.get(i).getOperation().equals("BNEZ")) {
						if (branchIndex == -1) {
							branchIndex = i;
						}
						instruction = new Instruction(instructionTable.get(i).getOperation(), null,
								instructionTable.get(i).getOperand1(), null, instructionTable.get(i).getOperand2());

					} else {
						instruction = new Instruction(instructionTable.get(i).getOperation(),
								instructionTable.get(i).getDest(), instructionTable.get(i).getOperand1(),
								instructionTable.get(i).getOperand2(), null);
					}
					// Instruction
					instruction.setTag(instructionTable.get(i).getTag());
					// System.out.println("Instruction that will start execution : " +
					// instruction.toString());
					if (!(instructionTable.get(i).getOperation().equals("BNEZ")
							&& instructionTable.get(i).getEndExecution() != 0)) {

						executionUnit.startExecution(instruction);
					}
				}

			}

			if (!instructionQueue.isEmpty() && !branch) {
				boolean issued = Issuing.issue(instructionQueue.peek());
				if (issued) {
					instructionQueue.remove();
				}
			}

//			if(branchCycle == cycle+1 || branchCycle == 0) {
//				branch = false;
//				branchCycle = 0;
//			}
//			System.out.println("Branching ? " + branch);
//			System.out.println("BRANCH CYCLE : " + branchCycle);

			System.out.println("FADDER : ");

			for (int i = 0; i < FAdder.length; i++) {
				System.out.println(FAdder[i].toString());
			}
			System.out.println("FMULTIPLIER");
			for (int i = 0; i < FMultiplier.length; i++) {
				System.out.println(FMultiplier[i].toString());
			}

			System.out.println("Load Buffer");
			for (int i = 0; i < loadBuffer.length; i++) {
				System.out.println(loadBuffer[i].toString());
			}

			System.out.println("store Buffer");
			for (int i = 0; i < storeBuffer.length; i++) {
				System.out.println(storeBuffer[i].toString());
			}

			System.out.println("INSTRUCTION TABLE");
			for (int i = 0; i < instructionTable.size(); i++) {
				System.out.println(instructionTable.get(i).toString());
			}

			System.out.println("IADDER : ");
			for (int i = 0; i < IAdder.length; i++) {
				System.out.println(IAdder[i].toString());
			}

			System.out.println("FLOAT Register File : \n " + floatRF.toString());
			System.out.println("INT Register File : \n " + intRF.toString());

			System.out.println("Memory : " + memory.toString());
			System.out.println("CYCLE : " + cycle);
			System.out.println("BUS QUEUE : " + busQueue.toString());
			System.out.println("BUS in simulator : " + bus.toString());

			System.out.println("----------------------------------------------------------");
			cycle++;
		}

		System.out.println("BUS QUEUE : " + busQueue.toString());
		System.out.println("BUS in simulator : " + bus.toString());

		Execute.fillRegisters();
		Simulator.bus.setBusCycle(Simulator.cycle);
		System.out.println("Register File : \n " + floatRF.toString());

//		while ((!instructionQueue.isEmpty() || busy() ) && cycle < 30) {
//			
//			System.out.println("Begining of cycle " + cycle);
//			System.out.println("FADDER : ");
//			
//			for(int i = 0 ; i < FAdder.length ; i ++) {
//				System.out.println(FAdder[i].toString());
//			}
//			System.out.println("FMULTIPLIER");
//			for(int i = 0 ; i < FMultiplier.length ; i ++) {
//				System.out.println(FMultiplier[i].toString());
//			}
//			
//			System.out.println("Load Buffer");
//			for(int i = 0 ; i < loadBuffer.length ; i++) {
//				System.out.println(loadBuffer[i].toString());
//			}
//
//			System.out.println("********");
//			 
//			System.out.println("INSTRUCTION TABLE");
//			for(int i =0 ; i< instructionTable.size() ; i++ ) {
//				System.out.println (instructionTable.get(i).toString());
//			}
//			
//			
//			
//			for(int i=0 ; i< instructionTable.size() ; i ++) {
//				if(instructionTable.get(i).getIssue() > 0 && instructionTable.get(i).getWriteBack() == 0 ) {
//					Instruction instruction = null;
//					if(instructionTable.get(i).getOperation().equals("L.D") || instructionTable.get(i).getOperation().equals("S.D")) {
//						instruction = new Instruction (instructionTable.get(i).getOperation() , instructionTable.get(i).getDest() , null , null , instructionTable.get(i).getOperand1());
//					}else {
//						instruction = new Instruction (instructionTable.get(i).getOperation() , instructionTable.get(i).getDest() , instructionTable.get(i).getOperand1() , instructionTable.get(i).getOperand2() , null );
//					}
//					//Instruction 
//					instruction.setTag(instructionTable.get(i).getTag());;
//					//executionUnit.setOperation(instruction, findLatency(instruction) , floatRF);
//					
//					
//				}
//			}
//			
//			
//			
//			// issuing
//			if (!stall) {
//				
//				 
//				if (!instructionQueue.isEmpty()) {
//					
//
//					Instruction nextInst = instructionQueue.peek();
//					
//					if (Issuing.issue(nextInst)) {
//						
//						System.out.println("Current issue : " + nextInst.toString());
//						instructionQueue.remove();
//						//executionUnit.setOperation(nextInst, findLatency(nextInst));
//						
//					} else {
//						
//						// instruction stalled
//						System.out.println("Cycle :" + cycle);
//						System.out.println("StallCycle :" + stallCycle);
//						System.out.println("Can't issue");
//						stall = true;
//						stallCycle = cycle;
//
//					}
//				}
//			}
//			if (stall && !instructionQueue.isEmpty()) {
//				
//
//				Instruction stalledInst = instructionQueue.peek();
//				if (Issuing.issue(stalledInst)) {
//					
//					instructionQueue.remove();
//					stall = false;
//					//executionUnit.setOperation(stalledInst, findLatency(stalledInst));
//				}
//			}
////			// executing
////			if (executionUnit.isBusy()) {
////				
////				executionUnit.cycle(bus);
////				System.out.println("BUS: " + bus.toString());
////				//System.out.println("Currently Executing:" + executionUnit.toString());
////			}
//
//			cycle++;
//			System.out.println("Cycle :" + cycle);
//			System.out.println("StallCycle :" + stallCycle);
//			
//			System.out.println("Register File : \n " + floatRF.toString());
//			System.out.println("Memory : "+ memory.toString());
//
//			System.out.println("------------------------------------------------------------------------------------------");
//
//
//		}
//
//		display();

	}

	public static int findLatency(Instruction instruction) {
		if (instruction.getOperation().equalsIgnoreCase("FMUL")
				|| instruction.getOperation().equalsIgnoreCase("FDIV")) {
			return mulLatency;
		} else if (instruction.getOperation().equalsIgnoreCase("FADD")
				|| instruction.getOperation().equalsIgnoreCase("FSUB")) {
			return addLatency;
		} else if (instruction.getOperation().equalsIgnoreCase("L.D")
				) {
			return loadLatency;
			
		}
		else if(instruction.getOperation().equalsIgnoreCase("S.D")) {
			return storeLatency;
		}
		else{
			return 1;
		}
	}

	// checks if execution or reservation stations are busy to determain if the
	// simulation should continue or not
	public boolean busy() {
		for (ReservationStation res : FAdder) {
			if (res.isBusy()) {
				return true;
			}
		}
		for (ReservationStation res : FMultiplier) {
			if (res.isBusy()) {
				return true;
			}
		}
		for (ReservationStation res : IAdder) {
			if (res.isBusy()) {
				return true;
			}
		}
		for (ReservationStation res : IMultiplier) {
			if (res.isBusy()) {
				return true;
			}
		}

		for (LoadBuffer load : loadBuffer) {
			if (load.getBusy()) {
				return true;
			}
		}

		for (StoreBuffer store : storeBuffer) {
			if (store.getBusy()) {
				return true;
			}
		}

//		if (executionUnit.isBusy()) {
//			return true;
//		}

		return false;

	}

	private void display() {
		// TODO Auto-generated method stub

		System.out.println("Final state");

	}

	public RegisterFile getRegisterFile() {
		return registerFile;
	}

	public static void main(String[] args) throws UndefinedInstructionException {
		Simulator simulator = new Simulator();
		Scanner scanner = new Scanner(System.in);
		// main main = new main();

		System.out.println("add the file containing the instructions:");
		// String filePath = scanner.nextLine().trim();

//	    // Other simulation parameters
//	    System.out.println("Enter Add Latency:");
//	     addLatency = scanner.nextInt();
//
//	    System.out.println("Enter Multiply Latency:");
//	    mulLatency = scanner.nextInt();
//	    
//	    System.out.println("Enter Load Latency:");
//	     loadLatency = scanner.nextInt();
//
//	    System.out.println("Enter Store Latency:");
//	    storeLatency = scanner.nextInt();
//	    System.out.println("Enter float add reservation station: ");
//	    floatAddResStation = scanner.nextInt();
//	    
//	    System.out.println("Enter int add reservation station: ");
//	    intAddResStation = scanner.nextInt();
//	    
//	    System.out.println("Enter float multiply reservation station:");
//	    floatMulResStations = scanner.nextInt();
//	    
//	    System.out.println("Enter int multiply reservation station:");
//	    intMulResStations = scanner.nextInt();
//	    
//	    System.out.println("Enter load buffer:");
//	    loadBuffers = scanner.nextInt();
//	    
//	    System.out.println("Enter store buffer:");
//	    storeBuffers = scanner.nextInt();
//	    
//	    
//	    
//	    Simulator.configure( addLatency, mulLatency,loadLatency, storeLatency, floatAddResStation, intAddResStation,
//				 floatMulResStations, intMulResStations,  loadBuffers,  storeBuffers);


		try {
			InstructionLoader loader = new InstructionLoader();
			List<Instruction> instructions = loader.loadFromFile(
					"C:\\Users\\mayar\\OneDrive\\Desktop\\yara\\TomasuloSimulator v2\\TomasuloSimulator\\TomasuloSimulator\\src\\file.txt");

			Queue<Instruction> instructionQueue = new LinkedList<>(instructions);
			System.out.println("Loaded instructions: " + instructionQueue);
			System.out.println("stalled instruction: " + stall);
			simulator.loadInstructions(instructionQueue);

			try {
				simulator.runSimulation();
			} catch (UndefinedInstructionException e) {
				// TODO Auto-generated catch block
				System.out.println("ERROR" + e.getMessage());

			}

		} catch (IOException e) {
			System.out.println("Error loading instructions from the file: " + e.getMessage());
		}
	}

}
