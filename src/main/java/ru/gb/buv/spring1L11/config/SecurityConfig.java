package ru.gb.buv.spring1L11.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.gb.buv.spring1L11.service.UserService;

import javax.sql.DataSource;

@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

//    @Autowired
//    private DataSource dataSource;
//    public void configureGlobal(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .withDefaultSchema()
//                .withUser(User.withUsername("ADMIN")
//                        .password(passwordEncoder().encode("$2a$12$UygrywttHqVF6nB2uiqV6uk3iehijjzySgSdll7v1TE6DvKIBlXaW"))
//                        .roles("ADMIN"));
//    }



    @Override
    //игнорит защиту H2 - а то капец:((...
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Dao Authentication Provider");
        http.headers().frameOptions().disable();
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
        http.authorizeRequests().antMatchers("/**").permitAll();
        http.authorizeRequests().anyRequest().permitAll();
        http.csrf().disable();
        /*http.authorizeRequests().antMatchers("/**").authenticated()
                .antMatchers(HttpMethod.GET,"/**").authenticated()
                .antMatchers(HttpMethod.POST, "/**").hasAnyRole("ADMIN","SUPERADMIN","MANAGER")
                .antMatchers(HttpMethod.PUT, "/**").hasAnyRole("ADMIN","SUPERADMIN","MANAGER")
                .antMatchers(HttpMethod.DELETE, "/**").hasAnyRole("ADMIN","SUPERADMIN","MANAGER")
                .and()
                .formLogin();*/
//http.authorizeRequests()
//                .antMatchers("/**").authenticated()
//                .antMatchers("/user_info").authenticated()
//                .antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERADMIN") // ROLE_ADMIN, ROLE_SUPERADMIN
//                .anyRequest().permitAll()
//                .and()
//                .formLogin();

    }
    //КОДИРОВКА
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //ПРОВАЙДЕР
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

//@Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)
//            throws Exception {
//        httpSecurity.authorizeRequests()
//                .antMatchers("/h2-console/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin();
//
//        httpSecurity.csrf()
//                .ignoringAntMatchers("/h2-console/**");
//        httpSecurity.headers()
//                .frameOptions()
//                .sameOrigin();
//        return httpSecurity.build();
//    }

}
