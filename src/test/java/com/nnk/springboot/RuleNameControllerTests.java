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
public class RuleNameControllerTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webContext;

	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}

	@Test
	public void testRuleName() throws Exception {
		testAddRuleForm();
		testAddValidateValid();
		testAddValidateInvalid();
		testHome();
		testShowUpdateForm();
		testUpdateRuleValid();
		testUpdateRuleInvalid();
		testDeleteRule();
		testHome();
	}
	
	private void testHome() throws Exception {
		MvcResult result = mockMvc.perform(get("/ruleName/list")).andExpect(view().name("ruleName/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	private void testAddRuleForm() throws Exception {
		MvcResult result = mockMvc.perform(get("/ruleName/add")).andExpect(view().name("ruleName/add"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	private void testAddValidateValid() throws Exception {
		mockMvc.perform(post("/ruleName/validate").param("name", "testName").param("description", "testDescription")
				.param("json", "testJson").param("template", "testTemplate").param("sqlStr", "testSqlStr").param("sqlPart", "testSqlPart")).andExpect(view().name("redirect:/ruleName/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
		
		mockMvc.perform(post("/ruleName/validate").param("name", "testName2").param("description", "testDescription2")
				.param("json", "testJson2").param("template", "testTemplate2").param("sqlStr", "testSqlStr2").param("sqlPart", "testSqlPart2")).andExpect(view().name("redirect:/ruleName/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}

	private void testAddValidateInvalid() throws Exception {
		mockMvc.perform(post("/ruleName/validate").param("name", "").param("description", "testDescription")
				.param("json", "testJson").param("template", "testTemplate").param("sqlStr", "testSqlStr").param("sqlPart", "testSqlPart")).andExpect(view().name("ruleName/add")).andExpect(model().errorCount(1))
				.andExpect(status().isOk());
	}

	private void testShowUpdateForm() throws Exception {
		MvcResult result = mockMvc.perform(get("/ruleName/update/2")).andExpect(view().name("ruleName/update"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	private void testUpdateRuleValid() throws Exception {
		mockMvc.perform(post("/ruleName/update/2").param("name", "UPDATEtestName").param("description", "UPDATEtestDescription")
				.param("json", "UPDATEtestJson").param("template", "UPDATEtestTemplate").param("sqlStr", "UPDATEtestSqlStr").param("sqlPart", "UPDATEtestSqlPart")).andExpect(view().name("redirect:/ruleName/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}

	private void testUpdateRuleInvalid() throws Exception {
		mockMvc.perform(post("/ruleName/update/2").param("name", "").param("description", "UPDATEtestDescription")
				.param("json", "UPDATEtestJson").param("template", "UPDATEtestTemplate").param("sqlStr", "UPDATEtestSqlStr").param("sqlPart", "UPDATEtestSqlPart")).andExpect(view().name("ruleName/update"))
				.andExpect(model().errorCount(1)).andExpect(status().isOk());
	}
	
	private void testDeleteRule() throws Exception {
		mockMvc.perform(get("/ruleName/delete/1")).andExpect(view().name("redirect:/ruleName/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}
}
