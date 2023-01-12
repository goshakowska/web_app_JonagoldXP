package com.jonagoldxp.customer;

import com.jonagoldxp.common.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

//    @Autowired private CountryRepository countryRepo;
    @Autowired private CustomerRepository customerRepo;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
//        List<Country> listCountries = service.listAllCountries();
//        model.addAttribute("listCountries", listCountries);
        model.addAttribute("pageTitle", "Customer Registration");
        model.addAttribute("customer", new Customer());

        return "/register/register_form";
    }
}
