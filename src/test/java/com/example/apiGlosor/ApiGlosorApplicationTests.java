package com.example.apiGlosor;

import com.example.apiGlosor.entities.Category;
import com.example.apiGlosor.entities.Glosa;
import com.example.apiGlosor.repositories.GlosaRepository;
import com.example.apiGlosor.service.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*; //JUnit 5 assertions
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//JUnit 4 and 5 can coexists
//JUnit 4 are located in org.junit and JUnit 5 in org.junit.jupiter

@RunWith(SpringRunner.class) //used to launch a spring contexts in JUnit 4
@SpringBootTest //this is the context I want to launch
@AutoConfigureMockMvc //to trigger HTTP requests to test Controller methods
class ApiGlosorApplicationTests {

	@Autowired
	ApiService service;

	@Autowired
	GlosaRepository glosaRepository;

	//The MockMvc object can be used to perform HTTP requests to test the Controller methods
	//The response can then be inspected, for example to assert that it contains expected values
	@Autowired
	MockMvc mvc;

	//If you need to send an object inside the body of a POST or PUT request, you can use an ObjectMapper
	//The ObjectMapper creates a JSON representation of a Java object
	@Autowired
	ObjectMapper mapper;

	@Test
	void contextLoads() {
	}

	@Test
	void getGlosa() throws Exception {
		Glosa glosa = service.findGlosaById(5);
		assertEquals("four", glosa.getEng());
	}

	@Test
	@Transactional //if your test is @Transactional, it rolls back the transaction at the end of each test method by defaults
	void deleteById(){
		glosaRepository.deleteById(13);
		Optional<Glosa> glosa = glosaRepository.findById(13);
		assertTrue(glosa.isEmpty());
	}

	@Test
	@Transactional
	void save(){
		glosaRepository.deleteById(13);
		Optional<Glosa> glosa = glosaRepository.findById(13);
		assertTrue(glosa.isEmpty());
	}

	@Test
	@Transactional
	void deleteByIdAndExceptionThrown(){
		glosaRepository.deleteById(13);
		assertThrows(GlosaNotFoundException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				Glosa glosa = service.findGlosaById(13);
			}
		});

	}

	@Test
	void getRandomGlosa() throws Exception {
		Glosa glosa = service.getRandomGlosainCat(2);
		assertEquals("medel", glosa.getCategory().getName());
	}


	@Test
	void getGlosorWithHttpReequest() throws Exception {
		mvc.perform(
						MockMvcRequestBuilders.get("/glosor")
				)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(containsString("fönster")));
	}

	//using .accept(MediaType.APPLICATION_JSON_UTF8) instead of specifying produces in Controller as with glosor
	//for result of UTF-8 with åäö
	@Test
	void getCategoriesWithHttpReequest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/categories").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(containsString("lätt")))
				.andExpect(MockMvcResultMatchers.content().string(containsString("medel")))
				.andExpect(MockMvcResultMatchers.content().string(containsString("svår")));
	}

	@Test
	void getGlosaByIdWithHttpReequest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/glosa/12").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(containsString("window")))
				.andExpect(MockMvcResultMatchers.content().string(containsString("fönster")))
				.andExpect(MockMvcResultMatchers.content().string(containsString("svår")))
				.andExpect(MockMvcResultMatchers.content().string(not(containsString("lätt"))));
	}

	@Test
	@Transactional
	void postGlosaWithHttpReequest() throws Exception {
		mvc.perform(
						MockMvcRequestBuilders.get("/glosor")
				)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(not(containsString("hejsan"))));

		mvc.perform( MockMvcRequestBuilders.post("/glosa/{cat}, 2")
						.content(mapper.writeValueAsString(new Glosa(null, "hejsan", "hejsan")))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
				)
				.andExpect(status().is2xxSuccessful());

		mvc.perform(
						MockMvcRequestBuilders.get("/glosor")
				)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(containsString("hejsan")));
	}

	//when to use dirtiesContext?
	// It does not work for non-embedded databases if you want to avoid changes in db efter the test has been performed



}
