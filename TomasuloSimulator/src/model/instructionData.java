package model;

import java.util.ArrayList;

public class instructionData {
	
	private String operation;
	private String dest;
	private String operand1;
	private String operand2;
	private int issue;
	private int startExecution;
	private int endExecution;
	private int writeBack;
	private String tag;
	
	



	public instructionData(String op, String dest, String op1, String op2 , int issue) {
		this.operation = op;
		this.dest = dest;
		this.operand1 = op1;
		this.operand2= op2;
		this.issue= issue;
		this.startExecution=0;
		this.endExecution =0;
		this.writeBack = 0;
		
		
	}
	
	
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	public String getOperand1() {
		return operand1;
	}
	public void setOperand1(String operand1) {
		this.operand1 = operand1;
	}
	public String getOperand2() {
		return operand2;
	}
	public void setOperand2(String operand2) {
		this.operand2 = operand2;
	}
	public int getIssue() {
		return issue;
	}
	public void setIssue(int issue) {
		this.issue = issue;
	}
	public int getStartExecution() {
		return startExecution;
	}
	public void setStartExecution(int startExecution) {
		this.startExecution = startExecution;
	}
	public int getEndExecution() {
		return endExecution;
	}
	public void setEndExecution(int endExecution) {
		this.endExecution = endExecution;
	}
	public int getWriteBack() {
		return writeBack;
	}
	public void setWriteBack(int writeBack) {
		this.writeBack = writeBack;
	}
	public String getTag() {
		return tag;
	}



	public void setTag(String tag) {
		this.tag = tag;
	}



	public String toString() {
		String x ="";
		
		x += "{" + this.getTag() + " , " + this.getOperation() + " , " + this.getDest() + " , " + this.getOperand1() + " , " + this.getOperand2() + " , " + this.getIssue() + " , " 
				+ this.getStartExecution() + " , " + this.getEndExecution() + " , " + this.getWriteBack() + " }";	
		
		return x;
		
	}
	
	public boolean equals(Instruction instruction) {
		
		if(instruction.getOperation().equals("L.D" ) || instruction.getOperation().equals("S.D" ) ) {
			
			if(this.getOperation().equals(instruction.getOperation()) && this.getDest().equals(instruction.getDest()) && this.getOperand1().equals(instruction.getImmediate().toString())) {
				return true;
			}
		}
		if(instruction.getOperation().equals("ADDI" ) || instruction.getOperation().equals("SUBI" )) {
			if(this.getOperation().equals(instruction.getOperation()) && this.getDest().equals(instruction.getDest()) && this.getOperand1().equals(instruction.getSrcRegister1()) && this.getOperand2().equals(instruction.getImmediate().toString())) {
				return true;
			}	
		}
		if(instruction.getOperation().equals("BNEZ" )) {

			if(this.getOperation().equals(instruction.getOperation()) && this.getOperand1().equals(instruction.getSrcRegister1()) && this.getOperand2().equals(instruction.getImmediate().toString())) {
				
				return true;
			}
		}
		if(this.getOperation().equals(instruction.getOperation()) && this.getDest().equals(instruction.getDest()) && this.getOperand1().equals(instruction.getSrcRegister1()) && this.getOperand2().equals(instruction.getSrcRegister2()) ) {
			return true;
		}
		return false;
	}


}
