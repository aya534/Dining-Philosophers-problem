
package AutoReservation;

public class Room {
    static final int NO_OWNER = 5;	
     
	private volatile int belongs_to = NO_OWNER; 
										
	private volatile boolean room = false; 
										
	public boolean inUse(int room_number) { 
		if (belongs_to == NO_OWNER)
			return false;
		else if (room_number != belongs_to)
			return true;
		else if (room == true)
			return true;
		
		return false;
	}
	
	public void claim(int room_number) { 
		belongs_to = room_number;
		room = true; 
	}
	
	public void release() { 
		belongs_to = NO_OWNER;
		room = false; 
	}
    
}
