import java.util.*;

// Mock RoomInventory to manage count
class RoomInventory {
    private int singleRooms = 5;
    public void incrementSingle() { singleRooms++; }
    public int getSingleAvailability() { return singleRooms; }
}

class CancellationService {
    private Stack<String> releasedRoomIds;
    private Map<String, String> reservationRoomTypeMap;

    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    public void cancelBooking(String reservationId, RoomInventory inventory) {
        if (reservationRoomTypeMap.containsKey(reservationId)) {
            String roomType = reservationRoomTypeMap.remove(reservationId);
            if (roomType.equals("Single")) {
                inventory.incrementSingle();
            }
            releasedRoomIds.push(reservationId);
            System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
        }
    }

    public void showRollbackHistory() {
        System.out.println("\nRollback History (Most Recent First):");
        Stack<String> tempStack = (Stack<String>) releasedRoomIds.clone();
        while (!tempStack.isEmpty()) {
            System.out.println("Released Reservation ID: " + tempStack.pop());
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        String resId = "Single-1";
        service.registerBooking(resId, "Single");

        System.out.println("Booking Cancellation");
        service.cancelBooking(resId, inventory);

        service.showRollbackHistory();
        System.out.println("\nUpdated Single Room Availability: " + inventory.getSingleAvailability());
    }
}