package instruction;

import Exceptions.UndefinedInstructionException;
import model.Instruction;
import model.LoadBuffer;
import model.ReservationStation;
import model.StoreBuffer;
import model.instructionData;
import simulator.Simulator;
import simulator.Simulator;

public class Issuing {

	public static boolean issue(Instruction instruction) throws UndefinedInstructionException {
		boolean issued = false;
		if (instruction.getOperation().equalsIgnoreCase("FADD")
				|| instruction.getOperation().equalsIgnoreCase("FSUB")) {
			//System.out.println("FADD , FSUB");
			for (int i = 0; i < Simulator.FAdder.length; i++) {
				//System.out.println("Busy : " + Simulator.FAdder[i].isBusy());
				//System.out.println("Checking for structure hazard");
				
				
				if (Simulator.FAdder[i].isBusy() == false) {
//					System.out.println("TRUE");
//					System.out.println("i HAVE A PLACE FOR THIS INSTRUCTION " + instruction.toString() );
					addToReservationStation(instruction, Simulator.FAdder[i]);
		            issued = issueToResStation(Simulator.FAdder, instruction);
//		            System.out.println("this is a print after ISSUING");
					
					return true;
				}
			}
//			System.out.println("FALSE");

			return false;
		} else if (instruction.getOperation().equalsIgnoreCase("FMUL")
				|| instruction.getOperation().equalsIgnoreCase("FDIV")) {
//			System.out.println("FMUL , FDIV");
			
			for (int i = 0; i < Simulator.FMultiplier.length; i++) {
				
				if (Simulator.FMultiplier[i].isBusy() == false) {
//					System.out.println("TRUE");
					
					addToReservationStation(instruction, Simulator.FMultiplier[i]);
		            issued = issueToResStation(Simulator.FMultiplier, instruction);
					
					return true;
				}
			}
			
//			System.out.println("FALSE");
			
			return false;
		} else if (instruction.getOperation().equalsIgnoreCase("L.D")) {
			for (int i = 0; i < Simulator.loadBuffer.length; i++) {
				if (Simulator.loadBuffer[i].getBusy() == false) {
					System.out.println("TRUE");
					addToLoadBuffer(instruction, Simulator.loadBuffer[i]);
		            issued = true;
					return true;
				}
			}
//			System.out.println("FALSE");


			return false;
		} else if (instruction.getOperation().equalsIgnoreCase("S.D")) {
//			System.out.println("S.D");
			for (int i = 0; i < Simulator.storeBuffer.length; i++) {
				if (Simulator.storeBuffer[i].getBusy() == false) {
//					System.out.println("TRUE");
					addToStoreBuffer(instruction, Simulator.storeBuffer[i]);
		            issued = true;
					return true;
				}
			}
//			System.out.println("FALSE");

			return false;

		} else if (instruction.getOperation().equalsIgnoreCase("ADDI")
				|| instruction.getOperation().equalsIgnoreCase("SUBI")
				|| instruction.getOperation().equalsIgnoreCase("DADD")
				|| instruction.getOperation().equalsIgnoreCase("BNEZ")) {
//			System.out.println("INTEGER INSTRUCTION");
			for (int i = 0; i < Simulator.IAdder.length; i++) {
				if (Simulator.IAdder[i].isBusy() == false) {
//					System.out.println("TRUE");
					addToReservationStation(instruction, Simulator.IAdder[i]);
		            issued = issueToResStation(Simulator.IAdder, instruction);
					return true;
				}
			}
//			System.out.println("FALSE");

			return false;
		}

		  if (!issued) {
	            Simulator.stall = true;
	            Simulator.stallCycle = Simulator.cycle;
	        } else {
	            Simulator.stall = false;
	        }
		  return issued;

	}



