package com.plexus.ejerciciousuario.testMockMVC;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.plexus.ejerciciousuario.repository.UserRepository;
import com.plexus.ejerciciousuario.testMockMVC.Util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = DemoApplication.class
)
public class UserIntegrationTests {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  Util util = new Util();

  @Autowired
  @Qualifier("userRepository")
  UserRepository userRepository;

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
    User user = new User("Juan60test", "password", "juan60test@mail.com");
    this.mockMvc.perform(post("/api/user")    
    .content(util.asJsonString(user))
    .contentType(MediaType.APPLICATION_JSON)
    .accept(MediaType.APPLICATION_JSON))
    /*.andExpect(status().isCreated())*/
    /*.andExpect(header().string("location", "http://localhost:8090/api/user/"))*/
    .andExpect(status().isOk());
  }
  
  @Test
	public void testDeleteUser() throws Exception {
    this.mockMvc.perform(delete("/api/user/31"))
    .andExpect(status().isOk());
  }

}