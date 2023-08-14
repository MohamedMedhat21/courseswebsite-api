CREATE DATABASE  IF NOT EXISTS `courses_website`;
use courses_website;

DROP TABLE IF EXISTS `student_courses`;
DROP TABLE IF EXISTS `course`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `role`;

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
`instructor_id` int,
PRIMARY KEY (`id`),
CONSTRAINT `instructor_fk` FOREIGN KEY (`instructor_id`) REFERENCES `users` (`id`) ON DELETE restrict
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `courses_website`.`course` (`name`,`description`,`instructor_id`)
VALUES
('sprint boot','Spring Boot is one of the Best Backend frameworks that developers count on while performing backend web development tasks',3),
('REST APIs','API that conforms to the design principles of the REST, or representational state transfer architectural style.',3),
('JAVA','Java is a high-level, class-based,object-oriented programming language that is designed to have as few implementation dependencies as possible.',3),
('C++','C++ is a high-level,general-purpose programming language created by Danish computer scientist Bjarne Stroustrup.',4),
('Algorithms','a procedure used for solving a problem or performing a computation.',4),
('Data Structures','a data structure is a data organization, management,and storage format that is usually chosen for efficient access to data.',4);


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
(1,5,date_add(current_date(),INTERVAL 10 DAY)),
(2,5,date_add(current_date(),INTERVAL 15 DAY)),
(3,5,date_add(current_date(),INTERVAL 20 DAY)),
(3,6,date_add(current_date(),INTERVAL 25 DAY)),
(4,6,date_add(current_date(),INTERVAL 30 DAY));


CREATE VIEW student_courses_data AS
SELECT
courses_website.student_courses.id,
courses_website.users.username,
courses_website.course.name course_name,
courses_website.course.id course_id,
courses_website.student_courses.enrollment_date,
courses_website.student_courses.users_id
FROM courses_website.users,courses_website.course,courses_website.student_courses
where courses_website.course.id=courses_website.student_courses.course_id and
courses_website.users.id=courses_website.student_courses.users_id;


SELECT uu.username,rr.name FROM courses_website.users uu
inner join courses_website.role rr on uu.role_id = rr.id;

SELECT courses_website.course.name,uuu.username instructor_name FROM courses_website.course
inner join courses_website.users uuu on courses_website.course.instructor_id = uuu.id;

SELECT u.username student_name,c.name course_name, stc.enrollment_date FROM courses_website.users u
inner join courses_website.student_courses stc on u.id = stc.users_id
inner join courses_website.course c on stc.course_id = c.id;