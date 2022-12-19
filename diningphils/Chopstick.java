package diningphils;

class Chopstick {
	static final int NO_OWNER = 5;	
       
	private volatile int belongs_to = NO_OWNER; 
										
	private volatile boolean chopstick = false; 
										
	public boolean inUse(int chair_number) { 
		if (belongs_to == NO_OWNER)
			return false;
		else if (chair_number != belongs_to)
			return true;
		else if (chopstick == true)
			return true;
		
		return false;
	}
	
	public void claim(int chair_number) { 
		belongs_to = chair_number;
		chopstick = true; 
	}
	
	public void release() { 
		belongs_to = NO_OWNER;
		chopstick = false; 
	}
}