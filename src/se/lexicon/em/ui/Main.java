package se.lexicon.em.ui;

import java.util.Scanner;

import se.lexicon.em.model.Garage;

public class Main {

	public static void main(String[] args) {
		UI ui = new UI();


		ui.printWelcomeMessage();
		ui.init();
		ui.printByeByeMessage();
		System.exit(0);
	}

}
