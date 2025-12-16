package com.cc6.user.config;

import com.cc6.user.classes.Role;
import com.cc6.user.classes.User;
import com.cc6.user.classes.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        createDefaultAdminUser();
    }

    private void createDefaultAdminUser() {
        try {
            // Verificar se já existe um usuário admin
            Optional<User> existingAdmin = userRepository.findByEmail("admin@sistema.com");

            if (existingAdmin.isPresent()) {
                log.info("Usuário admin padrão já existe: admin@sistema.com");
                return;
            }

            // Criar usuário admin padrão
            User adminUser = new User();
            adminUser.setName("Administrador do Sistema");
            adminUser.setEmail("admin@sistema.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setPhone("(00) 00000-0000");
            adminUser.setCpf("00000000000");
            adminUser.setAddress("Endereço do Sistema");
            adminUser.setBirthday(LocalDate.of(1990, 1, 1));
            adminUser.setRole(Role.ROLE_ADMIN);

            userRepository.save(adminUser);

            log.info("✅ Usuário admin padrão criado com sucesso!");
            log.info("📧 Email: admin@sistema.com");
            log.info("🔑 Senha: admin123");
            log.info("⚠️  IMPORTANTE: Altere a senha padrão após o primeiro login!");

        } catch (Exception e) {
            log.error("Erro ao criar usuário admin padrão: {}", e.getMessage(), e);
        }
    }
}

