CREATE TABLE application (
 application_id INT NOT NULL,
 personal_number VARCHAR(500),
 name VARCHAR(500),
 age VARCHAR(500),
 address VARCHAR(500),
 phone_number VARCHAR(500),
 email VARCHAR(500),
 skill_level VARCHAR(500),
 sibling VARCHAR(500)
);

ALTER TABLE application ADD CONSTRAINT PK_application PRIMARY KEY (application_id);


CREATE TABLE application_instrument (
 application_id  INT NOT NULL,
 typeOfInstrument VARCHAR(500)
);


CREATE TABLE email (
 email_id CHAR(10) NOT NULL,
 email CHAR(10)
);

ALTER TABLE email ADD CONSTRAINT PK_email PRIMARY KEY (email_id);


CREATE TABLE instructor (
 instructor_id INT NOT NULL,
 name VARCHAR(500),
 address VARCHAR(500),
 phoneNumber VARCHAR(500),
 email VARCHAR(500),
 teach_ensambles VARCHAR(500)
);

ALTER TABLE instructor ADD CONSTRAINT PK_instructor PRIMARY KEY (instructor_id);


CREATE TABLE instructor_instrument (
 instructor_id INT NOT NULL,
 instrument VARCHAR(500)
);

ALTER TABLE instructor_instrument ADD CONSTRAINT PK_instructor_instrument PRIMARY KEY (instructor_id);


CREATE TABLE instructor_payment (
 payment_id INT NOT NULL,
 instructor_id INT NOT NULL,
 amount INT
);

ALTER TABLE instructor_payment ADD CONSTRAINT PK_instructor_payment PRIMARY KEY (payment_id,instructor_id);


CREATE TABLE instrument (
 instrument_id INT NOT NULL,
 student_id VARCHAR(500),
 instrument VARCHAR(500),
 brand VARCHAR(500),
 available VARCHAR(500),
 quantity VARCHAR(500)
);

ALTER TABLE instrument ADD CONSTRAINT PK_instrument PRIMARY KEY (instrument_id);


CREATE TABLE phone (
 phone_id INT NOT NULL,
 phone_number VARCHAR(500)
);

ALTER TABLE phone ADD CONSTRAINT PK_phone PRIMARY KEY (phone_id);


CREATE TABLE student (
 student_id INT NOT NULL,
 application_id INT NOT NULL,
 personal_number VARCHAR(12),
 name VARCHAR(500),
 age VARCHAR(500),
 address VARCHAR(500),
 phone_number VARCHAR(500),
 email VARCHAR(500),
 skill_level VARCHAR(500),
 sibling VARCHAR(500)
);

ALTER TABLE student ADD CONSTRAINT PK_student PRIMARY KEY (student_id);


CREATE TABLE student_instrument (
 student_id INT NOT NULL,
 typeOfInstrument VARCHAR(500)
);

ALTER TABLE student_instrument ADD CONSTRAINT PK_student_instrument PRIMARY KEY (student_id);


CREATE TABLE student_payment (
 payment_id INT NOT NULL,
 student_id INT NOT NULL,
 amount INT,
 discount INT
);

ALTER TABLE student_payment ADD CONSTRAINT PK_student_payment PRIMARY KEY (payment_id,student_id);


CREATE TABLE taken_lesson (
 student_id INT NOT NULL,
 lesson_id VARCHAR(500)
);

ALTER TABLE taken_lesson ADD CONSTRAINT PK_taken_lesson PRIMARY KEY (student_id);


CREATE TABLE lease_contract (
 lease_id INT NOT NULL,
 instrument_id INT NOT NULL,
 price VARCHAR(500),
 time_start TIMESTAMP(500),
 time_end TIMESTAMP(500),
 student_id INT
);

ALTER TABLE lease_contract ADD CONSTRAINT PK_lease_contract PRIMARY KEY (lease_id);


CREATE TABLE lesson (
 instructor_id  INT NOT NULL,
 lesson_id INT NOT NULL,
 type_of_lesson VARCHAR(500),
 min_nr_slots VARCHAR(500),
 max_nr_slots VARCHAR(500),
 instrument VARCHAR(500),
 skill_level VARCHAR(500),
 place VARCHAR(500),
 price VARCHAR(500),
 genre VARCHAR(500)
);

ALTER TABLE lesson ADD CONSTRAINT PK_lesson PRIMARY KEY (instructor_id ,lesson_id);


CREATE TABLE parent_email (
 student_id INT NOT NULL,
 email_id CHAR(10) NOT NULL
);

ALTER TABLE parent_email ADD CONSTRAINT PK_parent_email PRIMARY KEY (student_id,email_id);


CREATE TABLE parent_phone (
 student_id INT NOT NULL,
 phone_id INT NOT NULL
);

ALTER TABLE parent_phone ADD CONSTRAINT PK_parent_phone PRIMARY KEY (student_id,phone_id);


CREATE TABLE scheme (
 instructor_id  INT NOT NULL,
 lesson_id INT NOT NULL,
 time_start TIMESTAMP(500),
 time_end TIMESTAMP(500)
);

ALTER TABLE scheme ADD CONSTRAINT PK_scheme PRIMARY KEY (instructor_id ,lesson_id);


ALTER TABLE application_instrument ADD CONSTRAINT FK_application_instrument_0 FOREIGN KEY (application_id ) REFERENCES application (application_id);


ALTER TABLE instructor_instrument ADD CONSTRAINT FK_instructor_instrument_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);


ALTER TABLE instructor_payment ADD CONSTRAINT FK_instructor_payment_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);


ALTER TABLE student ADD CONSTRAINT FK_student_0 FOREIGN KEY (application_id) REFERENCES application (application_id);


ALTER TABLE student_instrument ADD CONSTRAINT FK_student_instrument_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE student_payment ADD CONSTRAINT FK_student_payment_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE taken_lesson ADD CONSTRAINT FK_taken_lesson_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE lease_contract ADD CONSTRAINT FK_lease_contract_0 FOREIGN KEY (instrument_id) REFERENCES instrument (instrument_id);
ALTER TABLE lease_contract ADD CONSTRAINT FK_lease_contract_1 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE lesson ADD CONSTRAINT FK_lesson_0 FOREIGN KEY (instructor_id ) REFERENCES instructor (instructor_id);


ALTER TABLE parent_email ADD CONSTRAINT FK_parent_email_0 FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE parent_email ADD CONSTRAINT FK_parent_email_1 FOREIGN KEY (email_id) REFERENCES email (email_id);


ALTER TABLE parent_phone ADD CONSTRAINT FK_parent_phone_0 FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE parent_phone ADD CONSTRAINT FK_parent_phone_1 FOREIGN KEY (phone_id) REFERENCES phone (phone_id);


ALTER TABLE scheme ADD CONSTRAINT FK_scheme_0 FOREIGN KEY (instructor_id ,lesson_id) REFERENCES lesson (instructor_id ,lesson_id);


