--liquibase formatted sql

--changeset author:1
CREATE TABLE IF NOT EXISTS webapp.species
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);
--rollback DROP TABLE IF EXISTS webapp.species;

--changeset author:2
INSERT INTO webapp.species (id, name) VALUES
(1, 'Dog'),
(2, 'Cat'),
(3, 'Bird'),
(4, 'Hamster'),
(5, 'Rabbit');
--rollback DELETE FROM webapp.species WHERE id IN (1, 2, 3, 4, 5);

--changeset author:3
CREATE TABLE IF NOT EXISTS webapp.users
(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    birth_date DATE,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    address VARCHAR(255),
    pesel VARCHAR(11) UNIQUE,
    phone VARCHAR(255) UNIQUE,
    profile_picture VARCHAR(255),
    role VARCHAR(255),
    email_verified BOOLEAN NOT NULL,
    email_verification_token VARCHAR(255)
);
--rollback DROP TABLE IF EXISTS webapp.users;

--changeset author:4
INSERT INTO webapp.users (username, password, firstname, lastname, role, email_verified) VALUES
('test@gmail.com', '{bcrypt}$2a$10$CVmtbTsJKYumfhn0CVE2luIcJisG/Xdsm/LOANB4BPiHLt3wuZHVG','Admin','Admin', 'ADMIN', true);
--rollback DELETE FROM webapp.users WHERE username = 'test@gmail.com';

--changeset author:5
CREATE TABLE IF NOT EXISTS webapp.clinics
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255) NOT NULL
);
--rollback DROP TABLE IF EXISTS webapp.clinics;

--changeset author:6
INSERT INTO webapp.clinics (id, name, address) VALUES
(1, 'Veterinary Clinic Vetclinic', 'Grota-Roweckiego 10D'),
(2, 'Lecznica dla Zwierząt', 'Wojska Polskiego 92');
--rollback DELETE FROM webapp.clinics WHERE id IN (1, 2);

--changeset author:7
CREATE TABLE IF NOT EXISTS webapp.veterinarians
(
    id BIGSERIAL PRIMARY KEY,
    clinic_id BIGINT,
    vet_code VARCHAR(255) NOT NULL UNIQUE,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    specialization VARCHAR(255),
    contact_number VARCHAR(255),
    FOREIGN KEY (clinic_id) REFERENCES webapp.clinics (id)
);
--rollback DROP TABLE IF EXISTS webapp.veterinarians;

--changeset author:8
INSERT INTO webapp.veterinarians (id, clinic_id, vet_code, firstname, lastname, specialization, contact_number) VALUES
(1, 1, 'MS232', 'Michał', 'Smitkowicz', 'Veterinary Doctor', '+48-553-430-419'),
(2, 1, 'EK002', 'Emilia', 'Karpovicz', 'Veterinary Doctor', '+48-236-961-656'),
(3, 1, 'DR629', 'Damian', 'Ratańczuk', 'Veterinary Doctor', '+48-811-326-763'),
(4, 2, 'MM992', 'Monika', 'Mąka', 'Veterinary Doctor', '+48-873-242-002'),
(5, 2, 'ML147', 'Maciej', 'Lewandowski', 'Veterinary Doctor', '+48-581-944-110');
--rollback DELETE FROM webapp.veterinarians WHERE id IN (1, 2, 3, 4, 5);

--changeset author:9
CREATE TABLE IF NOT EXISTS webapp.pets
(
    id BIGSERIAL PRIMARY KEY,
    species_id BIGINT,
    breed VARCHAR(255),
    name VARCHAR(255) UNIQUE,
    age INT,
    gender VARCHAR(255),
    description TEXT,
    image_url VARCHAR(255),
    is_available BOOLEAN,
    FOREIGN KEY (species_id) REFERENCES webapp.species (id)
);
--rollback DROP TABLE IF EXISTS webapp.pets;

