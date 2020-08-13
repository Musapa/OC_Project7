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
public class TradeControllerTests {
	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webContext;

	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}

	@Test
	public void testTrade() throws Exception {
		testAddTradeForm();
		testAddValidateValid();
		testAddValidateInvalid();
		testHome();
		testShowUpdateForm();
		testUpdateTradeValid();
		testUpdateTradeInvalid();
		testDeleteTrade();
		testHome();
	}
	
	private void testHome() throws Exception {
		MvcResult result = mockMvc.perform(get("/trade/list")).andExpect(view().name("trade/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	private void testAddTradeForm() throws Exception {
		MvcResult result = mockMvc.perform(get("/trade/add")).andExpect(view().name("trade/add"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	private void testAddValidateValid() throws Exception {
		mockMvc.perform(post("/trade/validate").param("account", "testAccount").param("type", "testType")
				.param("buyQuantity", "1")).andExpect(view().name("redirect:/trade/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
		
		mockMvc.perform(post("/trade/validate").param("account", "testAccount2").param("type", "testType2")
				.param("buyQuantity", "2")).andExpect(view().name("redirect:/trade/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}

	private void testAddValidateInvalid() throws Exception {
		mockMvc.perform(post("/trade/validate").param("account", "testAccount").param("type", "testType")
				.param("buyQuantity", "INVALID")).andExpect(view().name("trade/add")).andExpect(model().errorCount(1))
				.andExpect(status().isOk());
	}

	private void testShowUpdateForm() throws Exception {
		MvcResult result = mockMvc.perform(get("/trade/update/2")).andExpect(view().name("trade/update"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	private void testUpdateTradeValid() throws Exception {
		mockMvc.perform(post("/trade/update/2").param("account", "UPDATEtestAccount").param("type", "UPDATEtestType")
				.param("buyQuantity", "1")).andExpect(view().name("redirect:/trade/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}

	private void testUpdateTradeInvalid() throws Exception {
		mockMvc.perform(post("/trade/update/2").param("account", "UPDATEtestAccount").param("type", "UPDATEtestType")
				.param("buyQuantity", "INVALID")).andExpect(view().name("trade/update"))
				.andExpect(model().errorCount(1)).andExpect(status().isOk());
	}
	
	private void testDeleteTrade() throws Exception {
		mockMvc.perform(get("/trade/delete/1")).andExpect(view().name("redirect:/trade/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}
	
}
