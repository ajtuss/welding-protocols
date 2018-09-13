package pl.coderslab.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.domain.dto.CustomerDTO;
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
        model.addAttribute("customer", new CustomerDTO());
        return "forms/addCustomer";
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute CustomerDTO customerDTO){
//        customerService.save(customerDTO); //todo
        return "redirect:/customers";
    }

    @GetMapping("/{id:\\d+}")
    public String showEditCustomerForm(@PathVariable Long id, Model model){
        CustomerDTO customerDTO = customerService.findById(id);
        model.addAttribute("customer", customerDTO);
        return "forms/editCustomer";
    }

    @PostMapping("{id:\\d+}")
    public String editCustomer(@PathVariable Long id, @ModelAttribute CustomerDTO customerDTO){
//        customerService.update(id, customerDTO);
        return "redirect:/customers"; //todo
    }
}
