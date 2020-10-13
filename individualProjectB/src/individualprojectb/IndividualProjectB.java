/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package individualprojectb;

import java.util.Scanner;

/**
 *
 * @author kostis
 */
public class IndividualProjectB {

   //StringBuilder message for the menu of the program

    private static final StringBuilder MESSAGE = new StringBuilder()
            .append("Please choose one of the options below:\n")
            .append("To Register a Course -(1)\n")
            .append("To Register a Trainer -(2)\n")
            .append("To Register a Student -(3)\n")
            .append("To Register an Assignment -(4) \n")
            .append("To insert a Student to Course-(5)\n")
            .append("To insert a Trainer to Course-(6)\n")
            .append("To insert Assignment to Course(7)\n")
            .append("To exit program-(0)\n")
            .append("Enter your choice here");
//StringBuilder message for the menu with synthetic data of the program
    private static final StringBuilder MESSAGE2 = new StringBuilder().append("Please choose one of the options below:\n")
            .append("(1) to show list of Trainers, \n")
            .append("(2) to show list of Students, \n")
            .append("(3) to show list of Courses, \n")
            .append("(4) to show list of Assignments, \n")
            .append("(5) to show all the Students per Course , \n")
            .append("(6) to show all the Trainers per Course , \n")
            .append("(7) to show all the Assignments per Course , \n")
            .append("(8) to show all the Assignments per Course per Student , \n")
            .append("(9) to show a list of students that belong to more than one courses , \n")
            .append("To exit program press (0)\n").append("Enter your choice here");
    
    
    public static void main(String[] args)  {
        DatabaseServices database = new DatabaseServices();
        database.createConnection();
        System.out.println("School's Management System\n" + "To START registration Process Press-(1)\n"
                + "To SHOW Database's Information Press-(2) \n" + "Enter your choice here:");
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        
        while (num == 1){
        
            System.out.println(MESSAGE);
            int num2 = sc.nextInt();
            if (num2 == 1) {
                database.registerCourses();
            } else if (num2 == 2) {
               database.registerTrainers();
            } else if (num2 == 3) {
                database.registerStudents();
  
            } else if (num2 == 4) {
               database.registerAssignments();
            } else if (num2 == 5) {
                database.insertStudentToCourse();
            } else if (num2 == 6) {
                database.insertTrainerToCourse();
            } else if (num2 == 7) {
               database.insertAssignmentToCourse();
            }else if (num2 == 0) {
                System.exit(0);
            }
           System.out.println("Do you wish to return to main menu? (1.Yes -2.No)");
            num = sc.nextInt();
            if (num == 2) {
                System.exit(0);
            }
        }
        
        while(num == 2){
            {
            System.out.println(MESSAGE2);
            int num3 = sc.nextInt();
            if (num3 == 1) {
                database.showTrainers();
            } else if (num3 == 2) {
                database.showStudents();
       
            } else if (num3 == 3) {
                 database.showCourses();
               
            } else if (num3 == 4) {
                database.showAssignments();
               
            } else if (num3 == 5) {
                database.showStudentsPerCourse();
            } else if (num3 == 6) {

               database.showTrainersPerCourse();

            } else if (num3 == 7) {
              database.showAssignmentsPerCourse();
            } else if (num3 == 8) {
                database.showAssignmentsPerCoursePerStudent();

            } else if (num3 == 9) {
                database.showStudentsMoreThanOneCourse();
            } else if (num3 == 0) {
                System.exit(0);
            }

            System.out.println("Do you wish to return to main menu? (1.No -2.Yes)");
            num = sc.nextInt();
        }
            
            
        
        }
        
    
}}
