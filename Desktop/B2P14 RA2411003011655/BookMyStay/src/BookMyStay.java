import java.util.*;
import java.util.stream.Collectors;

// Represents a booking for analytics
class Booking {
    String roomType;
    double price;
    Booking(String type, double price) { this.roomType = type; this.price = price; }
}

class AnalyticsService {
    public void generateReport(List<Booking> bookings) {
        System.out.println("Booking Statistics & Analytics");

        // Count bookings per type
        Map<String, Long> counts = bookings.stream()
                .collect(Collectors.groupingBy(b -> b.roomType, Collectors.counting()));

        // Calculate total revenue
        double totalRevenue = bookings.stream().mapToDouble(b -> b.price).sum();

        counts.forEach((type, count) ->
                System.out.println("Room Type: " + type + " | Count: " + count));
        System.out.println("Total Revenue: " + totalRevenue);
    }
}

public class BookMyStay {
    public static void main(String[] args) {
        List<Booking> bookings = Arrays.asList(
                new Booking("Single", 1200.0),
                new Booking("Double", 2000.0),
                new Booking("Single", 1200.0),
                new Booking("Suite", 5000.0)
        );

        new AnalyticsService().generateReport(bookings);
    }
}