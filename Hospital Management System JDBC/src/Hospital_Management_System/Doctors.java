package Hospital_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Doctors {
	private Connection connection;

	public Doctors(Connection connection) {
		this.connection = connection;
	}

	public void viewDoctorss() {
		String getDoctorDetails = "select * from doctors";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(getDoctorDetails);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("Doctors: ");
			System.out.println("+------------+------------------+--------------------+");
			System.out.println("| Doctor Id  | Name             |  specialization    |");
			System.out.println("+------------+------------------+--------------------+");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String  specialization = resultSet.getString("specialization");
				System.out.printf("| %-10s | %-16s | %-18s |\n", id, name,  specialization);
				System.out.println("+------------+------------------+--------------------+");

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean getDoctorById(int id) {
		String sql = "select * from doctors where id=?";
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
