package com.epam.makedon.agency.repository.impl;

import com.epam.makedon.agency.entity.impl.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserRepositoryTest {
    private static final long ID = 1;
    private static final String LOGIN = "login";

    private static User user;

    @Mock
    private UserRepositoryImpl mockObj;

    @Before
    public void init() {
        mockObj = mock(UserRepositoryImpl.class);
        user = new User();
        user.setId(ID);
        user.setLogin(LOGIN);
        UserRepositoryImpl.getInstance().remove(user);
    }

    @After
    public void destroy() {
        mockObj = null;
        UserRepositoryImpl.getInstance().remove(user);
        user = null;
    }

    @Test
    public void addTrueTest() {
        when(mockObj.add(user)).thenReturn(true);
        assertEquals(mockObj.add(user), UserRepositoryImpl.getInstance().add(user));
        verify(mockObj).add(user);
    }

    @Test
    public void addFalseTest() {
        UserRepositoryImpl.getInstance().add(user);
        when(mockObj.add(user)).thenReturn(false);
        assertEquals(mockObj.add(user), UserRepositoryImpl.getInstance().add(user));
        verify(mockObj).add(user);
    }

    @Test
    public void getTrueTest() {
        UserRepositoryImpl.getInstance().add(user);
        when(mockObj.get(1)).thenReturn(Optional.of(user));
        assertEquals(mockObj.get(1), UserRepositoryImpl.getInstance().get(1));
        verify(mockObj).get(1);
    }

    @Test
    public void getFalseTest() {
        UserRepositoryImpl.getInstance().add(user);
        when(mockObj.get(2)).thenReturn(Optional.ofNullable(null));
        assertEquals(mockObj.get(2), UserRepositoryImpl.getInstance().get(2));
        verify(mockObj).get(2);
    }

    @Test
    public void removeTrueTest() {
        UserRepositoryImpl.getInstance().add(user);
        when(mockObj.remove(user)).thenReturn(true);
        assertEquals(mockObj.remove(user), UserRepositoryImpl.getInstance().remove(user));
        verify(mockObj).remove(user);
    }

    @Test
    public void removeFalseTest() {
        User h = new User();
        UserRepositoryImpl.getInstance().add(user);
        when(mockObj.remove(h)).thenReturn(false);
        assertEquals(mockObj.remove(h), UserRepositoryImpl.getInstance().remove(h));
        verify(mockObj).remove(h);
    }

    @Test
    public void updateTrueTest() {
        UserRepositoryImpl.getInstance().add(user);
        User h = new User();
        h.setId(ID);
        h.setLogin("new login");
        when(mockObj.update(h)).thenReturn(Optional.of(h));
        assertEquals(mockObj.update(h), UserRepositoryImpl.getInstance().update(h));
        verify(mockObj).update(h);
        UserRepositoryImpl.getInstance().remove(h);
    }

    @Test
    public void updateFalseTest() {
        UserRepositoryImpl.getInstance().add(user);
        User h = new User();
        h.setId(3);
        h.setLogin("new login");
        when(mockObj.update(h)).thenReturn(Optional.ofNullable(null));
        assertEquals(mockObj.update(h), UserRepositoryImpl.getInstance().update(h));
        verify(mockObj).update(h);
        UserRepositoryImpl.getInstance().remove(h);
    }
}
