INSERT INTO schedule.groups (group_id, group_name) VALUES ('3c01e6f1-762e-43b8-a6e1-7cf493ce92e2', 'OR-41');
INSERT INTO schedule.groups (group_id, group_name) VALUES ('3c01e6f1-762e-43b8-a6e1-7cf493ce1234', 'GM-87');
INSERT INTO schedule.groups (group_id, group_name) VALUES ('3c01e6f1-762e-43b8-a6e1-7cf493ce5325', 'XI-12');
INSERT INTO schedule.groups (group_id, group_name) VALUES ('3c01e6f1-762e-43b8-a6e1-7cf493ce1111', 'TT-12');
INSERT INTO schedule.groups (group_id, group_name) VALUES ('3c01e6f1-762e-43b8-a6e1-7cf493ce2356', 'YT-16');
INSERT INTO schedule.groups (group_id, group_name) VALUES ('3c01e6f1-762e-43b8-a6e1-7cf493ce8906', 'LG-55');
INSERT INTO schedule.groups (group_id, group_name) VALUES ('3c01e6f1-762e-43b8-a6e1-7cf493ce5775', 'GQ-22');
INSERT INTO schedule.groups (group_id, group_name) VALUES ('3c01e6f1-762e-43b8-a6e1-7cf493ce2344', 'TH-13');
INSERT INTO schedule.groups (group_id, group_name) VALUES ('3c01e6f1-762e-43b8-a6e1-7cf493ce2337', 'GN-33');
INSERT INTO schedule.groups (group_id, group_name) VALUES ('3c01e6f1-762e-43b8-a6e1-7cf493ce9988', 'IT-18');

INSERT INTO schedule.courses (course_id, course_name, course_description, number_seats, seats_available) VALUES ('1d95bc79-a549-4d2c-aeb5-3f929aee0f22', 'Mathematics', 'course of Mathematics', 30, 29);
INSERT INTO schedule.courses (course_id, course_name, course_description, number_seats, seats_available) VALUES ('1d95bc79-a549-4d2c-aeb5-3f929aee1234', 'Biology', 'course of Biology', 30, 28);
INSERT INTO schedule.courses (course_id, course_name, course_description, number_seats, seats_available) VALUES ('1d95bc79-a549-4d2c-aeb5-3f929aee5324', 'Chemistry', 'course of Chemistry', 15, 15);
INSERT INTO schedule.courses (course_id, course_name, course_description, number_seats, seats_available) VALUES ('1d95bc79-a549-4d2c-aeb5-3f929aee6589', 'Physics', 'course of Physics', 15, 15);
INSERT INTO schedule.courses (course_id, course_name, course_description, number_seats, seats_available) VALUES ('1d95bc79-a549-4d2c-aeb5-3f929aee8999', 'Philosophy', 'course of Philosophy', 30, 30);
INSERT INTO schedule.courses (course_id, course_name, course_description, number_seats, seats_available) VALUES ('1d95bc79-a549-4d2c-aeb5-3f929aee0096', 'Drawing', 'course of Drawing', 10, 10);
INSERT INTO schedule.courses (course_id, course_name, course_description, number_seats, seats_available) VALUES ('1d95bc79-a549-4d2c-aeb5-3f929aee1222', 'Literature', 'course of Literature', 10, 10);
INSERT INTO schedule.courses (course_id, course_name, course_description, number_seats, seats_available) VALUES ('1d95bc79-a549-4d2c-aeb5-3f929aee7658', 'English', 'course of English', 30, 30);
INSERT INTO schedule.courses (course_id, course_name, course_description, number_seats, seats_available) VALUES ('1d95bc79-a549-4d2c-aeb5-3f929aee3356', 'Geography', 'course of Geography', 15, 15);
INSERT INTO schedule.courses (course_id, course_name, course_description, number_seats, seats_available) VALUES ('1d95bc79-a549-4d2c-aeb5-3f929aee0887', 'Physical training', 'course of Physical training', 30, 30);

INSERT INTO schedule.users (user_type, user_id, group_id, student_card, first_name, last_name, email, password,
                            password_check, registration_status)
VALUES ('US_SA', '33c99439-aaf0-4ebd-a07a-bd0c550db4e1', '3c01e6f1-762e-43b8-a6e1-7cf493ce92e2', 'DT94381727', 'John',
        'Doe', 'dis@ukr.net', '$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS',
        '$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS', 'NEW');

INSERT INTO schedule.users (user_type, user_id, group_id, student_card, first_name, last_name, email, password,
                            password_check, registration_status)
VALUES ('US_SA', '33c99439-aaf0-4ebd-a07a-bd0c550d2311', '3c01e6f1-762e-43b8-a6e1-7cf493ce5325', 'RT85796142', 'Jane',
        'Does', 'dtestMail@gmail.com', '$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS',
        '$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS', 'NEW');

INSERT INTO schedule.users (user_type, user_id, first_name, last_name, email, password,
                            password_check, registration_status, degrees, phone_number)
