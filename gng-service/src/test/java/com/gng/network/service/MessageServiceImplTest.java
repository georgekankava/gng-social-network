package com.gng.network.service;


import com.gng.network.dao.MessageDao;
import com.gng.network.enities.User;
import com.gng.network.helper.MessageHelper;
import com.gng.network.json.response.Message;
import com.gng.network.service.UserService;
import com.gng.network.service.impl.MessageServiceImpl;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.inject.Inject;

import static org.mockito.Mockito.*;

/**
 * Created by georgekankava on 12/1/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class MessageServiceImplTest {

    @Mock
    private MessageDao messageDao;

    @Mock
    private UserService userService;

    @Mock
    private MessageHelper messageHelper;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private MessageServiceImpl messageService;


    @Test(expected = UsernameNotFoundException.class)
    public void addMessage() throws Exception {
        Message message = mock(Message.class);
        User from = mock(User.class);
        User to = mock(User.class);
        when(userService.findUserById(1)).thenReturn(from).thenReturn(to);

        when(message.getAuthor()).thenReturn(1).thenReturn(null);
        when(message.getReceiver()).thenReturn(1).thenReturn(null);
        when(message.getMessage()).thenReturn("Test Message");
        when(from.getFullname()).thenReturn("Test Full Name");

        messageService.addMessage(message);
        messageService.addMessage(message);
    }

    @Test
    public void removeMessage() throws Exception {

    }

    @Test
    public void getJsonMessages() throws Exception {

    }

}