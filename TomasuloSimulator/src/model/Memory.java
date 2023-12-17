package model;

import java.util.Arrays;

public class Memory {
    private double[] dataValue;

    public Memory() {
        this.dataValue = new double [200];
        for(int i =0; i< this.dataValue.length; i++) {
        	this.dataValue[i]= 0.0;
        }
    }

    public double getDataValue(int index) {
        if(index < dataValue.length)
        return dataValue[index];
        else{
            System.out.println("index out of range");
            return -404;
        }
    }

    @Override
	public String toString() {
		return "Memory [dataValue=" + Arrays.toString(dataValue) + "]";
	}

	public void setDataValue(double data , int index) {
        if(index < dataValue.length)
        this.dataValue[index] = data;
        else
            System.out.println("index out of range");
    }
}
