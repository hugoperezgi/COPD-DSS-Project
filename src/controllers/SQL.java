package controllers;

import java.security.spec.KeySpec;
import java.sql.*;
import java.time.LocalDate;
import entities.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SQL {
    private static Connection c;

	public void connect() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:./db/data.db");
		c.createStatement().execute("PRAGMA foreign_keys=ON");
	}

    public void disconnect() throws SQLException{
        c.close();
    }

    public void setupDb() throws SQLException{
        //TODO Update this so it makes sense for this project :)
        Statement s = c.createStatement();                      
        String str = "CREATE TABLE users " 
                    + "(userId INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " username TEXT NOT NULL unique,"
                    + " password VARBINARY(32) NOT NULL," //psw hashcode			   
                    + " role INTEGER NOT NULL)"; //0:Admin 1:medstaff 2:Patient		   
        s.executeUpdate(str);                                       
        s.close();        

        Statement s1 = c.createStatement();   
        String str1 = "create TABLE patients "
                    + "(patientID INTEGER  PRIMARY KEY AUTOINCREMENT," 
                    + " name TEXT NOT NULL, "
                    + " surname TEXT NOT NULL, "
                    + " gender TEXT NOT NULL, "
                    + " birthdate DATE NOT NULL, "
                    + " blood_type TEXT NOT NULL, "
                    + " userId INTEGER REFERENCES users(userId) ON UPDATE CASCADE ON DELETE CASCADE)";
        s1.executeUpdate(str1);                                       
        s1.close();       

        Statement s2 = c.createStatement();                      
        String str2 = "CREATE TABLE workers "
                    + "(workerId INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " workerName TEXT NOT NULL, "
                    + " workerSurname TEXT NOT NULL, "
                    + " userId INTEGER REFERENCES users(userId) ON UPDATE CASCADE ON DELETE CASCADE)";
        s2.executeUpdate(str2);                                       
        s2.close();   

        Statement s3 = c.createStatement();                      
        String str3 = "CREATE TABLE doctor_patient "
                    + "(patient_id INTEGER REFERENCES patients(patientID) ON UPDATE CASCADE ON DELETE CASCADE,"
                    + " doctor_id INTEGER REFERENCES workers(workerId) ON UPDATE CASCADE ON DELETE CASCADE,"
                    + " PRIMARY KEY (patient_id,doctor_id))";
        s3.executeUpdate(str3);                                       
        s3.close();  

        Statement s4 = c.createStatement();                      
        String str4 = "CREATE TABLE medical_tests "
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " patient_id INTEGER REFERENCES patients(patientID) ON UPDATE CASCADE ON DELETE CASCADE,"
                    + " signs TEXT NOT NULL,"	//Signs and Symptoms			   
                    + " doct_comments TEXT,"	//Doctor edits
                    + " date DATE NOT NULL,"				   
                    + " symptoms BYTE NOT NULL,"				   
                    + " param0 LONGTEXT,"				   
                    + " param1 LONGTEXT,"				   
                    + " param2 LONGTEXT,"				   
                    + " param3 LONGTEXT,"				   
                    + " param4 LONGTEXT,"				   
                    + " param5 LONGTEXT)";		//String with the params (Up to 4 294 967 295 characters) -> Maybe needs change
        s4.executeUpdate(str4);                                       
        s4.close();        

		User p=null,w=null;

		try {
			this.addUser(new User("admin", hashPassword("admin"), 0));
		} catch (Exception e) {
			System.out.println("Something went wrong while creating the admin");
			e.printStackTrace();
		}
		try {
			this.addUser(new User("doctor", hashPassword("doctor"), 1));
			 w=this.checkPassword("doctor", hashPassword("doctor"));
			// this.addWorker(new Worker(w.getUserID(), "name", "surname"));
		} catch (Exception e) {
			System.out.println("Something went wrong while creating the doctor");
			e.printStackTrace();
		}
		try {
			this.addUser(new User("patient", hashPassword("patient"), 2));
			 p=this.checkPassword("patient", hashPassword("patient"));
			// this.addPatient(new Patient(p.getUserID(), 0, "name1", "surname1", "0-", "Female", java.sql.Date.valueOf(LocalDate.now())));
		} catch (Exception e) {
			System.out.println("Something went wrong while creating the patient");
			e.printStackTrace();
		}
		try {
			// this.createLinkDoctorPatient(this.selectPatientByUserId(p.getUserID()).getPatientID(), this.selectWorkerByUserId(w.getUserID()).getWorkerID());
		} catch (Exception e) {
			System.out.println("Something went wrong while creating the link");
			e.printStackTrace();
		}

		try {
			this.addUser(new User("a", hashPassword("a"), 0));
		} catch (Exception e) {
			System.out.println("Something went wrong while creating the admin");
			e.printStackTrace();
		}
		try {
			this.addUser(new User("d", hashPassword("d"), 1));
			 w=this.checkPassword("d", hashPassword("d"));
			// this.addWorker(new Worker(w.getUserID(), "name", "surname"));
		} catch (Exception e) {
			System.out.println("Something went wrong while creating the doctor");
			e.printStackTrace();
		}
		try {
			this.addUser(new User("p", hashPassword("p"), 2));
			 p=this.checkPassword("p", hashPassword("p"));
			// this.addPatient(new Patient(p.getUserID(), 0, "name2", "surname2", "0-", "Male", java.sql.Date.valueOf(LocalDate.now())));
		} catch (Exception e) {
			System.out.println("Something went wrong while creating the patient");
			e.printStackTrace();
		}
		try {
			// this.createLinkDoctorPatient(this.selectPatientByUserId(p.getUserID()).getPatientID(), this.selectWorkerByUserId(w.getUserID()).getWorkerID());
		} catch (Exception e) {
			System.out.println("Something went wrong while creating the link");
			e.printStackTrace();
		}

		System.out.println("Database setup.");		
    }   
    
	public Integer addUser(User u) throws SQLException{
        String str = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        PreparedStatement prepS = c.prepareStatement(str);
        prepS.setString(1, u.getUsername());
        prepS.setBytes(2, u.getPasswordHash());
        prepS.setInt(3, u.getRole());
        prepS.executeUpdate();
        prepS.close();
		return this.checkPassword(u.getUsername(), u.getPasswordHash()).getUserID();
	}

	public static byte[] hashPassword(String psw) throws Exception{
        byte[] s = {(byte) 0, (byte) 1};
        KeySpec k = new PBEKeySpec(psw.toCharArray(), s, 65353, 256);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return f.generateSecret(k).getEncoded();
    }  

	public User checkPassword(String username, byte[] password) throws SQLException{
		String str = "SELECT * FROM users WHERE username = ? AND password = ?";
		PreparedStatement p = c.prepareStatement(str);
		p.setString(1, username);
		p.setBytes(2, password);
		ResultSet rs = p.executeQuery();
		User u = null;
		if(rs.next()){
			u= new User(rs.getInt("userId"), rs.getString("username"), rs.getBytes("password"), rs.getInt("role"));
		}
		p.close();
		rs.close();
		return u;
	}

}
