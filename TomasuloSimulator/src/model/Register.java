package model;
//individual register in register file
public class Register {
	private String name;
	private Double value;
	private String Qi;
	
	public Register(String name, Double value) {
		this.name =name;
		this.value =value;
		this.Qi = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getQi() {
		return Qi;
	}

	public void setQi(String Qi) {
		this.Qi = Qi;
	}
	//testing
    @Override
    public String toString() {
        String valueString = (value == null) ? "null" : String.format("%f", value);
        return String.format("Register{name='%s', value=%s, Qi='%s'}", name, valueString, Qi);
    }

}
