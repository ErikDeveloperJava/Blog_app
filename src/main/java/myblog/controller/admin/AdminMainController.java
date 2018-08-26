package myblog.controller.admin;

import myblog.pages.Pages;
import myblog.service.UserService;
import myblog.util.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminMainController implements Pages {

    @Autowired
    private UserService userService;

    @GetMapping
    public String main(Model model, Pageable pageable, @RequestParam(value = "token",required = false,defaultValue = "none")String token){
        int length = PageableUtil.getLength(userService.count(), pageable.getPageSize());
        pageable = PageableUtil.getCheckedPageable(pageable,length);
        model.addAttribute("users",userService.getAll(pageable));
        model.addAttribute("pageNumber",pageable.getPageNumber());
        model.addAttribute("length",length-1);
        return token.equals("none") ? "admin/" + INDEX : USERS;
    }
}
