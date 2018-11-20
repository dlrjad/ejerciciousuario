package com.plexus.ejerciciousuario.testMockito;


import com.plexus.ejerciciousuario.controller.UserController;
import com.plexus.ejerciciousuario.model.User;
import com.plexus.ejerciciousuario.repository.UserRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserUnitTests {

  private UserController userController;
  private UserRepository userRepository;

  @Before
  public void setUp() {
      /*userRepository = Mockito.mock(UserRepository.class);
      userController = new UserController(userRepository);*/
  }

  @Test
  public void itShouldReturnTheServiceValueWith200StatusCode() {
      ResponseEntity<?> httpResponse = userController.getUsers();
      Assert.assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);
  }

}