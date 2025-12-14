package com.example.patientrecordsystem;

import com.example.patientrecordsystem.dto.DepartmentCreateRequest;
import com.example.patientrecordsystem.dto.LoginRequest;
import com.example.patientrecordsystem.dto.PatientCreateRequest;
import com.example.patientrecordsystem.dto.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SmokeIT {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;

    private String registerAndLogin() throws Exception {
        var reg = new RegisterRequest("admin@prs.com ", "pass1234", "Admin", "User", "ADMIN", "555");
        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(reg)))
                .andExpect(status().isCreated());

        var login = new LoginRequest("admin@prs.com", "pass1234");
        String resp = mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return om.readTree(resp).get("accessToken").asText();


    }

    @Test
    void smoke() throws Exception {
        String token = registerAndLogin();

        // create department
        var depReq = new DepartmentCreateRequest("Cardiology", "Heart dept", true);
        String depResp = mvc.perform(post("/api/departments")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(depReq)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        UUID depId = UUID.fromString(om.readTree(depResp).get("id").asText());

        // create a doctor user
        var regDoc = new RegisterRequest("doc@prs.com", "pass1234", "Doc", "One", "DOCTOR", "555");
        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(regDoc)))
                .andExpect(status().isCreated());

        // list users not exposed; we will just create doctor by fetching userId via repository in real use.
        // For test, we skip doctor create to keep API minimal.

        // create patient (without doctor/department optional)
        var pReq = new PatientCreateRequest("John", "Smith", "TR123", null, "Male", "555", "j@x.com",
                "Addr", "A+", null, null, null, null, null, null, null, null);
        mvc.perform(post("/api/patients")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(pReq)))
                .andExpect(status().isCreated());


    }
}