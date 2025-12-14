package server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import server.dto.UserDTO;
import server.dto.UserRole;
import server.service.user.UserService;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.default.login}")
    private String defaultAdminLogin;

    @Value("${app.admin.default.password}")
    private String defaultAdminPassword;

    @Bean
    public CommandLineRunner createDefaultAdmin() {
        return args -> {
            if(!userService.isAdminExists()) {
                UserDTO admin = new UserDTO(defaultAdminLogin,
                        passwordEncoder.encode(defaultAdminPassword),
                        UserRole.ADMIN);
                userService.save(admin);
                System.out.println("Created new default admin");
                System.out.println("\tlogin: " + defaultAdminLogin);
                System.out.println("\tpassword: " + defaultAdminPassword);
            }
        };
    }
}
