package com.moyeoit.context.admin.presentation;

import com.moyeoit.context.admin.application.AdminAccessTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminEnterController {

    private static final String ADMIN_TOKEN_COOKIE = "ADMIN_TOKEN";
    private final AdminAccessTokenService tokenService;

    @GetMapping("/enter")
    public String enterPage(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("error", error);
        return "admin/enter";
    }

    @PostMapping("/enter")
    public String enter(@RequestParam("code") String code,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        Model model) {
        if (!tokenService.matchesCode(code)) {
            model.addAttribute("error", "invalid");
            return "admin/enter";
        }

        String token = tokenService.createToken();
        Cookie cookie = new Cookie(ADMIN_TOKEN_COOKIE, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(request.isSecure());
        cookie.setMaxAge(Math.toIntExact(tokenService.ttlSeconds()));
        response.addCookie(cookie);
        return "redirect:/admin";
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(ADMIN_TOKEN_COOKIE, "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/admin/enter";
    }
}
