package com.jonagoldxp.customer;

import com.jonagoldxp.common.entity.Customer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {

//    @Autowired private CountryRepository countryRepo;
    @Autowired private CustomerRepository customerRepo;
    @Autowired
    private CustomerService service;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
//        List<Country> listCountries = service.listAllCountries();
//        model.addAttribute("listCountries", listCountries);
        model.addAttribute("pageTitle", "Customer Registration");
        model.addAttribute("customer", new Customer());

        return "/register/register_form";
    }

    @PostMapping("/create_customer")
    public String createCustomer(Customer customer, Model model,
                                 HttpServletRequest request) {
        service.save(customer);
        model.addAttribute("pageTitle", "Registration Succeeded!");

        return "redirect:/customers";
    }
}
