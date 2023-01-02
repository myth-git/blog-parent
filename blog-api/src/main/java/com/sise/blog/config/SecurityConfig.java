package com.sise.blog.config;

import com.sise.blog.handler.*;
import com.sise.blog.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
//import org.springframework.security.web.access.AccessDeniedHandlerImpl;


import javax.annotation.Resource;

/**
 * @Description: security配置类
 * @Author: xzw
 * @Date: 2022/12/31 20:23
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource   //查询数据库是否有该用户
    private UserDetailsServiceImpl userDetailsService;
    @Resource   //用户权限处理
    private AccessDeniedHandlerImpl accessDeniedHandler;
    @Resource   //用户未登录处理
    private AuthenticationEntryPointImpl authenticationEntryPoint;
    @Resource   //用户登录成功处理
    private AuthenticationSuccessHandlerImpl authenticationSuccessHandler;
    @Resource   //注销成功处理
    private LogoutSuccessHandlerImpl logoutSuccessHandler;
    @Resource   //登录失败处理
    private AuthenticationFailHandlerImpl authenticationFailHandler;



    // 自定义filter交给工厂管理， 不然spring搜不到我们这个过滤器
    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter filter = new LoginAuthenticationFilter();
        filter.setFilterProcessesUrl("/login");//用户前端登录所用的路径
        filter.setUsernameParameter("username");
        filter.setPasswordParameter("password");
        /*
        *设置AuthenticationManager,这里我们用自定义的,一般来说都是用自定义的，
        * 因为默认的AuthenticationManager功能比较单一
        * 所以下面要进行暴露出来
        * */
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);//登录成功的结果处理
        filter.setAuthenticationFailureHandler(authenticationFailHandler);//登陆失败的结果处理
        //session并发控制,因为默认的并发控制方法是空方法.这里必须自己配置一个
        filter.setSessionAuthenticationStrategy(new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry()));
        return filter;
    }
    /**
     * 自定义的loginFilter要使用，所以要将这个manager暴露出来给其使用
     */
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 解决session失效后 sessionRegistry中session没有同步失效的问题，启用并发session控制，首先需要在配置中增加下面监听器
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * 注册bean sessionRegistry
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    //configure方法覆盖了原有的AuthenticationManager ，实现自定义AuthenticationManager
    @Override
    protected void configure(HttpSecurity http) throws Exception {  //将SpringSecurity的拦截请求全部放行
        http.headers().frameOptions().disable();    //Spring Security4默认是将'X-Frame-Options' 设置为 'DENY'
        http
                .formLogin()                        //自定义自己编写的登录页面
                .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)    //默认会话失效
                .clearAuthentication(true)     //清除认证标记
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/**")   //设置哪些路劲可以直接访问，不需要认证
//                .anyRequest()
                .permitAll()
                //解决跨域
                .and()
                .cors()
                // 关闭csrf防护
                .and()
                .csrf()
                .disable().exceptionHandling()
                // 未登录处理
                .authenticationEntryPoint(authenticationEntryPoint)
                // 权限不足处理
                .accessDeniedHandler(accessDeniedHandler);
        http.addFilterAt(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                //session并发控制过滤器
                .addFilterAt(new ConcurrentSessionFilter(sessionRegistry()),ConcurrentSessionFilter.class);
    }

    /**
     * 身份认证接口（自定义的AuthenticationManager）
     *  用户名和密码认证
     *  userDetailService是自定义数据源
     * @param auth 身份
     * @throws Exception 异常处理
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // 从数据库读取的用户进行身份认证
                .userDetailsService(userDetailsService);
    }

}
