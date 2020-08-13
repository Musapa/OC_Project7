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
public class CurveControllerTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webContext;

	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}

	@Test
	public void testCurve() throws Exception {
		testAddCurveForm();
		testAddValidateValid();
		testAddValidateInvalid();
		testHome();
		testShowUpdateForm();
		testUpdateCurveValid();
		testUpdateCurveInvalid();
		testDeleteCurve();
		testHome();
	}

	private void testHome() throws Exception {
		MvcResult result = mockMvc.perform(get("/curvePoint/list")).andExpect(view().name("curvePoint/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	private void testAddCurveForm() throws Exception {
		MvcResult result = mockMvc.perform(get("/curvePoint/add")).andExpect(view().name("curvePoint/add"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	private void testAddValidateValid() throws Exception {
		mockMvc.perform(post("/curvePoint/validate").param("curveId", "1").param("term", "10")
				.param("value", "55.5")).andExpect(view().name("redirect:/curvePoint/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
		
		mockMvc.perform(post("/curvePoint/validate").param("curveId", "2").param("term", "10")
				.param("value", "55.5")).andExpect(view().name("redirect:/curvePoint/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}

	private void testAddValidateInvalid() throws Exception {
		mockMvc.perform(post("/curvePoint/validate").param("curveId", "1").param("term", "20")
				.param("value", "INVALID")).andExpect(view().name("curvePoint/add")).andExpect(model().errorCount(1))
				.andExpect(status().isOk());
	}

	private void testShowUpdateForm() throws Exception {
		MvcResult result = mockMvc.perform(get("/curvePoint/update/2")).andExpect(view().name("curvePoint/update"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	private void testUpdateCurveValid() throws Exception {
		mockMvc.perform(post("/curvePoint/update/2").param("curveId", "2").param("term", "5555")
				.param("value", "155.5")).andExpect(view().name("redirect:/curvePoint/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}

	private void testUpdateCurveInvalid() throws Exception {
		mockMvc.perform(post("/curvePoint/update/2").param("curveId", "2").param("term", "5555")
				.param("value", "INVALID")).andExpect(view().name("curvePoint/update"))
				.andExpect(model().errorCount(1)).andExpect(status().isOk());
	}
	
	private void testDeleteCurve() throws Exception {
		mockMvc.perform(get("/curvePoint/delete/1")).andExpect(view().name("redirect:/curvePoint/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}
}