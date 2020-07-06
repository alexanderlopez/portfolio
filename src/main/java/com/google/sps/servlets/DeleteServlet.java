package com.google.sps.servlets;

import java.io.IOException;
import java.util.List;
import com.google.sps.data.CommentTable;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.FetchOptions;

@WebServlet("/delete-data")
public class DeleteServlet extends HttpServlet {

    private DatastoreService datastore;

    public DeleteServlet() {
        datastore = DatastoreServiceFactory.getDatastoreService();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Query keysQuery = new Query(CommentTable.ENTRY_KEY).setKeysOnly();
        PreparedQuery keysResult = datastore.prepare(keysQuery);
        List<Entity> results = keysResult.asList(FetchOptions.Builder.withDefaults());

        for (Entity entityKey : results) {
            datastore.delete(entityKey.getKey());
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }
}