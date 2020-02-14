package com.example.YogaProject;

import com.example.YogaProject.controllers.MainController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController controller;

    @Test
    @WithUserDetails("eva@mail.ru")
    public void mainPageTestAuthorization() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div[1]/span").string("Evach"));
        //пройдет если в контексте для текущего пользователя установлена веб сессия
    }

    @Test
    @WithUserDetails("eva@mail.ru")
    public void mainPageRefsForAdmin() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(content().string(containsString("Hello bro!")))
                .andExpect(content().string(containsString("Activities")))
                .andExpect(content().string(containsString("List of users")))
                .andExpect(content().string(containsString("Activity Types")))
                .andExpect(content().string(containsString("Lounges")))
                .andExpect(content().string(containsString("Logout")));
    }

    @Test
    public void mainPageRefsForAnonUser() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(unauthenticated())
                .andExpect(content().string(containsString("Hello bro!")))
                .andExpect(content().string(containsString("Activities")))
                .andExpect(content().string(containsString("Login")))
                .andExpect(content().string(containsString("Registration")));
    }

    @Test
    @WithUserDetails("ad@mail.ru")
    public void mainPageRefsForAuthUser() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(content().string(containsString("Hello bro!")))
                .andExpect(content().string(containsString("Logout")))
                .andExpect(content().string(containsString("Activities")));
    }
}