	public static void addToReservationStation(Instruction instruction, ReservationStation resStation) {
//		resStation.setBusy(true);
//		resStation.setOperation(instruction.getOperation());
		if (instruction.getOperation().equalsIgnoreCase("FADD")
				|| instruction.getOperation().equalsIgnoreCase("FSUB")) {
			for (int i = 0; i < Simulator.FAdder.length; i++) {
				if (Simulator.FAdder[i].isBusy() == false) {
					
					
					Simulator.FAdder[i].setBusy(true);
					Simulator.FAdder[i].setOperation(instruction.getOperation());
					// for first register
					if (Simulator.floatRF.getRegisterFile(instruction.getSrcRegister1()).getQi() == null) {

						Simulator.FAdder[i]
								.setVj(Simulator.floatRF.getRegisterFile(instruction.getSrcRegister1()).getValue());
//						Simulator.floatRF.getRegisterFile(instruction.getSrcRegister1())
//								.setQi(Simulator.FAdder[i].getTag());
					} else {
						Simulator.FAdder[i]
								.setQj(Simulator.floatRF.getRegisterFile(instruction.getSrcRegister1()).getQi());
					}
					// for second register
					if (Simulator.floatRF.getRegisterFile(instruction.getSrcRegister2()).getQi() == null) {

						Simulator.FAdder[i]
								.setVk(Simulator.floatRF.getRegisterFile(instruction.getSrcRegister2()).getValue());
//						Simulator.floatRF.getRegisterFile(instruction.getSrcRegister2())
//								.setQi(Simulator.FAdder[i].getTag());

					} else {
						Simulator.FAdder[i]
								.setQk(Simulator.floatRF.getRegisterFile(instruction.getSrcRegister2()).getQi());
					}
					instruction.setTag(Simulator.FAdder[i].getTag());
//					System.out.println("this is a print after adding to the reservation station");
//					for(int i1 = 0 ; i1 < Simulator.FAdder.length ; i1 ++) {
//						System.out.println(Simulator.FAdder[i1].toString());
//					}
					
					if(Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi() == null) {
						Simulator.floatRF.getRegisterFile(instruction.getDest()).setQi(Simulator.FAdder[i].getTag());
					}
//					
					break;
				}
			}
		} else if (instruction.getOperation().equalsIgnoreCase("FMUL")
				|| instruction.getOperation().equalsIgnoreCase("FDiv")) {
			for (int i = 0; i < Simulator.FMultiplier.length; i++) {
				if (Simulator.FMultiplier[i].isBusy() == false) {
					Simulator.FMultiplier[i].setBusy(true);
					Simulator.FMultiplier[i].setOperation(instruction.getOperation());
					// for first register
					if (Simulator.floatRF.getRegisterFile(instruction.getSrcRegister1()).getQi() == null) {

						Simulator.FMultiplier[i]
								.setVj(Simulator.floatRF.getRegisterFile(instruction.getSrcRegister1()).getValue());
//						Simulator.floatRF.getRegisterFile(instruction.getSrcRegister1())
//								.setQi(Simulator.FMultiplier[i].getTag());

					} else {
						Simulator.FMultiplier[i]
								.setQj(Simulator.floatRF.getRegisterFile(instruction.getSrcRegister1()).getQi());
					}
					// for second register
					if (Simulator.floatRF.getRegisterFile(instruction.getSrcRegister2()).getQi() == null) {

						Simulator.FMultiplier[i]
								.setVk(Simulator.floatRF.getRegisterFile(instruction.getSrcRegister2()).getValue());
//						Simulator.floatRF.getRegisterFile(instruction.getSrcRegister2())
//								.setQi(Simulator.FMultiplier[i].getTag());
					} else {
						Simulator.FMultiplier[i]
								.setQk(Simulator.floatRF.getRegisterFile(instruction.getSrcRegister2()).getQi());
					}
					if(Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi() == null) {
						Simulator.floatRF.getRegisterFile(instruction.getDest()).setQi(Simulator.FMultiplier[i].getTag());
					}
					instruction.setTag(Simulator.FMultiplier[i].getTag());
					
					break;

				}

			}
		} else if (instruction.getOperation().equalsIgnoreCase("ADDI")
				|| instruction.getOperation().equalsIgnoreCase("SUBI")
				|| instruction.getOperation().equalsIgnoreCase("DADD")
				) {
			for (int i = 0; i < Simulator.IAdder.length; i++) {
				if (Simulator.IAdder[i].isBusy() == false) {
					Simulator.IAdder[i].setBusy(true);
					Simulator.IAdder[i].setOperation(instruction.getOperation());
					// for first register
					if (Simulator.intRF.getRegisterFile(instruction.getSrcRegister1()).getQi() == null) {

						Simulator.IAdder[i]
								.setVj(Simulator.intRF.getRegisterFile(instruction.getSrcRegister1()).getValue());
//						Simulator.floatRF.getRegisterFile(instruction.getSrcRegister1())
//								.setQi(Simulator.IAdder[i].getTag());

					} else {
						Simulator.IAdder[i]
								.setQj(Simulator.intRF.getRegisterFile(instruction.getSrcRegister1()).getQi());
					}
					if(instruction.getOperation().equalsIgnoreCase("DADD")) {
					// for second register
					if (Simulator.intRF.getRegisterFile(instruction.getSrcRegister2()).getQi() == null) {

						Simulator.IAdder[i]
								.setVk(Simulator.intRF.getRegisterFile(instruction.getSrcRegister2()).getValue());
//						Simulator.floatRF.getRegisterFile(instruction.getSrcRegister2())
//								.setQi(Simulator.IAdder[i].getTag());
					} else {
						Simulator.IAdder[i]
								.setQk(Simulator.intRF.getRegisterFile(instruction.getSrcRegister2()).getQi());
					}
					}else {
						Simulator.IAdder[i].setVk(Double.valueOf(instruction.getImmediate()));
					}
					
					instruction.setTag(Simulator.IAdder[i].getTag());
					
						if(Simulator.intRF.getRegisterFile(instruction.getDest()).getQi() == null) {
							Simulator.intRF.getRegisterFile(instruction.getDest()).setQi(Simulator.IAdder[i].getTag());
						}
					

					break;

				}
			}
		}
		if(instruction.getOperation().equalsIgnoreCase("BNEZ")) {
			for (int i = 0; i < Simulator.IAdder.length; i++) {
				if (Simulator.IAdder[i].isBusy() == false) {
					Simulator.IAdder[i].setOperation(instruction.getOperation());
					Simulator.IAdder[i].setBusy(true);
					Simulator.IAdder[i].setVj(Simulator.intRF.getRegisterFile(instruction.getSrcRegister1()).getValue());
					instruction.setTag(Simulator.IAdder[i].getTag());
					break;
				}
			}
		}
		addIssueTime(instruction);
		addTag(instruction);

	}
	public static void addToLoadBuffer(Instruction instruction, LoadBuffer load) {
	 if (instruction.getOperation().equalsIgnoreCase("L.D")) {
			for (int i = 0; i < Simulator.loadBuffer.length; i++) {
				if (Simulator.loadBuffer[i].getBusy() == false) {
					System.out.println("there is a place in load buffer");
					Simulator.loadBuffer[i].setBusy(true);
					Simulator.loadBuffer[i].setAddress(Integer.parseInt(instruction.getImmediate()));
					instruction.setTag(Simulator.loadBuffer[i].getTag());
					if(Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi()==null) {
						Simulator.floatRF.getRegisterFile(instruction.getDest()).setQi(instruction.getTag());
					}
					addIssueTime(instruction);
					addTag(instruction);
					break;
				}
			}

		}

//		System.out.println("=-=-==-=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-==-==-=-=-=-=-");
//		for(int i = 0 ; i < Simulator.instructionTable.size(); i++) {
//			if(Simulator.instructionTable.get(i).equals(instruction)) {
//				System.out.println(Simulator.instructionTable.get(i).toString());
//			}
//		}
//		System.out.println("=-=-==-=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-==-==-=-=-=-=-");
		
	}
	
