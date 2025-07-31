package com.risosu.EDesalesProgramacionNCapasJunio3.Security;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class Security {

//    @Autowired
//    public JdbcTemplate JdbcTemplate;
//    @Autowired
//    private UserDetailsService customUserDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/Presentacion/Login").permitAll()
                .requestMatchers("/Presentacion/CargaMasiva").hasAnyAuthority("Admin", "Programador")
                .requestMatchers("/Presentacion").hasAnyAuthority("Admin", "Analista", "Programador")
                .requestMatchers("/Presentacion/**").authenticated() 
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                .loginPage("/Presentacion/Login")
                .defaultSuccessUrl("/Presentacion", true)
                .failureUrl("/Presentacion/Login?error=true")
                .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource datasource) {

        JdbcUserDetailsManager CustoUserManager = new JdbcUserDetailsManager(datasource);
        CustoUserManager.setUsersByUsernameQuery("select username, password, Roll.idRoll from usuario inner join Roll on usuario.idroll = Roll.idRoll where username = ? ");
        CustoUserManager.setAuthoritiesByUsernameQuery("SELECT usuario.username, roll.nombre AS authority FROM usuario usuario JOIN Roll roll ON usuario.idroll = roll.idRoll WHERE usuario.username = ? ");

        return CustoUserManager;
    }

//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        UserDetails programador = User.builder()
//                .username("Elian")
//                .password(passwordEncoder().encode("password1"))
//                .authorities("Programador")
//                .build();
//        UserDetails admin = User.builder()
//                .username("Kevin")
//                .password(passwordEncoder().encode("password2"))
//                .authorities("Admin")
//                .build();
//        UserDetails analista = User.builder()
//                .username("mago1")
//                .password(passwordEncoder().encode("password3"))
//                .authorities("Analista")
//                .build();
//        return new InMemoryUserDetailsManager(programador, admin, analista);
//    }
}
