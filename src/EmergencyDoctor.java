import java.util.List;

public class EmergencyDoctor extends Doctor {

    public EmergencyDoctor(String name, String degree, List<String> availableDays) {
        super(name, degree, availableDays);
    }

    @Override
    public String getSpecialization() {
        return "Emergency Doctor";
    }

    @Override
    public void displayAvailability() {
        System.out.println("Available days: " + String.join(", ", getAvailableDays()));
    }
}
