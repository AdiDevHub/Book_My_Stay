import java.util.*;

// Mock classes to support the concurrency logic
class Reservation {
    String guestName;
    String roomType;
    Reservation(String n, String t) { this.guestName = n; this.roomType = t; }
}

class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();
    public void add(Reservation r) { queue.add(r); }
    public boolean isEmpty() { return queue.isEmpty(); }
    public Reservation poll() { return queue.poll(); }
}

class RoomInventory {
    int single = 5, doubleR = 3, suite = 2;
}

class RoomAllocationService {
    public void allocateRoom(Reservation r, RoomInventory inv) {
        System.out.println("Booking confirmed for Guest: " + r.guestName + ", Room ID: " + r.roomType + "-" + (r.roomType.equals("Single")?inv.single--:inv.doubleR--));
    }
}

class ConcurrentBookingProcessor implements Runnable {
    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(BookingRequestQueue bq, RoomInventory inv, RoomAllocationService as) {
        this.bookingQueue = bq;
        this.inventory = inv;
        this.allocationService = as;
    }

    @Override
    public void run() {
        while (true) {
            Reservation reservation;
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) break;
                reservation = bookingQueue.poll();
            }
            if (reservation != null) {
                synchronized (inventory) {
                    allocationService.allocateRoom(reservation, inventory);
                }
            }
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        BookingRequestQueue queue = new BookingRequestQueue();
        queue.add(new Reservation("Abhi", "Single"));
        queue.add(new Reservation("Vanmathi", "Double"));
        queue.add(new Reservation("Kural", "Suite"));
        queue.add(new Reservation("Subha", "Single"));

        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        Thread t1 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));

        t1.start(); t2.start();

        try { t1.join(); t2.join(); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println("\nRemaining Inventory:\nSingle: " + inventory.single + "\nDouble: " + inventory.doubleR + "\nSuite: " + inventory.suite);
    }
}