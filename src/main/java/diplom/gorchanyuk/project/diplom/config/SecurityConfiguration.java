package diplom.gorchanyuk.project.diplom.config;

import diplom.gorchanyuk.project.diplom.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    //    private final DataSource dataSource;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userService).passwordEncoder(passwordEncoder);
//                .jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("SELECT username,password,enabled "
//                        + "FROM users "
//                        + "WHERE username = ?")
//                .authoritiesByUsernameQuery("SELECT u.username,a.authority " +
//                        "FROM users u " +
//                        "LEFT JOIN user_authority ua " +
//                        "ON u.id = ua.user_id " +
//                        "LEFT JOIN authority a " +
//                        "ON a.id = ua.authority_id " +
//                        "WHERE username = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/proger/admin/**").hasRole("ADMIN")
                .mvcMatchers("/index", "/images/**", "/js/**", "/css/**").permitAll()
                .mvcMatchers("/proger/profile/**").authenticated()
                .mvcMatchers("/proger/menu_entry/**").authenticated()
                .mvcMatchers("/proger/user_courses/**").authenticated()
                .anyRequest().permitAll()
//                .authenticated()
                .and()
                //Настройка для входа в систему
                .formLogin()
                .loginPage("/proger/login")
                //Перенарпавление на главную страницу после успешного входа
                .defaultSuccessUrl("/proger")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/proger/logout")
                .logoutSuccessUrl("/proger");
    }

}