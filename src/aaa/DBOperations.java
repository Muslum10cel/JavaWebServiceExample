/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aaa;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author muslumoncel
 */
public class DBOperations {

	private static Connection connection = null;
	private static ResultSet resultSet = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private final String DEPARTMENT = "Department";
	private final String LONGITUDE = "Longitude";
	private final String LATITUDE = "Latitude";

	protected static void openConnection() {
		try {
			try {
				Class.forName(Initialize.CLASS_NAME).newInstance();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
				Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, ex);
			}
			connection = (Connection) DriverManager.getConnection(Initialize.DB_URL, Initialize.USERNAME, Initialize.PASSWORD);
		} catch (SQLException ex) {
			Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public synchronized List<String> getAllDepartments() {
		List<String> departments = new ArrayList<>();
		try {
			openConnection();
			statement = (Statement) connection.createStatement();
			resultSet = statement.executeQuery(Queries.getAllDepartments);
			if (resultSet != null) {
				while (resultSet.next()) {
					String dprtmnt = resultSet.getString(DBTablesAndColumns.COLUMN_DEPARTMENT_CODE);
					departments.add(dprtmnt);
				}
			}

		} catch (SQLException ex) {
			Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			closeEverything();
		}
		return departments;
	}

	public synchronized JSONObject getCoordinates(String dept_code) {
		JSONObject jSONObject = new JSONObject();
		openConnection();
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(Queries.getInfos);
			preparedStatement.setString(1, dept_code);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				while (resultSet.next()) {
					jSONObject.put(DEPARTMENT, resultSet.getString(DBTablesAndColumns.COLUMN_DEPARTMENT_NAME));
					jSONObject.put(LONGITUDE, resultSet.getDouble(DBTablesAndColumns.COLUMN_DEPARTMENT_LONGITUDE));
					jSONObject.put(LATITUDE, resultSet.getDouble(DBTablesAndColumns.COLUM_DEPARTMENT_LATITUDE));
				}
			}
		} catch (JSONException | SQLException ex) {
			Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, ex);
		}
		return jSONObject;
	}

	private static void closeEverything() {
		try {
			connection.close();
			if (statement != null) {
				statement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException ex) {
			Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
