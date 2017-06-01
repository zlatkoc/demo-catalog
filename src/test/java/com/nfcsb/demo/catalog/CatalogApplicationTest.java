package com.nfcsb.demo.catalog;

import com.nfcsb.demo.catalog.entities.Greeting;
import com.zandero.utils.extra.JsonUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testing RESTs in unit tests
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogServer.class)
@WebAppConfiguration
public class CatalogApplicationTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private String contentType = MediaType.APPLICATION_JSON_UTF8_VALUE;

	@Before
	public void setup() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
	}

	@Test
	public void testGreeting() throws Exception {

		MvcResult result = mockMvc.perform(get("/greeting/1")).andExpect(status().isOk()).andExpect(content().contentType(contentType)).andExpect(jsonPath("$.firstName").value("World")).andExpect(jsonPath("$.lastName").value("1")).andExpect(jsonPath("$.message").value("Hello, 0!")).andReturn();

		// alternative way
		Greeting entity = JsonUtils.fromJson(result.getResponse().getContentAsString(), Greeting.class);
		assertEquals("World", entity.getFirstName());
		assertEquals("1", entity.getLastName());
		assertEquals("Hello, 0!", entity.getMessage());
	}

	@Test
	public void testHello() throws Exception {

		mockMvc.perform(get("/hello")).andExpect(status().isOk());
	}

	/*@Test
	public void testNonExistingAPI() throws Exception {

		MvcResult result = mockMvc.perform(get("/notExistent")).andExpect(status().isNotFound()).andReturn();

		assertEquals("{\"message\":\"Path not found: '/notExistent'\",\"code\":404}", result.getResponse().getContentAsString());
	}*/

	@Test
	public void testSecurity() throws Exception {

		mockMvc.perform(get("/private")).andExpect(status().isUnauthorized());

		mockMvc.perform(get("/private").header("X-Token", "Dummy")).andExpect(status().isOk());
	}

	@Test
	public void testSecurity_2() throws Exception {

		mockMvc.perform(get("/private2")).andExpect(status().isUnauthorized());

		MvcResult result = mockMvc.perform(get("/private2").header("X-Token", "Dummy")).andExpect(status().isOk()).andReturn();

		assertEquals("Dummy_user", result.getResponse().getContentAsString());
	}

	@Test
	public void testException() throws Exception {

		MvcResult result = mockMvc.perform(get("/throw/0")).andExpect(status().isBadRequest()).andReturn();

		assertEquals("{\"message\":\"Missing proper value!\",\"code\":400}", result.getResponse().getContentAsString());
	}
}