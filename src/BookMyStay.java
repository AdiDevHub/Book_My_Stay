import java.util.HashMap;
import java.util.Map;

// Room structure (Base)
abstract class Room {
    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;
    public Room(int b, int s, double p) { this.numberOfBeds = b; this.squareFeet = s; this.pricePerNight = p; }
    public void display() {
        System.out.println("Beds: " + numberOfBeds + "\nSize: " + squareFeet + " sqft\nPrice: " + pricePerNight);
    }
}

class SingleRoom extends Room { public SingleRoom() { super(1, 250, 1500.0); } }
class DoubleRoom extends Room { public DoubleRoom() { super(2, 400, 2500.0); } }
class SuiteRoom extends Room { public SuiteRoom() { super(3, 750, 5000.0); } }

// Centralized Inventory
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();
    public RoomInventory() {
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }
    public Map<String, Integer> getAvailability() { return availability; }
}

// SEARCH SERVICE: Handles read-only logic
class RoomSearchService {
    public void searchAvailableRooms(RoomInventory inv, Room single, Room doubleR, Room suite) {
        Map<String, Integer> avail = inv.getAvailability();
        System.out.println("--- Available Rooms ---\n");

        if (avail.get("Single") > 0) {
            System.out.println("Single Room (Available: " + avail.get("Single") + ")");
            single.display(); System.out.println();
        }
        if (avail.get("Double") > 0) {
            System.out.println("Double Room (Available: " + avail.get("Double") + ")");
            doubleR.display(); System.out.println();
        }
        if (avail.get("Suite") > 0) {
            System.out.println("Suite Room (Available: " + avail.get("Suite") + ")");
            suite.display();
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        RoomSearchService search = new RoomSearchService();

        search.searchAvailableRooms(inventory, new SingleRoom(), new DoubleRoom(), new SuiteRoom());
    }
}