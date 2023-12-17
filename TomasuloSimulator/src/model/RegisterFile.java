package model;

import java.util.Arrays;

public class RegisterFile {
    private Register[] registerFile ;

    public RegisterFile() {
        this.registerFile = new Register[32];
        for(int i=0 ; i<32 ; i++){
            this.registerFile[i] = new Register("F" + i, (double)i);

        }
    }

    public Register getRegisterFile(String name) {
    	//System.out.println("NAME in register file " + name);
    	if (name.charAt(name.length()-1)==',') {
    		name = name.substring(0, name.length()-1);
    	}

        int index = Integer.parseInt(name.substring(1));
        return registerFile[index];
    }
    

    public void setRegisterFile(double value , String name) {
        int index = Character.getNumericValue(name.charAt(1));
        this.registerFile[index].setValue(value);
    }

	@Override
	public String toString() {
		return "RegisterFile [registerFile=" + Arrays.toString(registerFile) + "]";
	}

	
}