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
public class BidControllerTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webContext;

	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}

	@Test
	public void testBid() throws Exception {
		testAddBidForm();
		testAddValidateValid();
		testAddValidateInvalid();
		testHome();
		testShowUpdateForm();
		testUpdateBidValid();
		testUpdateBidInvalid();
		testDeleteBidValid();
		testHome();
	}

	private void testHome() throws Exception {
		MvcResult result = mockMvc.perform(get("/bidList/list")).andExpect(view().name("bidList/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	private void testAddBidForm() throws Exception {
		MvcResult result = mockMvc.perform(get("/bidList/add")).andExpect(view().name("bidList/add"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	private void testAddValidateValid() throws Exception {
		mockMvc.perform(post("/bidList/validate").param("account", "test@mail.com").param("type", "EPC")
				.param("bidQuantity", "5")).andExpect(view().name("redirect:/bidList/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
		
		mockMvc.perform(post("/bidList/validate").param("account", "test2@mail.com").param("type", "EPC2")
				.param("bidQuantity", "10")).andExpect(view().name("redirect:/bidList/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}

	private void testAddValidateInvalid() throws Exception {
		mockMvc.perform(post("/bidList/validate").param("account", "test@mail.com").param("type", "1234")
				.param("bidQuantity", "INVALID")).andExpect(view().name("bidList/add")).andExpect(model().errorCount(1))
				.andExpect(status().isOk());
	}

	private void testShowUpdateForm() throws Exception {
		MvcResult result = mockMvc.perform(get("/bidList/update/2")).andExpect(view().name("bidList/update"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	private void testUpdateBidValid() throws Exception {
		mockMvc.perform(post("/bidList/update/2").param("account", "test2-UPDATE@mail.com").param("type", "EPC2-UPDATE")
				.param("bidQuantity", "1")).andExpect(view().name("redirect:/bidList/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}

	private void testUpdateBidInvalid() throws Exception {
		mockMvc.perform(post("/bidList/update/2").param("account", "test2-UPDATE@mail.com").param("type", "EPC2-UPDATE")
				.param("bidQuantity", "INVALID")).andExpect(view().name("bidList/update"))
				.andExpect(model().errorCount(1)).andExpect(status().isOk());
	}
	
	private void testDeleteBidValid() throws Exception {
		mockMvc.perform(get("/bidList/delete/1")).andExpect(view().name("redirect:/bidList/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}
}
