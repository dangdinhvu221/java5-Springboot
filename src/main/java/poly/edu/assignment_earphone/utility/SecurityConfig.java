package poly.edu.assignment_earphone.utility;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import poly.edu.assignment_earphone.repositories.IUserRepository;
import poly.edu.assignment_earphone.services.UserDetailService;

import static org.hibernate.criterion.Restrictions.and;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailService userDetailsService;
    @Autowired
    private IUserRepository usersRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);// Cung cáp userservice cho spring security
        authProvider.setPasswordEncoder(passwordEncoder());// cung cấp password encoder
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/homePage/**", "/earPhone/homePage", "/earPhone/SignUp", "/LogIn/**", "/dashboard/**")
                .permitAll()
                .anyRequest()
                .authenticated(); // Tất cả các request khác đều cần phải xác thực mới được truy cập

        http.formLogin() // Cho phép người dùng xác thực bằng form login
                .loginPage("/earPhone/logInForm")
                .defaultSuccessUrl("/earPhone/homePage?login=true", true)// login thành công thì vào request này
                .failureUrl("/earPhone/logInForm?login=fail")
                .usernameParameter("username")//
                .passwordParameter("password")
                .loginProcessingUrl("/j_spring-security_check")
                .permitAll(); // Tất cả đều được truy cập vào địa chỉ này

        http.logout() // Cho phép logout
                .logoutUrl("/j_spring_security_logout")
                .logoutSuccessUrl("/earPhone/homePage");

        http.rememberMe()
                .tokenValiditySeconds(86400);
    }
}
