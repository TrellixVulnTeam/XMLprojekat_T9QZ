package com.example.securityservice.Config;

import com.example.securityservice.Dto.JwtAuthenticationRequest;
import com.example.securityservice.Security.RestAuthenticationEntryPoint;
import com.example.securityservice.Security.TokenAuthenticationFilter;
import com.example.securityservice.Service.impl.CustomUserDetailsService;
import com.example.securityservice.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true)
//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //injection password encodera da bi se izbegao circular reference
    private EncoderConfig encoderConfig;


    //return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    // Servis koji se koristi za citanje podataka o korisnicima aplikacije
    private final CustomUserDetailsService customUserDetailsService;

    // Handler za vracanje 401 kada klijent sa neodogovarajucim korisnickim imenom i lozinkom pokusa da pristupi resursu
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    // Injektujemo implementaciju iz TokenUtils klase kako bismo mogli da koristimo njene metode za rad sa JWT u TokenAuthenticationFilteru

    private final TokenUtils tokenUtils;
    @Autowired
    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService, RestAuthenticationEntryPoint restAuthenticationEntryPoint, TokenUtils tokenUtils, EncoderConfig encoderConfig){
        this.customUserDetailsService = customUserDetailsService;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.tokenUtils = tokenUtils;
        this.encoderConfig = encoderConfig;
    }

    // Registrujemo authentication manager koji ce da uradi autentifikaciju korisnika za nas
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Definisemo nacin utvrdjivanja korisnika pri autentifikaciji
    //* 13.8. 18:31
    @Override
    //@Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // Definisemo uputstva AuthenticationManager-u:

                // 1. koji servis da koristi da izvuce podatke o korisniku koji zeli da se autentifikuje
                // prilikom autentifikacije, AuthenticationManager ce sam pozivati loadUserByUsername() metodu ovog servisa
                .userDetailsService(customUserDetailsService)

                // 2. kroz koji enkoder da provuce lozinku koju je dobio od klijenta u zahtevu
                // da bi adekvatan hash koji dobije kao rezultat hash algoritma uporedio sa onim koji se nalazi u bazi (posto se u bazi ne cuva plain lozinka)
                .passwordEncoder(encoderConfig.passwordEncoder());
    }







    // Definisemo prava pristupa za zahteve ka odredjenim URL-ovima/rutama
    @Override
    //@Autowired
    protected void configure(HttpSecurity http) throws Exception {
        http
                // komunikacija izmedju klijenta i servera je stateless posto je u pitanju REST aplikacija
                // ovo znaci da server ne pamti nikakvo stanje, tokeni se ne cuvaju na serveru
                // ovo nije slucaj kao sa sesijama koje se cuvaju na serverskoj strani - STATEFULL aplikacija
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // sve neautentifikovane zahteve obradi uniformno i posalji 401 gresku
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

                // svim korisnicima dopusti da pristupe sledecim putanjama:
                .authorizeRequests().antMatchers("/auth/**").permitAll()		// /auth/**
                .antMatchers("/h2-console/**").permitAll()	// /h2-console/** ako se koristi H2 baza)
                .antMatchers("/api/foo").permitAll()		// /api/foo
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/auth/signup").permitAll()
                .antMatchers("/upload").permitAll()


                // ukoliko ne zelimo da koristimo @PreAuthorize anotacije nad metodama kontrolera, moze se iskoristiti hasRole() metoda da se ogranici
                // koji tip korisnika moze da pristupi odgovarajucoj ruti. Npr. ukoliko zelimo da definisemo da ruti 'admin' moze da pristupi
                // samo korisnik koji ima rolu 'ADMIN', navodimo na sledeci nacin:
                // .antMatchers("/admin").hasRole("ADMIN") ili .antMatchers("/admin").hasAuthority("ROLE_ADMIN")

                // za svaki drugi zahtev korisnik mora biti autentifikovan
                .anyRequest().authenticated().and()

                // za development svrhe ukljuci konfiguraciju za CORS iz WebConfig klase
                .cors().and()

                // umetni custom filter TokenAuthenticationFilter kako bi se vrsila provera JWT tokena umesto cistih korisnickog imena i lozinke (koje radi BasicAuthenticationFilter)
                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, customUserDetailsService), BasicAuthenticationFilter.class);

        // zbog jednostavnosti primera ne koristimo Anti-CSRF token (https://cheatsheetseries.owasp.org/cheatsheets/Cross-Site_Request_Forgery_Prevention_Cheat_Sheet.html)
        http.csrf().disable();
    }

    // Definisanje konfiguracije koja utice na generalnu bezbednost aplikacije
    @Override
    public void configure(WebSecurity web) throws Exception {
        // Autentifikacija ce biti ignorisana ispod navedenih putanja (kako bismo ubrzali pristup resursima)
        // Zahtevi koji se mecuju za web.ignoring().antMatchers() nemaju pristup SecurityContext-u

        // Dozvoljena POST metoda na ruti /auth/login, za svaki drugi tip HTTP metode greska je 401 Unauthorized
        web.ignoring().antMatchers(HttpMethod.POST, "/auth/login");

        // Ovim smo dozvolili pristup statickim resursima aplikacije
        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "favicon.ico", "/**/*.html",
                "/**/*.css", "/**/*.js");
    }
}
