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
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class RatingControllerTests {
	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webContext;

	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}
	
	@Test
	public void testHome() throws Exception {
		MvcResult result = mockMvc.perform(get("/rating/list")).andExpect(view().name("rating/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	@Test
	public void testAddRatingForm() throws Exception {
		MvcResult result = mockMvc.perform(get("/rating/add")).andExpect(view().name("rating/add"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	@Test
	public void testAddValidateValid() throws Exception {
		mockMvc.perform(post("/rating/validate").param("moodysRating", "testMoody").param("sandPRating", "testSand")
				.param("fitchRating", "fitchTest").param("orderNumber", "1")).andExpect(view().name("redirect:/rating/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
		
		mockMvc.perform(post("/rating/validate").param("moodysRating", "testMoody2").param("sandPRating", "testSand2")
				.param("fitchRating", "fitchTest2").param("orderNumber", "2")).andExpect(view().name("redirect:/rating/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}

	@Test
	public void testAddValidateInvalid() throws Exception {
		mockMvc.perform(post("/rating/validate").param("moodysRating", "testMoody").param("sandPRating", "testSand")
				.param("fitchRating", "fitchTest").param("orderNumber", "INVALID")).andExpect(view().name("rating/add")).andExpect(model().errorCount(1))
				.andExpect(status().isOk());
	}

	@Test
	public void testShowUpdateForm() throws Exception {
		MvcResult result = mockMvc.perform(get("/rating/update/2")).andExpect(view().name("rating/update"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	@Test
	public void testUpdateRatingValid() throws Exception {
		mockMvc.perform(post("/rating/update/2").param("moodysRating", "UpdateTestMoody2").param("sandPRating", "UpdateTestSand2")
				.param("fitchRating", "UpdateTestFitch2").param("orderNumber", "2")).andExpect(view().name("redirect:/rating/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}

	@Test
	public void testUpdateRatingInvalid() throws Exception {
		mockMvc.perform(post("/rating/update/2").param("moodysRating", "UpdateTestMoody2").param("sandPRating", "UpdateTestSand2")
				.param("fitchRating", "UpdateTestFitch2").param("orderNumber", "INVALID")).andExpect(view().name("rating/update"))
				.andExpect(model().errorCount(1)).andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteRating() throws Exception {
		mockMvc.perform(get("/rating/delete/1")).andExpect(view().name("redirect:/rating/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}
}
