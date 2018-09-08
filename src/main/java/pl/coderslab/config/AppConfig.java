package pl.coderslab.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {


    @Override
    public void addFormatters(FormatterRegistry registry) {
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }



}
