import java.util.*;

// 1. ABSTRACT BASE CLASS
abstract class Room {
    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;
    public Room(int b, int s, double p) { this.numberOfBeds = b; this.squareFeet = s; this.pricePerNight = p; }
    public void display() { System.out.println("Beds: " + numberOfBeds + ", Size: " + squareFeet + " sqft, Price: " + pricePerNight); }
}

// 2. ROOM TYPE SUBCLASSES
class SingleRoom extends Room { public SingleRoom() { super(1, 250, 1500.0); } }
class DoubleRoom extends Room { public DoubleRoom() { super(2, 400, 2500.0); } }
class SuiteRoom extends Room { public SuiteRoom() { super(3, 750, 5000.0); } }

// 3. BOOKING RESERVATION OBJECT
class Reservation {
    private String guestName;
    private String roomType;
    public Reservation(String name, String type) { this.guestName = name; this.roomType = type; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

// 4. CENTRALIZED INVENTORY
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();
    public RoomInventory() { availability.put("Single", 5); availability.put("Double", 3); availability.put("Suite", 2); }
    public Map<String, Integer> getRoomAvailability() { return availability; }
    public void updateAvailability(String type, int count) { availability.put(type, count); }
}

// 5. BOOKING REQUEST QUEUE
class BookingRequestQueue {
    private Queue<Reservation> requestQueue = new LinkedList<>();
    public void addRequest(Reservation r) { requestQueue.offer(r); }
    public Reservation getNextRequest() { return requestQueue.poll(); }
    public boolean hasPendingRequests() { return !requestQueue.isEmpty(); }
}

// 6. ALLOCATION SERVICE
class RoomAllocationService {
    private Set<String> allocatedIds = new HashSet<>();
    private Map<String, Integer> typeCount = new HashMap<>();

    public void allocateRoom(Reservation res, RoomInventory inv) {
        String type = res.getRoomType();
        int available = inv.getRoomAvailability().getOrDefault(type, 0);
        if (available > 0) {
            typeCount.put(type, typeCount.getOrDefault(type, 0) + 1);
            String id = type + "-" + typeCount.get(type);
            inv.updateAvailability(type, available - 1);
            System.out.println("Booking confirmed for " + res.getGuestName() + ", Room ID: " + id);
        } else { System.out.println("No availability for " + type); }
    }
}

// 7. MAIN EXECUTION CLASS
public class BookMyStay {
    public static void main(String[] args) {
        RoomInventory inv = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomAllocationService service = new RoomAllocationService();

        // Registering requests
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Suite"));

        System.out.println("Room Allocation Processing\n");
        while (queue.hasPendingRequests()) {
            service.allocateRoom(queue.getNextRequest(), inv);
        }
    }
}