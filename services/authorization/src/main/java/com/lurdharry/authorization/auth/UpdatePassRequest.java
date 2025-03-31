package com.lurdharry.authorization.auth;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public record UpdatePassRequest(
        @NotNull(message = "password is required")
        String oldPassword,

        @NotNull(message = "new password is required")
        String newPassword,

        String confirmNewPassword
) {

    @AssertTrue(message = "New password and confirmation password do not match")
    public boolean doesPasswordMatch(){
        return newPassword != null && newPassword.equals(confirmNewPassword);
    }

}
