//package com.example.ibproekt2.security;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.security.authentication.AuthenticationDetailsSource;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {
//
//    @Override
//    public CustomWebAuthenticationDetails buildDetails(HttpServletRequest context) {
//        return new CustomWebAuthenticationDetails(context);
//    }
//}