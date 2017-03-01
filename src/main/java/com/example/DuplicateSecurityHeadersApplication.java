package com.example;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;

@SpringBootApplication
@RestController
public class DuplicateSecurityHeadersApplication {

	public static void main(String[] args) {
		SpringApplication.run(DuplicateSecurityHeadersApplication.class, args);
	}

	@RequestMapping("/request")
	public String request(HttpServletRequest request, HttpServletResponse response) {
		if (request.isAsyncStarted()) {
			request.getAsyncContext().complete();
		}
		return "request";
	}

	@RequestMapping("/async")
	public void async(HttpServletRequest request) {
		AsyncContext context = request.startAsync();
		context.dispatch("/request");
	}

	@Bean
	public OncePerRequestFilter doOnceFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
				response.addHeader("X-Only-Once", "Here");
				filterChain.doFilter(request, response);
			}
		};
	}
}
