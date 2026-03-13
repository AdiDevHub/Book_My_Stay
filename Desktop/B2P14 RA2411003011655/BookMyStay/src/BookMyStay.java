import java.util.*;

// Custom exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Mock RoomInventory class to support the validator
class RoomInventory {
    public boolean isValidRoom(String roomType) {
        return Arrays.asList("Single", "Double", "Suite").contains(roomType);
    }
}

// Validator class
class ReservationValidator {
    public void validate(String guestName, String roomType, RoomInventory inventory) throws InvalidBookingException {
        if (guestName == null || guestName.isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }
        if (!inventory.isValidRoom(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }
    }
}

// Dummy class to satisfy the main method requirement
class BookingRequestQueue {}

public class BookMyStay {
    public static void main(String[] args) {
        System.out.println("Booking Validation");
        Scanner scanner = new Scanner(System.in);

        try {
            RoomInventory inventory = new RoomInventory();
            ReservationValidator validator = new ReservationValidator();
            // BookingRequestQueue is instantiated in your snippet
            BookingRequestQueue bookingQueue = new BookingRequestQueue();

            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();
            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            validator.validate(guestName, roomType, inventory);
            System.out.println("Booking successful!");

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}