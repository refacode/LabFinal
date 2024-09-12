import java.util.Date;

public class Appointment {
    private Doctor doctor;
    private Patient patient;
    private Date date;
    private String time;
    private String status;

    // Constructor
    public Appointment(Doctor doctor, Patient patient, Date date, String time, String status) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    // Getters and Setters
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
