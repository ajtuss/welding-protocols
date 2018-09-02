package pl.coderslab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Environment env;

    @Autowired
    public SecurityConfig(Environment env) {
        this.env = env;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource())
            .usersByUsernameQuery("SELECT username, password, active FROM users WHERE username = ?")
            .authoritiesByUsernameQuery("SELECT u.username, r.role FROM users u JOIN users_roles ur ON u.id = ur.user_id JOIN role r ON ur.roles_id = r.id WHERE u.username = ?")
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/css/*").permitAll()
            .antMatchers("/webjars/*").permitAll()
            .antMatchers("/register").anonymous()
            .antMatchers("/login").anonymous()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login").failureUrl("/login?error=true")
            .and()
            .logout().logoutUrl("/logout").logoutSuccessUrl("/login")
            .permitAll()
            .and()
            .csrf().disable();
    }
}
