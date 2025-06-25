package com.pds.neviabookingreminder.redis.queue;


public interface MessagePublisher {
    void publish(final String message);
}