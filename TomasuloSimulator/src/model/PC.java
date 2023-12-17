package model;

public class PC {

	private int counter;
	
	public PC(int address) {
		this.counter = address;
	}
	
	public int get() {
		return counter;
	}

	public void set(int newAddress) {
		// TODO Auto-generated method stub
		this.counter = newAddress;
		
	}
	public void increment() {
		counter++;
	}

}
