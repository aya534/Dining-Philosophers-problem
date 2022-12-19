package AutoReservation;
import javax.lang.model.*;
import java.util.concurrent.ThreadLocalRandom;

class Guest implements Runnable{
    private volatile Room key = new Room(), room = new Room();
	private int MAX_RESERVATION, num_res, room_number;
	private volatile long eating_time, waiting_time;
	enum State {REQUEST , WAITING, OCCUPIED, CHECKOUT; }
	private State current_state = State.REQUEST;
	
	public Guest(int room_num, Room Key, Room Room, int book_num) {
		room_number = room_num;
		key= Key;
		room = Room;
		MAX_RESERVATION = book_num;
		current_state = State.REQUEST;
		
		setRandomTimes();
	}
	
	public void run() {
		AutoReservation.printStatus();
		
		while (num_res < MAX_RESERVATION) {
			changeState(State.REQUEST);
			
			waitt();
			
			changeState(State.REQUEST);	
			try {
				if (pickupkey() == false)
					continue;
			} catch(NullPointerException e) {/* do nothing */}
			
			occupy();
		}
		
		changeState(State.CHECKOUT);
	}
	
	public void waitt() { 
		changeState(State.WAITING);
		setRandomTimes();
		
		try {
			Thread.sleep(waiting_time);
		} catch (InterruptedException e) {/* do nothing */}
	}
	
	public boolean pickupkey() {			
		if (key.inUse(room_number) == false)
			key.claim(room_number);
		else
			return false;
		
		if (room.inUse(room_number) == false)
			room.claim(room_number);
		else {
			key.release();
			return false;
		}
		
		return true;
	}
	
	public void occupy() {
		changeState(State.OCCUPIED);
		try {
			Thread.sleep(eating_time);
		} catch (InterruptedException e) {/* do nothing */}
		
		
		try {
			key.release();
			room.release();
		} catch (NullPointerException e) {/* do nothing */}
		
		num_res++;
	}
	
	public void changeState(State new_state) { 
		if (current_state != new_state) {
			current_state = new_state; 
			AutoReservation.printStatus();
		}
		else
			return;
	}
	
	public String getState() {
		String str_state = "";
		if (current_state == State.REQUEST)
			str_state = "REQUEST ROOM";
		else if (current_state == State.WAITING)
			str_state = "WAITING";
		else if (current_state == State.OCCUPIED)
			str_state = "OCCUPYING ROOM";
		else if (current_state == State.CHECKOUT)
			str_state = "CHECKING-OUT";
		
		return str_state;
	}
	
	public int getNumReserve() { return num_res; }
	
	public void setRandomTimes() {
		eating_time = ThreadLocalRandom.current().nextLong(5000);
		waiting_time = ThreadLocalRandom.current().nextLong(eating_time);
	}

 
    
}
