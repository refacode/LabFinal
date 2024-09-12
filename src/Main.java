import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static List<Doctor> doctors = new ArrayList<>();
    private static List<Patient> patients = new ArrayList<>();
    private static List<Appointment> appointments = new ArrayList<>();
    private static FileWork fileWork = new FileWork();

    public static void main(String[] args) {
        try {
            loadData(); // Load initial data
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("Failed to load data. Exiting...");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to the Doctor's Appointment Management System");
            System.out.println("1. View Doctors");
            System.out.println("2. View Patients");
            System.out.println("3. View Appointments");
            System.out.println("4. Add Doctor");
            System.out.println("5. Add Patient");
            System.out.println("6. Book Appointment");
            System.out.println("7. Serve Patient");
            System.out.println("8. Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    viewDoctors();
                    break;
                case 2:
                    viewPatients();
                    break;
                case 3:
                    viewAppointments();
                    break;
                case 4:
                    addDoctor(scanner);
                    break;
                case 5:
                    addPatient(scanner);
                    break;
                case 6:
                    bookAppointment(scanner);
                    break;
                case 7:
                    servePatient(scanner);
                    break;
                case 8:
                    try {
                        fileWork.saveDoctorsToFile(doctors);
                        fileWork.savePatientsToFile(patients);
                        fileWork.saveAppointmentsToFile(appointments);
                        saveServedPatientsToFile(); // Save served patients
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Failed to save data.");
                    }
                    System.out.println("Exiting... Data saved.");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void loadData() throws IOException, ParseException {
        doctors = fileWork.loadDoctorsFromFile();
        patients = fileWork.loadPatientsFromFile();
        appointments = fileWork.loadAppointmentsFromFile(doctors, patients);
        addDefaultDoctors(); // Add default doctors if not present
    }

    private static void addDefaultDoctors() {
        if (doctors.isEmpty()) {
            List<String> orthoDays = Arrays.asList("Monday", "Wednesday", "Friday");
            List<String> emergencyDays = Arrays.asList("Tuesday", "Thursday", "Saturday");

            Doctor refa = new SpecialistOrthopedic("Refa", "MD", orthoDays);
            Doctor ratri = new EmergencyDoctor("Ratri", "MD", emergencyDays);

            doctors.add(refa);
            doctors.add(ratri);
        }
    }

    private static void viewDoctors() {
        if (doctors.isEmpty()) {
            System.out.println("No doctors available.");
        } else {
            System.out.println("Doctors List:");
            for (int i = 0; i < doctors.size(); i++) {
                Doctor doctor = doctors.get(i);
                System.out.println((i + 1) + ". Name: " + doctor.getName() +
                                   ", Degree: " + doctor.getDegree() +
                                   ", Specialization: " + doctor.getSpecialization() +
                                   ", Available Days: " + String.join(", ", doctor.getAvailableDays()));
            }
        }
    }

    private static void viewPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients available.");
        } else {
            System.out.println("Patients List:");
            for (int i = 0; i < patients.size(); i++) {
                Patient patient = patients.get(i);
                System.out.println((i + 1) + ". Name: " + patient.getName() +
                                   ", Age: " + patient.getAge() +
                                   ", Contact Info: " + patient.getContactInfo() +
                                   ", Medical History: " + patient.getMedicalHistory());
            }
        }
    }

    private static void viewAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments available.");
        } else {
            System.out.println("Appointments List:");
            for (Appointment appointment : appointments) {
                System.out.println("Doctor: " + appointment.getDoctor().getName() +
                                   ", Patient: " + appointment.getPatient().getName() +
                                   ", Date: " + appointment.getDate() +
                                   ", Time: " + appointment.getTime() +
                                   ", Status: " + appointment.getStatus());
            }
        }
    }

    private static void addDoctor(Scanner scanner) {
        System.out.print("Enter doctor's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter doctor's degree: ");
        String degree = scanner.nextLine();
        System.out.print("Enter available days (comma separated): ");
        List<String> availableDays = Arrays.asList(scanner.nextLine().split(",\\s*"));

        System.out.println("Select specialization:");
        System.out.println("1. Specialist Orthopedic");
        System.out.println("2. Emergency Doctor");
        int type = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Doctor doctor = null;
        if (type == 1) {
            doctor = new SpecialistOrthopedic(name, degree, availableDays);
        } else if (type == 2) {
            doctor = new EmergencyDoctor(name, degree, availableDays);
        } else {
            System.out.println("Invalid specialization.");
            return;
        }

        doctors.add(doctor);
        System.out.println("Doctor added successfully.");
    }

    private static void addPatient(Scanner scanner) {
        System.out.print("Enter patient's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter patient's age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter patient's contact info: ");
        String contactInfo = scanner.nextLine();
        System.out.print("Enter patient's medical history: ");
        String medicalHistory = scanner.nextLine();

        Patient patient = new Patient(name, age, contactInfo, medicalHistory);
        patients.add(patient);
        System.out.println("Patient added successfully.");
    }

    private static void bookAppointment(Scanner scanner) {
        System.out.println("Select a doctor:");
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println((i + 1) + ". " + doctors.get(i).getName());
        }
        int doctorIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline

        if (doctorIndex < 0 || doctorIndex >= doctors.size()) {
            System.out.println("Invalid doctor selection.");
            return;
        }

        System.out.print("Select patient by number: ");
        for (int i = 0; i < patients.size(); i++) {
            System.out.println((i + 1) + ". " + patients.get(i).getName());
        }
        int patientIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline

        if (patientIndex < 0 || patientIndex >= patients.size()) {
            System.out.println("Invalid patient selection.");
            return;
        }

        Patient patient = patients.get(patientIndex);
        Doctor doctor = doctors.get(doctorIndex);

        if (!doctor.canServeMorePatients()) {
            System.out.println("Doctor has reached the limit of patients for today.");
            return;
        }

        System.out.print("Enter appointment date (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();
        System.out.print("Enter appointment time (HH:mm): ");
        String timeStr = scanner.nextLine();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(dateStr);

            Appointment appointment = new Appointment(doctor, patient, date, timeStr, "Scheduled");
            appointments.add(appointment);
            doctor.servePatient(patient); // Update doctor with patient served
            System.out.println("Appointment booked successfully.");
        } catch (ParseException e) {
            System.out.println("Invalid date format.");
        }
    }

    private static void servePatient(Scanner scanner) {
        System.out.println("Select patient by number to serve:");
        for (int i = 0; i < patients.size(); i++) {
            System.out.println((i + 1) + ". " + patients.get(i).getName());
        }
        int patientIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline

        if (patientIndex < 0 || patientIndex >= patients.size()) {
            System.out.println("Invalid patient selection.");
            return;
        }

        Patient patient = patients.get(patientIndex);
        boolean served = false;

        for (Iterator<Appointment> it = appointments.iterator(); it.hasNext(); ) {
            Appointment appointment = it.next();
            if (appointment.getPatient().equals(patient) && "Scheduled".equals(appointment.getStatus())) {
                Doctor doctor = appointment.getDoctor();
                if (doctor.canServeMorePatients()) {
                    appointment.setStatus("Served");
                    doctor.servePatient(patient);
                    it.remove(); // Remove appointment from list
                    served = true;
                    break;
                } else {
                    System.out.println("Doctor cannot serve more patients today.");
                }
            }
        }

        if (served) {
            patients.remove(patient); // Remove patient from list
            saveServedPatient(patient); // Save served patient to file
            System.out.println("Patient served and removed from the list.");
        } else {
            System.out.println("No appointment found for the patient or already served.");
        }
    }

    private static void saveServedPatient(Patient patient) {
        try {
            fileWork.saveServedPatientToFile(patient);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save served patient.");
        }
    }

    private static void saveServedPatientsToFile() throws IOException {
        for (Patient patient : patients) {
            fileWork.saveServedPatientToFile(patient);
        }
    }
}
