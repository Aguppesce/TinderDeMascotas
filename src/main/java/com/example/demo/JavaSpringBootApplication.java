package com.example.demo;

import com.example.demo.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JavaSpringBootApplication extends WebSecurityConfigurerAdapter {

        @Autowired
        private UsuarioServicio usuarioServicio;
        
	public static void main(String[] args) {
		SpringApplication.run(JavaSpringBootApplication.class, args);
	}
        
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
            auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
        }

}
