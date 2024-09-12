import java.util.List;

public class SpecialistOrthopedic extends Doctor {

    public SpecialistOrthopedic(String name, String degree, List<String> availableDays) {
        super(name, degree, availableDays);
    }

    @Override
    public String getSpecialization() {
        return "Orthopedic Specialist";
    }

    @Override
    public void displayAvailability() {
        System.out.println("Available days: " + String.join(", ", getAvailableDays()));
    }
}
