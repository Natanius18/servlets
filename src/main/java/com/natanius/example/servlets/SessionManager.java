package com.natanius.example.servlets;

import static java.util.UUID.randomUUID;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {

    private final Map<String, Session> sessions = new HashMap<>();

    public Session getSession(HttpServletRequest request, String sessionId) {

        String name = request.getParameter("name");
        Session sessionById = sessions.get(sessionId);
        if (name != null) {
            if (sessionById != null) {
                return updateSession(sessionId, name);
            }
            return createSession(name);
        }

        if (sessionById == null) {
            return createSession("new user");
        }
        return sessionById;
    }

    private Session createSession(String userName) {
        UUID sessionId = randomUUID();
        Session session = new Session(sessionId, userName);
        sessions.put(sessionId.toString(), session);
        return session;
    }

    private Session updateSession(String sessionId, String userName) {
        Session session = new Session(UUID.fromString(sessionId), userName);
        sessions.put(sessionId, session);
        return session;
    }
}
