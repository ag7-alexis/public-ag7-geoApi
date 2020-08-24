package ag7.dev.ag7geoapi.controller;

import com.auth0.jwt.JWT;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ag7.dev.ag7geoapi.controller.viewmodel.APIKeyViewModel;
import ag7.dev.ag7geoapi.controller.viewmodel.LoginViewModel;
import ag7.dev.ag7geoapi.db.UserRepository;
import ag7.dev.ag7geoapi.model.User;
import ag7.dev.ag7geoapi.security.JwtProperties;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.util.Calendar;
import java.util.Date;

/**
 * User Controller : to manage user info
 */

@RestController
@RequestMapping(value = "/")
@CrossOrigin
public class UserController {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * request to register
     *
     * @param loginViewModel LoginView
     * @param bindingResult  bR
     */

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody LoginViewModel loginViewModel, BindingResult bindingResult) {

        if (userRepository.findByUsername(loginViewModel.getUsername()) != null)
            return new ResponseEntity<>(new String("Username allready exist"), new HttpHeaders(),
                    HttpStatus.INTERNAL_SERVER_ERROR);

        User newUser = new User(loginViewModel.getUsername(), passwordEncoder.encode(loginViewModel.getPassword()),
                "USER", "RESTRICTED");
        newUser.setDateEndApiKay(new Date());
        userRepository.save(newUser);

        return ResponseEntity.ok(true);
    }

    /**
     * 
     * Request to get api key of a user and update if the key is expired
     * 
     * @param username    username
     * @param headerToken token present in request header
     * @return APIKeyViewModel
     */

    @RequestMapping(value = "info/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserInfo(@PathVariable String username,
            @RequestHeader(name = "Authorization") String headerToken) {
        String token = headerToken.replace(JwtProperties.PREFIX, "");
        String testUsername = JWT.require(HMAC512(JwtProperties.SECRET.getBytes())).build().verify(token).getSubject();
        Date dateExpi = JWT.require(HMAC512(JwtProperties.SECRET.getBytes())).build().verify(token).getExpiresAt();

        if (!username.equals(testUsername))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        User user = this.userRepository.findByUsername(username);

        APIKeyViewModel apiKeyViewModel = new APIKeyViewModel();

        if (user.getApiKey() == null) {
            user.setApiKey(headerToken);
            user.setDateEndApiKay(dateExpi);
            this.userRepository.save(user);

            apiKeyViewModel.setUpdate(true);
        } else {
            Date datePreExpi = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(user.getDateEndApiKay());
            c.add(Calendar.DATE, -15);
            datePreExpi = c.getTime();

            Date now = new Date();
            if (now.after(datePreExpi)) {
                apiKeyViewModel.setUpdate(true);

                user.setApiKey(headerToken);
                user.setDateEndApiKay(dateExpi);
                this.userRepository.save(user);
            } else {
                apiKeyViewModel.setUpdate(false);
            }
        }
        apiKeyViewModel.setToken(user.getApiKey());
        apiKeyViewModel.setDateEndToken(user.getDateEndApiKay());

        return ResponseEntity.ok(apiKeyViewModel);
    }

    @RequestMapping(value = "ping", method = RequestMethod.GET)
    public ResponseEntity<?> ping() {
        return ResponseEntity.ok(true);
    }
}
