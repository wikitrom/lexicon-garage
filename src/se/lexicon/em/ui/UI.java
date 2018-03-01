package se.lexicon.em.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import javax.print.attribute.standard.NumberOfDocuments;

import se.lexicon.em.exceptions.GarageFullException;
import se.lexicon.em.exceptions.VehicleNotFoundException;
import se.lexicon.em.model.Car;
import se.lexicon.em.model.Garage;
import se.lexicon.em.model.Vehicle;
import se.lexicon.em.utilities.ParkingStatusType;
import se.lexicon.em.utilities.VehicleType;

/**
 * Class to handle input/output for the LexiconGarage application This is where
 * the control loop is located.
 * 
 * @author Mats and Ezeih
 */
public class UI {

	Garage garage;
	Vehicle newVehicle;

	// private methods
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	private boolean createNewGarage() {
		Scanner sc = new Scanner(System.in).useDelimiter("\\s");
		String location;
		int mexNumberOfParkedVehicles;

		System.out.println();
		if (garage == null) {
			System.out.println();
			System.out.println("> Build a new garage");
			System.out.println("  ==================");
			System.out.println("Please provide a location and how many cars the garage can handle and hit [RETURN].");
			System.out.println("Syntax: <location> <number of cars>");

			// TODO: make data input more robust
			location = sc.next();
			mexNumberOfParkedVehicles = sc.nextInt();
			garage = new Garage(location, mexNumberOfParkedVehicles);
			System.out.println("DEBUG: Location is: " + location + " MaxNoCars: " + mexNumberOfParkedVehicles);

			return true;
		} else {
			System.out.println("A garage already exist!");
			System.out.println("Only ONE garage is supported in current version.");
			System.out.println("Please use existing garage if not full.");
			return false;
		}

	}

	private void parkNewVehicle() {

		/*
		 * Psuedo code
		 * 
		 * 0. check if garage has space left, return null or throw exception if full
		 * 
		 * 1. ask for registration number
		 * 
		 * 2. check if vehicle already exist in the garage and has status=NOT_PARKED 3.
		 * if it exist just park it, i.e. parkVehicle(regNumber).
		 * 
		 * 4. If it does not exist create a new car and park it i nthe garage, i.e. call
		 * parkVehicle(vehicle);
		 * 
		 */
		System.out.println("Park vehicle");
		VehicleType type;
		Scanner sc = new Scanner(System.in).useDelimiter("\\s");

		// check that we have a garage and that it is not already full
		if (garage == null) {
			System.out.println();
			System.out.println("No garage exist where the vehicle can be parked.");
			System.out.println("Please build the garage first.");
			System.out.println();
			return;
		}

		if (garage.isGarageFull()) {
			System.out.println();
			System.out.println("Sorry, the garage is full. No space for your vehicle.");
			System.out.println();
			return;
		}

		System.out.println("There is space in the garage.");

		// check if vehicle is already known at the garage
		// FIX: regNo should be string.
		// String regNo;
		int regNo;
		regNo = askForRegNumber();
		System.out.println("DEBUG: the reg number is " + regNo); // TODO: remove

		Vehicle existingVehicle;

		try {
			existingVehicle = garage.findVehicles(regNo);
			if (existingVehicle.getStatus() == ParkingStatusType.NOT_PARKED) {
				existingVehicle.setStatus(ParkingStatusType.PARKED);
				System.out.println("Welcome back to our garage.");
				System.out.println("Your " + existingVehicle.getType().toString() + "has been parked");
			} else {
				System.out.println(
						"Youe vehicle with registration number " + regNo + " is already parked in this garage.");
				System.out.println("Please check the number you have provided.");
			}

		} catch (VehicleNotFoundException e) {

			System.out.println();
			System.out.println("We need to add your vehical to our register.");
			System.out.println();
			registerAndParkNewVehicle(regNo);
		}
	}

	private boolean unparkVehicle() {

		/*
		 * pseudo code
		 * 
		 * 1. ask for regNumber
		 * 
		 * 2. check if vehicle is parked.
		 * 
		 * 3. if parked unpark it and return true.
		 * 
		 * 4. if not parked, return false
		 */
		System.out.println("Unpark vehicle");
		return true;
	}

	private void listParkedVehicles() {

		/*
		 * pseudo code:
		 * 
		 * 1. fetch all vehicles from garage
		 * 
		 * 2. loop over vehicles and print out each vehicle that has status = PARKED
		 * 
		 */
		System.out.println("List all parked vehicles");

	}

	private void listParkedVehicleTypes() {

		/*
		 * pseudo code:
		 * 
		 * 1. fetch all vehicles from garage
		 * 
		 * 2. loop over all parked vehicles and count how many of each type.
		 * 
		 * 3. print out number of vehicles for each supported type
		 * 
		 */
		System.out.println("List how many vehicles of each type that is in the garage.");
	}

