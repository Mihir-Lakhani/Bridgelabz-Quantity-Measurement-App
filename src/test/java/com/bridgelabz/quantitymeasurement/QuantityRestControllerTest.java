package com.bridgelabz.quantitymeasurement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class QuantityRestControllerTest {
    @Autowired MockMvc mvc;

    @Test
    void addsLengthsThroughRestApi() throws Exception {
        mvc.perform(post("/api/v1/quantities/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"category":"LENGTH","firstValue":1,"firstUnit":"FEET",
                                 "secondValue":12,"secondUnit":"INCHES"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(2.0))
                .andExpect(jsonPath("$.unit").value("FEET"));
    }

    @Test
    void rejectsDivisionByZeroAndRecordsError() throws Exception {
        mvc.perform(post("/api/v1/quantities/divide")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"category":"LENGTH","firstValue":1,"firstUnit":"FEET",
                                 "secondValue":0,"secondUnit":"FEET"}
                                """))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/api/v1/quantities/history/errored"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].operation").value("ERROR"));
    }
}
