package com.github.braully.dws;

import com.sun.faces.config.ConfigureListener;
import static com.sun.faces.util.CollectionsUtils.map;
import java.util.Map;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import static org.apache.jasper.compiler.ELFunctionMapper.map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import static org.springframework.core.convert.TypeDescriptor.map;
import static org.springframework.data.repository.util.ReactiveWrapperConverters.map;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.context.ServletContextAware;

@EnableWebSecurity
@SpringBootApplication

public class AplicacaoWeb extends WebSecurityConfigurerAdapter implements ServletContextAware {

    public static void main(String... args) {
        SpringApplication.run(AplicacaoWeb.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence cs) {
                return cs.toString();
            }

            @Override
            public boolean matches(CharSequence cs, String string) {
                return cs.toString().equals(string);
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("feliperobueno")
                .password(passwordEncoder().encode("123"))
                .roles("estagiario").build();

        UserDetails admin = User.withUsername("sky")
                .password(passwordEncoder().encode("net"))
                .roles("foda", "fodaoS+").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

     @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(datasource())
                .usersByUsernameQuery(
                        "select login as username, senha as password, true as enabled from usuario where login=?")
                .authoritiesByUsernameQuery(
                        "select login as username, g.grupo as role "
                        + "from grupo g"
                        + " inner join usuario_grupos_usuario ug on ug.grupos_usuario_id = g.id"
                        + " inner join usuario u on ug.usuario_id = u.id "
                        + "where login=?");
    }

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login*").permitAll()
                .antMatchers("/login.xhtml").permitAll()
                .antMatchers("/Principal.xhtml").permitAll()
                .antMatchers("/Contato.xhtml").permitAll()
                .antMatchers("/catalago.xhtml").permitAll()
                .antMatchers("/*.css").permitAll()
                .antMatchers("/selascou.xhtml").permitAll()
                .antMatchers("/todas-solicitacoes").hasRole("foda")
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/login.xhtml")
                .loginProcessingUrl("/login")
                .permitAll().and()
                .logout().permitAll();
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        FacesServlet servlet = new FacesServlet();
        return new ServletRegistrationBean(servlet, "*.xhtml");
    }

    @Bean
    public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
        return new ServletListenerRegistrationBean<ConfigureListener>(
                new ConfigureListener());
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource datasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public static CustomScopeConfigurer viewScope() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.setScopes(
                Map.of("view", new ViewScope()));
        return configurer;
    }

}
