package myblog.controller;

import myblog.model.User;
import myblog.model.UserRole;
import myblog.pages.Pages;
import myblog.service.PostService;
import myblog.util.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

@Controller
public class MainController implements Pages {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String main(@RequestAttribute("user")User user, Model model,
                       Pageable pageable){
        if(user.getRole().equals(UserRole.ADMIN)){
            return "redirect:/admin";
        }else {
            int length = PageableUtil.getLength(postService.count(),pageable.getPageSize());
            pageable = PageableUtil.getCheckedPageable(pageable,length);
            model.addAttribute("posts",postService.getAll(pageable));
            model.addAttribute("pageNumber",pageable.getPageNumber());
            model.addAttribute("length",length-1);
            model.addAttribute("title","All posts");
            return INDEX;
        }
    }

    @GetMapping("/about")
    public String about(){
        return ABOUT;
    }
}
