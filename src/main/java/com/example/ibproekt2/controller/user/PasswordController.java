package com.example.ibproekt2.controller.user;

import com.example.ibproekt2.email.ForgotPasswordEmailContext;
import com.example.ibproekt2.entity.dto.ResetPasswordData;
import com.example.ibproekt2.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ResourceBundle;


@Controller
@RequestMapping("/password")
@RequiredArgsConstructor
public class PasswordController {

    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String MSG = "resetPasswordMsg";

    private final MessageSource messageSource;

    private final UserService userService;

    @GetMapping("/forgot")
    public String forgotPassword(Model model){
        model.addAttribute("forgotPassword", new ResetPasswordData());
        return "forgotPassword";
    }

    @PostMapping("/request")
    public String resetPassword(@ModelAttribute("forgotPassword")ResetPasswordData resetPasswordData, RedirectAttributes redirAttr) {
        System.out.println(resetPasswordData);
        try {
            userService.forgottenPassword(resetPasswordData.getEmail());
        } catch (Exception e) {
            // log the error
        }
//        redirAttr.addFlashAttribute(MSG,
//                messageSource.getMessage("user.forgotpwd.msg", null, LocaleContextHolder.getLocale())
//        );
        return REDIRECT_LOGIN;
    }

    @GetMapping("/change")
    public String changePassword(@RequestParam(required = false) String token, final RedirectAttributes redirAttr, final Model model) {
        if (token == null) {
//            redirAttr.addFlashAttribute("tokenError",
//                    messageSource.getMessage("user.registration.verification.missing.token", null, LocaleContextHolder.getLocale())
//            );
            return REDIRECT_LOGIN;
        }

        ResetPasswordData data = new ResetPasswordData();
        data.setToken(token);
        setResetPasswordForm(model, data);

        return "/changePassword";
    }

    @PostMapping("/change")
    public String changePassword(final ResetPasswordData data, final Model model) {
        try {
            userService.updatePassword(data.getPassword(), data.getToken());
        } catch (Exception e) {
            // log error statement
//            model.addAttribute("tokenError",
//                    messageSource.getMessage("user.registration.verification.invalid.token", null, LocaleContextHolder.getLocale())
//            );
            e.printStackTrace();
            return "/changePassword";
        }
//        model.addAttribute("passwordUpdateMsg",
//                messageSource.getMessage("user.password.updated.msg", null, LocaleContextHolder.getLocale())
//        );
        setResetPasswordForm(model, new ResetPasswordData());
        return "/changePassword";
    }

    private void setResetPasswordForm(final Model model, ResetPasswordData data){
        model.addAttribute("forgotPassword",data);
    }
}