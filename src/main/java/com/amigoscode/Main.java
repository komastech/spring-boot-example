package com.amigoscode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @GetMapping
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }



    @PostMapping
    public void addCustomers(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setAge(request.age);
        customer.setName(request.name);
        customer.setEmail(request.email);
        customer.setGenre(request.genre);
        customerRepository.save(customer);
    }


    record Person(String name, int age, String level) {

    }

    record GreetResponse(
            String greet,
            List<String> favProgrammingLanguage,
            Person person
    ) {
    }

    record NewCustomerRequest(
        String name,
        String email,
        Integer age,
        String genre){
    }

}
