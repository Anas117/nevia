package com.pds.nevianotificationmanager.redis.queue;


public interface MessagePublisher {
    void publish(final String message);
}