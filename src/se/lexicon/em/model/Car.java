package se.lexicon.em.model;

import se.lexicon.em.utilities.VehicleType;

public class Car extends Vehicle
{
	int numberOfSeats;

	public Car(int regNumber, String model,VehicleType type, int numberOfSeats)
	{
		super(regNumber, model, VehicleType.CAR);
		this.numberOfSeats = numberOfSeats;

	}

	public int getNumberOfSeats()
	{
		return numberOfSeats;
	}

	@Override
	public String toString()
	{
		return "Car [numberOfSeats=" + numberOfSeats + "]";
	}

}
