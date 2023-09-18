package com.example.ibproekt2.service.impl;

import com.example.ibproekt2.controller.user.RegisterRequest;
import com.example.ibproekt2.email.AccountVerificationEmailContext;
import com.example.ibproekt2.email.EmailService;
import com.example.ibproekt2.email.ForgotPasswordEmailContext;
import com.example.ibproekt2.entity.SecureToken;
import com.example.ibproekt2.entity.User;
import com.example.ibproekt2.repository.SecureTokenRepository;
import com.example.ibproekt2.repository.UserRepository;
import com.example.ibproekt2.security.Role;
import com.example.ibproekt2.service.SecureTokenService;
import com.example.ibproekt2.util.exceptions.UserExistsException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecureTokenService secureTokenService;
    private final SecureTokenRepository secureTokenRepository;
    private final EmailService emailService;

    public User register(RegisterRequest request) throws UserExistsException {
        User user = User.builder()
                ._username(request.get_username())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new UserExistsException();
        }


        userRepository.save(user);

        sendRegistrationConfirmationEmail(user);
        return user;
    }

    private String baseUrl = "http://localhost:8080";

    private void sendRegistrationConfirmationEmail(User user){
        SecureToken secureToken = secureTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenRepository.save(secureToken);

        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseUrl, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyUser(String token) throws Exception {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if(Objects.isNull(secureToken) || !(token.equals(secureToken.getToken())) || secureToken.isExpired()){
            throw new Exception("Token is not valid");
        }
        Optional<User> userOptional = userRepository.findById(secureToken.getUser().getId());
        if(userOptional.isEmpty()){
            return false;
        }
        User user = userOptional.get();
        user.setAccountVerified(true);
        userRepository.save(user); // let's same user details

        // we don't need invalid password now
        secureTokenService.removeToken(secureToken);
        return true;
    }

    public void forgottenPassword(String email) throws Exception {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty())
            throw new Exception("User not found");
        User user = userOptional.get();
        sendResetPasswordEmail(user);
    }

    public void updatePassword(String password, String token) throws Exception {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if(Objects.isNull(secureToken) || !token.equals(secureToken.getToken()) || secureToken.isExpired()){
//            System.out.println(secureToken.isExpired());
//            System.out.println(Objects.isNull(secureToken));
//            System.out.println(token.equals(secureToken.getToken()));
            throw new Exception("Token is not valid");
        }
        Optional<User> userOptional = userRepository.findById(secureToken.getUser().getId());
        if(userOptional.isEmpty()){
            throw new Exception("unable to find user for the token");
        }
        User user = userOptional.get();
        secureTokenService.removeToken(secureToken);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }


    protected void sendResetPasswordEmail(User user) {
        SecureToken secureToken= secureTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenRepository.save(secureToken);
        ForgotPasswordEmailContext emailContext = new ForgotPasswordEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseUrl, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public boolean loginDisabled(String username) {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty())
            return false;

        return user.get().isEnabled();
    }
}
