package com.moyeoit.global.auth.argument_resolver;

public interface AccessUser {

    Long getId();

    String getName();

    String getEmail();

    boolean isActive();

}
