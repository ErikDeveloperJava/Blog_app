package myblog.config.security;


import lombok.Getter;
import myblog.model.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User{

    @Getter
    private User user;

    public CurrentUser(User user) {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }
}
