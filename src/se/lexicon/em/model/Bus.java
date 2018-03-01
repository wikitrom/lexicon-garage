package se.lexicon.em.model;

import se.lexicon.em.utilities.VehicleType;

public class Bus extends Vehicle
{
	private int numberOfPassengers;

	public Bus(int regNumber, String model, VehicleType type, int numberOfPassengers)
	{
		super(regNumber, model, VehicleType.BUS);
		this.numberOfPassengers = numberOfPassengers;
	}

	public int getNumberOfPassengers()
	{
		return numberOfPassengers;
	}

	@Override
	public String toString()
	{
		return "Bus [numberOfPassengers=" + numberOfPassengers + "]";
	}
	

}
