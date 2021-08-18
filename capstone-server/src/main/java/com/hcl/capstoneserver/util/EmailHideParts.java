package com.hcl.capstoneserver.util;

public class EmailHideParts {
    private final String email;

    public EmailHideParts(String email) {
        this.email = email;
    }

    public String hide() {
        String[] splitted = email.split("@");
        StringBuilder encryptedEmail = new StringBuilder(splitted[0]);
        for (int i = 3; i < encryptedEmail.length(); i++) {
            encryptedEmail.setCharAt(i, '*');
        }
        encryptedEmail.append('@');
        encryptedEmail.append(splitted[1]);
        return String.format("We have sent the verification code to the registered email address: %s", encryptedEmail);
    }
}
