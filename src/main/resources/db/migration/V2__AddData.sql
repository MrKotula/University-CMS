insert into schedule.groups (group_id, group_name) values ('3c01e6f1-762e-43b8-a6e1-7cf493ce92e2', 'OR-41');
insert into schedule.groups (group_id, group_name) values ('3c01e6f1-762e-43b8-a6e1-7cf493ce1234', 'GM-87');
insert into schedule.groups (group_id, group_name) values ('3c01e6f1-762e-43b8-a6e1-7cf493ce5325', 'XI-12');
insert into schedule.groups (group_id, group_name) values ('3c01e6f1-762e-43b8-a6e1-7cf493ce1111', 'TT-12');
insert into schedule.groups (group_id, group_name) values ('3c01e6f1-762e-43b8-a6e1-7cf493ce2356', 'YT-16');
insert into schedule.groups (group_id, group_name) values ('3c01e6f1-762e-43b8-a6e1-7cf493ce8906', 'LG-55');
insert into schedule.groups (group_id, group_name) values ('3c01e6f1-762e-43b8-a6e1-7cf493ce5775', 'GQ-22');
insert into schedule.groups (group_id, group_name) values ('3c01e6f1-762e-43b8-a6e1-7cf493ce2344', 'TH-13');
insert into schedule.groups (group_id, group_name) values ('3c01e6f1-762e-43b8-a6e1-7cf493ce2337', 'GN-33');
insert into schedule.groups (group_id, group_name) values ('3c01e6f1-762e-43b8-a6e1-7cf493ce9988', 'IT-18');

insert into schedule.courses (course_id, course_name, course_description) values ('1d95bc79-a549-4d2c-aeb5-3f929aee0f22', 'math', 'course of Mathematics');
insert into schedule.courses (course_id, course_name, course_description) values ('1d95bc79-a549-4d2c-aeb5-3f929aee1234', 'biology', 'course of Biology');
insert into schedule.courses (course_id, course_name, course_description) values ('1d95bc79-a549-4d2c-aeb5-3f929aee5324', 'chemistry', 'course of Chemistry');
insert into schedule.courses (course_id, course_name, course_description) values ('1d95bc79-a549-4d2c-aeb5-3f929aee6589', 'physics', 'course of Physics');
insert into schedule.courses (course_id, course_name, course_description) values ('1d95bc79-a549-4d2c-aeb5-3f929aee8999', 'philosophy', 'course of Philosophy');
insert into schedule.courses (course_id, course_name, course_description) values ('1d95bc79-a549-4d2c-aeb5-3f929aee0096', 'drawing', 'course of Drawing');
insert into schedule.courses (course_id, course_name, course_description) values ('1d95bc79-a549-4d2c-aeb5-3f929aee1222', 'literature', 'course of Literature');
insert into schedule.courses (course_id, course_name, course_description) values ('1d95bc79-a549-4d2c-aeb5-3f929aee7658', 'English', 'course of English');
insert into schedule.courses (course_id, course_name, course_description) values ('1d95bc79-a549-4d2c-aeb5-3f929aee3356', 'geography', 'course of Geography');
insert into schedule.courses (course_id, course_name, course_description) values ('1d95bc79-a549-4d2c-aeb5-3f929aee0887', 'physical training', 'course of Physical training');

insert into schedule.students (user_id, group_id, first_name, last_name, email, registration_status) values ('33c99439-aaf0-4ebd-a07a-bd0c550db4e1', '3c01e6f1-762e-43b8-a6e1-7cf493ce92e2', 'John', 'Doe', 'dis@ukr.net', 'NEW');
insert into schedule.students (user_id, group_id, first_name, last_name, email, registration_status) values ('33c99439-aaf0-4ebd-a07a-bd0c550d2311', '3c01e6f1-762e-43b8-a6e1-7cf493ce5325', 'Jane', 'Does', 'dtestMail@gmail.com', 'NEW');

insert into schedule.users_accounts (user_id, first_name, last_name, email, registration_status) values ('33c99439-aaf0-4ebd-a07a-bd0c550db498', 'Lue', 'Doest', 'dors@ukr.net', 'NEW');
insert into schedule.users_accounts (user_id, first_name, last_name, email, registration_status) values ('33c99439-aaf0-4ebd-a07a-bd0c550d2333', 'Jira', 'Luwent', 'disty@gmail.com.net', 'NEW');

insert into schedule.students_courses (user_id, course_id) values ('33c99439-aaf0-4ebd-a07a-bd0c550db4e1', '1d95bc79-a549-4d2c-aeb5-3f929aee0f22');
insert into schedule.students_courses (user_id, course_id) values ('33c99439-aaf0-4ebd-a07a-bd0c550db4e1', '1d95bc79-a549-4d2c-aeb5-3f929aee1234');
insert into schedule.students_courses (user_id, course_id) values ('33c99439-aaf0-4ebd-a07a-bd0c550d2311', '1d95bc79-a549-4d2c-aeb5-3f929aee1234');

INSERT INTO schedule.roles (role_id, role) VALUES ('54RG9439-aaf0-4ebd-a07a-bd0c550db4e1', 'ADMIN');
INSERT INTO schedule.roles (role_id, role) VALUES ('64TR9439-aaf0-4ebd-a07a-bd0c550db4e1', 'MODERATOR');
INSERT INTO schedule.roles (role_id, role) VALUES ('98LD9439-aaf0-4ebd-a07a-bd0c550db4e1', 'STUDENT');
INSERT INTO schedule.roles (role_id, role) VALUES ('PR3W9439-aaf0-4ebd-a07a-bd0c550db4e1', 'TEACHER');
INSERT INTO schedule.roles (role_id, role) VALUES ('LDG69439-aaf0-4ebd-a07a-bd0c550db4e1', 'USER');

INSERT INTO schedule.users_roles (user_id, role_id) VALUES ('33c99439-aaf0-4ebd-a07a-bd0c550db498', '54RG9439-aaf0-4ebd-a07a-bd0c550db4e1');
INSERT INTO schedule.users_roles (user_id, role_id) VALUES ('33c99439-aaf0-4ebd-a07a-bd0c550db498', 'LDG69439-aaf0-4ebd-a07a-bd0c550db4e1');
INSERT INTO schedule.users_roles (user_id, role_id) VALUES ('33c99439-aaf0-4ebd-a07a-bd0c550d2333', '98LD9439-aaf0-4ebd-a07a-bd0c550db4e1');
INSERT INTO schedule.users_roles (user_id, role_id) VALUES ('33c99439-aaf0-4ebd-a07a-bd0c550d2333', 'LDG69439-aaf0-4ebd-a07a-bd0c550db4e1');
