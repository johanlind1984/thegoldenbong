package com.atg.thegoldenbong.security;

/*@Configuration
@EnableWebSecurity*/
public class SecurityConfig {
/*
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.withUsername("joakimlind")
                .password(passwordEncoder().encode("BekSolLe"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("johanlind")
                .password(passwordEncoder().encode("MAkkAfaKK"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1, admin);    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
}
