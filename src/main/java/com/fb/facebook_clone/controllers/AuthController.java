package com.fb.facebook_clone.controllers;

import com.fb.facebook_clone.exceptions.AuthException;
import com.fb.facebook_clone.models.User;
import com.fb.facebook_clone.services.AuthService;
import com.fb.facebook_clone.utils.Utils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(name = "Auth")
public class AuthController {
    private final AuthService auth;

    @GetMapping(path = {"/auth/login", "/login"})
    public String showLoginPage(HttpSession session, Model model) {
        Login login = new Login("", "");
        model.addAttribute("login", login);
        if(session != null && session.getAttribute("loginError") != null) {
            model.addAttribute("loginError", session.getAttribute("loginError"));
            session.invalidate();
        }
        return "login";
    }

    record Login(String emailOrUsername, String password) {
        public boolean isEmail() {
            return emailOrUsername.contains("@");
        }
    }

    @PostMapping("/verify/login")
    public String login(HttpSession httpSession, @Validated Login login) {
        try {
            User user;
            if(login.isEmail()) {
                user = auth.loginWithEmail(login.emailOrUsername, login.password);
            } else {
                user = auth.loginWithUsername(login.emailOrUsername, login.password);
            }
            auth.setSession(httpSession, auth.userRecord(user));
            return "redirect:/";
        } catch (Exception e) {
            httpSession.setAttribute("loginError", e.getMessage().replace("AuthException: ",  ""));
            return "redirect:/auth/login";
        }
    }

    @GetMapping(path = {"/auth/signup", "/signup"})
    public String showSignupPage(HttpSession httpSession, Model model) {
        Signup signup = new Signup("","","", "", "", "");
        model.addAttribute("signup", signup);
        if(httpSession != null && httpSession.getAttribute("signupError") != null) {
            model.addAttribute("signupError", httpSession.getAttribute("signupError"));
            httpSession.invalidate();
        }
        return "signup";
    }

    record Signup(String firstName, String lastName, String emailAddress, String gender, String username, String password) {}

    @PostMapping("/verify/signup")
    public String signup(HttpSession httpSession, @Validated Signup signup) {
        try {
            User user = auth.signup(
                    signup.emailAddress.trim(), signup.password.trim(), Utils.getGenderFromString(signup.gender.trim()),
                    signup.firstName.trim(), signup.lastName.trim(), signup.username.trim()
            );
            auth.setSession(httpSession, auth.userRecord(user));
            return "redirect:/";
        } catch (AuthException e) {
            httpSession.setAttribute("signupError", e.getMessage().replace("AuthException: ",  ""));
            return "redirect:/auth/signup";
        }
    }
}