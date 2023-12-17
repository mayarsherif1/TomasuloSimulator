package model;

public class StoreBuffer {
	@Override
	public String toString() {
		return "StoreBuffer [tag=" + tag + ", Busy=" + Busy + ", address=" + address + ", V=" + V + ", Q=" + Q
				+ ", status=" + status + "]";
	}
	private String tag;
	private boolean Busy;
	private int address;
	private Double V;
	private String Q;
	
	private boolean status;
	public StoreBuffer(String tag) {
		this.Busy = false;
		this.tag= tag;
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
	public Double getV() {
		return V;
	}
	public void setV(Double v) {
		this.V = v;
	}
	public String getQ() {
		return Q;
	}
	public void setQ(String q) {
		this.Q = q;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	

}
