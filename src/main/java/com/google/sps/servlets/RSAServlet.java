package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Entity;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/rsa-data")
public class RSAServlet extends HttpServlet {

    private DatastoreService datastore;

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

        if (nickname == null) {
            nickname = "NULL";
            hasNickname = false;
        }

        responseJson = "{ \"loggedIn\" : true, \"link\" : \"" + logoutUrl + 
                        "\", \"linkText\" : \"Log out\", \"welcome\" : \"Hey " + 
                        nickname + "!\"," +
                        "\"hasNickname\" : " + hasNickname.toString() + "}";

        response.getWriter().println(responseJson);
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
}