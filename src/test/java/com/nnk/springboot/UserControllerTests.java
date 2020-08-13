package com.nnk.springboot;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@WithMockUser(username = "test@mail.com", roles = { "ADMIN" })
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class UserControllerTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webContext;

	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}

	@Test
	public void testUser() throws Exception {
		testAddUserForm();
		testAddValidateValid();
		testAddValidateInvalid();
		testHome();
		testShowUpdateForm();
		testUpdateUserValid();
		testUpdateUserInvalid();
		testDeleteUser();
		testHome();
	}

	private void testHome() throws Exception {
		MvcResult result = mockMvc.perform(get("/user/list")).andExpect(view().name("user/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	private void testAddUserForm() throws Exception {
		MvcResult result = mockMvc.perform(get("/user/add")).andExpect(view().name("user/add"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	private void testAddValidateValid() throws Exception {
		mockMvc.perform(post("/user/validate").param("fullname", "fullNameTest").param("username", "usernameTest")
				.param("password", "Musapa1234@").param("role", "ADMIN")).andExpect(view().name("redirect:/user/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
		
		mockMvc.perform(post("/user/validate").param("fullname", "fullNameTest2").param("username", "usernameTest2")
				.param("password", "Musapa1234@").param("role", "USER")).andExpect(view().name("redirect:/user/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}

	private void testAddValidateInvalid() throws Exception {
		mockMvc.perform(post("/user/validate").param("fullname", "fullNameTest").param("username", "usernameTest")
				.param("password", "musapa1234@").param("role", "ADMIN")).andExpect(view().name("user/add")).andExpect(model().errorCount(1))
				.andExpect(status().isOk());
	}

	private void testShowUpdateForm() throws Exception {
		MvcResult result = mockMvc.perform(get("/user/update/2")).andExpect(view().name("user/update"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	private void testUpdateUserValid() throws Exception {
		mockMvc.perform(post("/user/update/2").param("fullname", "UPDATEfullNameTest").param("username", "UPDATEusernameTest")
				.param("password", "Musapa1234@").param("role", "ADMIN")).andExpect(view().name("redirect:/user/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}

	private void testUpdateUserInvalid() throws Exception {
		mockMvc.perform(post("/user/update/2").param("fullname", "fullNameTest").param("username", "usernameTest")
				.param("password", "musapa1234@").param("role", "ADMIN")).andExpect(view().name("user/update"))
				.andExpect(model().errorCount(1)).andExpect(status().isOk());
	}
	
	private void testDeleteUser() throws Exception {
		mockMvc.perform(get("/user/delete/1")).andExpect(view().name("redirect:/user/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}
	
}
