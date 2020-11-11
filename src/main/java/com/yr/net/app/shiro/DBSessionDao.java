package com.yr.net.app.shiro;

import com.yr.net.app.jpa.ShiroSession;
import com.yr.net.app.jpa.ShiroSessionRepository;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DBSessionDao implements SessionDAO {
    private Map<Object, Session> sessionMap = new ConcurrentHashMap<>();

    @Autowired
    private ShiroSessionRepository shiroSessionRepository;

    @Override
    public Serializable create(Session session) {
        String sessionId = UUID.randomUUID().toString().replaceAll("-", "");
        ((SimpleSession) session).setId(sessionId);
        return sessionId;
    }

    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
//        return sessionMap.get(sessionId);
        ShiroSession shiroSession = shiroSessionRepository.findById((String) sessionId).orElse(null);
        if (shiroSession != null) {
            Session session = byteToSession(shiroSession.getSessionData());
            return session;
        }
        return null;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        byte[] bb = sessionToByte(session);
        ShiroSession shiroSession = new ShiroSession((String)session.getId(), bb);
        shiroSessionRepository.save(shiroSession);
    }

    @Override
    public void delete(Session session) {
        sessionMap.remove(session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return sessionMap.values();
    }

    private byte[] sessionToByte(Session session){
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        byte[] bytes = null;
        try {
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(session);
            bytes = bo.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    // restore session
    private Session byteToSession(byte[] bytes){
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream in;
        SimpleSession session = null;
        try {
            in = new ObjectInputStream(bi);
            session = (SimpleSession) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return session;
    }
}
