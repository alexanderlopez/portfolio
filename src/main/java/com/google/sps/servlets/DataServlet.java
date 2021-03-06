// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import com.google.gson.Gson;
import com.google.sps.data.CommentTable;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
    private static final String MAX_COMMENTS_KEY = "max_comments";

  private CommentTable commentList = new CommentTable();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json;");
    
    String json = new Gson().toJson(commentList.getCommentEntries(getMaxComments(request)));
    response.getWriter().println(json);
  }

  private int getMaxComments(HttpServletRequest request) {
        String maxCommentsString = request.getParameter(MAX_COMMENTS_KEY);

        int maxComments;
        if (maxCommentsString == null || maxCommentsString.isEmpty()) {
            maxComments = -1;
        }
        else {
            maxComments = Integer.parseInt(maxCommentsString);
        }
        
        return maxComments;
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String name = request.getParameter(CommentTable.NAME_KEY);
    String comment = request.getParameter(CommentTable.COMMENT_KEY);

    commentList.addEntry(name, comment);

    response.sendRedirect("/index.html#comments_title");
  }
}
