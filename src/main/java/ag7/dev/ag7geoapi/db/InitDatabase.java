package ag7.dev.ag7geoapi.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ag7.dev.ag7geoapi.model.User;

@Component
public class InitDatabase implements CommandLineRunner {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    
    public InitDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        // User user = new User("admin", passwordEncoder.encode("admin"), "ADMIN", "BASICS");
        // this.userRepository.save(user);

    }

    
}