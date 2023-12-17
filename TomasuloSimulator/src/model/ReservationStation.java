package model;

import data.CommonDataBus;

//we have 2 types
//in example in the lecture we have 3 resStation
//so we can have 3 add/sub instructions running at the same time
//instructions stay here as long as their operands are not available yet!
//for example an ADD.D instruction that is waiting for registers
//F0, F2 if f2 is available but f0 is being computed by a previous instructions
//so the add waits here until f0 is available
//once both of them are available so they are fetched to one of the adders
//if we have 3 resStation for adds/subs that means we have 3 adders which can combute add and sub

//if we have 2 resStation for mult/div instructions 
//it means that we have 2 FP multipliers that can multiply or divide 
//like the add/sub they come here wait for the operand



//load and store buffers 
//the store will come and wait here until the operand is ready
//and then will wait again until the data cache replies to them 
//whenever the store starts executing which mean the store do a write to the cache


//once they finish it wil publish the result to the bus
//whoever is waiting for this value will grab it from the bus
//we forward to the alu and to the memory and to the register file
//the one waiting will grab it or if they all depend on it they will all grab it


public class ReservationStation {
	private int time;
	//private String name;
	private String tag;
	private boolean busy; 
	private String operation;
	private Double Vj, Vk; 
	private String Qj,Qk;
	//private Double address;
	private double result;
	private boolean status;
	
	
	public ReservationStation(String tag) {
		this.busy=false;
		this.Vj = null;
		this.Vk = null;
		this.Qj = null;
		this.Qk = null;
		//this.address = null;
		this.time =0;
		this.status = false;	
		this.tag = tag;
		this.setResult(0);
		

	}
	
	public String getTag() {
		return tag;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Double getVj() {
		return Vj;
	}

	public void setVj(Double vj) {
		this.Vj = vj;
	}

	public Double getVk() {
		return Vk;
	}

	public void setVk(Double vk) {
		this.Vk = vk;
	}

	public String getQj() {
		return Qj;
	}

	public void setQj(String qj) {
		this.Qj = qj;
	}

	public String getQk() {
		return Qk;
	}

	public void setQk(String qk) {
		this.Qk = qk;
	}

	
	//testing
    @Override
    public String toString() {
    	    	
        return "ReservationStation{" +
        	   "tag=" + tag +
               ", busy=" + busy +
               ", operation='" + operation + '\'' +
               ", Vj=" + Vj +
               ", Vk=" + Vk +
               ", Qj='" + Qj + '\'' +
               ", Qk='" + Qk + '\'' +
               '}';
    }

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public void updateBus(CommonDataBus bus) {
		if(this.tag != null && this.tag.equals(bus.getTag()) && !bus.isEmpty()) {
			if(this.Qj != null && this.Qj.equals(bus.getTag())) {
				this.Vj = bus.getValue();
				this.Qj = null;
				}
			if(this.Qk != null && this.Qk.equals(bus.getTag())) {
				this.Vk = bus.getValue();
				this.Qk = null;
			}
			if(this.Qj == null && this.Qk ==null) {
				this.status = true; //operation is ready to execute
			}
		}
	}

	public double getResult() {
		// TODO Auto-generated method stub
		if(!status) {
			throw new IllegalStateException("Result not ready");
		}
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	public void clear() {
		this.busy=false;
		this.Vj = null;
		this.Vk = null;
		this.Qj = null;
		this.Qk = null;
		this.time =0;
		this.status = false;	
		this.setResult(0);		
	}
	

}
