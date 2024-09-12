import java.util.List;

public abstract class Doctor {
    private String name;
    private String degree;
    private List<String> availableDays;
    private int patientsServedToday; // Track number of patients served today

    // Constructor
    public Doctor(String name, String degree, List<String> availableDays) {
        this.name = name;
        this.degree = degree;
        this.availableDays = availableDays;
        this.patientsServedToday = 0; // Initialize to 0
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public List<String> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(List<String> availableDays) {
        this.availableDays = availableDays;
    }

    public int getPatientsServedToday() {
        return patientsServedToday;
    }

    public void setPatientsServedToday(int patientsServedToday) {
        this.patientsServedToday = patientsServedToday;
    }

    // Abstract method to be implemented by subclasses
    public abstract String getSpecialization();

    // Method to serve a patient
    public void servePatient(Patient patient) {
        // Increment the count of patients served today
        patientsServedToday++;
    }

    // Method to check if the doctor can serve another patient today
    public boolean canServeMorePatients() {
        return patientsServedToday < 2; // Example limit of 2 patients per day
    }

    // Method to display availability (to be overridden by subclasses)
    public abstract void displayAvailability();
}
