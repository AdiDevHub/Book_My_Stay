import java.util.*;

/**
 * ============================================================================
 * MAIN CLASS - UseCase7AddOnServiceSelection
 * ============================================================================
 * Use Case 7: Add-On Service Selection
 * * Description:
 * This class demonstrates how optional services can be attached to a confirmed
 * booking. Services are added after room allocation and do not affect inventory.
 * * @version 7.0
 */

public class BookMyStay {

    static class AddOnService {
        private String serviceName;
        private double cost;

        public AddOnService(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }

        public String getServiceName() { return serviceName; }
        public double getCost() { return cost; }
    }

    /**
     * Inner class that manages optional services associated with reservations.
     */
    static class AddOnServiceManager {
        private Map<String, List<AddOnService>> servicesByReservation;

        public AddOnServiceManager() {
            servicesByReservation = new HashMap<>();
        }

        public void addService(String reservationId, AddOnService service) {
            servicesByReservation.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
        }

        public double calculateTotalServiceCost(String reservationId) {
            List<AddOnService> services = servicesByReservation.get(reservationId);
            if (services == null) return 0.0;
            return services.stream().mapToDouble(AddOnService::getCost).sum();
        }
    }

    /**
     * Application entry point.
     */
    public static void main(String[] args) {
        AddOnServiceManager manager = new AddOnServiceManager();
        String reservationId = "Single-1";

        // Adding services to match your required output of 1500.0
        manager.addService(reservationId, new AddOnService("Breakfast", 500.0));
        manager.addService(reservationId, new AddOnService("Spa", 1000.0));

        System.out.println("Add-On Service Selection");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + manager.calculateTotalServiceCost(reservationId));
    }
}
