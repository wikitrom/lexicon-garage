package se.lexicon.em.model;

import se.lexicon.em.utilities.VehicleType;

public class Boat extends Vehicle
{
	private int weight;

	public Boat(int regNumber, String model, VehicleType type, int weight)
	{
		super(regNumber, model, VehicleType.BOAT);
		this.weight = weight;
	}

	public int getWeight()
	{
		return weight;
	}

	@Override
	public String toString()
	{
		return "Boat [weight=" + weight + "]";
	}
	

}
