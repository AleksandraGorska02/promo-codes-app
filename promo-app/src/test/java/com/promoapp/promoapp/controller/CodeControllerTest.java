package com.promoapp.promoapp.controller;

import com.promoapp.promoapp.db.entity.Code;
import com.promoapp.promoapp.db.service.CodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CodeController.class)
class CodeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CodeService codeService;


    //Get all codes
    @Test
    void getAllCodes() throws Exception {


        Code code1 = new Code();
        code1.setCode("code1");
        code1.setDiscount(10);
        code1.setPercentage(true);
        code1.setCurrency("USD");
        code1.setMaxUses(100);
        code1.setExpirationDate("2024-12-31");

        Code code2 = new Code();
        code2.setCode("code2");
        code2.setDiscount(20);
        code2.setPercentage(true);
        code2.setCurrency("USD");
        code2.setMaxUses(3);
        code2.setExpirationDate("2024-05-31");

        when(codeService.getAllCodes()).thenReturn(List.of(code1, code2));

        this.mockMvc.perform(get("/code/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"code\":\"code1\",\"discount\":10.0,\"currency\":\"USD\",\"percentage\":true,\"maxUses\":100,\"expirationDate\":\"2024-12-31\"},{\"code\":\"code2\",\"discount\":20.0,\"currency\":\"USD\",\"percentage\":true,\"maxUses\":3,\"expirationDate\":\"2024-05-31\"}]"));
    }

    @Test
    void getAllCodesEmpty() throws Exception {
        when(codeService.getAllCodes()).thenReturn(List.of());
        this.mockMvc.perform(get("/code/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }



    //Add code
    @Test
    void addCorrectCode() throws Exception {
        when(codeService.addCode(any(Code.class))).thenReturn(true);

        this.mockMvc.perform(post("/code/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"newCode\",\"discount\":10.0,\"currency\":\"USD\",\"percentage\":true,\"maxUses\":100,\"expirationDate\":\"2024-12-31\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("code added successfully"));
    }
    @Test
    void addCodeWithEmptyCode() throws Exception {

        this.mockMvc.perform(post("/code/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"\",\"discount\":10.0,\"currency\":\"USD\",\"percentage\":true,\"maxUses\":100,\"expirationDate\":\"2024-12-31\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("code must be 3-24 characters long and contain only letters and numbers"));
    }
    @Test
    void addCodeWithNullCode() throws Exception {

        this.mockMvc.perform(post("/code/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"discount\":10.0,\"currency\":\"USD\",\"percentage\":true,\"maxUses\":100,\"expirationDate\":\"2024-12-31\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("All fields must be filled"));
    }
    @Test
    void addCodeWithNullDiscount() throws Exception {

        this.mockMvc.perform(post("/code/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"newCode\",\"currency\":\"USD\",\"percentage\":true,\"maxUses\":100,\"expirationDate\":\"2024-12-31\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("All fields must be filled"));
    }



    @Test
    void addCodeWithNegativeDiscount() throws Exception {

        this.mockMvc.perform(post("/code/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"newCode\",\"discount\":-10.0,\"currency\":\"USD\",\"percentage\":false,\"maxUses\":100,\"expirationDate\":\"2024-12-31\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The discount cannot be negative"));
    }


    @Test
    void addCodeWithWhiteSpaces() throws Exception {

        this.mockMvc.perform(post("/code/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"new Code\",\"discount\":10.0,\"currency\":\"USD\",\"percentage\":true,\"maxUses\":100,\"expirationDate\":\"2024-12-31\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("code must be 3-24 characters long and contain only letters and numbers"));
    }
    @Test
    void addCodeWithSpecialCharacters() throws Exception {

        this.mockMvc.perform(post("/code/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"newCode!\",\"discount\":10.0,\"currency\":\"USD\",\"percentage\":true,\"maxUses\":100,\"expirationDate\":\"2024-12-31\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("code must be 3-24 characters long and contain only letters and numbers"));
    }
    @Test
    void addCodeWith110Discount() throws Exception {

        this.mockMvc.perform(post("/code/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"newCode\",\"discount\":110.0,\"currency\":\"USD\",\"percentage\":true,\"maxUses\":100,\"expirationDate\":\"2024-12-31\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The discount cannot be greater than 100%"));
    }

    @Test
    void addCodeWithExistingCode() throws Exception {
        when(codeService.addCode(any(Code.class))).thenReturn(false);
        this.mockMvc.perform(post("/code/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"newCode\",\"discount\":10.0,\"currency\":\"USD\",\"percentage\":true,\"maxUses\":100,\"expirationDate\":\"2024-12-31\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Code already exists"));
    }

    //Get code details
    @Test
    void getCodeDetails() throws Exception {
        Code code1 = new Code();
        code1.setCode("code1");
        code1.setDiscount(10);
        code1.setPercentage(true);
        code1.setCurrency("USD");
        code1.setMaxUses(100);
        code1.setExpirationDate("2024-12-31");

        when(codeService.getCodeDetails("code1")).thenReturn(code1);

            this.mockMvc.perform(get("/code/details/code1"))
                    .andExpect(status().isOk())
                    .andExpect(content().json("{\"code\":\"code1\",\"discount\":10.0,\"currency\":\"USD\",\"percentage\":true,\"maxUses\":100,\"currentUses\":0,\"expirationDate\":\"2024-12-31\"}"));

}}