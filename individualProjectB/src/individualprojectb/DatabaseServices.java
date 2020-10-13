/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package individualprojectb;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kostis
 */
public class DatabaseServices {

    public Connection connection = null;
    public Statement statement = null;
    public ResultSet resultSet = null;

    private String serverIP = "localhost"; //ra1.anystream.eu
    //"127.0.0.1"// localhost
    private String srvPort = "3306";
    private String databaseName = "school";
    String username = "root";
    String password = "12345678";

    //creates the url string that is used to connect to the database
    private String createJDBCConnectionString() {
        String value = "";
        value = "jdbc:mysql://" + serverIP + ":" + srvPort + "/" + databaseName + "?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC";
        return value;
    }

    //Connect with database
    public void createConnection() {
        try {
            connection = DriverManager.getConnection(createJDBCConnectionString(), username, password);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //close connection with database
    public void closeConnection() {

        try {
            connection.close();
            System.out.println("Connection with database closed!");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Method that shows list of Student from database
    public void showStudents() {
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM students")) {
            while (resultSet.next()) {

                System.out.println("id: " + resultSet.getInt(1) + " First Name: " + resultSet.getString(2) + " Last Name: " + resultSet.getString(3)
                        + " Date of Birth: " + resultSet.getDate(4) + " Tuition Fees: " + resultSet.getDouble(5));

            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Method to register student from keyboard
    public void registerStudents() {
        // Read data from user's input
        Scanner input_user = new Scanner(System.in);
        System.out.println("Write Student's First Name: ");
        String studentFirstName = input_user.next();
        System.out.println("Write Student's Last Name: ");
        String studentLastName = input_user.next();
        System.out.println("Write the Date of Birth of Student(YYYY-MM-DD): ");
        String studentDateOfBirth = input_user.next();
        LocalDate dateofBirth = LocalDate.parse(studentDateOfBirth);
        System.out.println("Write Student's Tution Fees: ");
        String studentTuitionFees = input_user.next();
        double TuitionFees = Double.valueOf(studentTuitionFees);

        try (
                PreparedStatement pstm = connection.prepareStatement("INSERT INTO `school`.`students` (st_first_name, st_last_name, date_of_birth, tuition_fees)\n"
                        + "VALUES (?, ?, ?, ?)")) {
            pstm.setString(1, studentFirstName);
            pstm.setString(2, studentLastName);
            pstm.setDate(3, Date.valueOf(dateofBirth));
            pstm.setDouble(4, TuitionFees);
            pstm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //method to show courses
    public void showCourses() {

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM courses")) {
            while (resultSet.next()) {
                LocalDate startDate = resultSet.getDate(5).toLocalDate();
                LocalDate endDate = resultSet.getDate(6).toLocalDate();

                System.out.println("id: " + resultSet.getInt(1) + "Title: " + resultSet.getString(2) + "Course Stream: " + resultSet.getString(3)
                        + "Course Type: " + resultSet.getString(4) + "Start Date: " + startDate + "End Date: " + endDate);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Method to register a course from keyboard
    public void registerCourses() {

        Scanner input_user = new Scanner(System.in);
        System.out.println("Write Course Title");

        String courseTitle = input_user.next();
        System.out.println("Write Course Stream");
        String courseStream = input_user.next();
        System.out.println("Write Course Type: ");
        String courseType = input_user.next();
        System.out.println("Write Start Date of the Course(YYYY-MM-DD): ");
        String startDate = input_user.next();
        LocalDate startDate1 = LocalDate.parse(startDate);
        System.out.println("Write End Date of the Course(YYYY-MM-DD): ");
        String endDate = input_user.next();
        LocalDate endDate1 = LocalDate.parse(endDate);

        try (
                PreparedStatement pstm = connection.prepareStatement("INSERT INTO `school`.`courses` (title, course_stream, course_type, start_date,end_date)\n"
                        + "VALUES (?, ?, ?, ?,?)")) {
            pstm.setString(1, courseTitle);
            pstm.setString(2, courseStream);
            pstm.setString(3, courseType);
            pstm.setDate(4, Date.valueOf(startDate1));
            pstm.setDate(5, Date.valueOf(endDate1));
            pstm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // method to show trainers
    public void showTrainers() {

        try (Statement stm = connection.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM trainers")) {
            while (rs.next()) {

                System.out.println("id: " + rs.getInt(1) + " First Name: " + rs.getString(2) + "Last Name: " + rs.getString(3)
                        + "Subject: " + rs.getString(4));

            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // method to register trainers from keyboard
    public void registerTrainers() {
        Scanner input_user = new Scanner(System.in);
        System.out.println("Write trainer's First name");
        String tr_first_name = input_user.next();
        System.out.println("Write trainer's Last name");
        String tr_last_name = input_user.next();
        System.out.println("Write trainer's subject: ");
        String tr_subject = input_user.next();

        try (PreparedStatement pstm = connection.prepareStatement("INSERT INTO `school`.`trainers` (tr_first_name, tr_last_name, tr_subject)\n"
                + "VALUES (?, ?, ?)")) {
            pstm.setString(1, tr_first_name);
            pstm.setString(2, tr_last_name);
            pstm.setString(3, tr_subject);
            pstm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // method that shows assignments
    public void showAssignments() {

        try (Statement stm = connection.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM assignments")) {
            while (rs.next()) {
                System.out.println("id: " + rs.getInt(1) + "Trainer's First Name: "
                        + rs.getString(2) + "Trainer's Last Name: " + rs.getString(3)
                        + " Trainer's Subject: " + rs.getString(4));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Method that registers Assignments from keyboard
    public void registerAssignments() {

        Scanner input_user = new Scanner(System.in);
        System.out.println("Write Assignment's title: ");
        String title = input_user.next();
        System.out.println("Write Assignment's description: ");
        String a_descr = input_user.next();
        System.out.println("Write Assignment's date (YYYY-MM-DD):  ");
        String a_Date = input_user.next();
        LocalDate a_Date1 = LocalDate.parse(a_Date);
        System.out.println("Write Assignment's Oral Mark");
        double oral_mark = input_user.nextDouble();
        System.out.println("Write Assignment's Total Mark");
        double total_mark = input_user.nextDouble();

        try (PreparedStatement prepstatement = connection.prepareStatement("INSERT INTO `school`.`assignments` (title, a_descr, a_Date, oral_mark, total_mark)\n"
                + "VALUES (?, ?, ?, ?, ?)")) {
            prepstatement.setString(1, title);
            prepstatement.setString(2, a_descr);
            prepstatement.setDate(3, Date.valueOf(a_Date1));
            prepstatement.setDouble(4, oral_mark);
            prepstatement.setDouble(5, total_mark);
            prepstatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Method that inserts students to a course
    public void insertStudentToCourse() {

        Scanner input_user = new Scanner(System.in);
        System.out.println("Write Student's ID: ");
        int studentId = input_user.nextInt();
        System.out.println("Write Course's ID: ");
        int courseId = input_user.nextInt();

        try (PreparedStatement pstm1 = connection.prepareStatement("INSERT INTO `school`.`students_per_course` (course_id, student_id)\n"
                + "VALUES (?, ?)")) {
            pstm1.setInt(1, courseId);
            pstm1.setInt(2, studentId);
            pstm1.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    // Method to assign trainer to course

    public void insertTrainerToCourse() {

        Scanner input_user = new Scanner(System.in);
        System.out.println("Write Student's ID: ");
        int trainerId = input_user.nextInt();
        System.out.println("Write Course's ID: ");
        int courseId = input_user.nextInt();

        try (PreparedStatement pstm1 = connection.prepareStatement("INSERT INTO `school`.`trainers_per_course` (course_id, trainer_id)\n"
                + "VALUES (?, ?)")) {
            pstm1.setInt(1, courseId);
            pstm1.setInt(2, trainerId);
            pstm1.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Method to assign assignment to course
    public void insertAssignmentToCourse() {

        Scanner input_user = new Scanner(System.in);
        System.out.println("Write Student's ID: ");
        int assignmentId = input_user.nextInt();
        System.out.println("Write Course's ID: ");
        int courseId = input_user.nextInt();

        try (
                PreparedStatement prepstm = connection.prepareStatement("\nINSERT INTO `school`.`assignments_per_course` (course_id, assignment_id)\n"
                        + "VALUES (?, ?)")) {
            prepstm.setInt(1, courseId);
            prepstm.setInt(2, assignmentId);
            prepstm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void showStudentsPerCourse() {

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT \n"
                        + "    c.title,\n"
                        + "    s.st_first_name,\n"
                        + "    s.st_last_name\n"
                        + "FROM students_per_course spc\n"
                        + "INNER JOIN students s \n"
                        + "	ON s.student_id = spc.student_id\n"
                        + "INNER JOIN courses c \n"
                        + "	ON c.course_id = spc.course_id;")) {
            while (resultSet.next()) {

                System.out.println("c.title: " + resultSet.getString(1) + " First Name: " + resultSet.getString(2) + " Last Name: " + resultSet.getString(3));

            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void showTrainersPerCourse() {

        try (Statement stm = connection.createStatement();
                ResultSet rs = stm.executeQuery("SELECT\n"
                        + "    t.tr_first_name,\n"
                        + "    t.tr_last_name\n"
                        + "FROM trainers_per_course tpc\n"
                        + " INNER JOIN trainers t\n"
                        + " ON tpc.trainer_id = t.trainer_id\n"
                        + " INNER JOIN courses c \n"
                        + "ON c.course_id = tpc.course_id;")) {
            while (rs.next()) {

                System.out.println(" First Name: " + rs.getString(1) + "Last Name: " + rs.getString(2));

            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void showAssignmentsPerCourse() {

        try (Statement stm = connection.createStatement();
                ResultSet rs = stm.executeQuery("SELECT\n"
                        + "    c.title,\n"
                        + "    a.title,\n"
                        + "	a.a_descr\n"
                        + "FROM assignments_per_course apc\n"
                        + "INNER JOIN assignments a \n"
                        + "	ON a.assignment_id = apc.assignment_id\n"
                        + "INNER JOIN courses c \n"
                        + "	ON c.course_id = apc.course_id;")) {
            while (rs.next()) {
                System.out.println("Course Title: " + rs.getString(1) + "Assignment Title: "
                        + rs.getString(2) + "Assignment Description: " + rs.getString(3));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void showAssignmentsPerCoursePerStudent() {

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT \n"
                        + "    s.st_first_name, \n"
                        + "    s.st_last_name,\n"
                        + "    c.title,\n"
                        + "    a.assignment_id, \n"
                        + "    a.title\n"
                        + "FROM assignments_per_course apc\n"
                        + "INNER JOIN assignments a \n"
                        + "	ON a.assignment_id = apc.assignment_id\n"
                        + "INNER JOIN courses c \n"
                        + "	ON c.course_id = apc.course_id\n"
                        + "INNER JOIN students_per_course spc \n"
                        + "	ON spc.course_id = apc.course_id\n"
                        + "INNER JOIN students s \n"
                        + "	ON s.student_id = spc.student_id\n"
                        + "    ORDER BY s.student_id, c.course_id;")) {
            while (resultSet.next()) {

                System.out.println("Student First Name:  " + resultSet.getString(1) + " Student Last Name " + resultSet.getString(2) + "Course Title: " + resultSet.getString(3)
                        + "Assignment ID: " + resultSet.getString(4) + "Assignment Title: " + resultSet.getString(5));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void showStudentsMoreThanOneCourse(){
    
    
    
    
    
    }
}
