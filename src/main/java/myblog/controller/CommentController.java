package myblog.controller;

import myblog.form.CommentResponseForm;
import myblog.model.Comment;
import myblog.model.User;
import myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/post/comments/count")
    public int commentsCount(@RequestParam("postId")int postId){
        return commentService.countByPostId(postId);
    }

    @GetMapping("/post/comments")
    public List<CommentResponseForm> comments(@RequestParam("postId")int postId){
        return commentService.getAllByPostId(postId);
    }

    @PostMapping("/post/comment/add")
    public Comment add(@Valid Comment comment, BindingResult result,
                       @RequestAttribute("user")User user){
        if(result.hasErrors()){
            return new Comment();
        }else {
            comment.setUser(user);
            comment.setSendDate(new Date());
            commentService.add(comment);
            return comment;
        }
    }
}