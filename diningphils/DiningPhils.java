package diningphils;

import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class DiningPhils extends Thread {
        // number of Philosophers at the table
	private static final int MAX_PHIL = 5; 
	private static int thread_creation_counter = 0; 
	public static Chopstick[] chopsticks = new Chopstick[MAX_PHIL]; 
	public static Philosopher[] philosophers = new Philosopher[MAX_PHIL];
	public static Thread[] thread_array = new Thread[MAX_PHIL];
		
	public static void main(String[] args) {
		int meals_allowed = 5;	
		if (args.length != 0)
			meals_allowed = Integer.parseInt(args[0]);
		
		for (int i = 0; i < MAX_PHIL; i++) {
			chopsticks[i] = new Chopstick();
			
			if (i < 4) 
				philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[i + 1], meals_allowed);
			else
				philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[0], meals_allowed);
			
			thread_array[i] = new Thread(philosophers[i], Integer.toString(i));
			thread_creation_counter++;
			
			thread_array[i].start();
		}
	}
	
	public static synchronized void printStatus() {	
		System.out.println("\nPhilosopher    State          Times Eaten");
		System.out.println("-------------------------------------------");
		for (int i = 0; i < thread_creation_counter; i++) {
			System.out.format("%-14s %-14s %-6s %n", thread_array[i].getName(), philosophers[i].getState(), 
			                  philosophers[i].getNumEat());
		}
	}
}



