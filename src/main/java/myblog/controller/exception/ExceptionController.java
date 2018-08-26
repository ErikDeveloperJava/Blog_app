package myblog.controller.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionController {

    @GetMapping("/error")
    public String errr500(){
        return "error/500";
    }
}