	private void findParkedVehicle() {

		/*
		 * pseudo code: 1. ask for what to search for
		 * 
		 * 2. call garage methods and print result.
		 * 
		 */
		System.out.println("Find a vehicle, maybe different search properties.");
	}

	private void registerAndParkNewVehicle(int regNo) { // FIX: String regNo

		// common vehicle data
		VehicleType type;
		String model;
		
		// vehicle specific data
		int noOfSeats;  // car
		int weight;     // boat

		type = askForVehicleType();
		model = askForModel();

	
		// type specific data
		switch (type.toString()) {

		case "CAR":
			noOfSeats = askForNoOfSeats();
			newVehicle = new Car(regNo, model, type, noOfSeats);
			break;
		case "BOAT":
			weight = askForWeight();		
		default:
			break;
		}
		// TODO: remove

		if (newVehicle != null) {
			garage.addVehicle(newVehicle);
		} else {
			// FIX: handle this in a better way...
			System.out.println();
			System.out.println("*** FAILED to add new vihical to garage!");
			System.out.println();
		}
		System.out.println(
				"DEBUG: common data is: " + " Type=" + type.toString() + " regNo=" + regNo + " model=" + model);

	}

	private VehicleType askForVehicleType() {
		Scanner sc = new Scanner(System.in).useDelimiter("\\s");
		String type;

		System.out.println(
				"This garage can handle the following types of vehicles:\nCar, Bus, Boat, Motorcycle (moto) and Airplane.");
		System.out.println("What type of vehicle to you want to park?");
		System.out.println("Syntax: <write one type only>");

		type = sc.next().toLowerCase();

		switch (type) {
		case "car":
			return VehicleType.CAR;
		case "bus":
			return VehicleType.BUS;
		case "boat":
			return VehicleType.BOAT;
		case "motorcycle":
		case "moto":
			return VehicleType.MOTORCYCLE;
		case "airplain":
			return VehicleType.AIRPLAN;
		default:
			return VehicleType.UNKNOWN;
		}
	}

	private int askForRegNumber() {

		// FIX: number should be a string

		Scanner sc = new Scanner(System.in).useDelimiter("\\s");
		int regNo;

		System.out.println("What is the registration number?");
		System.out.println("Syntax: <number>");

		regNo = sc.nextInt();

		return regNo;
	}

	private String askForModel() {

		Scanner sc = new Scanner(System.in);
		String model;

		System.out.println("What is the vehicle model?");
		System.out.println("Syntax: <string>");

		model = sc.next();

		return model;
	}

	private int askForNoOfSeats() {

		Scanner sc = new Scanner(System.in).useDelimiter("\\s");
		int seats;

		System.out.println("How many seats are there?");
		System.out.println("Syntax: <number>");

		seats = sc.nextInt();

		return seats;
	}

	private int askForWeight() {

		Scanner sc = new Scanner(System.in).useDelimiter("\\s");
		int weight;

		System.out.println("What is the weight?");
		System.out.println("Syntax: <number>");

		weight = sc.nextInt();

		return weight;
	}

	// public methods
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 * Control loop
	 * 
	 * @param none
	 */
	public void init() {

		// TODO: Make loop more robust, handle wrong input, i.e. not a number causes
		// exception

		//
		int value = 0;
		int selection;
		Scanner sc = new Scanner(System.in); // .useDelimiter("\\s");

		do {
			printMenue1();
			System.out.println("Enter a number key and hit [RETURN]: ");
			selection = sc.nextInt();

			switch (selection) {
			case 0:
				// noting more to do, quit method
				break;
			case 1:
				createNewGarage();
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

		} while (selection > 0);
		sc.close();
	}

	public void printWelcomeMessage() {
		System.out.println("Welcom to Lexicon Garage");
		System.out.println("========================");
		System.out.println();
	}

	public void printByeByeMessage() {
		System.out.println();
		System.out.println("Thank you for using Lexicon Garage system.");
		System.out.println();
		System.out.println("Bye bye!");

	}

	/**
	 * Prints out main menu
	 */
	public void printMenue1() {
		System.out.println("");
		System.out.println("What do you want todo?");

		System.out.println();
		System.out.println("(0) Exit garage system.");
		System.out.println("(1) Build a garage");
		System.out.println("(2) Park vehicle");
		System.out.println("(3) Unpark vehicle");
		System.out.println("(4) List parked vehicles");
		System.out.println("(5) List parked vehicle Types");
		System.out.println("(6) Find vehicle");
		System.out.println();
	}

}
//
// listParkedVehicles() : void
// listParkedVehicleTypes() : void
// listKnownVehicles() : void ???
//
// findVehicle(regNumber) : Vehicle

// isGarageFull() : boolean

// parkVehicle(Vehicle) : boolean
// parkVehicle(regNumber) : boolean ???
// unparkVehicle(RegNumber) : boolean

// setParkingStatus(RegNumber, status) : void ???
