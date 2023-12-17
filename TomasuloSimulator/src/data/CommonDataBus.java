package data;

import simulator.Simulator;

public class CommonDataBus {
	private String tag;
	private Double value;
	private int busCycle;
	public CommonDataBus() {
		this.tag = null;
		this.value = null;
		this.busCycle = 0;
		
	}

	public int getBusCycle() {
		return busCycle;
	}

	public void setBusCycle(int busCycle) {
		this.busCycle = busCycle;
	}

	public void broadcast(String tag, Double value) {
		if (this.tag == null && this.value == null) {
			this.tag = tag;
			this.value = value;
		}
	}

	public void clear() {
		this.tag = null;
		this.value = null;
	}

	@Override
	public String toString() {
		return "CommonDataBus [tag=" + tag + ", value=" + value + "]";
	}

	public boolean isEmpty() {
		return tag == null && value == null;

	}

	public String getTag() {
		return tag;
	}

	public Double getValue() {
		return value;
	}
	
	public boolean canWrite() {
		return Simulator.cycle > this.busCycle;
	}


}