VALUES ('US_TA', '33c99439-aaf0-4ebd-a07a-bd0c550d8799', 'Jin',
        'Tores', 'teacherMail@gmail.com', '$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS',
        '$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS', 'REGISTERED',
        'DOCTORAL', '067-768-874');

INSERT INTO schedule.users (user_type, user_id, first_name, last_name, email, password,
                            password_check, registration_status, degrees, phone_number)
VALUES ('US_TA', '33c99439-aaf0-4ebd-a07a-bd0c55asUr71', 'Nick',
        'Bills', 'accountMail@gmail.com', '$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS',
        '$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS', 'REGISTERED',
        'MASTER', '073-587-887');

INSERT INTO schedule.users (user_type, user_id, first_name, last_name, email, password, password_check,
                            registration_status)
VALUES ('US_AC', '11111439-aaf0-4ebd-a07a-bd0c550d2333', 'Admin', 'Admin', 'admin@',
        '$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS',
        '$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS', 'REGISTERED');

INSERT INTO schedule.students_courses (user_id, course_id) VALUES ('33c99439-aaf0-4ebd-a07a-bd0c550db4e1', '1d95bc79-a549-4d2c-aeb5-3f929aee0f22');
INSERT INTO schedule.students_courses (user_id, course_id) VALUES ('33c99439-aaf0-4ebd-a07a-bd0c550db4e1', '1d95bc79-a549-4d2c-aeb5-3f929aee1234');
INSERT INTO schedule.students_courses (user_id, course_id) VALUES ('33c99439-aaf0-4ebd-a07a-bd0c550d2311', '1d95bc79-a549-4d2c-aeb5-3f929aee1234');

INSERT INTO schedule.course_teachers (user_id, course_id) VALUES ('33c99439-aaf0-4ebd-a07a-bd0c550d8799', '1d95bc79-a549-4d2c-aeb5-3f929aee0f22');
INSERT INTO schedule.course_teachers (user_id, course_id) VALUES ('33c99439-aaf0-4ebd-a07a-bd0c55asUr71', '1d95bc79-a549-4d2c-aeb5-3f929aee0f22');
INSERT INTO schedule.course_teachers (user_id, course_id) VALUES ('33c99439-aaf0-4ebd-a07a-bd0c55asUr71', '1d95bc79-a549-4d2c-aeb5-3f929aee1234');

INSERT INTO schedule.diploma_students(user_id, user_id_student) VALUES ('33c99439-aaf0-4ebd-a07a-bd0c550d8799', '33c99439-aaf0-4ebd-a07a-bd0c550d2311');

INSERT INTO schedule.roles (role_id, role) VALUES ('54RG9439-aaf0-4ebd-a07a-bd0c550db4e1', 'ADMIN');
INSERT INTO schedule.roles (role_id, role) VALUES ('64TR9439-aaf0-4ebd-a07a-bd0c550db4e1', 'MODERATOR');
INSERT INTO schedule.roles (role_id, role) VALUES ('98LD9439-aaf0-4ebd-a07a-bd0c550db4e1', 'STUDENT');
INSERT INTO schedule.roles (role_id, role) VALUES ('PR3W9439-aaf0-4ebd-a07a-bd0c550db4e1', 'TEACHER');
INSERT INTO schedule.roles (role_id, role) VALUES ('LDG69439-aaf0-4ebd-a07a-bd0c550db4e1', 'USER');

INSERT INTO schedule.users_roles (user_id, role_id) VALUES ('33c99439-aaf0-4ebd-a07a-bd0c550db4e1', '98LD9439-aaf0-4ebd-a07a-bd0c550db4e1');
INSERT INTO schedule.users_roles (user_id, role_id) VALUES ('33c99439-aaf0-4ebd-a07a-bd0c550d2311', '98LD9439-aaf0-4ebd-a07a-bd0c550db4e1');
INSERT INTO schedule.users_roles (user_id, role_id) VALUES ('11111439-aaf0-4ebd-a07a-bd0c550d2333', '54RG9439-aaf0-4ebd-a07a-bd0c550db4e1');
INSERT INTO schedule.users_roles (user_id, role_id) VALUES ('11111439-aaf0-4ebd-a07a-bd0c550d2333', 'LDG69439-aaf0-4ebd-a07a-bd0c550db4e1');
INSERT INTO schedule.users_roles (user_id, role_id) VALUES ('33c99439-aaf0-4ebd-a07a-bd0c550d8799', 'PR3W9439-aaf0-4ebd-a07a-bd0c550db4e1');
INSERT INTO schedule.users_roles (user_id, role_id) VALUES ('33c99439-aaf0-4ebd-a07a-bd0c550d8799', 'LDG69439-aaf0-4ebd-a07a-bd0c550db4e1');
INSERT INTO schedule.users_roles (user_id, role_id) VALUES ('33c99439-aaf0-4ebd-a07a-bd0c550d8799', '64TR9439-aaf0-4ebd-a07a-bd0c550db4e1');
INSERT INTO schedule.users_roles (user_id, role_id) VALUES ('33c99439-aaf0-4ebd-a07a-bd0c55asUr71', 'PR3W9439-aaf0-4ebd-a07a-bd0c550db4e1');
INSERT INTO schedule.users_roles (user_id, role_id) VALUES ('33c99439-aaf0-4ebd-a07a-bd0c55asUr71', 'LDG69439-aaf0-4ebd-a07a-bd0c550db4e1');

