package com.zup.gestaodeativos.config;


import com.zup.gestaodeativos.models.User;
import com.zup.gestaodeativos.dto.UserRole;
import com.zup.gestaodeativos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {
    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        String adminEmail = "admin@zup.com";
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = User.builder()
                    .nome("Administrador")
                    .email(adminEmail)
                    .telefone("99999999999")
                    .senha("admin123")
                    .role(UserRole.ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println("Admin criado com sucesso!");
        }
    }
}