package se.lexicon.em.model;

import se.lexicon.em.utilities.ParkingStatusType;
import se.lexicon.em.utilities.VehicleType;

public abstract class Vehicle
{
	int regNumber;
	String model;
	private VehicleType type;
	private ParkingStatusType status;

	public Vehicle(int regNumber, String model, VehicleType type)
	{
		this.regNumber = regNumber;
		this.model = model;
		this.type = type;
		

	}

	public int getRegNumber()
	{
		return regNumber;
	}

	public String getModel()
	{
		return model;
	}

	public VehicleType getType()
	{
		return type;
	}

	@Override
	public String toString()
	{
		return "Vehicle [regNumber=" + regNumber + ", model=" + model + ", type=" + type + ", status=" + status + "]";
	}
	
	
	

}
