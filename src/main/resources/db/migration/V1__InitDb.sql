CREATE SCHEMA IF NOT EXISTS schedule;

CREATE TYPE registrationStatus AS ENUM ('NEW', 'REGISTERED');

CREATE TYPE roleModel AS ENUM ('ADMIN', 'MODERATOR', 'STUDENT', 'TEACHER', 'USER');

CREATE TABLE schedule.groups
(
    group_id character(36) NOT NULL,
    group_name character varying(10) NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id)
);

CREATE TABLE schedule.roles (
  role_id character(36) NOT NULL,
  role roleModel NOT NULL,
  CONSTRAINT roles_pkey PRIMARY KEY (role_id)
);
    
CREATE TABLE IF NOT EXISTS schedule.courses
(
    course_id character(36) NOT NULL,
    course_name character varying(24) NOT NULL,
    course_description character varying(36),
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
);

CREATE TABLE IF NOT EXISTS schedule.users
(
    user_type varchar(5),
    user_id character(36) NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    email character varying(36),
    password character varying(70),
    password_check character varying(70),
    registration_status registrationStatus,
    group_id character(36),
    student_card character(36),
    CONSTRAINT user_id_pkey PRIMARY KEY (user_id),
    CONSTRAINT group_id FOREIGN KEY (group_id)
    REFERENCES schedule.groups (group_id)
    ON UPDATE CASCADE
    ON DELETE SET NULL
    );

CREATE TABLE IF NOT EXISTS schedule.students_courses
(
    user_id character(36) NOT NULL,
    course_id character(36) NOT NULL,
    CONSTRAINT course_id FOREIGN KEY (course_id) REFERENCES schedule.courses (course_id)
    ON UPDATE NO ACTION
    ON DELETE CASCADE,
    CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES schedule.users (user_id)
    ON UPDATE NO ACTION
    ON DELETE CASCADE
);

CREATE TABLE schedule.users_roles (
  user_id character(36) NOT NULL,
  role_id character(36) NOT NULL,
  CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES schedule.users (user_id),
  CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES schedule.roles (role_id)
);
