create database  Xhospital;
use Xhospital;-- 创建 users 表


-- 创建 users 表，并添加 user_type 字段
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       contact_info VARCHAR(255)

);
alter table  users add  column user_type VARCHAR(50) NOT NULL;
ALTER TABLE users MODIFY COLUMN user_type VARCHAR(255) DEFAULT 'user';

-- 创建 doctors 表
CREATE TABLE doctors (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         specialty VARCHAR(255),
                         available_time VARCHAR(255),
                         username VARCHAR(255) NOT NULL,
                         password VARCHAR(255) NOT NULL
);
alter table  doctors add  column user_type VARCHAR(50) NOT NULL;
UPDATE doctors SET user_type = 'doctor' WHERE id = 1;

-- 创建 appointments 表
CREATE TABLE appointments (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              user_id INT,
                              doctor_id INT,
                              appointment_time DATETIME,
                              FOREIGN KEY (user_id) REFERENCES users(id),
                              FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

-- 创建 reviews 表
CREATE TABLE reviews (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         user_id INT,
                         doctor_id INT,
                         rating INT,
                         review_content VARCHAR(255),
                         FOREIGN KEY (user_id) REFERENCES users(id),
                         FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

-- 创建 admins 表
CREATE TABLE admins (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL
);
alter table  admins add  column user_type VARCHAR(50) NOT NULL;
UPDATE admins SET user_type = 'admin' WHERE id = 1;

CREATE TABLE chat_messages (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               sender VARCHAR(255) NOT NULL,
                               receiver VARCHAR(255) NOT NULL,
                               message TEXT NOT NULL,
                               timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

