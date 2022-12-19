package AutoReservation;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;
public class AutoReservation extends Thread{
    // number of guests in waiting list
	private static final int MAX_GUEST = 5; 
	private static int thread_creation_counter = 0; 
	public static Room[] rooms = new Room[MAX_GUEST]; 
	public static Guest[] guests = new Guest[MAX_GUEST];
	public static Thread[] thread_array = new Thread[MAX_GUEST];
    public static void main(String[] args) {
        int reservation_allowed = 5;	
		if (args.length != 0)
			reservation_allowed = Integer.parseInt(args[0]);
		
		for (int i = 0; i < MAX_GUEST; i++) {
			rooms[i] = new Room();
			
			if (i < 4) 
				guests[i] = new Guest(i, rooms[i], rooms[i + 1], reservation_allowed);
			else
				guests[i] = new Guest(i, rooms[i], rooms[0], reservation_allowed);
			
			thread_array[i] = new Thread(guests[i], Integer.toString(i));
			thread_creation_counter++;
			
			thread_array[i].start();
		}
    }
    public static synchronized void printStatus() {	
		System.out.println("\nGuests    State          Times Reserved");
		System.out.println("-------------------------------------------");
		for (int i = 0; i < thread_creation_counter; i++) {
			System.out.format("%-14s %-14s %-6s %n", thread_array[i].getName(), guests[i].getState(), 
			                  guests[i].getNumReserve());
		}
	}
    
}
