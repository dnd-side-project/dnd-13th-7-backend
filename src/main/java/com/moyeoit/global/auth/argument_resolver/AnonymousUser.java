package com.moyeoit.global.auth.argument_resolver;

import com.moyeoit.global.exception.AppException;
import com.moyeoit.global.exception.BaseErrorCode;

public class AnonymousUser implements AccessUser {

    @Override
    public Long getId() {
        throw new AppException(BaseErrorCode.UNAUTHORIZED);
    }

    @Override
    public String getName() {
        throw new AppException(BaseErrorCode.UNAUTHORIZED);
    }

    @Override
    public String getEmail() {
        throw new AppException(BaseErrorCode.UNAUTHORIZED);
    }

    @Override
    public boolean isActive() {
        throw new AppException(BaseErrorCode.UNAUTHORIZED);
    }

}
