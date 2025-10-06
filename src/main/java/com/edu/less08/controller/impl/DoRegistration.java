package com.edu.less08.controller.impl;

import com.edu.less08.controller.Command;
import com.edu.less08.model.RegistrationInfo;
import com.edu.less08.service.ServiceException;
import com.edu.less08.service.ServiceProvider;
import com.edu.less08.service.UserSecurityService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DoRegistration implements Command {
    private final UserSecurityService userSecurity = ServiceProvider.getInstance().getUserSecurityService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        String userLogin = request.getParameter("login");
        String userEmail = request.getParameter("email");
        String userPassword = request.getParameter("password");
        String userConfirmPassword = request.getParameter("confirmPassword");
        String termsAgreement = request.getParameter("terms");

        RegistrationInfo registrationInfo = new RegistrationInfo.RegInfoBuilder()
                .setLogin(userLogin)
                .setEmail(userEmail)
                .setPassword(userPassword)
                .setConfirmPassword(userConfirmPassword)
                .setTermsAgreement(termsAgreement)
                .build();

            if (userSecurity.registrate(registrationInfo)) {
                request.getSession().setAttribute("successRegistrationMessage", "Registration was successful. Log in please");
                response.sendRedirect("Controller?command=GO_TO_AUTH_PAGE");
            }
        } catch (ServiceException e) {
            request.getSession().setAttribute("errorMessage", e.getUserMessage());
            response.sendRedirect("Controller?command=GO_TO_REGISTRATION_PAGE");
        }
    }
}
