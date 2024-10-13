package Hospital_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Patient {

	private Connection connection;
	private Scanner scanner;

	public Patient(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;
	}

	public void addPatient() {
		System.out.print("Enter Patient Name: ");
		String name = scanner.next();
		System.out.print("Enter Patient Age: ");
		int age = scanner.nextInt();
		System.out.print("Enter Patient Gender: ");
		String gender = scanner.next();

		try {
			String addpatient = "insert into patients(name,age,gender) values (?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(addpatient);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, age);
			preparedStatement.setString(3, gender);
			int affectedrows = preparedStatement.executeUpdate();

			if (affectedrows > 0) {
				System.out.println("Patient Added Successfully");

			} else {
				System.out.println("Failed to add Patient");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void viewPatients() {
		String getPatientDetails = "select * from patients";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(getPatientDetails);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("Patients: ");
			System.out.println("+------------+------------------+-----------+----------------+");
			System.out.println("| Patient Id | Name             | Age       | Gender         |");
			System.out.println("+------------+------------------+-----------+----------------+");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				String gender = resultSet.getString("gender");
				System.out.printf("| %-10s | %-17s | %-9s | %-14s |\n", id, name, age, gender);
				System.out.println("+------------+------------------+-----------+----------------+");

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean getPatientById(int id) {
		String sql = "select * from patients where id=?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

}
