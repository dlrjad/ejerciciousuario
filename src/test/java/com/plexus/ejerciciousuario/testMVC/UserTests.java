package com.plexus.ejerciciousuario.testMVC;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.plexus.ejerciciousuario.DemoApplication;
import com.plexus.ejerciciousuario.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = DemoApplication.class
)
public class UserTests {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  User user = new User();

  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

	@Test
	public void testGetUsers() throws Exception {
    this.mockMvc.perform(get("/api/users"))
    .andExpect(status().isOk());
  }
  
  @Test
	public void testGetUser() throws Exception {
    this.mockMvc.perform(get("/api/user/2"))
    .andExpect(status().isOk());
  }

  @Test
	public void testPostUser() throws Exception {
    this.mockMvc.perform(post("/api/user")
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("name").value("name_user"))
    .andExpect(jsonPath("mail").value("mail@mail.com"))
    .andExpect(status().isCreated());
  }
  
  @Test
	public void testDeleteUser() throws Exception {
    this.mockMvc.perform(delete("/api/user/4"))
    .andExpect(status().isOk());
  }

}