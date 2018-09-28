package pl.coderslab.rest;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
@RequestMapping("/api")
public class ApiRestController {

    @GetMapping
    public ResourceSupport getApi() {
        ResourceSupport rootSupport = new ResourceSupport();

        rootSupport.add(linkTo(methodOn(BrandRestController.class).getAll(null)).withRel("brands"),
                linkTo(methodOn(WelderModelRestController.class).getAll()).withRel("models"),
                linkTo(methodOn(CustomerRestController.class).getAll()).withRel("customers"),
                linkTo(methodOn(MachineRestController.class).getAll()).withRel("machines"),
                linkTo(methodOn(ValidationsRestController.class).getAll()).withRel("validations"),
                linkTo(methodOn(MeasureRestController.class).getAll()).withRel("measures"));

        return rootSupport;
    }
}
