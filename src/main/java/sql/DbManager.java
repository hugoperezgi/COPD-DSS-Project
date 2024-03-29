package sql;
import entities.*;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class DbManager {
    private static Connection c;


    public static void start_db()throws Exception{

        if(!new File("./db").mkdirs()){            
            String url = "jdbc:sqlite:./db/medical_database.db";
            c = DriverManager.getConnection(url);  
            c.createStatement().execute("PRAGMA foreign_keys=ON");  
            return;
        }
        
        String url = "jdbc:sqlite:./db/medical_database.db";
        c = DriverManager.getConnection(url);
        c.createStatement().execute("PRAGMA foreign_keys=ON");
        Statement stmt = c.createStatement();

        stmt.execute("CREATE TABLE IF NOT EXISTS Users (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Username TEXT NOT NULL," +
                "Password TEXT NOT NULL," +
                "Role TEXT NOT NULL" +
                ")");
        // Create Patients table
        stmt.execute("CREATE TABLE IF NOT EXISTS Patients (" +
                "patient_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT NOT NULL," +
                "MedicalCardNumber INTEGER NOT NULL," +
                "BirthDate DATE NOT NULL," +
                "user_id INTEGER REFERENCES Users(user_id) ON UPDATE CASCADE ON DELETE CASCADE" +
                ")");

        // Create Users table

        // Create MedicalHistory table
        stmt.execute("CREATE TABLE IF NOT EXISTS MedicalHistory (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Phenotype CHAR," +
                "Severity INTEGER," +
                "Treatment TEXT," +
                "BeginDate DATE," +
                "Duration INTEGER," +
                "patient_id INTEGER REFERENCES Patients(patient_id) ON UPDATE CASCADE ON DELETE CASCADE" +
                ")");

        createUser("a",User.encryptPassword("a"),"admin");
        createPatient("p",0,Date.valueOf(LocalDate.now()),createUser("p",User.encryptPassword("p"),"patient"));
        createUser("d",User.encryptPassword("d"),"doctor");
    }

    public static void createPatient(String name, int medicalCardNumber, Date birthdate, int userId) throws Exception {
        String query = "INSERT INTO Patients (Name, medicalCardNumber, birthdate, user_id) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setInt(2, medicalCardNumber);
        pstmt.setDate(3, birthdate);
        pstmt.setInt(4, userId);
        pstmt.executeUpdate();
    }

    public static void deletePatient(int patientId) throws Exception {
        String query = "DELETE FROM Patients WHERE patient_id = ?";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setInt(1, patientId);
        pstmt.executeUpdate();
    }

    public static List<Patient> getAllPatients() throws Exception {
        List<Patient> patients = new ArrayList<Patient>();
        String query = "SELECT * FROM Patients";
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            int id = rs.getInt("patient_id");
            String name = rs.getString("Name");
            int medicalCardNumber = rs.getInt("MedicalCardNumber");
            Date birthDate = rs.getDate("BirthDate");
            int userId = rs.getInt("User_ID");
            Patient patient = new Patient(id, name, medicalCardNumber, birthDate, userId);
            patients.add(patient);
        }

        return patients;
    }

    public static Patient getPatientFromUserID(Integer userID) throws Exception {
        Patient patient;
        String query = "SELECT * FROM Patients WHERE user_id = ?";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setInt(1, userID);
        ResultSet rs = pstmt.executeQuery();
        int id = rs.getInt("patient_id");
        String name = rs.getString("Name");
        int medicalCardNumber = rs.getInt("MedicalCardNumber");
        Date birthDate = rs.getDate("BirthDate");
        patient = new Patient(id,name,medicalCardNumber,birthDate,userID);
        return patient;
    }



    public static int createUser(String username, String password, String role) throws Exception {
        if (!role.equalsIgnoreCase("admin") && !role.equalsIgnoreCase("doctor") && !role.equalsIgnoreCase("patient")) {
            throw new IllegalArgumentException("Invalid role, the valid options for roles are: admin, doctor or patient");
        }
        String query = "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        pstmt.setString(3, role);
        pstmt.executeUpdate();
        String query2 = "SELECT user_id FROM Users WHERE username = ? AND password = ?";
        PreparedStatement pstmt2 = c.prepareStatement(query2);
        pstmt2.setString(1, username);
        pstmt2.setString(2, password);
        ResultSet rs = pstmt2.executeQuery();
        return rs.getInt("user_id");
    }

    public static List<User> getAllUsers() throws Exception {
        List<User> users = new ArrayList<User>();
        String query = "SELECT * FROM Users";
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            int id = rs.getInt("user_id");
            String username = rs.getString("Username");
            // String password = rs.getString("Password");
            String role = rs.getString("Role");
            User user = new User(id,username,null,role);
            users.add(user);
        }
        return users;
    }

    public static User check_user (String username, String password) throws Exception {
        String str = "SELECT * FROM Users WHERE username = ? AND password = ?";
		PreparedStatement p = c.prepareStatement(str);
		p.setString(1, username);
		p.setString(2, password);
		ResultSet rs = p.executeQuery();
		User u = null;
		if(rs.next()){
			u= new User(rs.getInt("user_id"),rs.getString("Username"),null,rs.getString("Role"));
		}
		p.close();
		rs.close();
		return u;
    }

    public static void deleteUser(int userId) throws Exception {
        String query = "DELETE FROM Users WHERE user_id = ?";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setInt(1, userId);
        pstmt.executeUpdate();

    }


    public static void createMedicalRecord(int patientId, String phenotype, int severity, String treatment, Date beginDate, int duration) throws Exception {
        String query = "INSERT INTO MedicalHistory (Phenotype, Severity, Treatment, BeginDate, Duration, patient_id) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setString(1, phenotype);
        pstmt.setInt(2, severity);
        pstmt.setString(3, treatment);
        pstmt.setDate(4, beginDate);
        pstmt.setInt(5, duration);
        pstmt.setInt(6, patientId);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public static List<MedicalHistory> getMedicalHistory(int patientId) throws Exception {
        List <MedicalHistory> medicalHistory = new ArrayList<>();
        String query = "SELECT * FROM MedicalHistory WHERE patient_id = ?";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setInt(1, patientId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("ID");
            String phenotype = rs.getString("Phenotype");
            int severity = rs.getInt("Severity");
            String treatment = rs.getString("Treatment");
            Date beginDate = rs.getDate("BeginDate");
            int duration = rs.getInt("duration");
            medicalHistory.add(new MedicalHistory(id,phenotype,severity,treatment,beginDate,duration));
        }
        return medicalHistory;
    }


    public static void createMedicalRecord_OnlyPhenotype(int patientId, String phenotype) throws Exception {
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


    public static void updateSeverity(int recordId, int severity) throws SQLException {
        String query = "UPDATE MedicalHistory SET severity = ? WHERE ID = ?";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setInt(1, severity);
        pstmt.setInt(2, recordId);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public static void updateTreatmentAndDates(int recordId, String treatment, Date beginDate, Integer duration) throws Exception {
        String query = "UPDATE MedicalHistory SET treatment = ?, beginDate = ?, Duration = ? WHERE ID = ?";
        PreparedStatement pstmt = c.prepareStatement(query);
        pstmt.setString(1, treatment);
        pstmt.setDate(2, beginDate);
        pstmt.setInt(3, duration);
        pstmt.setInt(4, recordId);
        pstmt.executeUpdate();
        pstmt.close();
    }


    public static void close_db() throws Exception {
        if (c != null) {
            c.close();
        }
    }
}