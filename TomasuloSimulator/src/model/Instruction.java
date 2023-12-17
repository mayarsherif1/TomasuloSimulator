package model;
//individual instruction
public class Instruction {

	private String operation;
	private String dest;
	private String srcRegister1;
	private String srcRegister2;
	private String immediate;
	//the tag of the RS that the instruction is issued to
	private String tag;
	private int startCycle;

	
	public Instruction(String operation, String dest, String srcRegister1, String srcRegister2, String immediate) {
		this.operation= operation;
		this.dest=dest;
		this.srcRegister1=srcRegister1;
		this.srcRegister2= srcRegister2;
		this.immediate = immediate;
		
	}

	public String getImmediate() {
		return immediate;
	}

	public void setImmediate(String immediate) {
		this.immediate = immediate;
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

	public String getSrcRegister1() {
		return srcRegister1;
	}

	public void setSrcRegister1(String srcRegister1) {
		this.srcRegister1 = srcRegister1;
	}

	public String getSrcRegister2() {
		return srcRegister2;
	}

	public void setSrcRegister2(String srcRegister2) {
		this.srcRegister2 = srcRegister2;
	}
	
	public void issuedTo(ReservationStation res) {
		this.tag = res.getTag();
	}
	public void issuedTo(LoadBuffer load) {
		this.tag = load.getTag();
	}
	public void issuedTo(StoreBuffer store) {
		this.tag = store.getTag();
	}
	
	public String getTag() {
		return tag;
	}
	
	//testing
	@Override
    public String toString() {
        return "Instruction{" +
               "operation='" + operation + '\'' +
               ", dest='" + dest + '\'' +
               ", srcRegister1='" + srcRegister1 + '\'' +
               ", srcRegister2='" + srcRegister2 + '\'' +
               ",iMMEDIATE = " + immediate + 
               '}';
    }

	public int getStartCycle() {
		return startCycle;
	}

	public void setStartCycle(int startCycle) {
		this.startCycle = startCycle;
	}

	public void setTag(String tag) {
		// TODO Auto-generated method stub
		this.tag = tag;
	}
	
	

}
