package com.amigoscode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


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


    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id){
        customerRepository.deleteById(id);
    }


    @PutMapping("{customerId}")
    public Customer updateCustomer(@RequestBody NewCustomerRequest request,@PathVariable("customerId") Integer id) throws Exception {
        Optional<Customer> updatedCustomer = customerRepository.findById(id);
        if(updatedCustomer.isPresent()){
            if(request.genre != null) updatedCustomer.get().setGenre(request.genre);
            if(request.name != null) updatedCustomer.get().setName(request.name);
            if(request.age != null) updatedCustomer.get().setAge(request.age);
            if(request.email != null) updatedCustomer.get().setEmail(request.email);
            customerRepository.save(updatedCustomer.get());
            return updatedCustomer.get();
        }else{
            throw new Exception("No users found");
        }
    }


    record Person(String name, int age, String level) {

    }

    record GreetResponse(
            String greet,
            List<String> favProgrammingLanguage,
            Person person
    ) {
    }

    public record NewCustomerRequest(
        String name,
        String email,
        Integer age,
        String genre){
    }

}
