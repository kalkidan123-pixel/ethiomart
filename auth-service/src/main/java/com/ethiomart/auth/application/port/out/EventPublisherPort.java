package com.ethiomart.auth.application.port.out;

import com.ethiomart.events.UserRegisteredEvent;

public interface EventPublisherPort {

    void publishUserRegistered(UserRegisteredEvent event);
}
