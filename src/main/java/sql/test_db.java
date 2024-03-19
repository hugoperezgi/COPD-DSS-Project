// package sql;

// public class test_db {

//         public static void main(String[] args) {
//             try {

//                 DbManager db = new DbManager();

//                 // Inserting a new patient
//                 db.createUser("user1", "password1", "patient");
//                 db.createPatient("John Doe", 123456789, new java.sql.Date(2024,02,29), 1);

//                 // Getting all patients
//                 System.out.println("All Patients:");
//                 System.out.println(db.getAllPatients().toString());

//                 // Inserting a new user
//                 db.createUser("user2", "password2", "doctor");

//                 db.deleteUser(2);
//                 // Inserting a medical record
//                 db.createMedicalRecord(1, "D", 2, "Antibiotics", new java.sql.Date(2024,02,29), new java.sql.Date(2025,02,29));

//                 // Getting medical history of a patient
//                 System.out.println("\nMedical History of Patient ID 1:");
//                 System.out.println(db.getMedicalHistory(1).toString());

//                 // Inserting only phenotype
//                 db.createMedicalRecord_OnlyPhenotype(1, "A");


//                 // Adding severity to an existing record
//                 db.updateSeverity(2, 2);

//                 // Updating treatment, begin date, and end date
//                 db.updateTreatmentAndDates(2, "Paracetamol", new java.sql.Date(2024,06,01), new java.sql.Date(2024,11,31));


//                 // Closing the connection
//                 db.close_db();
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }
//         }
//     }
