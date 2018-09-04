package pl.coderslab.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{id:\\d+}")
    public String showEditCustomerForm(@PathVariable Long id, Model model){
        CustomerDto customerDto = customerService.findById(id);
        model.addAttribute("customer", customerDto);
        return "forms/editCustomer";
    }

    @PostMapping("{id:\\d+}")
    public String editCustomer(@PathVariable Long id, @ModelAttribute CustomerDto customerDto){
        customerService.updateCustomer(id,customerDto);
        return "redirect:/customers";
    }
}
