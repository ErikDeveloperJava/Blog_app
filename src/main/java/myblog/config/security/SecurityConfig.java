package myblog.config.security;

import myblog.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("USD")
    private UserDetailsService userDetailsService;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","/gallery","/categories")
                .permitAll()
                .antMatchers("/login","/register")
                .anonymous()
                .antMatchers("/admin","/admin/**")
                .hasAuthority(UserRole.ADMIN.name())
                .antMatchers("/posts/popular","/galleries","/category/*","/about","/post/comments/count",
                        "/post/comments")
                .hasAnyAuthority(UserRole.USER.name(),UserRole.ROLE_ANONYMOUS.name())
                .antMatchers("/post/comment/add")
                .hasAnyAuthority(UserRole.USER.name())
        .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/")
        .and()
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("RM")
        .and()
                .rememberMe()
                .userDetailsService(userDetailsService)
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("RM")
                .tokenValiditySeconds(200000)
        .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
        .and()
                .csrf()
                .disable();
    }
}
