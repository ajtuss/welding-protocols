package pl.coderslab.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.domain.dto.CustomerDto;
import pl.coderslab.domain.services.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String showCustomers(){

        return "customers";
    }

    @GetMapping("/add")
    public String showAddCustomerForm(Model model){
        model.addAttribute("customer", new CustomerDto());
        return "forms/addCustomer";
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute CustomerDto customerDto){
        customerService.saveCustomer(customerDto);
        return "redirect:/customers";
    }
}