INSERT INTO schedule.timetable (schedule_id, lecture_room, date_of_lecture, course_course_id, group_group_id, teacher_user_id, start_of_lecture, end_of_lecture) VALUES
    ('e2cae483-0284-4088-ad1e-6780128ecd36', 'c. 742', '2023-09-01', '1d95bc79-a549-4d2c-aeb5-3f929aee0f22',
     '3c01e6f1-762e-43b8-a6e1-7cf493ce92e2', '33c99439-aaf0-4ebd-a07a-bd0c550d8799', '08:30:00', '10:05:00');
INSERT INTO schedule.timetable (schedule_id, lecture_room, date_of_lecture, course_course_id, group_group_id, teacher_user_id, start_of_lecture, end_of_lecture) VALUES
    ('tr1ae483-0284-4088-ad1e-6780128e8721', 'c. 742', '2023-09-01', '1d95bc79-a549-4d2c-aeb5-3f929aee0f22',
     '3c01e6f1-762e-43b8-a6e1-7cf493ce9988', '33c99439-aaf0-4ebd-a07a-bd0c550d8799', '10:20:00', '11:55:00');
INSERT INTO schedule.timetable (schedule_id, lecture_room, date_of_lecture, course_course_id, group_group_id, teacher_user_id, start_of_lecture, end_of_lecture) VALUES
   ('dd2ae483-0284-4088-ad1e-6780128e5672', 'c. 742', '2023-09-01', '1d95bc79-a549-4d2c-aeb5-3f929aee0f22',
     '3c01e6f1-762e-43b8-a6e1-7cf493ce5775', '33c99439-aaf0-4ebd-a07a-bd0c550d8799', '14:30:00','16:05:00');
INSERT INTO schedule.timetable (schedule_id, lecture_room, date_of_lecture, course_course_id, group_group_id, teacher_user_id, start_of_lecture, end_of_lecture) VALUES
    ('u6tae483-0284-4088-ad1e-6780128e43t6', 'c. 476', '2023-09-01', '1d95bc79-a549-4d2c-aeb5-3f929aee0f22',
     '3c01e6f1-762e-43b8-a6e1-7cf493ce2337', '33c99439-aaf0-4ebd-a07a-bd0c55asUr71', '10:20:00', '11:55:00');
INSERT INTO schedule.timetable (schedule_id, lecture_room, date_of_lecture, course_course_id, group_group_id, teacher_user_id, start_of_lecture, end_of_lecture) VALUES
    ('bd3ae483-0284-4088-ad1e-6780128e5542', 'c. 376', '2023-09-01', '1d95bc79-a549-4d2c-aeb5-3f929aee1234',
     '3c01e6f1-762e-43b8-a6e1-7cf493ce2337', '33c99439-aaf0-4ebd-a07a-bd0c55asUr71', '12:10:00', '13:45:00');
INSERT INTO schedule.timetable (schedule_id, lecture_room, date_of_lecture, course_course_id, group_group_id, teacher_user_id, start_of_lecture, end_of_lecture) VALUES
    ('hg3ae483-0284-4088-ad1e-6780128e5443', 'c. 879', '2023-09-01', '1d95bc79-a549-4d2c-aeb5-3f929aee1234',
     '3c01e6f1-762e-43b8-a6e1-7cf493ce92e2', '33c99439-aaf0-4ebd-a07a-bd0c55asUr71', '16:20:00', '17:50:00');
INSERT INTO schedule.timetable (schedule_id, lecture_room, date_of_lecture, course_course_id, group_group_id, teacher_user_id, start_of_lecture, end_of_lecture) VALUES
    ('jh5ae483-0284-4088-ad1e-6780128e5464', 'c. 879', '2023-09-02', '1d95bc79-a549-4d2c-aeb5-3f929aee1234',
     '3c01e6f1-762e-43b8-a6e1-7cf493ce92e2', '33c99439-aaf0-4ebd-a07a-bd0c55asUr71', '16:20:00', '17:50:00');

INSERT INTO schedule.lecture_start_time (start_lecture_time) VALUES ('08:30:00'), ('10:20:00'), ('12:10:00'), ('14:30:00'), ('16:20:00');
INSERT INTO schedule.lecture_end_time (end_lecture_time) VALUES ('10:05:00'), ('11:55:00'), ('13:45:00'), ('16:05:00'), ('17:50:00');
INSERT INTO schedule.lecture_rooms (lecture_room) VALUES ('c. 742'), ('c. 476'), ('c. 376'), ('c. 879'), ('c. 321'), ('c. 755'), ('c. 432');