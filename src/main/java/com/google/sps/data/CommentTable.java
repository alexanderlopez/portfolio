package com.google.sps.data;

import java.util.ArrayList;

public class CommentTable {

    private ArrayList<Comment> commentTable;

    public CommentTable() {
        commentTable = new ArrayList<Comment>();
    }

    public void addEntry(String name, String comment) {
        if (name == null || String.isEmpty(name))
            name = "Anonymous";

        if (comment == null || String.isEmpty(comment))
            return;

        commentTable.add(new Comment(name, comment));
    }

    private class Comment {
        String name;
        String comment;

        public Comment(String _name, String _comment) {
            name = _name;
            comment = _comment;
        }

        public String getName() {
            return name;
        }

        public String getComment() {
            return comment;
        }
    }
}