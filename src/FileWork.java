import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileWork {

    // File paths (example paths, adjust as needed)
    private static final String DOCTOR_FILE = "doctor.txt";
    private static final String PATIENT_FILE = "patient.txt";
    private static final String APPOINTMENT_FILE = "appointment.txt";
    private static final String SERVED_PATIENT_FILE = "served_patients.txt";

    // Load doctors from file
    public List<Doctor> loadDoctorsFromFile() throws IOException, ParseException {
        List<Doctor> doctors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DOCTOR_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) continue;
                
                String name = parts[0];
                String degree = parts[1];
                String specialization = parts[2];
                List<String> availableDays = Arrays.asList(parts[3].split("\\s*,\\s*"));

                Doctor doctor;
                if (specialization.equalsIgnoreCase("Specialist Orthopedic")) {
                    doctor = new SpecialistOrthopedic(name, degree, availableDays);
                } else if (specialization.equalsIgnoreCase("Emergency Doctor")) {
                    doctor = new EmergencyDoctor(name, degree, availableDays);
                } else {
                    continue; // Unknown specialization
                }

                doctors.add(doctor);
            }
        }
        return doctors;
    }

    // Load patients from file
    public List<Patient> loadPatientsFromFile() throws IOException, ParseException {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PATIENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) continue;

                String name = parts[0];
                int age = Integer.parseInt(parts[1]);
                String contactInfo = parts[2];
                String medicalHistory = parts[3];

                Patient patient = new Patient(name, age, contactInfo, medicalHistory);
                patients.add(patient);
            }
        }
        return patients;
    }

    // Load appointments from file
    public List<Appointment> loadAppointmentsFromFile(List<Doctor> doctors, List<Patient> patients) throws IOException, ParseException {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(APPOINTMENT_FILE))) {
            String line;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                String doctorName = parts[0];
                String patientName = parts[1];
                Date date = dateFormat.parse(parts[2]);
                String time = parts[3];
                String status = parts[4];

                Doctor doctor = findDoctorByName(doctors, doctorName);
                Patient patient = findPatientByName(patients, patientName);

                if (doctor != null && patient != null) {
                    Appointment appointment = new Appointment(doctor, patient, date, time, status);
                    appointments.add(appointment);
                }
            }
        }
        return appointments;
    }

    // Find doctor by name
    private Doctor findDoctorByName(List<Doctor> doctors, String name) {
        for (Doctor doctor : doctors) {
            if (doctor.getName().equalsIgnoreCase(name)) {
                return doctor;
            }
        }
        return null;
    }

    // Find patient by name
    private Patient findPatientByName(List<Patient> patients, String name) {
        for (Patient patient : patients) {
            if (patient.getName().equalsIgnoreCase(name)) {
                return patient;
            }
        }
        return null;
    }

    // Save doctors to file
    public void saveDoctorsToFile(List<Doctor> doctors) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DOCTOR_FILE))) {
            for (Doctor doctor : doctors) {
                writer.write(doctor.getName() + "," + doctor.getDegree() + "," + doctor.getSpecialization() + "," + String.join(",", doctor.getAvailableDays()));
                writer.newLine();
            }
        }
    }

    // Save patients to file
    public void savePatientsToFile(List<Patient> patients) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATIENT_FILE))) {
            for (Patient patient : patients) {
                writer.write(patient.getName() + "," + patient.getAge() + "," + patient.getContactInfo() + "," + patient.getMedicalHistory());
                writer.newLine();
            }
        }
    }

    // Save appointments to file
    public void saveAppointmentsToFile(List<Appointment> appointments) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(APPOINTMENT_FILE))) {
            for (Appointment appointment : appointments) {
                writer.write(appointment.getDoctor().getName() + "," + appointment.getPatient().getName() + "," + new SimpleDateFormat("yyyy-MM-dd").format(appointment.getDate()) + "," + appointment.getTime() + "," + appointment.getStatus());
                writer.newLine();
            }
        }
    }

    // Save served patient to file
    public void saveServedPatientToFile(Patient patient) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SERVED_PATIENT_FILE, true))) {
            writer.write(patient.getName() + "," + patient.getAge() + "," + patient.getContactInfo() + "," + patient.getMedicalHistory());
            writer.newLine();
        }
    }
}
