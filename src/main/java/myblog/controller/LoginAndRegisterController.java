package myblog.controller;

import myblog.form.UserRequestForm;
import myblog.model.User;
import myblog.model.UserRole;
import myblog.pages.Pages;
import myblog.service.UserService;
import myblog.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginAndRegisterController implements Pages {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageUtil imageUtil;

    @GetMapping("/login")
    public String login(){
        return LOGIN;
    }

    @GetMapping("/register")
    public String registerGet(Model model){
        model.addAttribute("form",new UserRequestForm());
        return REGISTER;
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("form") @Valid UserRequestForm form, BindingResult result){
        if(result.hasErrors()){
            return REGISTER;
        }else if (userService.existsByUsername(form.getUsername())){
            result.addError(new FieldError("form","username","user with username '"+ form.getUsername() +"' already exists"));
            return REGISTER;
        }else if(!form.getPassword().equals(form.getRepeatPassword())){
            result.addError(new FieldError("form","password","passwords do not matches"));
            result.addError(new FieldError("form","repeatPassword","passwords do not matches"));
            return REGISTER;
        }else if(form.getImage().isEmpty()){
            result.addError(new FieldError("form","image","image is empty"));
            return REGISTER;
        }else if(!imageUtil.isValidFormat(form.getImage().getContentType())){
            result.addError(new FieldError("form","image","invalid image format"));
            return REGISTER;
        }else {
            User user = User.builder()
                    .name(form.getName())
                    .surname(form.getSurname())
                    .password(form.getPassword())
                    .username(form.getUsername())
                    .role(UserRole.USER)
                    .build();
            userService.add(user,form.getImage());
            return "redirect:/login";
        }
    }
}