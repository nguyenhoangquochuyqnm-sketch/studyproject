package com.doan.cv.config;

import com.doan.cv.constant.Gender;
import com.doan.cv.entity.Company;
import com.doan.cv.entity.Role;
import com.doan.cv.entity.User;
import com.doan.cv.repository.CompanyRepository;
import com.doan.cv.repository.RoleRepository;
import com.doan.cv.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSeeder(UserRepository userRepository,
                      RoleRepository roleRepository,
                      CompanyRepository companyRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return; // đã có user rồi, tránh seed trùng mỗi lần start app
        }

        Role superAdminRole = roleRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Role SUPER_ADMIN (id=1) not found"));
        Role hrRole = roleRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Role HR (id=2) not found"));
        Role candidateRole = roleRepository.findById(3L)
                .orElseThrow(() -> new RuntimeException("Role CANDIDATE (id=3) not found"));

        Company technova = companyRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Company TechNova (id=1) not found"));
        Company finserve = companyRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Company FinServe (id=2) not found"));

        User superAdmin = new User();
        superAdmin.setName("admin");
        superAdmin.setEmail("admin@gmail.com");
        superAdmin.setPassword(passwordEncoder.encode("123456"));
        superAdmin.setAge(30);
        superAdmin.setGender(Gender.MALE);
        superAdmin.setAddress("Hà Nội");
        superAdmin.setRole(superAdminRole);

        User hrAlice = new User();
        hrAlice.setName("Alice Nguyen");
        hrAlice.setEmail("hr.alice@technova.com");
        hrAlice.setPassword(passwordEncoder.encode("123456"));
        hrAlice.setAge(28);
        hrAlice.setGender(Gender.FEMALE);
        hrAlice.setAddress("TP. Hồ Chí Minh");
        hrAlice.setRole(hrRole);
        hrAlice.setCompany(technova);

        User hrBob = new User();
        hrBob.setName("Bob Tran");
        hrBob.setEmail("hr.bob@finserve.com");
        hrBob.setPassword(passwordEncoder.encode("123456"));
        hrBob.setAge(32);
        hrBob.setGender(Gender.MALE);
        hrBob.setAddress("Hà Nội");
        hrBob.setRole(hrRole);
        hrBob.setCompany(finserve);

        User candidate1 = new User();
        candidate1.setName("Candidate One");
        candidate1.setEmail("candidate1@gmail.com");
        candidate1.setPassword(passwordEncoder.encode("123456"));
        candidate1.setAge(24);
        candidate1.setGender(Gender.MALE);
        candidate1.setAddress("Đà Nẵng");
        candidate1.setRole(candidateRole);

        User candidate2 = new User();
        candidate2.setName("Candidate Two");
        candidate2.setEmail("candidate2@gmail.com");
        candidate2.setPassword(passwordEncoder.encode("123456"));
        candidate2.setAge(23);
        candidate2.setGender(Gender.FEMALE);
        candidate2.setAddress("TP. Hồ Chí Minh");
        candidate2.setRole(candidateRole);

        userRepository.saveAll(java.util.List.of(superAdmin, hrAlice, hrBob, candidate1, candidate2));

        System.out.println("✅ Seeded 5 users successfully.");
    }
}