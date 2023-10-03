drop database if exists `courses_website`;

CREATE DATABASE  IF NOT EXISTS `courses_website`;
use courses_website;

DROP TABLE IF EXISTS `student_courses`;
DROP TABLE IF EXISTS `course`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `token`;

CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `users` (
`id` int NOT NULL AUTO_INCREMENT,
`username` varchar(50) NOT NULL,
`password` varchar(200) NOT NULL,
`email` varchar(50) NOT NULL,
`enabled` tinyint NOT NULL,
`role_id` int,
PRIMARY KEY (`id`),
CONSTRAINT `role_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_token` (
`id` int NOT NULL AUTO_INCREMENT,
`token` varchar(255) NOT NULL,
`revoked` tinyint NOT NULL,
`expired` tinyint NOT NULL,
`user_id` int,
PRIMARY KEY (`id`),
CONSTRAINT `user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `courses_website`.`role` (`name`)
VALUES ('ADMIN'),('INSTRUCTOR'),('STUDENT');

INSERT INTO `courses_website`.`users` (`username`,`password`,`email`,`enabled`,`role_id`)
VALUES
('moha','$2a$05$o9PnzMD9N0DtlU1atThiAeI5iqBPFv6TmIAfvtIaOmaEFNbjsjSFq','moha@gmail.com',1,1),
('boha','$2a$05$o9PnzMD9N0DtlU1atThiAeI5iqBPFv6TmIAfvtIaOmaEFNbjsjSFq','boha@gmail.com',1,1),
('joha','$2a$05$o9PnzMD9N0DtlU1atThiAeI5iqBPFv6TmIAfvtIaOmaEFNbjsjSFq','joha@gmail.com',1,2),
('soha','$2a$05$o9PnzMD9N0DtlU1atThiAeI5iqBPFv6TmIAfvtIaOmaEFNbjsjSFq','soha@gmail.com',1,2),
('noha','$2a$05$o9PnzMD9N0DtlU1atThiAeI5iqBPFv6TmIAfvtIaOmaEFNbjsjSFq','noha@gmail.com',1,3),
('doha','$2a$05$o9PnzMD9N0DtlU1atThiAeI5iqBPFv6TmIAfvtIaOmaEFNbjsjSFq','doha@gmail.com',1,3);


CREATE TABLE `course` (
`id` int NOT NULL AUTO_INCREMENT,
`name` varchar(50) NOT NULL,
`description` varchar(200) NOT NULL,
`headline` varchar(150) NOT NULL,
`creation_date` datetime,
`total_hours` int,
`image_path` varchar(255),
`course_link` varchar(200),
`instructor_id` int,
`instructor_name` varchar(200),
PRIMARY KEY (`id`),
CONSTRAINT `instructor_fk` FOREIGN KEY (`instructor_id`) REFERENCES `users` (`id`) ON DELETE restrict
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `courses_website`.`course` (`name`,`description`,`instructor_id`,`creation_date`,`total_hours`,`headline`,`image_path`,`course_link`,`instructor_name`)
VALUES
('sprint boot','Spring Boot is one of the Best Backend frameworks that developers count on while performing backend web development tasks',3,date_sub(current_date(),INTERVAL 55 DAY),25,'Spring Core, Spring REST, Spring Security, JPA, Hibernate, MySQL','https://img-c.udemycdn.com/course/240x135/647428_be28_10.jpg','https://www.youtube.com/embed/9SGDpanrc8U','joha'),
('REST APIs','API that conforms to the design principles of the REST, or representational state transfer architectural style.',3,date_sub(current_date(),INTERVAL 40 DAY),10,'Build REST APIs with Python, Flask, Docker, Flask-Smorest, and Flask-SQLAlchemy','https://img-b.udemycdn.com/course/240x135/970600_68be_4.jpg','https://www.youtube.com/embed/lsMQRaeKNDk','joha'),
('JAVA','Java is a high-level, class-based,object-oriented programming language that is designed to have as few implementation dependencies as possible.',3,date_sub(current_date(),INTERVAL 33 DAY),40,'Obtain valuable Core Java Skills And Java Certification','https://img-b.udemycdn.com/course/240x135/533682_c10c_4.jpg','https://www.youtube.com/embed/eIrMbAQSU34','joha'),
('C++','C++ is a high-level,general-purpose programming language created by Danish computer scientist Bjarne Stroustrup.',4,date_sub(current_date(),INTERVAL 20 DAY),36,'Obtain Modern C++ Object-Oriented Programming (OOP) and STL skills.','https://img-b.udemycdn.com/course/240x135/1576854_9aeb_2.jpg','https://www.youtube.com/embed/8jLOx1hD3_o','soha'),
('Algorithms','a procedure used for solving a problem or performing a computation.',4,date_sub(current_date(),INTERVAL 11 DAY),15,'Master the Coding Interview: Data Structures + Algorithms','https://img-b.udemycdn.com/course/240x135/1917546_682b_3.jpg','https://www.youtube.com/embed/8hly31xKli0','soha'),
('Data Structures','a data structure is a data organization, management,and storage format that is usually chosen for efficient access to data.',4,date_sub(current_date(),INTERVAL 8 DAY),50,'Learn, Analyse and Implement Data Structure using C and C++','https://img-b.udemycdn.com/course/240x135/2121018_9de5_5.jpg','https://www.youtube.com/embed/6WdTxK_V9Qo','soha');


CREATE TABLE `student_courses` (
`id` int NOT NULL AUTO_INCREMENT,
`course_id` int,
`users_id` int,
`enrollment_date` varchar(50) NOT NULL,
PRIMARY KEY (`id`),
CONSTRAINT `crs_fk` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE RESTRICT,
CONSTRAINT `usr_fk` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `courses_website`.`student_courses` (`course_id`,`users_id`,`enrollment_date`)
VALUES
(1,5,date_sub(current_date(),INTERVAL 10 DAY)),
(2,5,date_sub(current_date(),INTERVAL 15 DAY)),
(3,5,date_sub(current_date(),INTERVAL 20 DAY)),
(3,6,date_sub(current_date(),INTERVAL 25 DAY)),
(4,6,date_sub(current_date(),INTERVAL 30 DAY));


CREATE VIEW student_courses_data AS
SELECT
courses_website.student_courses.id,
t1.username,
courses_website.course.name course_name,
courses_website.course.id course_id,
courses_website.course.image_path image_path,
courses_website.course.headline headline,
courses_website.student_courses.enrollment_date,
courses_website.student_courses.users_id,
t2.username instructor_name
FROM courses_website.users t1,courses_website.users t2,courses_website.course,courses_website.student_courses
where courses_website.course.id=courses_website.student_courses.course_id and
t1.id=courses_website.student_courses.users_id and
t2.id=courses_website.course.instructor_id;


SELECT uu.username,rr.name FROM courses_website.users uu
inner join courses_website.role rr on uu.role_id = rr.id;

SELECT courses_website.course.name,uuu.username instructor_name FROM courses_website.course
inner join courses_website.users uuu on courses_website.course.instructor_id = uuu.id;

SELECT u.username student_name,c.name course_name, stc.enrollment_date FROM courses_website.users u
inner join courses_website.student_courses stc on u.id = stc.users_id
inner join courses_website.course c on stc.course_id = c.id;