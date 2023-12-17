//package model;
//
//import data.CommonDataBus;
//import model.RegisterFile;
//import simulator.Simulator;
//
//
//public class ExecutionUnit {
////	public enum OperationType {
////		ADDI, FADD, FSUB, FMUL, FDIV,BNEZ, DADD, SUBI
////	}
//
//	private boolean busy;
//	private boolean ResultReady;
//	private String operation;
//	private Double operand1;
//	private Double operand2;
//	private Double result;
//	private int latency;
//	private String tag;
//	private String Qj;
//	private String Qk;
//
//
//	private Instruction instruction;
//	private Memory memory;
//	private PC pc;
//	private RegisterFile registerFile;
//	private ReservationStation reservationStation;
//	private LoadBuffer loadBuffer;
//	private StoreBuffer storeBuffer;
//
//	public ExecutionUnit() {
////		this.memory = memory;
////		this.pc = pc;
//		this.busy = false;
//		this.ResultReady = false;
//		
//
//	}
//
//	public void setOperation(Instruction instruction, int opLatency, RegisterFile registerFile) {
//		this.memory = Simulator.memory;
//		this.registerFile = registerFile;
//		this.instruction = instruction;
//		this.latency = opLatency;
//		this.busy = true;
//		this.ResultReady = false;
//
//		this.operation = instruction.getOperation();
//		if (instruction.getOperation().equalsIgnoreCase("L.D") || instruction.getOperation().equalsIgnoreCase("S.D")) {
//			loadBuffer = getLoadBuffer(instruction.getTag());
//			storeBuffer = getStoreBuffer(instruction.getTag());
//
//			this.operand1 = loadBuffer != null ? Double.valueOf(loadBuffer.getAddress()) : null;
//			System.out.println("operand 1 in load buffer"+ operand1);
//			this.operand2 = storeBuffer != null ? Double.valueOf(storeBuffer.getAddress()) : null;
//			
//			updateOperandsInLoadOrStore();
//
//		} 
//		
//		else {
//
//			this.reservationStation = getReservationStation(instruction.getTag());
//
//			updateOperandsInRes();
//			
//		}
//		cycle(Simulator.bus , instruction);
//
////			if(Simulator.floatRF.getRegisterFile(instruction.getSrcRegister1())==null) {
////			this.operand1 = resStation != null ? resStation.getVj() : Double.valueOf(instruction.getSrcRegister1());
////
////			}else {
////				this.Qj= resStation != null ? resStation.getQj() : instruction.getSrcRegister1() ;
////
////			}
////			if(Simulator.floatRF.getRegisterFile(instruction.getSrcRegister2())==null) {
////			this.operand2 = resStation != null ? resStation.getVk() : Double.valueOf(instruction.getSrcRegister2());
////
////			}
////			else {
////				this.Qk = resStation != null ? resStation.getQk() : instruction.getSrcRegister2() ;
////
////			}
////			System.out.println("Fetched Operands: operand1 = " + operand1 + ", operand2 = " + operand2);
//			if(tag == null) {
//				Simulator.executionUnit.busy =false;
//				return;
//			}
//		
//		}
//	private void updateOperandsInLoadOrStore() {
//		if(loadBuffer == null) {
//			return;
//		}
//			this.operand1 = Double.valueOf(loadBuffer.getAddress());
//
//	}
//
//	private void updateOperandsInRes() {
//		if (reservationStation == null) {
//	        return;
//	    }
//	
//		//op1
//		if(reservationStation.getQj()== null) {
//			this.operand1 = reservationStation.getVj();
//			//System.out.println("operand1 in Vj : "+ operand1);
//		}
//		else {
//			if(reservationStation.getQj().equals(instruction.getTag())) {
//				System.out.println("I am waiting for myself");
//				this.operand1 = reservationStation.getVj();
//
//			}
//			else
//			this.operand1 = getRegisterValue(instruction.getSrcRegister1());
//			//System.out.println("operand1 in Qj : "+ operand1);
//
//		}
//		//op2
//		if( reservationStation.getQk() == null) {
//			this.operand2 = reservationStation.getVk();
//			//System.out.println("operand2 in Vk : "+ operand2);
//
//		}
//		else {
//			if(reservationStation.getQj().equals(instruction.getTag())) {
//				//System.out.println("I am waiting for myself");
//
//				this.operand1 = reservationStation.getVj();
//
//			}
//			else
//			this.operand2 = getRegisterValue(instruction.getSrcRegister2());
//			//System.out.println("operand1 in Qj : "+ operand2);
//
//		}
//		
//		System.out.println("Fetched Operands: operand1 = " + operand1 + ", operand2 = " + operand2);
//
//	}		
//
//	public void cycle(CommonDataBus bus , Instruction x) {
//		if (busy && !ResultReady) {
//			updateFromCBD(Simulator.bus);
//			//updateOperandsInRes();
////			
//			if(loadBuffer != null && instruction.getOperation().equals("L.D")) {
//				if(loadBuffer.getBusy() == false)
//					return;
//			}
//			if(instruction.getTag().equals("A1")) {
//				System.out.println("2L7A2NY     EL TAG          A11111111111111111111111");
//				System.out.println(bus.toString());
//				//OperandsReady(x);
//			}
//			if (OperandsReady(x)) {
//				
//				
//				for(int i = 0 ; i < Simulator.instructionTable.size() ; i ++) {
//					
//					if(Simulator.instructionTable.get(i).equals(x)) {
//						
//						if(Simulator.instructionTable.get(i).getStartExecution() == 0) {
//							Simulator.instructionTable.get(i).setStartExecution(Simulator.cycle);
//							Simulator.instructionTable.get(i).setEndExecution(Simulator.cycle + latency - 1);
//						}
//						if(Simulator.cycle == Simulator.instructionTable.get(i).getEndExecution()) {
//							//System.out.println("Executing now");
//							execute();
//
//							//System.out.println("busy before operation complete!!!!!!!!!!!!!!"+ busy);
//							operationCompleted();
//							Simulator.executionUnit.busy =false;
//							this.busy = false;
//							//System.out.println("busy after operation complete"+ busy);
//							
//							
//						}
//						}
//					}
//				
//
////				
////				if (latency > 0) {
////					latency--;
////				}
////				if (latency == 0) {
////					execute();
////					operationCompleted();
////
////				}
//			}
////			else {
////				Simulator.stall= true;
////				System.out.println("Stalling: waiting for operands!!");
////				return;
////			
////			}
//		}
//	}
//
//	private boolean OperandsReady(Instruction instruction) {
//		
//		 boolean operandsReady = false;
//	        System.out.println("CHECKING FOR OPERANDS");
//	        if (instruction.getOperation().equalsIgnoreCase("L.D")|| instruction.getOperation().equalsIgnoreCase("S.D")) {
//	        	//System.out.println("operand for load is ready ");
//	        	//System.out.println("operand 1 : " + operand1);
//	            operandsReady = operand1 != null && (Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi() == null || Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi().equals(instruction.getTag())) ;
//	            //System.out.println("Operands rEADY : " + operandsReady);
//	        } else {
//	            operandsReady = (operand1 != null && operand1 != -1) && (operand2 != null && operand2 != -1);
//	           
//	        }
//
////	        if (!operandsReady) {
////	        	//in Q
////	            handleStalling();
////	        }
//	        System.out.println("Operand 1 : " + operand1);
//            System.out.println("Operan 2   : "  + operand2);
//	        return operandsReady;
//	    }
//	
//
////	private void handleStalling() {
////		
////		Simulator.stall = true;
////		Simulator.stallCycle++;
////		
////	}
//
//	private void updateFromCBD(CommonDataBus bus) {
//
//		if (loadBuffer != null && loadBuffer.getTag() != null && loadBuffer.getTag().equals(bus.getTag())) {
//			loadBuffer.setAddress(bus.getValue().intValue());
//			loadBuffer.setTag(null);
//		}
//		if (storeBuffer != null && storeBuffer.getTag() != null && storeBuffer.getTag().equals(bus.getTag())) {
//			storeBuffer.setAddress(bus.getValue().intValue());
//			storeBuffer.setTag(null);
//		}
//		if (reservationStation != null ) {
//			if (reservationStation.getQj() != null && reservationStation.getQj().equals(bus.getTag())) {
//				reservationStation.setVj(bus.getValue());
//				reservationStation.setQj(null);
//	            
//		}
//			if (reservationStation.getQk() != null && reservationStation.getQk().equals(bus.getTag())) {
//				reservationStation.setVk(bus.getValue());
//				reservationStation.setQk(null);
//	            
//			}
//		}
//        if (reservationStation != null && reservationStation.getQj() == null) {
//            operand1 = reservationStation.getVj();
//        }
//        if (reservationStation != null && reservationStation.getQk() == null) {
//            operand2 = reservationStation.getVk();
//        }
//		
//	}
//
//	public void execute() {
//		//System.out.println("Execute method : " + instruction.toString());
//		if (!busy) {
//			return;
//		}
//		switch (operation) {
//		case "ADDI":
//			result = getRegisterValue(instruction.getSrcRegister1()) + Double.parseDouble(instruction.getImmediate());
//			busy = false;
//			Simulator.executionUnit.busy =false;
//
//			ResultReady = true;
//			break;
//		case "FADD":
//			result = getRegisterValue(instruction.getSrcRegister1()) + getRegisterValue(instruction.getSrcRegister2());
//			busy = false;
//			ResultReady = true;
//			break;
//		case "FSUB":
//			result = getRegisterValue(instruction.getSrcRegister1()) - getRegisterValue(instruction.getSrcRegister2());
//			busy = false;
//			Simulator.executionUnit.busy =false;
//
//			ResultReady = true;
//			break;
//
//		case "FMUL":
//			result = getRegisterValue(instruction.getSrcRegister1()) * getRegisterValue(instruction.getSrcRegister2());
//			//System.out.println("---------------------------"+result);
//			busy = false;
//			Simulator.executionUnit.busy =false;
//
//			ResultReady = true;
//			break;
//		case "FDIV":
//			if (operand2 == 0) {
//				throw new ArithmeticException("can not divide by zero");
//			}
//			result = getRegisterValue(instruction.getSrcRegister1()) / getRegisterValue(instruction.getSrcRegister2());
//			busy = false;
//			Simulator.executionUnit.busy =false;
//
//			ResultReady = true;
//			break;
//
//		case "L.D":
//			if (operand1 != null) {
//				
//				
//				LoadBuffer load = getLoadBuffer(instruction.getTag());
////				System.out.println(load != null);
////				System.out.println(load.getBusy());
//				if (load != null && load.getBusy()) {
//					result = (double) memory.getDataValue(load.getAddress());
//				}
//			}
//			busy = false;
//			ResultReady = true;
//			Simulator.executionUnit.busy =false;
//
//			break;
//		case "S.D":
//			if (operand1 != null && operand2 != null) {
//				StoreBuffer store = getStoreBuffer(instruction.getTag());
//				if (store != null && !store.getBusy()) {
//					memory.setDataValue(store.getAddress(), (int) operand2.doubleValue());
//				}
//			}
//			busy = false;
//			ResultReady = true;
//			break;
//
//		case "BNEZ":
//			if (operand1 != null && operand1 != 0) {
//				pc.set(operand2.intValue());
//			}
//			busy = false;
//			ResultReady = true;
//			Simulator.executionUnit.busy =false;
//
//			break;
//		}
//
//		busy = false;
//		Simulator.executionUnit.busy =false;
//
//		ResultReady = true;
//	}
//	
//
//
//	// this will fetch the value of the register considering
//	// if its ready or not
//	private double getRegisterValue(String srcReg) {
//		Register reg = registerFile.getRegisterFile(srcReg);
//
//	    if (reg == null) return -1;
//
//	    //System.out.println(Simulator.bus.getTag() != null && Simulator.bus.getTag().equals(reg.getQi()));
//		if (reg.getQi() == null) {
//			return reg.getValue();
//	    } else if (Simulator.bus.getTag() != null && Simulator.bus.getTag().equals(reg.getQi())) {
//
//	        return Simulator.bus.getValue();
//
//		}
//
//		return -1; //says that the value is not available yet
//	}
//
//	private ReservationStation getReservationStation(String regTag) {
//		// search reservation stations to find the tag
//		for (ReservationStation res : Simulator.FAdder) {
//			if (res.getTag() != null && res.getTag().equals(regTag)) {
//				return res;
//			}
//		}
//		for (ReservationStation res : Simulator.FMultiplier) {
//			if (res.getTag() != null && res.getTag().equals(regTag)) {
//				return res;
//			}
//		}
//		for (ReservationStation res : Simulator.IAdder) {
//			if (res.getTag() != null && res.getTag().equals(regTag)) {
//				return res;
//			}
//		}
//		for (ReservationStation res : Simulator.IMultiplier) {
//			if (res.getTag() != null && res.getTag().equals(regTag)) {
//				return res;
//			}
//		}
//
//		return null;
//	}
//
//	public LoadBuffer getLoadBuffer(String regTag) {
//		for (LoadBuffer load : Simulator.loadBuffer) {
//			if (load.getTag() != null && load.getTag().equals(regTag)) {
//				return load;
//			}
//		}
//		return null;
//
//	}
//
//	public StoreBuffer getStoreBuffer(String regTag) {
//		for (StoreBuffer store : Simulator.storeBuffer) {
//			if (store.getTag() != null && store.getTag().equals(regTag)) {
//				return store;
//			}
//		}
//		return null;
//	}
//
//	public boolean isBusy() {
//		return busy;
//	}
//
//	public boolean isResultReady() {
//		return ResultReady;
//	}
//
//	public Double getResult() {
//		if (!ResultReady) {
//			throw new IllegalStateException("result not ready");
//		}
//		return result;
//	}
//
//	public void operationCompleted() {
//		if (ResultReady) {
//			String tag = instruction.getTag();
//			Double result = getResult();
//	        if (result != null && !Double.isNaN(result)) {
//	        	
//			Simulator.bus.broadcast(tag, result);
//			for(int i=0 ; i< Simulator.instructionTable.size(); i++) {
//				if(Simulator.instructionTable.get(i).equals(instruction)) {
//					Simulator.instructionTable.get(i).setWriteBack(Simulator.cycle);
//				}
//			}
//			
//	        updateRegisterFile();
//			
//			 if (reservationStation != null) {
//	                reservationStation.setBusy(false);
//	                reservationStation.clear();
//	            }
//			 if (reservationStation != null && reservationStation.getTag().equals(tag)) {
//			        this.reservationStation.setBusy(false);
//			        this.reservationStation.clear();
//			 }
//			 //System.out.println("Load Buffer: "+ loadBuffer.toString());
//			 System.out.println("load buffer is busy?"+ busy);
//			 if(loadBuffer !=null ) {
//				 loadBuffer.setBusy(false);
//				 loadBuffer.setAddress(0);
//			 }
//			 updateRegisterFile();
//		
//			
//	        }
//	        this.busy= false;
//			clearOperation();
//		}
//
//	}
//
//	private void updateRegisterFile() {
//		if (instruction.getOperation().equalsIgnoreCase("ADDI")
//				|| instruction.getOperation().equalsIgnoreCase("SUBI")
//				|| instruction.getOperation().equalsIgnoreCase("DADD")
//				|| instruction.getOperation().equalsIgnoreCase("BNEZ")) {
//			
//	        Register destReg = Simulator.intRF.getRegisterFile(instruction.getDest());
//	        if (destReg != null && destReg.getQi() != null && destReg.getQi().equals(instruction.getTag())) {
//	            destReg.setValue(result);
//	            destReg.setQi(null);
//	        }
////			Simulator.intRF.getRegisterFile(instruction.getDest()).setQi(null);
////			Simulator.intRF.getRegisterFile(instruction.getDest()).setValue(result);
//
//
//		} else if(instruction.getOperation().equalsIgnoreCase("FADD")
//				|| instruction.getOperation().equalsIgnoreCase("FSUB") || instruction.getOperation().equalsIgnoreCase("FMUL")
//				|| instruction.getOperation().equalsIgnoreCase("FDIV") ){
//			
//			Simulator.floatRF.getRegisterFile(instruction.getDest()).setQi(null);
//			Simulator.floatRF.getRegisterFile(instruction.getDest()).setValue(result);
//
//
//		}
//		 else if(instruction.getOperation().equalsIgnoreCase("L.D")|| instruction.getOperation().equalsIgnoreCase("S.D")) {
//			Simulator.floatRF.getRegisterFile(instruction.getDest()).setQi(null);
//			Simulator.floatRF.getRegisterFile(instruction.getDest()).setValue(result);
//
//
//		}		
//	}
//
//	public void clearOperation() {
//		this.busy = false;
//		this.ResultReady = false;
//		if (instruction.getOperation().equals("ADDI") || instruction.getOperation().equals("SUBI")
//				|| instruction.getOperation().equals("BNEZ") || instruction.getOperation().equals("DADD") ) {
//			Simulator.floatRF.getRegisterFile(instruction.getDest()).setValue(result);
//			Simulator.floatRF.getRegisterFile(instruction.getDest()).setQi(null);
//			Simulator.intRF.getRegisterFile(instruction.getSrcRegister1()).setQi(null);
//			Simulator.intRF.getRegisterFile(instruction.getSrcRegister2()).setQi(null);
//			Simulator.executionUnit.busy = false;
//
//			
//		}
//		else if (instruction.getOperation().equals("FADD") || instruction.getOperation().equals("FSUB") || instruction.getOperation().equals("FMUL") || instruction.getOperation().equals("FDIV")){
//			Simulator.floatRF.getRegisterFile(instruction.getDest()).setValue(result);
//			Simulator.floatRF.getRegisterFile(instruction.getDest()).setQi(null);
//		Simulator.floatRF.getRegisterFile(instruction.getSrcRegister1()).setQi(null);
//		Simulator.floatRF.getRegisterFile(instruction.getSrcRegister2()).setQi(null);
//		Simulator.executionUnit.busy = false;
//
//		}else {
//			Simulator.floatRF.getRegisterFile(instruction.getDest()).setValue(result);
//			Simulator.floatRF.getRegisterFile(instruction.getDest()).setQi(null);
//			Simulator.executionUnit.busy = false;
//
//		}
//		this.instruction = null;
//		this.operation = null;
//		this.operand1 = null;
//		this.operand2 = null;
//		this.result = null;
//		this.latency = 0;
//		
//		System.out.println("BUSY value after clearing " + Simulator.executionUnit.isBusy());
//		
//
//	}
//
//	public String getTag() {
//		return tag;
//	}
//
//	public void setTag(String tag) {
//		this.tag = tag;
//	}
//
//	@Override
//	public String toString() {
//		return "ExecutionUnit [busy=" + busy + ", ResultReady=" + ResultReady + ", operation=" + operation
//				+ ", operand1=" + operand1 + ", operand2=" + operand2 + ", result=" + result + ", latency=" + latency
//				+ ", tag=" + tag + ", Qj=" + Qj + ", Qk=" + Qk + ", instruction=" + instruction + ", memory=" + memory
//				+ ", pc=" + pc + ", registerFile=" + registerFile + ", reservationStation=" + reservationStation
//				+ ", loadBuffer=" + loadBuffer + ", storeBuffer=" + storeBuffer + "]";
//	}
//
//	
//
//}