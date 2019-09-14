package com.xavier.config.redis;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;

public class RedisSessionDao extends EnterpriseCacheSessionDAO {

    private RedisTemplate<byte[], byte[]> redisTemplate;

    public RedisSessionDao(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        redisTemplate.opsForValue().set(sessionId.toString().getBytes(), sessionToByte(session));
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = super.doReadSession(sessionId);
        if (session == null) {
            byte[] bytes = redisTemplate.opsForValue().get(sessionId.toString().getBytes());
            if (bytes != null && bytes.length > 0) {
                session = byteToSession(bytes);
            }
        }
        return session;
    }

    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        redisTemplate.opsForValue().set(session.getId().toString().getBytes(), sessionToByte(session));
    }

    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
        redisTemplate.delete(session.getId().toString().getBytes());
    }

    private byte[] sessionToByte(Session session) {
        if (null == session) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        byte[] bytes = null;
        ObjectOutputStream oo;
        try {
            oo = new ObjectOutputStream(bo);
            oo.writeObject(session);
            bytes = bo.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;

    }

    private Session byteToSession(byte[] bytes) {
        if (0 == bytes.length) {
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream in;
        SimpleSession session = null;
        try {
            in = new ObjectInputStream(bi);
            session = (SimpleSession) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }

}

