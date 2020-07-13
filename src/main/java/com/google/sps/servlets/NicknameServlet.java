package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Entity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/nickname")
public class NicknameServlet extends HttpServlet {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        
        if (!userService.isUserLoggedIn()) {
            response.sendRedirect("/rsa.html");
            return;
        }

        BufferedReader inputStream = 
            new BufferedReader(new InputStreamReader(
                                getClass().getClassLoader().getResourceAsStream("nickname.html")));
        
        

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        String currentLine = inputStream.readLine();
        while (currentLine != null) {
            writer.println(currentLine);
            currentLine = inputStream.readLine();
        }

        writer.flush();
        writer.close();
        inputStream.close();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        
        if (!userService.isUserLoggedIn()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        User user = userService.getCurrentUser();
        String nickname = request.getParameter("nickname");
        
        if (nickname == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String userId = user.getUserId();
        
        setNickname(userId, nickname);
        response.sendRedirect("/rsa.html");
    }

    private void setNickname(String id, String nickname) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query userQuery = new Query("UserData");
        userQuery.setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, id));
        Entity userData = datastore.prepare(userQuery).asSingleEntity();

        if (userData == null) {
            userData = new Entity("UserData");
            userData.setProperty("id", id);
            userData.setProperty("nickname", nickname);
            datastore.put(userData);
            return;
        }

        userData.setProperty("nickname", nickname);
        datastore.put(userData);
    }
}