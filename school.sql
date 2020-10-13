CREATE DATABASE IF NOT EXISTS school;
use school;

-- creates table students with attributes from Part A
create table if not exists students (
student_id INT NOT NULL auto_increment,
st_first_name varchar(45),
st_last_name varchar(45),
date_of_birth DATE,
tuition_fees  DECIMAL(6,2),

constraint pk primary key (student_id)
);
-- creates table courses with attributes from Part A
create table if not exists courses(
    course_id     INT NOT NULL AUTO_INCREMENT,
	title         VARCHAR(45) NOT NULL,
	course_stream        VARCHAR(45),
	course_type        VARCHAR(45),
	start_date    DATE,
	end_date      DATE,
    CONSTRAINT pk primary key (course_id)
    );

-- -- creates table trainers with attributes from Part A
create table if not exists trainers(
trainer_id INT NOT NULL auto_increment,
tr_first_name varchar(45),
tr_last_name  varchar(45),
tr_subject varchar(45),

CONSTRAINT pk primary key (trainer_id)
);
-- creates table assignments with attributes from Part A
create table if not exists assignments(
assignment_id int not null auto_increment,
title        	VARCHAR(255) NOT NULL ,
a_descr         VARCHAR(255),
a_Date  	    date ,
oral_mark       DECIMAL(4,2) ,
total_mark    	DECIMAL(4,2),

CONSTRAINT pk primary key (assignment_id)
);

CREATE TABLE IF NOT EXISTS students_per_course (
	course_id  INT NOT NULL,
	student_id INT NOT NULL,
PRIMARY KEY (course_id, student_id),
FOREIGN KEY (course_id) REFERENCES courses (course_id),
FOREIGN KEY (student_id) REFERENCES students (student_id)
);

CREATE TABLE IF NOT EXISTS trainers_per_course (
	course_id  INT NOT NULL,
	trainer_id INT NOT NULL,
PRIMARY KEY (course_id, trainer_id),
FOREIGN KEY (course_id) REFERENCES courses (course_id),
FOREIGN KEY (trainer_id) REFERENCES trainers (trainer_id)
);

CREATE TABLE IF NOT EXISTS assignments_per_course (
	course_id     int NOT NULL,
	assignment_id int NOT NULL,    
PRIMARY KEY (assignment_id, course_id),
FOREIGN KEY (assignment_id) REFERENCES assignments (assignment_id),
FOREIGN KEY (course_id) REFERENCES courses (course_id)
);

-- Insert data to students table
INSERT INTO students(st_first_name,st_last_name,date_of_birth,tuition_fees)
VALUES ("Nick", "Papadopoulos","1985-01-02", 800),
	   ("Agnes ", "Michael","1987-06-30", 700),
	   ("Blake", "Reese", "1986-05-30", 900),
       ("Poppie", "Sykes", "1990-04-30", 1000),
	   ("Ioana", "Rubio","1988-06-25", 500),
       ("Kate", "Roth", "1985-04-30", 1700),
       ("Xena", "Dolan","1991-07-23", 1200),
       ("Iain", "Pacheco", "1984-08-30", 1100),
       ("Lyndon", "French", "1988-03-15", 1300),
       ("Danny", "Mackay"," 1985-01-15", 600);

insert into courses(title,course_stream,course_type ,start_date,end_date)
values("Course 1 Java", "Java", "Full time", "2019-03-20", "2019-08-10"),
      ("Course 2 C#", "C#", "Part time", "2019-03-20", "2019-08-10"),
      ("Course 3 Javascript", "Javascript", "Full time", "2019-01-06", "2019-05-18"),
      ("Course 4 Python", "Python", "Part time","2019-03-30", "2019-08-12");
      
-- 
insert into trainers(tr_first_name,tr_last_name,tr_subject)
VALUES ("Billie ", "Irving", "C#"),
    ("Alexia", "Roberson", "Java"),
    ("Franklyn", "Anderson", "Javascript"),    
    ("Ronan", "Delgado", "Python");


insert into assignments(title,a_descr,a_Date,oral_mark,total_mark)
values ("Java Exercises", " Solving math exercises", "2019-02-20", 8, 3),
       ("CSharp exercises", "Laboratory on programming languages", "2019-02-20", 4, 10),
       ("JavaScript exercises", "Designing and developing algorithms", "2019-03-10", 6, 9),
       ("Python Exercises", "Developing data structures", "2019-04-06", 3, 9);
	

INSERT INTO students_per_course (course_id, student_id)
VALUES 	(1, 1), (1, 2), (1, 3), (1, 4), (1, 10),
		(2, 5), (2, 6), (2, 7), (2, 8), (2, 1),
		(3, 7),(4, 9), (4, 10);


INSERT INTO trainers_per_course (course_id, trainer_id)
VALUES (1, 3), (2, 4), (3, 1), (4, 2);

INSERT INTO assignments_per_course (course_id,assignment_id)
VALUES (1,1),(2,2),(3,3),(4,4);
       
-- SELECT queries for school database based on the Individual Project Part B text.

-- Select that lists all the students
select * from students;

-- Select that lists all the courses
SELECT * FROM courses;

-- Select query that lists all the trainers
SELECT * FROM trainers;

-- Select that lists all the assignments
SELECT * FROM assignments;

-- All the students per course
SELECT 
    c.title,
    s.st_first_name,
    s.st_last_name
FROM students_per_course spc
INNER JOIN students s 
	ON s.student_id = spc.student_id
INNER JOIN courses c 
	ON c.course_id = spc.course_id;

-- All the trainers per course
SELECT
    t.tr_first_name,
    t.tr_last_name
FROM trainers_per_course tpc
 INNER JOIN trainers t
 ON tpc.trainer_id = t.trainer_id
 INNER JOIN courses c 
ON c.course_id = tpc.course_id;
-- All the assignments per course
SELECT
    c.title,
    a.title,
	a.a_descr
FROM assignments_per_course apc
INNER JOIN assignments a 
	ON a.assignment_id = apc.assignment_id
INNER JOIN courses c 
	ON c.course_id = apc.course_id;
-- All the assignments per course per student
SELECT 
    s.st_first_name, 
    s.st_last_name,
    c.title,
    a.assignment_id, 
    a.title
FROM assignments_per_course apc
INNER JOIN assignments a 
	ON a.assignment_id = apc.assignment_id
INNER JOIN courses c 
	ON c.course_id = apc.course_id
INNER JOIN students_per_course spc 
	ON spc.course_id = apc.course_id
INNER JOIN students s 
	ON s.student_id = spc.student_id
    ORDER BY s.student_id, c.course_id;
-- A list of students that belong to more than one courses
SELECT 
    s.st_first_name, 
    s.st_last_name,
    COUNT(spc.course_id) AS Number_of_courses
FROM students_per_course spc
INNER JOIN students s 
	ON s.student_id = spc.student_id
GROUP BY spc.student_id
HAVING Number_of_courses > 1;

