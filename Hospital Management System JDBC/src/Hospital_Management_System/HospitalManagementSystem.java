package Hospital_Management_System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.zip.Inflater;

public class HospitalManagementSystem {

	private static final String url = "jdbc:mysql://localhost:3306/hospital";
	private static final String username = "root";
	private static final String password = "1234";

	public static void main(String[] args) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			System.out.println("Connection Established");

			Scanner scanner = new Scanner(System.in);
			Patient patient = new Patient(connection, scanner);
			Doctors doctors = new Doctors(connection);

			while (true) {
				System.out.println("HOSTPITAL MANAGEMENT SYSTEM");
				System.out.println("1. Add Patient");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.print("Enter You Choice: ");
				int choice = scanner.nextInt();
				switch (choice) {
				case 1: {

					patient.addPatient();
					break;
				}
				case 2: {
					patient.viewPatients();
					break;
				}
				case 3: {
					doctors.viewDoctorss();
					break;
				}
				case 4: {
					break;
				}
				case 5: {
					System.out.println("Thank You !!!");
					return;
				}
				default:
					System.out.println("Invalid Choice");
				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void bookAppointment(Patient patient, Doctors doctors, Connection connection, Scanner scanner)
			throws Exception {
		System.out.print("Enter Patient Id: ");
		int patientid = scanner.nextInt();
		System.out.print("Enter Doctor Id: ");
		int doctorid = scanner.nextInt();
		System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
		String date = scanner.nextLine();

		if (patient.getPatientById(patientid) && doctors.getDoctorById(doctorid)) {
			if (checkDoctorAvailibity(doctorid, date, connection)) {
				String appointmentQuery = "insert into appointments(patient_id,doctor_id,appointment_date) values (?,?,?)";

				try {
					PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
					preparedStatement.setInt(1, patientid);
					preparedStatement.setInt(2, doctorid);
					preparedStatement.setString(3, date);
					int affectedrows = preparedStatement.executeUpdate();
					if (affectedrows > 0) {
						System.out.println("Appointment Booked");

					} else {
						System.out.println("Failed to book appointment");
					}

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} else {
				System.out.println("Doctor is not available on that day");
			}

		} else {
			System.out.println("Patient Or Doctors are not available");
		}

	}

	public static boolean checkDoctorAvailibity(int doctorid, String date, Connection connection) {
		String sqlString = "select count(*) from appointment where doctor_id=? and appointment_date=?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
			preparedStatement.setInt(1, doctorid);
			preparedStatement.setString(2, date);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int count = resultSet.getInt(1);
				if (count == 0) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;

	}
}
