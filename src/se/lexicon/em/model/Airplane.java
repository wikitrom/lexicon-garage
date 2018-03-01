package se.lexicon.em.model;

import se.lexicon.em.utilities.VehicleType;

public class Airplane extends Vehicle
{
	private int numberOfPilots;

	public Airplane(int regNumber, String model, VehicleType type,int numberOfPilots)
	{
		super(regNumber, model, VehicleType.AIRPLAN);
		this.numberOfPilots = numberOfPilots;
	}

	public int getNumberOfPilots()
	{
		return numberOfPilots;
	}

	@Override
	public String toString()
	{
		return "Airplane [numberOfPilots=" + numberOfPilots + "]";
	}

}
