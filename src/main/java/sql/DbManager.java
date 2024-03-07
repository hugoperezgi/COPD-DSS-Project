package sql;
import entities.*;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class DbManager {
    private static Connection c;
    public DbManager() throws Exception {
        start_db();
    }


    public void start_db() throws Exception{
        String url = "jdbc:sqlite:./db/medical_database.db";
        c = DriverManager.getConnection(url);
        Statement stmt = c.createStatement();

        // Create Patients table
        stmt.execute("CREATE TABLE IF NOT EXISTS Patients (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT NOT NULL," +
                "MedicalCardNumber INTEGER NOT NULL," +
                "BirthDate DATE NOT NULL," +
                "user_id INTEGER," +
                "FOREIGN KEY (user_id) REFERENCES Users(ID)" +
                ")");

        // Create Users table
        stmt.execute("CREATE TABLE IF NOT EXISTS Users (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Username TEXT NOT NULL," +
                "Password TEXT NOT NULL," +
                "Role TEXT NOT NULL" +
                ")");

        // Create MedicalHistory table
        stmt.execute("CREATE TABLE IF NOT EXISTS MedicalHistory (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Phenotype CHAR," +
                "Severity INTEGER," +
                "Treatment TEXT," +
                "BeginDate DATE," +
                "EndDate DATE," +
                "patient_id INTEGER NOT NULL," +
                "FOREIGN KEY (patient_id) REFERENCES Patients(ID)" +
                ")");
    }

    public void insertPatient(String name, int medicalCardNumber, Date birthdate, int userId) throws Exception {
        String query = "INSERT INTO Patients (Name, medicalCardNumber, birthdate, user_id) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setInt(2, medicalCardNumber);
        pstmt.setDate(3, birthdate);
        pstmt.setInt(4, userId);
        pstmt.executeUpdate();

    }

    public void deletePatient(int patientId) throws Exception {
        String query = "DELETE FROM Patients WHERE ID = ?";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setInt(1, patientId);
        pstmt.executeUpdate();
    }

    public List<Patient> getAllPatients() throws Exception {
        List<Patient> patients = new ArrayList();
        String query = "SELECT * FROM Patients";
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            int id = rs.getInt("ID");
            String name = rs.getString("Name");
            int medicalCardNumber = rs.getInt("MedicalCardNumber");
            Date birthDate = rs.getDate("BirthDate");
            int userId = rs.getInt("User_ID");

            Patient patient = new Patient(id, name, medicalCardNumber, birthDate, userId);
            patients.add(patient);
        }

        return patients;
    }

    public ResultSet getMedicalHistory(int patientId) throws Exception {
        //cambiar a medical record cuando este creada la clase
        String query = "SELECT * FROM MedicalHistory WHERE patient_id = ?";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setInt(1, patientId);
        return pstmt.executeQuery();
    }

    public void createUser(String username, String password, String role) throws Exception {
        if (!role.equalsIgnoreCase("admin") && !role.equalsIgnoreCase("doctor") && !role.equalsIgnoreCase("patient")) {
            throw new IllegalArgumentException("Invalid role, the valid options for roles are: admin, doctor or patient");
        }
        String query = "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        pstmt.setString(3, role);
        pstmt.executeUpdate();
    }

    public void deleteUser(int userId) throws Exception {
        String query = "DELETE FROM Users WHERE ID = ?";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setInt(1, userId);
        pstmt.executeUpdate();
    }


    public void createMedicalRecord(int patientId, String phenotype, int severity, String treatment, Date beginDate, Date endDate) throws Exception {
        if (severity < 1 || severity > 4) {
            throw new IllegalArgumentException("Invalid severity degree, it should be a number between 1 and 4");
        }
        if (!phenotype.matches("[A-D]|Unclear")) {
            throw new IllegalArgumentException("Invalid phenotype, the values allowed are: A, B, C, D or Unclear");
        }
        String query = "INSERT INTO MedicalHistory (phenotype, severity, treatment, beginDate, endDate, patient_id) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setString(1, phenotype);
        pstmt.setInt(2, severity);
        pstmt.setString(3, treatment);
        pstmt.setDate(4, beginDate);
        pstmt.setDate(5, endDate);
        pstmt.setInt(6, patientId);
        pstmt.executeUpdate();
        pstmt.close();
    }


    public void createMedicalRecord_OnlyPhenotype(int patientId, String phenotype) throws Exception {
        if (!phenotype.matches("[A-D]|Unclear")) {
            throw new IllegalArgumentException("Invalid phenotype, the values allowed are: A, B, C, D or Unclear");
        }
        String query = "INSERT INTO MedicalHistory (phenotype, patient_id) VALUES (?, ?)";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setString(1, phenotype);
        pstmt.setInt(2, patientId);
        pstmt.executeUpdate();
        pstmt.close();
    }


    public void updateSeverity(int recordId, int severity) throws SQLException {
        if (severity < 1 || severity > 4) {
            throw new IllegalArgumentException("Invalid severity degree, it should be a number between 1 and 4");
        }
        String query = "UPDATE MedicalHistory SET severity = ? WHERE ID = ?";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setInt(1, severity);
        pstmt.setInt(2, recordId);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public void updateTreatmentAndDates(int recordId, String treatment, Date beginDate, Date endDate) throws Exception {
        String query = "UPDATE MedicalHistory SET treatment = ?, beginDate = ?, endDate = ? WHERE ID = ?";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setString(1, treatment);
        pstmt.setDate(2, beginDate);
        pstmt.setDate(3, endDate);
        pstmt.setInt(4, recordId);
        pstmt.executeUpdate();
        pstmt.close();
    }


    public void close_db() throws Exception {
        if (c != null) {
            c.close();
        }
    }
}