	public static void addToStoreBuffer(Instruction instruction, StoreBuffer store) {
		if (instruction.getOperation().equalsIgnoreCase("S.D")) {
			for (int i = 0; i < Simulator.storeBuffer.length; i++) {
				if (Simulator.storeBuffer[i].getBusy() == false) {
					Simulator.storeBuffer[i].setBusy(true);
					Simulator.storeBuffer[i].setAddress(Integer.parseInt(instruction.getImmediate()));
					if (Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi() == null) {
						Simulator.storeBuffer[i]
								.setV(Simulator.floatRF.getRegisterFile(instruction.getDest()).getValue());
						Simulator.floatRF.getRegisterFile(instruction.getDest())
								.setQi(Simulator.storeBuffer[i].getTag());
					} else {
						Simulator.storeBuffer[i].setQ(Simulator.floatRF.getRegisterFile(instruction.getDest()).getQi());

					}
					instruction.setTag(Simulator.storeBuffer[i].getTag());
					addIssueTime(instruction);
					addTag(instruction);
					break;

				}
			}
		}

//		System.out.println("=-=-==-=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-==-==-=-=-=-=-");
//		for(int i = 0 ; i < Simulator.instructionTable.size(); i++) {
//			if(Simulator.instructionTable.get(i).equals(instruction)) {
//				System.out.println(Simulator.instructionTable.get(i).toString());
//			}
//		}
//		System.out.println("=-=-==-=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-==-==-=-=-=-=-");


	}
	
	 private static boolean issueToResStation(ReservationStation[] stations, Instruction instruction) {
	        for (ReservationStation station : stations) {
	            if (!station.isBusy()) {
	                //addToReservationStation(instruction, station);
	                return true;
	            }
	        }
	        return false;
	    }
	
	
	private static void addIssueTime(Instruction instruction) {
		for(int i = 0 ; i < Simulator.instructionTable.size(); i++) {
//			System.out.println(Simulator.instructionTable);
//			System.out.println("I'm in i : " + i);
//			System.out.println("Found it : " + (Simulator.instructionTable.get(i).equals(instruction) && Simulator.instructionTable.get(i).getIssue() == 0 && Simulator.instructionTable.get(i).getTag() == null));
			if(Simulator.instructionTable.get(i).equals(instruction) && Simulator.instructionTable.get(i).getIssue() == 0 && Simulator.instructionTable.get(i).getTag() == null) {
				Simulator.instructionTable.get(i).setIssue(Simulator.cycle);
			}
		}
	}
	
	private static void addTag(Instruction instruction) {
		for(int i = 0 ; i < Simulator.instructionTable.size(); i++) {
			if(Simulator.instructionTable.get(i).equals(instruction) && Simulator.instructionTable.get(i).getIssue() ==Simulator.cycle  && Simulator.instructionTable.get(i).getTag() == null) {
				Simulator.instructionTable.get(i).setTag(instruction.getTag());
			}
		}
	}

}
