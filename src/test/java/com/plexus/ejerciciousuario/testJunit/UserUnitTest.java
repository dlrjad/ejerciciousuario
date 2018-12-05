package com.plexus.ejerciciousuario.testJunit;

import com.plexus.ejerciciousuario.model.User;

import org.junit.Assert;
import org.junit.Test;

public class UserUnitTest {

    private User user = new User();

    @Test
    public void itShouldGetRoles() {
        Assert.assertEquals(null, user.getRoles());
    }

}