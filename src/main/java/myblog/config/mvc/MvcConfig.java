package myblog.config.mvc;

import myblog.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(new AuthenticationInterceptor())
                .excludePathPatterns("/resources/**","/login","/register",
                        "/categories","/posts/popular","/galleries","/admin/user/delete/*",
                        "/admin/gallery/image/upload","/admin/gallery/delete/*","/admin/post/delete/*",
                        "post/comments","/post/comments/count")
                .addPathPatterns("/**");
    }
}
