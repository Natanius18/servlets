package com.natanius.example.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    private static final String CUSTOM_SESSION_ID = "myCustomSuperSecretNataniusSessionId";
    private final SessionManager sessionManager = new SessionManager();

    private static final String BODY_HTML = """
        <!DOCTYPE html>
        <html>
        <head>
            <title>Hello</title>
        </head>
        <body>
        <h1>Hello, %s</h1>
        <a href="hello">Check hello without params</a>
        <br>
        <a href="/">Go back</a>
        </body>
        </html>
        """;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String sessionId = null;
        for (Cookie cookie : request.getCookies()) {
            if (CUSTOM_SESSION_ID.equals(cookie.getName())) {
                sessionId = cookie.getValue();
                break;
            }
        }

        Session session = sessionManager.getSession(request, sessionId);

        response.addCookie(new Cookie(CUSTOM_SESSION_ID, session.getSessionId().toString()));
        try (PrintWriter out = response.getWriter()) {
            out.write(String.format(BODY_HTML, session.getUserName()));
        }
    }

}
