package utils;
//manging the buffer size and latency
public class Config {
	
	private int addLatency;
	private int mulLatency;
	
	private int resStationBufferSize;
	private int instructionQueueSize;
	private int registerFileSize;
	
	
	
	public Config() {
		addLatency =2;
		mulLatency = 10;
		
		resStationBufferSize = 10;
		instructionQueueSize = 10;
		registerFileSize = 32;
	}
	
	public int getAddLatency() {
		return addLatency;
		
	}
	
	 public void setAddLatency(int addLatency) {
	        this.addLatency = addLatency;
	    }
	 
	 public int getMulLatency() {
	        return mulLatency;
	    }
	 
	 public void setMulLatency(int mulLatency) {
	        this.mulLatency = mulLatency;
	    }

	public int getResStationBufferSize() {
		return resStationBufferSize;
	}

	public void setResStationBufferSize(int resStationBufferSize) {
		this.resStationBufferSize = resStationBufferSize;
	}

	public int getInstructionQueueSize() {
		return instructionQueueSize;
	}

	public void setInstructionQueueSize(int instructionQueueSize) {
		this.instructionQueueSize = instructionQueueSize;
	}

	public int getRegisterFileSize() {
		return registerFileSize;
	}

	public void setRegisterFileSize(int registerFileSize) {
		this.registerFileSize = registerFileSize;
	}
	 
	 

}
