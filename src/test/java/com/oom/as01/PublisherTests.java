package com.oom.as01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PublisherTests {
    private SocialApp socialApp;

    @BeforeEach
    void setUp() {
    	socialApp = new SocialApp();
    }

    @Test
    public void shouldAddUserToSubscribedListWhenSubscribed() {
    	CreateUser user = new CreateUser("12345");
        socialApp.addObserver(user);
        Assertions.assertEquals(user, socialApp.getObserverList().get(0));
    }

    @Test
    public void shouldRemoveUserFromSubscribedListWhenUnsubscribed() {
    	CreateUser user = new CreateUser("12345");
        socialApp.addObserver(user);
        socialApp.removeObserver(user);
        Assertions.assertEquals(0, socialApp.getObserverList().size());
    }

    @Test
    public void shouldNotifySubscribedUsersAboutLatestMessages() {
        CreateUser user = new CreateUser("12345");
        socialApp.addObserver(user);
        socialApp.addPost("Test Post");
        Assertions.assertEquals("Test Post", user.getLastMessage());
    }

}
