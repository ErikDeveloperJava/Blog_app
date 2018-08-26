package myblog.controller.admin;

import myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/admin/user/delete/{id}")
    public @ResponseBody
    boolean delete(@PathVariable("id")int id){
        userService.deleteById(id);
        return true;
    }
}
