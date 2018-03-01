package se.lexicon.em.model;

import se.lexicon.em.utilities.VehicleType;

public class Motorcycle extends Vehicle
{
	int cylinderVolume;

	public Motorcycle(int regNumber, String model, VehicleType type,int cylinderVolume)
	{
		super(regNumber, model, VehicleType.MOTORCYCLE);
		this.cylinderVolume = cylinderVolume;
	}

	public int getCylinderVolume()
	{
		return cylinderVolume;
	}

	@Override
	public String toString()
	{
		return "Motorcycle [cylinderVolume=" + cylinderVolume + "]";
	}
	

}
