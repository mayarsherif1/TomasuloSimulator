package instruction;

import java.util.LinkedList;
import java.util.Queue;

import data.CommonDataBus;
import model.Instruction;
import model.LoadBuffer;
import model.ReservationStation;
import model.StoreBuffer;
import model.instructionData;
import simulator.Simulator;

public class Execute {
	ReservationStation res;
	LoadBuffer lb;
	StoreBuffer sb;
	double result;
	int count;
	int Execcycle;
//take instruction 
//check all operands are ready 
//if yes set start and end execution if not set
//if set check if it should end then do the operation and write back 
	
//NOT READY 
//check bus if has data if not terminate
	public boolean checkReady(Instruction instruction) {
		if(instruction.getOperation().equals("DADD")|| instruction.getOperation().equals("ADDI") || instruction.getOperation().equals("SUBI")) {
			for(int i=0 ; i< Simulator.IAdder.length; i++) {
				System.out.println(Simulator.IAdder[i].getTag().equals(instruction.getTag()));
				if(Simulator.IAdder[i].getTag().equals(instruction.getTag())){
					if(Simulator.IAdder[i].getQj() == null && Simulator.IAdder[i].getQk()==null && (Simulator.intRF.getRegisterFile(instruction.getDest()).getQi()==null || Simulator.intRF.getRegisterFile(instruction.getDest()).getQi().equals(instruction.getTag()))) {
						res = Simulator.IAdder[i];
						return true;
					}else {
						return false;
					}
					
					
				}
			}
		}if(instruction.getOperation().equals("FADD")|| instruction.getOperation().equals("FSUB") ) {
			for(int i=0 ; i< Simulator.FAdder.length; i++) {
				System.out.println(Simulator.FAdder[i].getTag().equals(instruction.getTag()));
				if(Simulator.FAdder[i].getTag().equals(instruction.getTag())){
					if(Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi()==null ) {
						Simulator.floatRF.getRegisterFile(instruction.getDest()).setQi(instruction.getTag());
					}
					if(Simulator.FAdder[i].getQj() == null && Simulator.FAdder[i].getQk()==null && (Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi()==null || Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi().equals(instruction.getTag()))) {
						Simulator.floatRF.getRegisterFile(instruction.getDest()).setQi(instruction.getTag());
						res = Simulator.FAdder[i];
						return true;
					}else {
						return false;
					}
				}
			}
		}
		if(instruction.getOperation().equals("FMUL")|| instruction.getOperation().equals("FDIV")&& (Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi()==null || Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi().equals(instruction.getTag()))) {
			for(int i=0 ; i< Simulator.FMultiplier.length; i++) {
				System.out.println(Simulator.FMultiplier[i].getTag().equals(instruction.getTag()));
				if(Simulator.FMultiplier[i].getTag().equals(instruction.getTag())){
					if(Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi()==null ) {
						Simulator.floatRF.getRegisterFile(instruction.getDest()).setQi(instruction.getTag());
					}
					if(Simulator.FMultiplier[i].getQj() == null && Simulator.FMultiplier[i].getQk()==null&& (Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi()==null || Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi().equals(instruction.getTag()))) {
						Simulator.floatRF.getRegisterFile(instruction.getDest()).setQi(instruction.getTag());

						res = Simulator.FMultiplier[i];
						return true;
					}else {
					
						return false;
					}
				}
			}
		}
		if(instruction.getOperation().equals("L.D")) {
			for(int i=0 ; i< Simulator.loadBuffer.length; i++) {
				if(Simulator.loadBuffer[i].getTag().equals(instruction.getTag())){
					if(Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi()==null || Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi().equals(instruction.getTag())) {
						lb = Simulator.loadBuffer[i];
						return true;
					}else {
						return false;
					}
				}
			}
		}if(instruction.getOperation().equals("S.D")) {
			for(int i=0 ; i< Simulator.storeBuffer.length; i++) {
				if(Simulator.storeBuffer[i].getTag().equals(instruction.getTag())){
					if((Simulator.storeBuffer[i].getQ() == null)&&(Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi()==null || Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi().equals(instruction.getTag()))) {
						sb = Simulator.storeBuffer[i];
						return true;
					}else {
						return false;
					}
				}
			}
		}
		if(instruction.getOperation().equals("BNEZ")) {
			for(int i=0 ; i< Simulator.IAdder.length; i++) {
				if(Simulator.IAdder[i].getTag().equals(instruction.getTag())){
					res = Simulator.IAdder[i];
					return true;
				}
			}
			
			
		}
		return false;
	}
	public static void checkBus(Instruction instruction) {
		if(instruction.getOperation().equals("DADD")|| instruction.getOperation().equals("ADDI") || instruction.getOperation().equals("SUBI")) {
			for(int i=0 ; i< Simulator.IAdder.length; i++) {
				if(Simulator.IAdder[i].getTag().equals(instruction.getTag())){
					if(Simulator.IAdder[i].getQj() != null && Simulator.bus.getTag()!=null&& Simulator.bus.getTag().equals(Simulator.IAdder[i].getQj())) {
						Simulator.IAdder[i].setVj(Simulator.bus.getValue());
						Simulator.IAdder[i].setQj(null);
					}
					if(Simulator.IAdder[i].getQk() != null&& Simulator.bus.getTag()!=null && Simulator.bus.getTag().equals(Simulator.IAdder[i].getQk())) {
						Simulator.IAdder[i].setVk(Simulator.bus.getValue());
						Simulator.IAdder[i].setQk(null);
					}
				}
			}
		}if(instruction.getOperation().equals("FADD")|| instruction.getOperation().equals("FSUB") ) {
			for(int i=0 ; i< Simulator.FAdder.length; i++) {
				if(Simulator.FAdder[i].getQj() != null && Simulator.bus.getTag()!=null && Simulator.bus.getTag().equals(Simulator.FAdder[i].getQj())) {
					Simulator.FAdder[i].setVj(Simulator.bus.getValue());
					Simulator.FAdder[i].setQj(null);
				}
				if(Simulator.FAdder[i].getQk() != null && Simulator.bus.getTag()!=null && Simulator.bus.getTag().equals(Simulator.FAdder[i].getQk())) {
					Simulator.FAdder[i].setVk(Simulator.bus.getValue());
					Simulator.FAdder[i].setQk(null);
				}
			}
		}
		if(instruction.getOperation().equals("FMUL")|| instruction.getOperation().equals("FDIV") ) {
			for(int i=0 ; i< Simulator.FMultiplier.length; i++) {
				if(Simulator.FMultiplier[i].getTag().equals(instruction.getTag())){
					if(Simulator.FMultiplier[i].getQj() != null && Simulator.bus.getTag()!=null&& Simulator.bus.getTag().equals(Simulator.FMultiplier[i].getQj())) {
						Simulator.FMultiplier[i].setVj(Simulator.bus.getValue());
						Simulator.FMultiplier[i].setQj(null);
					}
					if(Simulator.FMultiplier[i].getQk() != null && Simulator.bus.getTag()!=null&& Simulator.bus.getTag().equals(Simulator.FMultiplier[i].getQk())) {
						Simulator.FMultiplier[i].setVk(Simulator.bus.getValue());
						Simulator.FMultiplier[i].setQk(null);
					}
				}
			}
		}
		if(instruction.getOperation().equals("L.D")) {
			for(int i=0 ; i< Simulator.loadBuffer.length; i++) {
				if( Simulator.bus.getTag()!=null && Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi().equals(Simulator.bus.getTag())) {
					Simulator.floatRF.getRegisterFile(instruction.getDest()).setValue(Simulator.bus.getValue());
					Simulator.floatRF.getRegisterFile(instruction.getDest()).setQi(null);
				}

				
			}
		}if(instruction.getOperation().equals("S.D")) {
			for(int i=0 ; i< Simulator.loadBuffer.length; i++) {
				if(Simulator.bus.getTag()!=null && Simulator.storeBuffer[i].getTag().equals(instruction.getTag())){

					if((Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi()==null)||(Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi()!=null && Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi().equals(Simulator.bus.getTag()))) {
						Simulator.floatRF.getRegisterFile(instruction.getDest()).setValue(Simulator.bus.getValue());
						Simulator.storeBuffer[i].setV(Simulator.bus.getValue());
						Simulator.storeBuffer[i].setQ(null);
						Simulator.floatRF.getRegisterFile(instruction.getDest()).setQi(null);
						
					}
				}
			}
		}
		

	}
	public  void doOperation(Instruction instruction) {
		CommonDataBus newBus = new CommonDataBus();
		if(instruction.getOperation().equals("DADD")||instruction.getOperation().equals("ADDI") ) {
			result = res.getVj() + res.getVk();
			newBus.broadcast(instruction.getTag(), result);
			Simulator.busQueue.add(newBus);
			Simulator.intRF.getRegisterFile(instruction.getDest()).setValue(result);
			Simulator.intRF.getRegisterFile(instruction.getDest()).setQi(null);
		}
		if(instruction.getOperation().equals("SUBI")) {
			result = res.getVj() - res.getVk();
			newBus.broadcast(instruction.getTag(), result);
			Simulator.busQueue.add(newBus);
			Simulator.intRF.getRegisterFile(instruction.getDest()).setValue(result);
			Simulator.intRF.getRegisterFile(instruction.getDest()).setQi(null);
		}
		if(instruction.getOperation().equals("FADD")) {
			result = res.getVj() + res.getVk();
			newBus.broadcast(instruction.getTag(), result);
			Simulator.busQueue.add(newBus);
			
			//Simulator.bus.broadcast(instruction.getTag(), res.getVj() + res.getVk());
		}
		if( instruction.getOperation().equals("FSUB")) {
			result = res.getVj() - res.getVk();
			newBus.broadcast(instruction.getTag(), result);
			Simulator.busQueue.add(newBus);
			//Simulator.bus.broadcast(instruction.getTag(), res.getVj() - res.getVk());
		}
		if(instruction.getOperation().equals("FMUL")) {
			result = res.getVj() * res.getVk();
			newBus.broadcast(instruction.getTag(), result);
			Simulator.busQueue.add(newBus);
			//Simulator.bus.broadcast(instruction.getTag(), res.getVj() * res.getVk());
		}
		if(instruction.getOperation().equals("FDIV")) {
			result = res.getVj() / res.getVk();
			newBus.broadcast(instruction.getTag(), result);
			Simulator.busQueue.add(newBus);
			//Simulator.bus.broadcast(instruction.getTag(), res.getVj() / res.getVk());
		}
		if(instruction.getOperation().equals("L.D")) {
			result = Simulator.memory.getDataValue(lb.getAddress());
			newBus.broadcast(instruction.getTag(), result);
			Simulator.busQueue.add(newBus);

			//Simulator.bus.broadcast(instruction.getTag(), Simulator.memory.getDataValue(lb.getAddress()));
		}
		if(instruction.getOperation().equals("S.D")) {
			Simulator.memory.setDataValue(sb.getV(), sb.getAddress());
			
		}
		if(instruction.getOperation().equals("BNEZ")) {

			if(res.getVj() != 0.0) {
				Simulator.branch = true;
				Simulator.branchCycle = Simulator.cycle;
				loadLOOP(Integer.parseInt(instruction.getImmediate()), instruction);
				
			}else {
				Simulator.branch = false;
				Simulator.branchCycle = 0;
			}
		}
		
		
	}
	
