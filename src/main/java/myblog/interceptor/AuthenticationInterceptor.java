package myblog.interceptor;

import myblog.config.security.CurrentUser;
import myblog.model.User;
import myblog.model.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if(authentication.getPrincipal() instanceof CurrentUser){
            CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
            user = currentUser.getUser();
        }else {
            user = User.builder()
                    .role(UserRole.ROLE_ANONYMOUS)
                    .build();
        }
        request.setAttribute("user",user);
        return true;
    }
}
