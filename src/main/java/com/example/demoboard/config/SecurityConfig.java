package com.example.demoboard.config;

import com.example.demoboard.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//WebSecurityConfig는 사용자 인증에 대한 정보를 포함
@Configuration //@Configuration 어노테이션은 어노테이션기반 환경구성을 돕는다. 클래스가 하나 이상의 @Bean 메소드를 제공하고 스프링 컨테이가 Bean정의를 생성하고 런타임시 그 Bean들이 요청들을 처리할 것을 선언
@EnableWebSecurity //어노테이션을 달면SpringSecurityFilterChain이 자동으로 포함
@AllArgsConstructor //클래스에 존재하는 모든 필드에 대한 생성자를 자동으로 생성
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    private MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 페이지 권한 설정
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/myinfo").hasRole("MEMBER")
                .antMatchers("/**").permitAll()
                .and() // 로그인 설정
                .formLogin()
                .loginPage("/user/login")
                .defaultSuccessUrl("/user/login/result")
                .permitAll()
                .and() // 로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/user/logout/result")
                .invalidateHttpSession(true)
                .and()
                // 403 예외처리 핸들링
                .exceptionHandling().accessDeniedPage("/user/denied");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
//    private MemberService memberService;
//
//    @Bean //개발자가 직접 제어할 수 없는 외부 라이브러리를 빈으로 만들고자 할 때 사용
//    public PasswordEncoder passwordEncoder(){ //pwd encoding
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    public void configure(WebSecurity webSecurity) throws Exception{
//        //static 하위의 폴더는 인증 생략
//        webSecurity.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
//    }
//
//    @Override
//    public void configure(HttpSecurity httpSecurity) throws Exception{ // HTTP 요청에 대한 웹기반 보안 구성
//        httpSecurity.authorizeRequests() //사용자 인증이 된 요청에 대해서만 요청을 허용한다.
//                .antMatchers("/admin/**").hasRole("ADMIN") //admin으로 시작하는 url은 ADMIN만 접근가능
//                .antMatchers("/user/myinfo/**").hasRole("MEMBER") // user/myinfo url은 멤버들만 접근가능
//                .antMatchers("/**").permitAll() //위 두개를 제외한 나머지 url은 모두 허용
//            .and()
//                .formLogin() //사용자는 폼기반 로그인으로 인증 할 수 있습니다. 로그인 정보는 기본적으로 HttpSession을 이용
//                .loginPage("/user/login")
//                .defaultSuccessUrl("/user/login/result")
//                .permitAll()
//            .and()
//                .logout() //HTTP Session 무효화, 본인의 설정된 인증을 깨끗히 제거
//                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
//                .logoutSuccessUrl("/user/logout/result")
//                .invalidateHttpSession(true) // HTTP 세션을 초기화하는 작업
//            .and() //accessdenied
//                .exceptionHandling().accessDeniedPage("user/denied");
//
//    }
//
//    //Spring Security에서 모든 인증은 AuthenticationManager를 통해 이루어지며 AuthenticationManager를 생성하기 위해서는 AuthenticationManagerBuilder를 사용
//    @Override
//    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
//        authenticationManagerBuilder.userDetailsService(memberService).passwordEncoder(passwordEncoder());
//    }
    //로그인 처리 즉, 인증을 위해서는 UserDetailService(MemberService)를 통해서 필요한 정보들을 가져옴
    //서비스 클래스에서는 UserDetailsService(MemberService) 인터페이스를 implements하여, loadUserByUsername() 메서드를 구현하면 됨
}