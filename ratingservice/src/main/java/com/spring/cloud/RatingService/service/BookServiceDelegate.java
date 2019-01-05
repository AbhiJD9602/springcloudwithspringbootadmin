package com.spring.cloud.RatingService.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.spring.cloud.RatingService.pojo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookServiceDelegate {


    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "callBookServiceAndGetData_Fallback")
    public List<Book> callBookServiceAndGetData() {

        System.out.println("Getting Book details");

        List<Book> response = restTemplate
                .exchange("http://localhost:8083/books"
                        , HttpMethod.GET
                        , null
                        , new ParameterizedTypeReference<List<Book>>() {
                        }).getBody();

        System.out.println("Response Received as " + response + " -  " + new Date());


        return response;

    }

    @SuppressWarnings("unused")
    private List<Book> callBookServiceAndGetData_Fallback() {

        System.out.println("Book Service is down!!! fallback route enabled...");

        return new ArrayList<>();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
