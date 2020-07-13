package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.data.RSA;
import java.math.BigInteger;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/rsa-data")
public class RSAServlet extends HttpServlet {

    private DatastoreService datastore;
    private RSA rsaHandler;

    public RSAServlet() {
        datastore = DatastoreServiceFactory.getDatastoreService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        response.setContentType("application/json");
        String responseJson;

        if (!userService.isUserLoggedIn()) {
            String loginUrl = userService.createLoginURL("/rsa.html");
            responseJson = "{ \"loggedIn\" : false, \"link\" : \"" + loginUrl + "\", \"linkText\" : \"Log in\", \"welcome\" : \"Hey stranger!\" }";
            response.getWriter().println(responseJson);
            return;
        }

        String logoutUrl = userService.createLogoutURL("/rsa.html");
        User currentUser = userService.getCurrentUser();
        String userId = currentUser.getUserId();
        String nickname = getNickname(userId);
        Boolean hasNickname = true;
        String responseKey;

        if (nickname == null) {
            nickname = "NULL";
            hasNickname = false;
            responseKey = "}";
        }
        else {
            responseKey = getKey(userId);
        }

        responseJson = "{ \"loggedIn\" : true, \"link\" : \"" + logoutUrl + 
                        "\", \"linkText\" : \"Log out\", \"welcome\" : \"Hey " + 
                        nickname + "!\"," +
                        "\"hasNickname\" : " + hasNickname.toString() + responseKey;

        response.getWriter().println(responseJson);
    }

    private String getKey(String id) {
        Query userQuery = new Query("UserData");
        userQuery.setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, id));
        Entity userData = datastore.prepare(userQuery).asSingleEntity();

        if (!userData.hasProperty("private_exponent")) {
            return ", \"hasKey\" : false }";
        }

        String publicExponent = (String) userData.getProperty("public_exponent");
        String privateExponent = (String) userData.getProperty("private_exponent");
        String publicModulus = (String) userData.getProperty("public_modulus");

        return (", \"hasKey\" : true, \"public_exponent\" : \"" + publicExponent + 
                "\", \"private_exponent\" : \"" + privateExponent +
                "\", \"public_modulus\" : \"" + publicModulus + "\"}");
    }

    private String getNickname(String id) {
        Query userQuery = new Query("UserData");
        userQuery.setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, id));
        Entity userData = datastore.prepare(userQuery).asSingleEntity();

        if (userData == null) {
            return null;
        }

        return (String) userData.getProperty("nickname");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if (action.equals("GENERATE_KEY")) {
            generateKeyRequest(request, response);
        }
        else if (action.equals("TRANSFORM")) {
            transformRequest(request, response);
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void generateKeyRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = UserServiceFactory.getUserService();

        if (!userService.isUserLoggedIn()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        User currentUser = userService.getCurrentUser();

        rsaHandler = new RSA();
        rsaHandler.generateKey();
        Query userQuery = new Query("UserData");
        userQuery.setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, currentUser.getUserId()));
        Entity userData = datastore.prepare(userQuery).asSingleEntity();

        String publicExponent = rsaHandler.getPublicExponent().toString();
        String privateExponent = rsaHandler.getPrivateExponent().toString();
        String publicModulus = rsaHandler.getPublicModulus().toString();
        
        userData.setProperty("public_exponent", publicExponent);
        userData.setProperty("private_exponent", privateExponent);
        userData.setProperty("public_modulus", publicModulus);

        datastore.put(userData);

        response.setContentType("application/json");

        String responseJson = "{ \"public_exponent\" : \"" + publicExponent +
                              "\", \"private_exponent\" : \"" + privateExponent +
                              "\", \"public_modulus\" : \"" + publicModulus + "\"}";

        response.getWriter().println(responseJson);
    }

    private void transformRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BigInteger exponent = new BigInteger(request.getParameter("exponent"));
        BigInteger modulus = new BigInteger(request.getParameter("modulus"));
        BigInteger cipher = new BigInteger(request.getParameter("cipher"));

        response.setContentType("text/html");
        response.getWriter().println(RSA.transform(cipher, exponent, modulus).toString());
    }
}