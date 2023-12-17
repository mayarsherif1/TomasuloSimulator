package model;


//the cache memory

public class Cache {
	private final Integer[] cacheMemory;
	private final int size;
	
	public Cache(int size) {
		this.size = size;
		this.cacheMemory =new Integer[size];
		
		
	}
	
	public void store(int address, int value) {
		if(address<0 || address >= size) {
			throw new IllegalArgumentException("invalid cache size");
		}
		cacheMemory[address] = value;
		
	}
	
	public Integer load(int address) {
		if(address<0 || address >= size) {
			throw new IllegalArgumentException("invalid cache address");
			
		}
		return cacheMemory[address];
	}
}
