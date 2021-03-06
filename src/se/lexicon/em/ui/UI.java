package se.lexicon.em.ui;

import java.util.InputMismatchException;
import java.util.Scanner;
import javax.print.attribute.standard.NumberOfDocuments;
import se.lexicon.em.exceptions.GarageFullException;
import se.lexicon.em.exceptions.VehicleNotFoundException;
import se.lexicon.em.model.Boat;
import se.lexicon.em.model.Bus;
import se.lexicon.em.model.Car;
import se.lexicon.em.model.Garage;
import se.lexicon.em.model.Motorcycle;
import se.lexicon.em.model.Vehicle;
import se.lexicon.em.utilities.ParkingStatusType;
import se.lexicon.em.utilities.VehicleType;

/**
 * Class to handle input/output for the LexiconGarage application This is where
 * the control loop is located.
 * 
 * @author Mats and Ezeih
 */
public class UI
{

	Garage garage;
	Vehicle aVehicle;

	// public methods
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 * Control loop
	 * 
	 * @param none
	 */
	public void init()
	{

		// TODO: Make loop more robust, handle wrong input, i.e. not a number
		// causes
		// exception

		//
		int value = 0;
		int selection;
		Scanner sc = new Scanner(System.in); // .useDelimiter("\\s");

		do
		{
			printMenu();
			System.out.println("Enter a number and [RETURN]: ");
			selection = sc.nextInt();

			switch (selection)
			{
			case 0:
				// noting more to do, quit method
				break;
			case 1:
				buildNewGarage();
				break;

			case 2:
				parkNewVehicle();

				break;

			case 3:
				unparkVehicle();
				break;

			case 4:
				listParkedVehicles();
				break;
			case 5:
				listParkedVehicleTypes();
				break;
			case 6:

				// TODO:
				System.out.println("(9) Find vehicle");
				findParkedVehicle();
				break;
			case 10:
				// TODO: maybe a secret cmd ... remove!
				System.out.println("(10) Set parking status for vehicle:");
				System.out.println("Possible states: PARKED, NOT_PARKED, ON_FIRE");
				break;

			default:
				System.out.println();
				System.out.println("Please enter a valid number!");
				System.out.println();
				break;
			}

		}
		while (selection > 0);
		sc.close();
	}

	public void printWelcomeMessage()
	{
		System.out.println();
		System.out.println("  Welcome to Lexicon Garage");
		System.out.println("  =========================");
		System.out.println();
	}

	public void printByeByeMessage()
	{
		System.out.println();
		System.out.println("Thank you for using Lexicon Garage system.");
		System.out.println("Bye bye!");
		System.out.println();
	}

	// private methods
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	private void buildNewGarage()
	{
		Scanner sc = new Scanner(System.in).useDelimiter("\n"); // closed by
																// init()
		String location, tmpInput;
		int mexNumberOfParkedVehicles;

		System.out.println();
		if (garage == null)
		{
			System.out.println();
			System.out.println("> Build a new garage");
			System.out.println("  ==================");
			System.out.println("Please provide a location for the garage (end with [RETURN])");
			location = sc.next().trim();

			System.out.println("Please provide the capacity (number of vehicles) for the garage (end with [RETURN])");
			tmpInput = sc.next().trim();
			if (tmpInput.matches("^[0-9]+"))
			{
				mexNumberOfParkedVehicles = Integer.parseInt(tmpInput);
			}
			else
			{
				System.out.println("Bad data! Garage capacity must be a number.");
				return;
			}

			garage = new Garage(location, mexNumberOfParkedVehicles);
			if (garage == null)
			{
				throw new RuntimeException("Critical error, failed to instantiate new Garage object");
			}

			System.out.println();
			System.out.println("A new garage was succesfully built by Lexicon Construction.");
			System.out.println("Location: " + location);
			System.out.println("Capacity: " + mexNumberOfParkedVehicles);
			System.out.println();
			return;
		}
		else
		{
			System.out.println();
			System.out.println("A garage already exist!");
			System.out.println("Only ONE garage is supported at present time.");
			System.out.println("Please use existing garage if not full.");
			return;
		}

	}

