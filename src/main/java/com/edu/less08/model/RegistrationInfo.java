package com.edu.less08.model;

import java.io.Serializable;

public class RegistrationInfo implements Serializable {
    private final String login;
    private final String email;
    private final String password;
    private final String confirmPassword;
    private final String termsAgreement;

    private RegistrationInfo(RegInfoBuilder builder) {
        this.login = builder.login;
        this.email = builder.email;
        this.password = builder.password;
        this.confirmPassword = builder.confirmPassword;
        this.termsAgreement = builder.termsAgreement;
    }

    public static class RegInfoBuilder {
        private String login;
        private String email;
        private String password;
        private String confirmPassword;
        private String termsAgreement;

        public RegInfoBuilder setLogin(String login) {
            this.login = login;
            return this;
        }

        public RegInfoBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public RegInfoBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public RegInfoBuilder setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
            return this;
        }

        public RegInfoBuilder setTermsAgreement(String termsAgreement) {
            this.termsAgreement = termsAgreement;
            return this;
        }

        public RegistrationInfo build() {
            return new RegistrationInfo(this);
        }
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getTermsAgreement() {
        return termsAgreement;
    }
}
