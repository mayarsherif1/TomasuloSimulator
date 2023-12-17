package model;

public class LoadBuffer {
	private String tag;
	private boolean Busy;
	private int address;
	
	//if load is done or not
	private boolean status;
	public LoadBuffer(String tag) {
		this.Busy = false;
		this.tag = tag;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public boolean getBusy() {
		return Busy;
	}
	public void setBusy(boolean busy) {
		this.Busy = busy;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "LoadBuffer [tag=" + tag + ", Busy=" + Busy + ", address=" + address + ", status=" + status + "]";
	}
	

}
