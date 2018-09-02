package pl.coderslab.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.domain.entities.User;
import pl.coderslab.domain.repositories.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String showUsers(){

        return "users";
    }

    @ModelAttribute("allUsers")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
