package com.moyeoit.context.user.controller.response;

import com.moyeoit.context.user.domain.Term;
import com.moyeoit.context.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivateResponse {

    private Long id;
    private boolean activate;
    private TermResponse term;

    public static ActivateResponse from(User user, Term term) {
        return new ActivateResponse(
                user.getId(),
                user.isActive(),
                TermResponse.from(term)
        );
    }

    public static ActivateResponse from(User user) {
        return new ActivateResponse(
                user.getId(),
                user.isActive(),
                null
        );
    }

}