	private void loadLOOP(int immediate, Instruction instruction) {
		// TODO Auto-generated method stub
		Queue<Instruction> newInstructionQueue = new LinkedList<>(); 
		Queue<Instruction> temp = new LinkedList<>(); 
		for(int i =0; i<Simulator.instructionTable.size(); i++) {
			if(i >= immediate && i<= Simulator.branchIndex ) {
				Instruction newinstruction = null;
				if(Simulator.instructionTable.get(i).getOperation().equals("L.D") || Simulator.instructionTable.get(i).getOperation().equals("S.D")) {
					newinstruction = new Instruction (Simulator.instructionTable.get(i).getOperation() , Simulator.instructionTable.get(i).getDest() , null , null , Simulator.instructionTable.get(i).getOperand1());
				}else if(Simulator.instructionTable.get(i).getOperation().equals("ADDI") || Simulator.instructionTable.get(i).getOperation().equals("SUBI")){
					newinstruction = new Instruction (Simulator.instructionTable.get(i).getOperation() , Simulator.instructionTable.get(i).getDest() , Simulator.instructionTable.get(i).getOperand1() , null , Simulator.instructionTable.get(i).getOperand2());

				}else if(Simulator.instructionTable.get(i).getOperation().equals("BNEZ")) {
					newinstruction = new Instruction (Simulator.instructionTable.get(i).getOperation() , null , Simulator.instructionTable.get(i).getOperand1() , null , Simulator.instructionTable.get(i).getOperand2());

				}
				else {
					newinstruction = new Instruction (Simulator.instructionTable.get(i).getOperation() , Simulator.instructionTable.get(i).getDest() , Simulator.instructionTable.get(i).getOperand1() , Simulator.instructionTable.get(i).getOperand2() , null );
				}
				newInstructionQueue.add(newinstruction);
				temp.add(newinstruction);
			}
		}
		
		
		while(!temp.isEmpty()) {
			Instruction tempinstr = temp.remove();
			if(tempinstr.getOperation().equalsIgnoreCase("L.D")|| tempinstr.getOperation().equalsIgnoreCase("S.D") ) {
				Simulator.instructionTable.add(new instructionData(tempinstr.getOperation(), tempinstr.getDest(), tempinstr.getImmediate(), null , 0));

			}else if(tempinstr.getOperation().equalsIgnoreCase("ADDI")|| tempinstr.getOperation().equalsIgnoreCase("SUBI")) {
				Simulator.instructionTable.add(new instructionData(tempinstr.getOperation(), tempinstr.getDest(), tempinstr.getSrcRegister1(), tempinstr.getImmediate() , 0));

			}else if(tempinstr.getOperation().equalsIgnoreCase("BNEZ")) {
				Simulator.instructionTable.add(new instructionData(tempinstr.getOperation(), null , tempinstr.getSrcRegister1(), tempinstr.getImmediate() , 0));

			}
			else {
			Simulator.instructionTable.add(new instructionData(tempinstr.getOperation(), tempinstr.getDest(), tempinstr.getSrcRegister1(), tempinstr.getSrcRegister2() , 0));
		}
		}
		while(!Simulator.instructionQueue.isEmpty()) {
		newInstructionQueue.add(Simulator.instructionQueue.remove());
		}
		Simulator.instructionQueue = newInstructionQueue;

		count++;
		
	}
	public static void fillRegisters() {
		for(int i=0 ; i<32 ; i++) {
			if(Simulator.floatRF.getRegisterFile("F" + i).getQi() != null && Simulator.floatRF.getRegisterFile("F" + i).getQi().equals(Simulator.bus.getTag())){
				Simulator.floatRF.getRegisterFile("F" + i).setValue(Simulator.bus.getValue());
				Simulator.floatRF.getRegisterFile("F" + i).setQi(null);
			}
		}
		for(int i=0 ; i<32 ; i++) {
			if(Simulator.intRF.getRegisterFile("R" + i).getQi() != null && Simulator.intRF.getRegisterFile("R" + i).getQi().equals(Simulator.bus.getTag())){
				Simulator.intRF.getRegisterFile("R" + i).setValue(Simulator.bus.getValue());
				Simulator.intRF.getRegisterFile("R" + i).setQi(null);

			}
		}
		
	}
	public void removeFromStations(Instruction instruction) {
		res = null;
		lb = null;
		sb = null;
		
		
		if(instruction.getOperation().equals("DADD")|| instruction.getOperation().equals("ADDI") || instruction.getOperation().equals("SUBI") || instruction.getOperation().equals("BNEZ")) {
			for(int i=0 ; i< Simulator.IAdder.length; i++) {
				if(Simulator.IAdder[i].getTag().equals(instruction.getTag())){
					Simulator.IAdder[i].setBusy(false);
					Simulator.IAdder[i].setQj(null);
					Simulator.IAdder[i].setQk(null);
					Simulator.IAdder[i].setVj(0.0);
					Simulator.IAdder[i].setVk(0.0);


				}
			}
		}if(instruction.getOperation().equals("FADD")|| instruction.getOperation().equals("FSUB") ) {
			for(int i=0 ; i< Simulator.FAdder.length; i++) {
				if(Simulator.FAdder[i].getTag().equals(instruction.getTag())){
					Simulator.FAdder[i].setBusy(false);
					Simulator.FAdder[i].setQj(null);
					Simulator.FAdder[i].setQk(null);
					Simulator.FAdder[i].setVj(0.0);
					Simulator.FAdder[i].setVk(0.0);
				}
			}
		}
		if(instruction.getOperation().equals("FMUL")|| instruction.getOperation().equals("FDIV") ) {
			for(int i=0 ; i< Simulator.FMultiplier.length; i++) {
				if(Simulator.FMultiplier[i].getTag().equals(instruction.getTag())){
					Simulator.FMultiplier[i].setBusy(false);
					Simulator.FMultiplier[i].setQj(null);
					Simulator.FMultiplier[i].setQk(null);
					Simulator.FMultiplier[i].setVj(0.0);
					Simulator.FMultiplier[i].setVk(0.0);
				}
			}
		}
		if(instruction.getOperation().equals("L.D")) {
			for(int i=0 ; i< Simulator.loadBuffer.length; i++) {
				if(Simulator.loadBuffer[i].getTag().equals(instruction.getTag())){
					Simulator.loadBuffer[i].setBusy(false);
					Simulator.loadBuffer[i].setAddress(0);
				}
			}
		}if(instruction.getOperation().equals("S.D")) {
			for(int i=0 ; i< Simulator.storeBuffer.length; i++) {
				if(Simulator.storeBuffer[i].getTag().equals(instruction.getTag())){
					Simulator.storeBuffer[i].setAddress(0);
					Simulator.storeBuffer[i].setBusy(false);
					Simulator.storeBuffer[i].setQ(null);
					Simulator.storeBuffer[i].setV(0.0);
				}
			}
		}
		
	}
	public  void findRES(Instruction instruction) {
		if(instruction.getOperation().equals("DADD")|| instruction.getOperation().equals("ADDI") || instruction.getOperation().equals("SUBI") || instruction.getOperation().equals("BNEZ")) {
			for(int i=0 ; i< Simulator.IAdder.length; i++) {
				if(Simulator.IAdder[i].getTag().equals(instruction.getTag())){
					if(Simulator.IAdder[i].getQj() == null && Simulator.IAdder[i].getQk()==null) {
						res = Simulator.IAdder[i];
					}else {
						return ;
					}
				}
			}
		}if(instruction.getOperation().equals("FADD")|| instruction.getOperation().equals("FSUB") ) {
			for(int i=0 ; i< Simulator.FAdder.length; i++) {
				if(Simulator.FAdder[i].getTag().equals(instruction.getTag())){
					if(Simulator.FAdder[i].getQj() == null && Simulator.FAdder[i].getQk()==null) {
						res = Simulator.FAdder[i];
					}else {
						return ;
					}
				}
			}
		}
		if(instruction.getOperation().equals("FMUL")|| instruction.getOperation().equals("FDIV") ) {
			for(int i=0 ; i< Simulator.FMultiplier.length; i++) {

				if(Simulator.FMultiplier[i].getTag().equals(instruction.getTag())){
					if(Simulator.FMultiplier[i].getQj() == null && Simulator.FMultiplier[i].getQk()==null  ) {
						res = Simulator.FMultiplier[i];
					}else {
						return ;
					}
				}
			}
		}
		if(instruction.getOperation().equals("L.D")) {
			for(int i=0 ; i< Simulator.loadBuffer.length; i++) {
				if(Simulator.loadBuffer[i].getTag().equals(instruction.getTag())){
					if(Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi()==null) {
						lb = Simulator.loadBuffer[i];
					}else {
						return ;
					}
				}
			}
		}if(instruction.getOperation().equals("S.D")) {
			for(int i=0 ; i< Simulator.storeBuffer.length; i++) {
				if(Simulator.storeBuffer[i].getTag().equals(instruction.getTag())){
					if(Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi()==null) {
						sb = Simulator.storeBuffer[i];
					}else {
						return ;
					}
				}
			}
		}
	}
	

