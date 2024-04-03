package com.pccw.platform.service;

import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class AppTest {

  @Autowired protected WebApplicationContext wac;

  protected MockMvc mvc;

  @BeforeEach
  public void setUp() {
    this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  void home() throws Exception {
    mvc.perform(get("/"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/swagger-ui.html"));
  }

  @Test
  void getHeartbeat() throws Exception {

    mvc.perform(get("/heartbeat").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("releasedAt", not(blankOrNullString())))
        .andExpect(jsonPath("startUpAt", not(blankOrNullString())))
        .andExpect(jsonPath("current", not(blankOrNullString())))
        .andExpect(jsonPath("name", is("PROJECT_NAME")))
        .andExpect(jsonPath("version", not(blankOrNullString())))
        .andExpect(jsonPath("description", not(blankOrNullString())))
        .andExpect(jsonPath("status", is("UP")));
  }

  @Test
  void getSwaggerUi() throws Exception {

    mvc.perform(get("/swagger-ui.html").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().is3xxRedirection());
  }

  @Test
  void getSwaggerDocs() throws Exception {

    mvc.perform(get("/v3/api-docs").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().is2xxSuccessful());
  }
}
