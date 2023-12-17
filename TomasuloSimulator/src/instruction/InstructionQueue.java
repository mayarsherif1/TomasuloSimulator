package instruction;
//instructions are fetched and issued from this queue
//first in first out (fifo)


//issue instruction in the queue conditions
//having a free or available reservation station
//if one of them is free the instruction will be issued and will be added to the reservation station
//sometimes called dispatch stage
//keep track of who will produce them!
//execute happens in the reservation station and adders/mult


import model.Instruction;

import java.util.LinkedList;
import java.util.Queue;

public class InstructionQueue {
	private Queue<Instruction> queue;
	
	public InstructionQueue() {
		this.queue = new LinkedList<>();
	}
	
	public void enqueue(Instruction instruction) {
		queue.add(instruction);
	}
	
	public Instruction dequeue(){
		return queue.remove();		
		
	}
	
	public Instruction peek() {
		return queue.peek();
	}
	
	
	public boolean isEmpty() {
		return queue.isEmpty();
		
	}
	
	public int size() {
		return queue.size();
	}
	

}
