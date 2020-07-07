package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.FetchOptions;

public class CommentTable {
    public static final String ENTRY_KEY = "CommentEntry";
    public static final String NAME_KEY = "name";
    public static final String COMMENT_KEY = "comment";

    private DatastoreService datastore;

    public CommentTable() {
        datastore = DatastoreServiceFactory.getDatastoreService();
    }

    public void addEntry(String name, String comment) {
        if (name == null || name.isEmpty()) {
            name = "Anonymous";
        }

        if (comment == null || comment.isEmpty()) {
            return;
        }

        if (!isSafe(name, comment)) {
            return;
        }

        Entity commentEntity = new Entity(ENTRY_KEY);
        commentEntity.setProperty(NAME_KEY, name);
        commentEntity.setProperty(COMMENT_KEY, comment);

        datastore.put(commentEntity);
    }

    private boolean isSafe(String name, String comment) {
        // Naive check for HTML injection by not allowing <, > in the text
        return (name.indexOf('<') == -1 && comment.indexOf('>') == -1);
    }

    public List<Comment> getCommentEntries() {
        return getCommentEntries(-1);
    }

    public List<Comment> getCommentEntries(int maxComments) {
        List<Comment> commentList = new ArrayList<Comment>();

        Query commentQuery = new Query(ENTRY_KEY);
        PreparedQuery commentEntities = datastore.prepare(commentQuery);

        Iterable<Entity> commentIterable;
        if (maxComments == -1) {
            commentIterable = commentEntities.asIterable();
        }
        else {
            commentIterable = commentEntities.asIterable(FetchOptions.Builder.withLimit(maxComments));
        }

        for (Entity commentEntity : commentIterable) {
            String name = (String) commentEntity.getProperty(NAME_KEY);
            String comment = (String) commentEntity.getProperty(COMMENT_KEY);

            commentList.add(new Comment(name, comment));
        }

        return commentList;
    }

    public class Comment {
        String name;
        String comment;

        public Comment(String name, String comment) {
            this.name = name;
            this.comment = comment;
        }
    }
}