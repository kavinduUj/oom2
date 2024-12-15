package com.oom.as01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserTests {
    CreateUser user;

    @BeforeEach
    public void setUp() {
        user = new CreateUser("1");
    }

    @Test
    public void shouldUpdateLocalMessagesWithReceivedMessage() {
        user.update("Test");
        Assertions.assertEquals(1, user.getAllMessagesInReverseOrder().size());
    }

    @Test
    public void shouldGetTheLastMessageInReverseOrder() {
        user.update("Test One");
        user.update("Test Two");
        Assertions.assertEquals("Test Two", user.getLastMessage());
    }
}