	public void startExecution(Instruction instruction) {
		Execcycle = 0;
		for(int i=0 ; i < Simulator.instructionTable.size()  ; i++) {

			if(Simulator.instructionTable.get(i).equals(instruction)) {
				Execcycle++;
				
				if(Simulator.instructionTable.get(i).getStartExecution() == 0) {
					
					if(checkReady(instruction)) {
						Simulator.instructionTable.get(i).setStartExecution(Simulator.cycle);
						Simulator.instructionTable.get(i).setEndExecution(Simulator.cycle + Simulator.findLatency(instruction)-1);
					}else {
						checkBus(instruction);
						if(checkReady(instruction)) {
							Simulator.instructionTable.get(i).setStartExecution(Simulator.cycle);
							Simulator.instructionTable.get(i).setEndExecution(Simulator.cycle + Simulator.findLatency(instruction)-1);
						}
					}
				}
				if(Simulator.instructionTable.get(i).getEndExecution() == Simulator.cycle ||(Simulator.instructionTable.get(i).getEndExecution() <= Simulator.cycle &&	Simulator.instructionTable.get(i).getWriteBack() == 0 ) ){
					System.out.println("Executing : " + instruction.toString());
					System.out.println(checkReady(instruction));
					if(checkReady(instruction)) {
					Simulator.doneTable.add(Simulator.instructionTable.get(i));
					
					findRES(instruction);
					doOperation(instruction);

					Simulator.instructionTable.get(i).setTag(null);
//					if(!instruction.getOperation().equals("BNEZ")) {
						fillRegisters();
						//Simulator.bus.setBusCycle(Simulator.cycle);
//					}
					Simulator.instructionTable.get(i).setWriteBack(Simulator.cycle);
					removeFromStations(instruction);
					//Simulator.instructionTable.remove(i);
					}else {
						checkBus(instruction);
						
					}
					res = null;
					lb = null;
					sb= null;
					break;
				}
				
			}

		} 
		
	}

}
