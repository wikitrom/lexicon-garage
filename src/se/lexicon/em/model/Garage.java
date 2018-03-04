package se.lexicon.em.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import se.lexicon.em.exceptions.VehicleNotFoundException;
import se.lexicon.em.utilities.ParkingStatusType;
import se.lexicon.em.utilities.VehicleType;

public class Garage {
	private String location;
	private int maxNoOfParkedVehicles;
	private int currentNoOfParkedVehicles = 0;
	private Vehicle[] vehicleArray;
	private int nextPosition = 0;
	private int noOfRegisteredVehicles = 0;
	ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	Vehicle pVehicle;

	public Garage(String location, int maxNoOfParkedVehicles) {
		this.maxNoOfParkedVehicles = maxNoOfParkedVehicles;
		vehicleArray = new Vehicle[maxNoOfParkedVehicles];
	}

	public Vehicle[] getVehicleArray() {
		return vehicleArray;
	}

	public void addVehicle(Vehicle newVehicle) {
		vehicleArray[nextPosition] = newVehicle;
		nextPosition++;
		currentNoOfParkedVehicles++;
		noOfRegisteredVehicles++;

	}

	public Vehicle findVehicles(int regNumber) throws VehicleNotFoundException {
		for (int count = 0; count < vehicleArray.length; count++) {
			if (vehicleArray[count] != null) {
				if (vehicleArray[count].getRegNumber() == regNumber)
					return vehicleArray[count];
			}
		}

		throw new VehicleNotFoundException();

	}

	public boolean isGarageFull() {
		return (currentNoOfParkedVehicles == maxNoOfParkedVehicles) ? true : false;

	}

	public boolean canTackeMoreCustomer() {
		return (noOfRegisteredVehicles < vehicleArray.length) ? true : false;

	}

	public void unparkVehicle(int regNumber) {
		Vehicle aVehicle;
		try {
			aVehicle = findVehicles(regNumber);
			if (aVehicle != null) {
				aVehicle.setStatus(ParkingStatusType.NOT_PARKED);
				currentNoOfParkedVehicles--;
			}

		} catch (VehicleNotFoundException e) {

		}

		// no need to do anything
	}

	public void setParkingStatus(ParkingStatusType status, int regNumbr) {
		{
			// FIX: MATS - this will not work, use next method instead

			if (pVehicle.getRegNumber() != regNumbr) {
				pVehicle.setStatus(status.NOT_PARKED);
			}
			if (pVehicle.getRegNumber() == regNumbr) {
				pVehicle.setStatus(status.PARKED);
			}

		}
	}


	public void setParkingStatus(Vehicle aVehicle, ParkingStatusType status) {

		if (aVehicle != null) {
			aVehicle.setStatus(status);
		}
	}

}
