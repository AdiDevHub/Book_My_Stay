import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner; // Import the Scanner class

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

public class BookMyStay {
    public static void main(String[] args) {
        // Initialize Scanner to read from standard input
        Scanner scanner = new Scanner(System.in);
        Queue<Reservation> queue = new LinkedList<>();

        System.out.println("Enter number of bookings to add:");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < n; i++) {
            System.out.println("Enter Guest Name:");
            String name = scanner.nextLine();

            System.out.println("Enter Room Type (Single/Double/Suite):");
            String type = scanner.nextLine();

            queue.offer(new Reservation(name, type));
        }

        System.out.println("\nProcessing Bookings:");
        while (!queue.isEmpty()) {
            Reservation r = queue.poll();
            System.out.println("Guest: " + r.getGuestName() + " | Room: " + r.getRoomType());
        }

        scanner.close(); // Always close the scanner
    }
}