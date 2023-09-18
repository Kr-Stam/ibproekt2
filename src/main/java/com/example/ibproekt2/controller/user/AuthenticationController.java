package com.example.ibproekt2.controller.user;


import com.example.ibproekt2.entity.dto.UserDto;
import com.example.ibproekt2.service.impl.UserService;
import com.example.ibproekt2.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@Slf4j
//@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public String register(
            @ModelAttribute("user") UserDto user
            ){
        try {
            if(!Utils.isEmailValid(user.getEmail()))
                throw new Exception();

            userService.register(RegisterRequest.builder()
                    ._username(user.getUsername())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .build());

            return "redirect:/home";
        }catch (Exception e){
            return "redirect:/register?error";
        }
    }

    @GetMapping("/register")
    public String getRegisterPage(
            Model model,
            @RequestParam("error") Optional<Object> error
    ){
        if(error.isPresent()){
            model.addAttribute("registerError", true);
        }
        return "register";
    }
    @GetMapping("/login")
    public String login(
            Model model,
            @RequestParam(value = "error", required = false) Optional<Object> error,
            @RequestParam(value = "invalid_session", required = false) Optional<Object> invalidSession,
            @RequestParam(value = "forgot", required = false) Optional<Object> forgot
    ){
        if(error.isPresent()){
            model.addAttribute("loginError", true);
        }else if(invalidSession.isPresent()){
            model.addAttribute("invalidSession", true);
        }else if(forgot.isPresent()){
            model.addAttribute("forgot", true);
        }
        return "login";
    }

    @PostMapping("/login_failure")
    public String loginFailure(Model model){

        return "redirect:/login?error";
    }


    @GetMapping("/logout")
    public String logout(Model model){
        return "redirect:/home";
    }

    @GetMapping("/verify")
    public String verifyCustomer(@RequestParam(required = false) String token, final Model model, RedirectAttributes redirAttr){

//        log.info("STIGA");
        if(token == null){
            redirAttr.addFlashAttribute("tokenError", "user.registration.verification.missing.token");
            return "redirect:/login";
        }
        try {
            userService.verifyUser(token);
        } catch (Exception e) {
            redirAttr.addFlashAttribute("tokenError", "user.registration.verification.invalid.token");
            return "redirect:/login";
        }

        redirAttr.addFlashAttribute("verifiedAccountMsg", "user.registration.verification.success");
        return "redirect:/login";
    }
}