--changeset author:10
INSERT INTO webapp.pets (id, species_id, breed, name, age, gender, description, image_url, is_available) VALUES
(8, 3, 'Budgerigar', 'Talker', 0, 'MALE', 'Our lively 4-month-old male Budgerigar,with his playful antics and curious nature', '/img/Budgerigar_Talker.jpeg', true),
(9, 4, 'Syrian Hamster', 'Zeus', 1, 'MALE', 'Despite his small size, he carries himself with a quiet confidence', '/img/Syrian_Zeus.jpg', true),
(7, 3, 'Canary', 'Samolion', 1, 'MALE', 'Samolion is not just a bird; he is a charming companion, always ready to fill the air with his delightful melodies', '/img/Canary_Samolion.jpeg', true),
(6, 2, 'Siberian', 'Ivanko', 3, 'MALE', 'Our magnificent 3-year-old Siberian! With his strikingly handsome appearance', '/img/Siberian_Ivanko.jpg', true),
(11, 5, 'Holland Lop', 'Fluffy', 7, 'MALE', 'Fluffy has been a cherished member of our family for many years, filling our home with warmth and affection', '/img/hollandLop_Fluffy.jpeg', true),
(3, 1, 'Pekingese', 'Mako', 1, 'MALE', 'This little fellow is full of personality, boasting a confident demeanor and a playful spirit that knows no bounds', '/img/Mako_Pekingese.png', true),
(4, 1, 'Spitz', 'Tiny', 0, 'FEMALE', 'Tiny is a quick learner and loves nothing more than showing off his repertoire of tricks to anyone willing to watch', '/img/Tiny_Spitz.jpg', true),
(10, 4, 'Chinese Hamster', 'Elsa', 1, 'FEMALE', 'This Chinese beauty is known for her agility and curiosity, always eager to explore every nook and cranny of her surroundings', '/img/chineseHamster_Elsa.jpg', true),
(5, 2, 'Persian', 'Croopka', 1, 'FEMALE', 'Enjoys nothing more than curling up in a cozy spot beside her favorite human', '/img/Persian_Croopka.jpg', true),
(2, 2, 'British Shorthair', 'Whiskers', 3, 'FEMALE', 'Playful British Shorthair', '/img/Whiskers_British_Shorthair_image.jpg', true),
(1, 1, 'Labrador', 'Charlie', 2, 'MALE', 'Friendly Labrador Retriever', '/img/Charlie_Labrador_image.jpg', true);
--rollback DELETE FROM webapp.pets WHERE id IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);

--changeset author:11
CREATE TABLE IF NOT EXISTS webapp.bookings
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    pet_id BIGINT,
    start_date DATE,
    end_date DATE,
    status VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES webapp.users (id),
    FOREIGN KEY (pet_id) REFERENCES webapp.pets (id)
);
--rollback DROP TABLE IF EXISTS webapp.bookings;

--changeset author:12
CREATE TABLE IF NOT EXISTS webapp.medical_records
(
    id BIGSERIAL PRIMARY KEY,
    pet_id BIGINT,
    veterinarian_id BIGINT,
    diagnosis TEXT,
    treatment TEXT,
    prescription TEXT,
    examination_date DATE,
    FOREIGN KEY (pet_id) REFERENCES webapp.pets (id),
    FOREIGN KEY (veterinarian_id) REFERENCES webapp.veterinarians (id)
);
--rollback DROP TABLE IF EXISTS webapp.medical_records;

--changeset author:13
INSERT INTO webapp.medical_records (id, pet_id, veterinarian_id, diagnosis, treatment, prescription, examination_date) VALUES
(7, 7, 5, 'Feather trimming', NULL, NULL, '2024-05-12'),
(1, 2, 2, 'Vaccination', 'Vaccination, ear cleaning', 'Administered vaccination for feline viral diseases, recommended regular ear cleaning with mild solution.', '2024-05-12'),
(2, 1, 1, 'Dental cleaning', 'Dental cleaning, weight check', 'Performed dental scaling and polishing, recommended weight management diet.', '2024-05-12'),
(8, 10, 2, 'Claw clipping', NULL, NULL, '2024-05-12'),
(11, 11, 3, 'General examination', NULL, NULL, '2024-05-12'),
(6, 6, 5, 'Nail trimming', NULL, NULL, '2024-05-12'),
(9, 8, 1, 'Beak treatment', NULL, NULL, '2024-05-12'),
(10, 3, 2, 'Preventive vaccination', NULL, NULL, '2024-05-12'),
(4, 4, 2, 'Microchipping', NULL, NULL, '2024-05-12'),
(5, 5, 3, 'Routine checkup', NULL, NULL, '2024-05-12'),
(3, 9, 4, 'Checkup', 'Checkup', 'No specific treatment required, recommended balanced diet and exercise.', '2024-05-12');
--rollback DELETE FROM webapp.medical_records WHERE id IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);

--changeset author:14
CREATE TABLE IF NOT EXISTS webapp.notifications
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    booking_id BIGINT,
    message TEXT,
    timestamp TIMESTAMP,
    formatted_time VARCHAR(64),
    read BOOLEAN,
    FOREIGN KEY (user_id) REFERENCES webapp.users (id),
    FOREIGN KEY (booking_id) REFERENCES webapp.bookings (id)
);
--rollback DROP TABLE IF EXISTS webapp.notifications;

