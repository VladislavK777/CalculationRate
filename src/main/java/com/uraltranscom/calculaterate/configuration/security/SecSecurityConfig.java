/*
package com.uraltranscom.calculaterate.configuration.security;

import com.uraltranscom.calculaterate.util.connect.ConnectionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

*/
/**
 * @author Vladislav.Klochkov
 * @project CalculationRate
 * @date 05.03.2019
 *//*


@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ConnectionDB connectionDB;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication().dataSource(connectionDB.getDataSource())
                .usersByUsernameQuery("select username, password, enabled"
                        + " from test_auth.users where username=?")
                .authoritiesByUsernameQuery("select username, authority "
                        + "from test_auth.authorities where username=?")
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/resources/**").permitAll()
                    .antMatchers("/settings").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll()
                .deleteCookies("JSESSIONID");
    }
}
*/
