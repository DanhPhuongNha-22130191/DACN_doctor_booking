-- Chèn dữ liệu mẫu cho Hospital
INSERT INTO hospitals (name, address, phone, email, status, created_at, updated_at) VALUES
('Bệnh viện Đa khoa Trung ương', '123 Đường Lê Lợi, Quận 1, TP. HCM', '02838291234', 'info@bvdktw.vn', 'active', NOW(), NOW()),
('Bệnh viện Đại học Y Dược', '215 Hồng Bàng, Quận 5, TP. HCM', '02838554269', 'lienhe@umc.edu.vn', 'active', NOW(), NOW()),
('Bệnh viện Chợ Rẫy', '201B Nguyễn Chí Thanh, Quận 5, TP. HCM', '02838554137', 'bvchoray@choray.vn', 'active', NOW(), NOW());

-- Chèn dữ liệu mẫu cho Department
INSERT INTO departments (hospital_id, name, status, created_at, updated_at) VALUES
(1, 'Khoa Nội', 'active', NOW(), NOW()),
(1, 'Khoa Ngoại', 'active', NOW(), NOW()),
(1, 'Khoa Sản', 'active', NOW(), NOW()),
(2, 'Khoa Tim mạch', 'active', NOW(), NOW()),
(2, 'Khoa Nhi', 'active', NOW(), NOW()),
(2, 'Khoa Da liễu', 'active', NOW(), NOW()),
(3, 'Khoa Chấn thương chỉnh hình', 'active', NOW(), NOW()),
(3, 'Khoa Hồi sức cấp cứu', 'active', NOW(), NOW()),
(1, 'Khoa Răng Hàm Mặt', 'active', NOW(), NOW()),
(1, 'Khoa Tai Mũi Họng', 'active', NOW(), NOW()),
(2, 'Khoa Ung Bướu', 'active', NOW(), NOW()),
(2, 'Khoa Thần Kinh', 'active', NOW(), NOW()),
(3, 'Khoa Phục Hồi Chức Năng', 'active', NOW(), NOW()),
(3, 'Khoa Xét Nghiệm', 'active', NOW(), NOW());

-- Chèn dữ liệu mẫu cho Doctor
INSERT INTO doctors (hospital_id, department_id, name, phone, email, status, created_at, updated_at) VALUES
(1, 1, 'BS. Nguyễn Văn A', '0901234567', 'nva@hospital.com', 'active', NOW(), NOW()),
(1, 2, 'BS. Trần Thị B', '0907654321', 'ttb@hospital.com', 'active', NOW(), NOW()),
(2, 4, 'BS. Phạm Văn C', '0912345678', 'pvc@hospital.com', 'active', NOW(), NOW()),
(2, 5, 'BS. Lê Thị D', '0918765432', 'ltd@hospital.com', 'active', NOW(), NOW()),
(3, 7, 'BS. Hoàng Văn E', '0922334455', 'hve@hospital.com', 'active', NOW(), NOW()),
(3, 8, 'BS. Vũ Thị F', '0933445566', 'vtf@hospital.com', 'active', NOW(), NOW());

-- Chèn dữ liệu mẫu cho User
INSERT INTO users (username, password, email, full_name, phone, role, created_at, updated_at) VALUES
('admin', 'admin123', 'admin@example.com', 'Quản trị viên', '0123456789', 'ADMIN', NOW(), NOW()),
('patient1', 'password123', 'patient1@example.com', 'Nguyễn Văn Bệnh', '0987654321', 'PATIENT', NOW(), NOW()),
('patient2', 'password456', 'patient2@example.com', 'Trần Thị Khám', '0977665544', 'PATIENT', NOW(), NOW()),
('doctor1', 'doctor123', 'doctor1@example.com', 'Bác sĩ Minh', '0966554433', 'DOCTOR', NOW(), NOW());

-- Chèn dữ liệu mẫu cho Appointment
INSERT INTO appointments (user_id, doctor_id, appointment_date, status, reason, created_at, updated_at) VALUES
(2, 1, DATE_ADD(NOW(), INTERVAL 1 DAY), 'PENDING', 'Khám sức khỏe tổng quát', NOW(), NOW()),
(2, 3, DATE_ADD(NOW(), INTERVAL 2 DAY), 'CONFIRMED', 'Đau ngực kéo dài', NOW(), NOW()),
(3, 2, DATE_ADD(NOW(), INTERVAL 3 DAY), 'PENDING', 'Tư vấn phẫu thuật', NOW(), NOW()),
(3, 4, DATE_ADD(NOW(), INTERVAL 4 DAY), 'CANCELLED', 'Khám nhi cho bé', NOW(), NOW()),
(3, 5, DATE_SUB(NOW(), INTERVAL 1 DAY), 'COMPLETED', 'Đau lưng sau tai nạn', NOW(), NOW());
