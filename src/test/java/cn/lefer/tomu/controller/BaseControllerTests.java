//package cn.lefer.tomu.controller;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * @author : lefer
// * @version : V1.0
// * @date :   2020/7/5
// * @Description : 基础服务测试用例
// */
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = BaseController.class)
//public class BaseControllerTests {
//    @Autowired
//    MockMvc mockMvc;
//
//    @Value("${application.version}")
//    String applicationVersion;
//
//    @Test
//    public void version() throws Exception{
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/api/v1/version")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string(applicationVersion));
//    }
//
//    @Test
//    public void auth() throws Exception{
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/api/v1/auth")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//
//}
