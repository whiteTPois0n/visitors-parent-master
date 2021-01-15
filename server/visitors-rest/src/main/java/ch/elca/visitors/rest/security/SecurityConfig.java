package ch.elca.visitors.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final DataSource dataSource;


    @Autowired
    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(getPasswordEncoder())
                .usersByUsernameQuery("select username, password, enabled from `user` where username = ?")
                .authoritiesByUsernameQuery("select u.username, r.role_name from `user` u inner join `role` r on u.role_id = r.id where u.username = ?")
                .rolePrefix("ROLE_");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").authenticated()
                .antMatchers("/super-admin/**").hasRole("SUPERADMIN")
                .antMatchers("/visit/**").hasRole("ADMIN")
                .antMatchers("/contact/**").hasRole("ADMIN")
                .antMatchers("/appointment/**").hasAnyRole("ADMIN", "HRSTAFF")
                .antMatchers("/**").permitAll()
                //                .anyRequest().denyAll()
                .anyRequest().permitAll()
                .and()
                .httpBasic();
    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}