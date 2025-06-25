package com.pds.dispatcher.config;

import brave.sampler.Sampler;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SleuthConfig {
    public Sampler defaultSampler(){
        // all requests will be marked with ID and can be used by other service like Zipkin
        return Sampler.ALWAYS_SAMPLE;
    }
}
