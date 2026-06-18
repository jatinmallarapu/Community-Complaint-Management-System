package com.CommunityCompaintManagement.CommunityCompaintManagement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
public class UserConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http    /*
        Enables CORS (Cross-Origin Resource Sharing) for your backend.
        Uses a custom CorsConfigurationSource defined later in the class.
        Important because your frontend runs on 127.0.0.1:5500 (Live Server), and your backend runs
        on localhost:8080. Different ports are considered different origins, so CORS must be
        explicitly allowed.
                 */
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                /*
                Enables CSRF (Cross-Site Request Forgery) protection.
                Uses CookieCsrfTokenRepository, which stores the CSRF token in a cookie sent to the client.
                .withHttpOnlyFalse() ensures the cookie is accessible via JavaScript, which is necessary for your frontend to read the CSRF token and send it in the headers.
                Without this, Spring Security would reject POST requests with a 403 error.
                 */


                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/ws/**")  // Allow WebSocket handshakes
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/CCM/login", "/CCM/csrfToken","/CCM/greet","/CCM/userdetails/{name}","/CCM/registercomplaint/{username}","/CCM/viewcomplaints/{username}","/CCM/admin/login","/CCM/admin/viewcomplaints",
                                "/CCM/admin/sendnotification","/CCM/getnotification/{username}","/CCM/deletecomplaint/{complaintId}","/CCM/editcomplaint","/CCM/getuserid","/CCM/admin/editcomplaintstatus","/CCM/admin/getadminid","/CCM/admin/addnotification","/CCM/getallnotifications/{userId}","/CCM/deletenotification","/CCM/deletecomplaintbasedonresolvednotification",
                                "/CCM/admin/addannouncement","/CCM/getallannouncements","/CCM/admin/addevent","/CCM/getevent","/ws/**","/v2/rag/chat/**","/CCM/allannouncments","/CCM/getalluserids","/CCM/getalluseradminmessages","/CCM/admin/getalladminusermessages","/CCM/registeruser").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://127.0.0.1:5500","http://localhost:5500")); // frontend origin
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));//Allows all headers from the frontend (e.g., Content-Type, X-CSRF-TOKEN).
        config.setAllowCredentials(true); // Crucial: allows cookies to be sent with requests. Without this, CSRF token cookie would not be sent, and you’d get 403 errors.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();//Defines URL-based CORS mapping, so we can attach our CORS config to specific paths.
        source.registerCorsConfiguration("/**", config);//Applies the CORS configuration to all endpoints in the backend.
        return source;//Returns the CORS source bean to be used in http.cors() configuration.
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://127.0.0.1:5500")
                        .allowedMethods("GET","POST","PUT","DELETE")
                        .allowCredentials(true);
            }
        };
    }
}
