package entities;

import java.sql.Date;
import java.text.SimpleDateFormat;


public class Patient {
    private int id;
    private String name;
    private int medicalCardNumber;
    private Date birthDate;
    private int userId;

    // Constructor
    public Patient(int id, String name, int medicalCardNumber, Date birthDate, int userId) {
        setId(id);
        setName(name);
        setMedicalCardNumber(medicalCardNumber);
        setBirthDate(birthDate);
        setUserId(userId);
    }

    public Patient() {
        this.id = 0;
        this.name = "Pepita Pérez";
        this.medicalCardNumber = 0;
        this.birthDate = null;
        this.userId = 0;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMedicalCardNumber() {
        return medicalCardNumber;
    }

    public void setMedicalCardNumber(int medicalCardNumber) {
        this.medicalCardNumber = medicalCardNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", medicalCardNumber=" + medicalCardNumber +
                ", birthDate=" + dateFormat.format(birthDate) +
                ", userId=" + userId +
                '}';
    }
}