	private void parkNewVehicle()
	{

		// TODO: make data input more robust

		/*
		 * Psuedo code
		 * 
		 * 0. check if garage has space left
		 * 
		 * 
		 * 1. ask for registration number
		 * 
		 * 2. check if vehicle already exist in the garage and has
		 * status=NOT_PARKED
		 * 
		 * 3. if it exist just park it, i.e. set parking status=PARKED
		 * 
		 * 4. If it does not exist instantiate a new vehicle of specified type
		 * and park it i the garage, i.e. call parkVehicle(vehicle);
		 */
		System.out.println();
		System.out.println("> Park vehicle");
		System.out.println("  ============");

		VehicleType type;

		// check that we have a garage and that it is not already full
		if (garage == null)
		{
			System.out.println("No garage exist where the vehicle can be parked.");
			System.out.println("Please build the garage first.");
			return;
		}
		if (garage.isGarageFull())
		{
			System.out.println("Sorry, the garage is full. No space for your vehicle.");
			return;
		}
		// System.out.println("There is space in the garage.");

		// ask for registration number
		// FIX: regNo should be string.
		int regNo;
		regNo = askForAnInteger("What is the registration number?\nSyntax: <number>");

		// check if vehicle is already known at the garage
		try
		{
			aVehicle = garage.findVehicles(regNo);
			// TODO: would be better to let garage handle the parking
			// use a 'public boolean parkVehicle(regNo);' method.
			if (aVehicle.getStatus() == ParkingStatusType.NOT_PARKED)
			{
				aVehicle.setStatus(ParkingStatusType.PARKED);
				System.out.println("Welcome back to our garage.");
				System.out.println("Your " + aVehicle.getType().toString() + " has been parked");
			}
			else
			{
				System.out.println(
						"A vehicle with registration number \'" + regNo + "\' is already parked in this garage.");
				System.out.println("Please check the registration number you have provided.");
			}

		}
		catch (VehicleNotFoundException e)
		{
			if (!garage.canTackeMoreCustomer())
			{
				System.out.println("Sorry, the garage has reach its maximum number of registered customers.\n"
						+ "No space for your vehicle.");
				return;
			}
			System.out.println("DEBUG: " + e.toString());
			System.out.println();
			System.out.println("To be able to park we need to register your vehicle.");
			registerAndParkNewVehicle(regNo);
		}
	}

	private void unparkVehicle()
	{
		System.out.println();
		System.out.println("> Unpark vehicle");
		System.out.println("  ==============");

		// FIX: regNo should be string.
		int regNo;

		regNo = askForAnInteger("What is the registration number?\nSyntax: <number>");

		try
		{
			aVehicle = garage.findVehicles(regNo);
			// TODO: Perhaps it is better to let garage handle the unparking all
			// together,
			// use a 'public boolean unparkVehicle(regno);'
			if (aVehicle.getStatus() == ParkingStatusType.PARKED)
			{
				// aVehicle.setStatus(ParkingStatusType.NOT_PARKED);
				garage.unparkVehicle(regNo); // needed to decrement garage
												// currentNoOfParkedVehicles
												// counter
				System.out.println("Your " + aVehicle.getType().toString() + " has been unparked");
			}
			else
			{
				System.out.println("A vehicle with registration number \'" + regNo + "\' is registered \n"
						+ "in this garage but not parked here.");
			}
		}
		catch (VehicleNotFoundException e)
		{
			System.out.println("DEBUG: " + e.toString());
			System.out.println();
			System.out.println("No vehicle with registration numer " + regNo + " has ever parked in this garage.");
			System.out.println("Please check the registration number you have provided.");
		}
		return;
	}

	private void listParkedVehicles()
	{

		/*
		 * pseudo code:
		 * 
		 * 1. fetch all vehicles from garage
		 * 
		 * 2. loop over vehicles and print out each vehicle that has status =
		 * PARKED
		 */
		System.out.println();
		System.out.println("> List all parked vehicles");
		System.out.println("  ========================");

		Vehicle[] vArray = garage.getVehicleArray();

		// TODO: each vehicle type should have a toString() that prints all
		// data, not
		// only the type specific data.
		for (int count = 0; count < vArray.length; count++)
		{
			if (vArray[count] != null)
			{
				if (vArray[count].getStatus() == ParkingStatusType.PARKED)
				{
					System.out.println(vArray[count].toString());
				}
			}
		}
	}

	private void listParkedVehicleTypes()
	{

		/*
		 * pseudo code:
		 * 
		 * 1. fetch all vehicles from garage
		 * 
		 * 2. loop over all parked vehicles and count how many of each type.
		 * 
		 * 3. print out number of vehicles for each supported type
		 */
		System.out.println();
		System.out.println("> List number of parked vehicles per type");
		System.out.println("  =======================================");

		Vehicle[] vArray = garage.getVehicleArray();
		VehicleType type;

		// FIX: this is uggly
		int carCount = 0;
		int boatCount = 0;
		int busCount = 0;
		int motoCount = 0;

		// TODO: each vehicle type should have a toString() that prints all
		// data, not
		// only the type specific data.
		for (int count = 0; count < vArray.length; count++)
		{
			if (vArray[count] != null)
			{
				if (vArray[count].getStatus() == ParkingStatusType.PARKED)
				{

					type = vArray[count].getType();
					switch (type.toString())
					{
					case "CAR":
						carCount++;
						break;
					case "BUS":
						busCount++;
						break;
					case "BOAT":
						boatCount++;
						break;
					case "MOTORCYCLE":
						motoCount++;
						break;
					default:
						break;
					}
				}

			}
		}
		System.out.println("Number of parked Cars: " + carCount);
		System.out.println("Number of parked Busses: " + busCount);
		System.out.println("Number of parked Boats: " + boatCount);
		System.out.println("Number of parked Motorcycles: " + motoCount);
		System.out.println();
	}

