package se.lexicon.em.model;

import java.util.ArrayList;
import java.util.Iterator;

import se.lexicon.em.exceptions.VehicleNotFoundException;
import se.lexicon.em.utilities.VehicleType;


public class Garage
{
	private String location;
	private int maxNoOfParkedVehicles;
	private int currentNoOfParkedVehicles;
	private Vehicle[] vehicleArray = new Vehicle[100];
	private int nextPosition = 0;
	ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	
	public Garage(String location, int maxNoOfParkedVehicles){
		this.maxNoOfParkedVehicles = maxNoOfParkedVehicles;
	}
	
	public Vehicle[] getVehicleArray(){
		return vehicleArray;
	}
	
	public void addVehicle (Vehicle newVehicle){
		vehicleArray[nextPosition] = newVehicle;
		nextPosition ++;	
		//vehicles.add(newVehicle);	
	}
	
	public Vehicle findVehicles(int regNumber) throws VehicleNotFoundException
	{		
		
		for(int count= 0; count < vehicles.size(); count ++ ){	
			if (vehicleArray[count].getRegNumber() == regNumber){
				return vehicleArray[count];
				
			}				
		}
		throw new VehicleNotFoundException();	
	 		 
	}
	
	

}


