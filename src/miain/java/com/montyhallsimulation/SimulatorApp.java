package com.montyhallsimulation;

import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class SimulatorApp {

	// random object to auto assign doors to player, prize and presenter
	public static final Random autoDoorAssigner = new Random();

	@GetMapping("/simulate/montyhall/{noOfSimulations}/{switchOrStick}")
	@ResponseBody
	String simulator(@PathVariable("noOfSimulations") Integer simulations,
			@PathVariable("switchOrStick") String switchOrStick) {

		boolean switchDoor = switchOrStick.equalsIgnoreCase("switch") ? true : false; // Strategy
		
		int winsBySticking = 0; // times the player wins by sticking to original choice of door
		int loseBySticking = 0; // times the player loses by sticking to original choice of door
		int winsBySwitching = 0; // times the player wins by switching the original choice of door
		int loseBySwitching = 0; // times the player loses by switching the original choice of door

		if(simulations != null) {
			for (int i = 0; i < simulations; i++) {
				int playerDoor = autoDoorAssigner.nextInt(3);
				int prizeDoor = autoDoorAssigner.nextInt(3);
				int presenterDoor = identifyOtherDoor(playerDoor, prizeDoor); // presenter to open the door other than prize door and player door
																				 
				if (switchDoor) { // player door changes as the switch strategy is chosen
					playerDoor = identifyOtherDoor(playerDoor, presenterDoor);
					if (prizeDoor == playerDoor) {
						winsBySwitching++;
					} else {
						loseBySwitching++;
					}
				} else { // player chooses to stick to the original choice
					if (prizeDoor == playerDoor) {
						winsBySticking++;
					} else {
						loseBySticking++;
					}
				}
			}
		} else {
			throw new IllegalArgumentException("Invalid number of simulations");
		}
		

		if (switchDoor) {
			return "Stats with SWITCH strategy - WIN : " + winsBySwitching + " LOSE : " + loseBySwitching;
		} else {
			return "Stats with STICK strategy - WIN : " + winsBySticking + " LOSE : " + loseBySticking;
		}
	}

	/**
	 * chooses a door other than the doors passed as argument
	 */
	private static int identifyOtherDoor(int playerDoor, int prizeOrPresenterDoor) {
		int otherDoor = autoDoorAssigner.nextInt(3);
		// change the other door if it matches one of the taken doors
		while (otherDoor == playerDoor || otherDoor == prizeOrPresenterDoor) {
			otherDoor = autoDoorAssigner.nextInt(3);
		}
		return otherDoor;
	}

	public static void main(String[] args) {
		SpringApplication.run(SimulatorApp.class, args);
	}
}
