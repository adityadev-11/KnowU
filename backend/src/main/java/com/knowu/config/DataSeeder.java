package com.knowu.config;

import com.knowu.model.*;
import com.knowu.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final SchoolClassRepository classRepo;
    private final StudentRepository     studentRepo;
    private final ExamRepository        examRepo;
    private final UserRepository        userRepo;
    private final PasswordEncoder       passwordEncoder;

    public DataSeeder(SchoolClassRepository classRepo,
                      StudentRepository studentRepo,
                      ExamRepository examRepo,
                      UserRepository userRepo,
                      PasswordEncoder passwordEncoder) {
        this.classRepo       = classRepo;
        this.studentRepo     = studentRepo;
        this.examRepo        = examRepo;
        this.userRepo        = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // ── Seed default admin user ──
        if (!userRepo.existsByEmail("admin@knowu.edu")) {
            userRepo.save(new User(
                "Admin Kumar",
                "admin@knowu.edu",
                passwordEncoder.encode("admin123"),
                User.Role.ADMIN
            ));
            System.out.println("🔐 Default admin created: admin@knowu.edu / admin123");
        }

        // ── Seed sample data (only if empty) ──
        if (classRepo.count() > 0) return;

        System.out.println("🌱 Seeding KnowU sample data...");

        // Classes
        SchoolClass xii_sci_a = classRepo.save(SchoolClass.builder().name("XII – Science A").section("A").stream("Science").grade(12).build());
        SchoolClass xii_sci_b = classRepo.save(SchoolClass.builder().name("XII – Science B").section("B").stream("Science").grade(12).build());
        SchoolClass xi_com    = classRepo.save(SchoolClass.builder().name("XI – Commerce B").section("B").stream("Commerce").grade(11).build());
        SchoolClass x_arts    = classRepo.save(SchoolClass.builder().name("X – Arts").section("A").stream("Arts").grade(10).build());

        // Students
        Student s1 = Student.builder().studentId("STU2481").firstName("Rahul").lastName("Kumar")
                .dateOfBirth(LocalDate.of(2007, 4, 12)).gender(Student.Gender.MALE)
                .phone("9876543210").email("rahul@knowu.edu").schoolClass(xii_sci_a)
                .status(Student.StudentStatus.ACTIVE).build();

        Student s2 = Student.builder().studentId("STU2480").firstName("Priya").lastName("Sharma")
                .dateOfBirth(LocalDate.of(2008, 7, 22)).gender(Student.Gender.FEMALE)
                .phone("9845123456").email("priya@knowu.edu").schoolClass(xi_com)
                .status(Student.StudentStatus.ACTIVE).build();

        Student s3 = Student.builder().studentId("STU2479").firstName("Arjun").lastName("Mehta")
                .dateOfBirth(LocalDate.of(2009, 1, 5)).gender(Student.Gender.MALE)
                .phone("9932145678").email("arjun@knowu.edu").schoolClass(x_arts)
                .status(Student.StudentStatus.PENDING).build();

        Student s4 = Student.builder().studentId("STU2478").firstName("Sneha").lastName("Gupta")
                .dateOfBirth(LocalDate.of(2007, 10, 30)).gender(Student.Gender.FEMALE)
                .phone("9901234567").email("sneha@knowu.edu").schoolClass(xii_sci_b)
                .status(Student.StudentStatus.ACTIVE).build();

        Student s5 = Student.builder().studentId("STU2477").firstName("Rohan").lastName("Verma")
                .dateOfBirth(LocalDate.of(2008, 3, 15)).gender(Student.Gender.MALE)
                .phone("9812345678").email("rohan@knowu.edu").schoolClass(xii_sci_a)
                .status(Student.StudentStatus.ACTIVE).build();

        studentRepo.saveAll(List.of(s1, s2, s3, s4, s5));

        // Exams
        Exam e1 = new Exam();
        e1.setTitle("Physics Unit Test 3"); e1.setSubject("Physics");
        e1.setSchoolClass(xii_sci_a); e1.setType(Exam.ExamType.UNIT_TEST);
        e1.setExamDate(LocalDate.now().plusDays(3)); e1.setStartTime(LocalTime.of(10, 0));
        e1.setDurationMinutes(90); e1.setRoom("Room 201"); e1.setMaxMarks(40);
        e1.setStatus(Exam.ExamStatus.SCHEDULED);

        Exam e2 = new Exam();
        e2.setTitle("Chemistry Mid-Term"); e2.setSubject("Chemistry");
        e2.setSchoolClass(xii_sci_b); e2.setType(Exam.ExamType.MID_TERM);
        e2.setExamDate(LocalDate.now().plusDays(5)); e2.setStartTime(LocalTime.of(9, 0));
        e2.setDurationMinutes(180); e2.setRoom("Hall A"); e2.setMaxMarks(100);
        e2.setStatus(Exam.ExamStatus.SCHEDULED);

        Exam e3 = new Exam();
        e3.setTitle("Mathematics Chapter 7"); e3.setSubject("Mathematics");
        e3.setSchoolClass(xii_sci_a); e3.setType(Exam.ExamType.UNIT_TEST);
        e3.setExamDate(LocalDate.now().plusDays(8)); e3.setStartTime(LocalTime.of(11, 30));
        e3.setDurationMinutes(90); e3.setRoom("Room 305"); e3.setMaxMarks(40);
        e3.setStatus(Exam.ExamStatus.SCHEDULED);

        Exam e4 = new Exam();
        e4.setTitle("English Comprehension"); e4.setSubject("English");
        e4.setSchoolClass(x_arts); e4.setType(Exam.ExamType.UNIT_TEST);
        e4.setExamDate(LocalDate.now().plusDays(10)); e4.setStartTime(LocalTime.of(9, 0));
        e4.setDurationMinutes(60); e4.setRoom("Room 102"); e4.setMaxMarks(25);
        e4.setStatus(Exam.ExamStatus.SCHEDULED);

        examRepo.saveAll(List.of(e1, e2, e3, e4));

        System.out.println("✅ Seeding complete – classes: " + classRepo.count()
                + ", students: " + studentRepo.count()
                + ", exams: " + examRepo.count());
    }
}
