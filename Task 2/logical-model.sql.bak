CREATE TABLE instructor (
 instructor_id SERIAL NOT NULL,
 name VARCHAR(500),
 address VARCHAR(500),
 teach_ensambles BOOLEAN
);

ALTER TABLE instructor ADD CONSTRAINT PK_instructor PRIMARY KEY (instructor_id);


CREATE TABLE instructor_email (
 instructor_id INT NOT NULL,
 email VARCHAR(500)
);

ALTER TABLE instructor_email ADD CONSTRAINT PK_instructor_email PRIMARY KEY (instructor_id);


CREATE TABLE instructor_instrument (
 instructor_id INT NOT NULL,
 typeOfInstrument VARCHAR(500)
);

ALTER TABLE instructor_instrument ADD CONSTRAINT PK_instructor_instrument PRIMARY KEY (instructor_id);


CREATE TABLE instructor_payment (
 payment_id SERIAL NOT NULL,
 instructor_id INT NOT NULL,
 amount INT,
 date DATE
);

ALTER TABLE instructor_payment ADD CONSTRAINT PK_instructor_payment PRIMARY KEY (payment_id,instructor_id);


CREATE TABLE instructor_phone (
 instructor_id INT NOT NULL,
 phone VARCHAR(500)
);

ALTER TABLE instructor_phone ADD CONSTRAINT PK_instructor_phone PRIMARY KEY (instructor_id);


CREATE TABLE lesson (
 lesson_id SERIAL NOT NULL,
 instructor_id  INT NOT NULL,
 type_of_lesson VARCHAR(500),
 min_nr_slots VARCHAR(500),
 max_nr_slots VARCHAR(500),
 instrument VARCHAR(500),
 skill_level VARCHAR(500),
 place VARCHAR(500),
 price VARCHAR(500),
 genre VARCHAR(500),
 time_start DATE,
 time_end DATE
);

ALTER TABLE lesson ADD CONSTRAINT PK_lesson PRIMARY KEY (lesson_id);


CREATE TABLE rental_instrument (
 rental_instrument_id SERIAL NOT NULL,
 instrument VARCHAR(500),
 brand VARCHAR(500),
 available VARCHAR(500),
 quantity VARCHAR(500)
);

ALTER TABLE rental_instrument ADD CONSTRAINT PK_rental_instrument PRIMARY KEY (rental_instrument_id);


CREATE TABLE student (
 student_id SERIAL NOT NULL,
 personal_number VARCHAR(12),
 name VARCHAR(500),
 age VARCHAR(500),
 address VARCHAR(500),
 skill_level VARCHAR(500),
 sibling VARCHAR(500),
 is_accepted BOOLEAN
);

ALTER TABLE student ADD CONSTRAINT PK_student PRIMARY KEY (student_id);


CREATE TABLE student_email (
 student_id INT NOT NULL,
 email VARCHAR(500)
);

ALTER TABLE student_email ADD CONSTRAINT PK_student_email PRIMARY KEY (student_id);


CREATE TABLE student_instrument (
 student_id INT NOT NULL,
 typeOfInstrument VARCHAR(500)
);

ALTER TABLE student_instrument ADD CONSTRAINT PK_student_instrument PRIMARY KEY (student_id);


CREATE TABLE student_lesson (
 student_id INT NOT NULL,
 lesson_id INT NOT NULL
);

ALTER TABLE student_lesson ADD CONSTRAINT PK_student_lesson PRIMARY KEY (student_id,lesson_id);


CREATE TABLE student_payment (
 payment_id SERIAL NOT NULL,
 student_id INT NOT NULL,
 amount INT,
 discount INT
);

ALTER TABLE student_payment ADD CONSTRAINT PK_student_payment PRIMARY KEY (payment_id,student_id);


CREATE TABLE student_phone (
 student_id INT NOT NULL,
 phone VARCHAR(500)
);

ALTER TABLE student_phone ADD CONSTRAINT PK_student_phone PRIMARY KEY (student_id);


CREATE TABLE lease_contract (
 lease_id SERIAL NOT NULL,
 student_id INT NOT NULL,
 rental_instrument_id INT NOT NULL,
 price VARCHAR(500),
 time_start DATE,
 time_end DATE
);

ALTER TABLE lease_contract ADD CONSTRAINT PK_lease_contract PRIMARY KEY (lease_id,student_id);


CREATE TABLE parent_email (
 student_id INT NOT NULL,
 email VARCHAR(500)
);

ALTER TABLE parent_email ADD CONSTRAINT PK_parent_email PRIMARY KEY (student_id);


CREATE TABLE parent_phone (
 student_id INT NOT NULL,
 phone VARCHAR(500)
);

ALTER TABLE parent_phone ADD CONSTRAINT PK_parent_phone PRIMARY KEY (student_id);


ALTER TABLE instructor_email ADD CONSTRAINT FK_instructor_email_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);


ALTER TABLE instructor_instrument ADD CONSTRAINT FK_instructor_instrument_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);


ALTER TABLE instructor_payment ADD CONSTRAINT FK_instructor_payment_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);


ALTER TABLE instructor_phone ADD CONSTRAINT FK_instructor_phone_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);


ALTER TABLE lesson ADD CONSTRAINT FK_lesson_0 FOREIGN KEY (instructor_id ) REFERENCES instructor (instructor_id);


ALTER TABLE student_email ADD CONSTRAINT FK_student_email_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE student_instrument ADD CONSTRAINT FK_student_instrument_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE student_lesson ADD CONSTRAINT FK_student_lesson_0 FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE student_lesson ADD CONSTRAINT FK_student_lesson_1 FOREIGN KEY (lesson_id) REFERENCES lesson (lesson_id);


ALTER TABLE student_payment ADD CONSTRAINT FK_student_payment_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE student_phone ADD CONSTRAINT FK_student_phone_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE lease_contract ADD CONSTRAINT FK_lease_contract_0 FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE lease_contract ADD CONSTRAINT FK_lease_contract_1 FOREIGN KEY (rental_instrument_id) REFERENCES rental_instrument (rental_instrument_id);


ALTER TABLE parent_email ADD CONSTRAINT FK_parent_email_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE parent_phone ADD CONSTRAINT FK_parent_phone_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