	/*
	 * pseudo code: 1. ask for what to search for
	 * 
	 * 2. call garage methods and print result.
	 */
	// System.out.println("Find a vehicle, maybe different search properties.");
	private void findParkedVehicle()
	{
		Vehicle tVehicle;
		System.out.println("Input regestration number:");
		Scanner sc = new Scanner(System.in);
		int regNumber = sc.nextInt();
		try
		{
			tVehicle= garage.findVehicles(regNumber);
			System.out.println("This Regestraion number" + tVehicle.getRegNumber() + " :is found");
		}
		catch (VehicleNotFoundException e)
		{
			System.out.println("The vehicles not found");
		}
	}

	// private Helper methods
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	private void registerAndParkNewVehicle(int regNo)
	{ // FIX: String regNo

		// ask for common vehicle data
		VehicleType type;
		String model;
		type = askForVehicleType();
		model = askForAString("What is the vehicle model?\nSyntax: <string>");

		// ask vehicle type specific data
		int noOfSeats; // car
		int weight; // boat
		int numOfPassengers; // bus
		int cylinderVolume; // motorcycle

		switch (type.toString())
		{
		case "CAR":
			noOfSeats = askForAnInteger("How many seats are there in the car?\n Syntax: <number>");
			aVehicle = new Car(regNo, model, type, noOfSeats);
			break;

		case "BOAT":
			weight = askForAnInteger("What is the boat weight?\nSyntax: <number>");
			aVehicle = new Boat(regNo, model, type, weight);
			break;

		case "BUS":
			numOfPassengers = askForAnInteger("Whow many passengers can the bus take?\nSyntax: <number>");
			aVehicle = new Bus(regNo, model, type, numOfPassengers);
			break;

		case "MOTORCYCLE":
			cylinderVolume = askForAnInteger("What is the cylinder volume of the motorbike?\nSyntax: <number>");
			aVehicle = new Motorcycle(regNo, model, type, cylinderVolume);
			break;

		case "UNKNOWN":
			System.out.println("Sorry, we can not park vehicles of unknown type.");
			return;
		default:
			break;
		// TODO: ADD more types
		}

		// if new vehicle instantiated, add to garage
		if (aVehicle != null)
		{
			aVehicle.setStatus(ParkingStatusType.PARKED);
			garage.addVehicle(aVehicle);
		}
		else
		{
			// FIX: handle this in a better way...
			System.out.println();
			System.out.println(
					"*** FAILED to add new vihicle of unsupported type " + type.toString() + " to the garage!");
			return;
		}
		System.out.println("Your vehicle of type" + type.toString().toLowerCase() + " has been parked in the garage.");
		System.out.println("We have the following information: \n" + aVehicle.toString() + " [Registration number: "
				+ aVehicle.getRegNumber() + "] [Model: " + aVehicle.getModel() + "]");

		aVehicle = null; // free up for re-use for next parking
	}

	private VehicleType askForVehicleType()
	{
		Scanner sc = new Scanner(System.in).useDelimiter("\\s");
		String type;

		System.out.println(
				"This garage can handle the following types of vehicles:\nCar, Bus, Boat, and Motorcycle (moto).");
		System.out.println("What type of vehicle to you want to park?");
		System.out.println("Syntax: <write one type only>");

		type = sc.next().toLowerCase();

		switch (type)
		{
		case "car":
			return VehicleType.CAR;
		case "bus":
			return VehicleType.BUS;
		case "boat":
			return VehicleType.BOAT;
		case "motorcycle":
		case "moto":
			return VehicleType.MOTORCYCLE;
			// case "airplain":
			// return VehicleType.AIRPLAN;
		default:
			return VehicleType.UNKNOWN;
		}
	}

	private int askForAnInteger(String displayText)
	{

		Scanner sc = new Scanner(System.in).useDelimiter("\\s"); // closed in
																	// init()
		int input;

		System.out.println(displayText);

		input = sc.nextInt();

		return input;
	}

	private String askForAString(String displayText)
	{

		Scanner sc = new Scanner(System.in).useDelimiter("\n"); // closed in
																// init()
		String input;

		System.out.println(displayText);

		input = sc.next().trim();

		return input;
	}

	/**
	 * Prints out main menu
	 */
	private void printMenu()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("What do you want to do?");
		sb.append("\n");
		sb.append("(0) Exit garage system.\n");
		sb.append("(1) Build a garage\n");
		sb.append("(2) Park vehicle\n");
		sb.append("(3) Unpark vehicle\n");
		sb.append("(4) List parked vehicles\n");
		sb.append("(5) List parked vehicle Types\n");
		sb.append("(6) Find vehicle\n");
		System.out.println(sb);
	}

}
