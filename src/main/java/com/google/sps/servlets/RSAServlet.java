package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/rsa-data")
public class RSAServlet extends HttpServlet {

    public RSAServlet() {

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        response.setContentType("application/json");
        String responseJson;

        if (userService.isUserLoggedIn()) {
            String logoutUrl = userService.createLogoutURL("/rsa.html");
            responseJson = "{ \"link\" : \"" + logoutUrl + "\", \"linkText\" : \"Log out\", \"welcome\" : \"Hey logged in user!\" }";
        }
        else {
            String loginUrl = userService.createLoginURL("/rsa.html");
            responseJson = "{ \"link\" : \"" + loginUrl + "\", \"linkText\" : \"Log in\", \"welcome\" : \"Hey stranger!\" }";
        }

        response.getWriter().println(responseJson);
    }
}