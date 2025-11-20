package com.moyeoit.domain.migration;

import com.moyeoit.global.auth.jwt.JwtCreateResult;
import com.moyeoit.global.auth.jwt.JwtIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mig")
public class MigrationController {

    private final JwtIssuer issuer;

    @GetMapping("/get-token")
    public String getToken(@RequestParam Long userId,
                           @RequestParam String userEmail) {
        JwtCreateResult result = issuer.issueAccess(userId, userEmail, true);
        return result.getAccessToken();
    }

}
