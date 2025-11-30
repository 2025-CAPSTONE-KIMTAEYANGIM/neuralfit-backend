/*
CREATE DATABASE neuralfit_test;
CREATE USER 'neuralfit_test'@'%' IDENTIFIED BY 'neuralfit_test';
GRANT ALL PRIVILEGES ON neuralfit_test.* TO 'neuralfit_test'@'%';
 */

-- ============================
-- 1. app_users
-- ============================
CREATE TABLE app_users
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    email      VARCHAR(255) UNIQUE,
    name       VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    user_role  ENUM('PATIENT', 'THERAPIST') NOT NULL,

    created_at DATETIME NULL, -- JPA: LocalDateTime
    updated_at DATETIME NULL  -- JPA: LocalDateTime
);

-- ============================
-- 2. patients
-- ============================
CREATE TABLE patients
(
    id        INT PRIMARY KEY, -- FK → app_users(id)
    birthdate DATE NOT NULL,   -- JPA: LocalDate
    gender    ENUM('FEMALE', 'MALE') NOT NULL,

    CONSTRAINT fk_patient_user
        FOREIGN KEY (id) REFERENCES app_users (id)
);

-- ============================
-- 3. therapists
-- ============================
CREATE TABLE therapists
(
    id             INT PRIMARY KEY, -- FK → app_users(id)
    organization   VARCHAR(255) NOT NULL,
    therapist_type ENUM('DOCTOR', 'NURSE') NOT NULL,

    CONSTRAINT fk_therapist_user
        FOREIGN KEY (id) REFERENCES app_users (id)
);

-- ============================
-- 4. user_connection
-- ============================
CREATE TABLE user_connection
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    therapist_id INT NOT NULL,
    patient_id   INT NOT NULL,
    created_at   DATETIME NULL, -- JPA: LocalDateTime

    UNIQUE KEY uq_therapist_patient (therapist_id, patient_id),

    CONSTRAINT fk_uc_therapist
        FOREIGN KEY (therapist_id) REFERENCES therapists (id),

    CONSTRAINT fk_uc_patient
        FOREIGN KEY (patient_id) REFERENCES patients (id)
);

-- ============================
-- 5. medical_record
-- ============================
CREATE TABLE medical_record
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    connection_id     INT  NOT NULL, -- FK → user_connection(id)
    consultation_date DATE NOT NULL, -- JPA: LocalDate

    consultation_type ENUM('FIRST', 'OTHER'),
    dx                ENUM('CN', 'DEMENTIA', 'MCI') NOT NULL,

    description       VARCHAR(255),
    patient_comment   VARCHAR(255),

    moca              INT,
    mmse              INT,
    faq               INT,
    ldel_total        INT,
    adas13            INT,

    abeta DOUBLE,
    ptau DOUBLE,
    ecog_pt_mem DOUBLE,
    ecog_pt_total DOUBLE,

    created_at        DATETIME NULL, -- JPA: LocalDateTime
    updated_at        DATETIME NULL, -- JPA: LocalDateTime

    UNIQUE KEY uq_connection_date (connection_id, consultation_date),

    CONSTRAINT fk_medical_record_connection
        FOREIGN KEY (connection_id) REFERENCES user_connection (id)
);
