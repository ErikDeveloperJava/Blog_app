package myblog.controller;

import myblog.form.PostRequestForm;
import myblog.model.Category;
import myblog.model.Post;
import myblog.pages.Pages;
import myblog.repository.CategoryRepository;
import myblog.service.PostService;
import myblog.util.ImageUtil;
import myblog.util.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController implements Pages {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageUtil imageUtil;

    @GetMapping("/posts/popular")
    public @ResponseBody
    List<Post> popularPost(){
        return postService.getAllPopulars(PageRequest.of(0,5));
    }

    @GetMapping("/admin/post/add")
    public String addGet(Model model){
        model.addAttribute("form",new PostRequestForm());
        return POST_ADD;
    }

    @PostMapping("/admin/post/add")
    public String addPost(@Valid @ModelAttribute("form")PostRequestForm form, BindingResult result){
        if(result.hasErrors()){
            return POST_ADD;
        }else if(!categoryRepository.existsById(form.getCatId())){
            result.addError(new FieldError("form","catId","please choose a category"));
            return POST_ADD;
        }else if(form.getImage().isEmpty()){
            result.addError(new FieldError("form","image","image is empty"));
            return POST_ADD;
        }else if(!imageUtil.isValidFormat(form.getImage().getContentType())){
            result.addError(new FieldError("form","image","invalid image format"));
            return POST_ADD;
        }else {
            Post post = Post.builder()
                    .title(form.getTitle())
                    .description(form.getDescription())
                    .createdDate(new Date())
                    .category(Category.builder().id(form.getCatId()).build())
                    .build();
            postService.add(post,form.getImage());
            return "redirect:/admin/posts";
        }
    }

    @GetMapping("/admin/posts")
    public String posts(Pageable pageable, Model model,
                        @RequestParam(value = "token",required = false,defaultValue = "none")String token){
        int length = PageableUtil.getLength(postService.count(),pageable.getPageSize());
        pageable = PageableUtil.getCheckedPageable(pageable,length);
        model.addAttribute("posts",postService.getAll(pageable));
        model.addAttribute("pageNumber",pageable.getPageNumber());
        model.addAttribute("length",length-1);
        return token.equals("none") ? POSTS : POSTS + "_js";
    }

    @PostMapping("/admin/post/delete/{id}")
    public @ResponseBody
    boolean delete(@PathVariable("id")int id){
        postService.deleteById(id);
        return true;
    }

    @GetMapping("/posts/category/{id}")
    public String searchByCategory(@PathVariable("id")String strId,Model model,Pageable pageable){
        try {
            Optional<Category> optCategory = categoryRepository.findById(Integer.parseInt(strId));
            Category category;
            if(!optCategory.isPresent()){
                return "redirect:/";
            }else {
                category = optCategory.get();
            }
            int length = PageableUtil.getLength(postService.countByCategoryId(category.getId()),pageable.getPageSize());
            pageable = PageableUtil.getCheckedPageable(pageable,length);
            List<Post> posts = postService.getAllByCategoryId(category.getId(), pageable);
            model.addAttribute("posts",posts);
            model.addAttribute("pageNumber",pageable.getPageNumber());
            model.addAttribute("length",length-1);
            model.addAttribute("title",!posts.isEmpty() ?
                    "search by category: '" + category.getName() + "'" :
                    "search for '" + category.getName()+ "' did not yield any results");
            return INDEX;
        }catch (NumberFormatException e){
            return "redirect:/";
        }
    }

    @GetMapping("/post/search")
    public String searchByTitle(@RequestParam("title")String title,Model model,Pageable pageable){
        if(title == null || title.trim().equals("")){
            return "redirect:/";
        }else {
            int length = PageableUtil.getLength(postService.countByTitleContains(title),pageable.getPageSize());
            pageable = PageableUtil.getCheckedPageable(pageable,length);
            model.addAttribute("posts");
            List<Post> posts = postService.getAllByTitleContains(title,pageable);
            model.addAttribute("posts",posts);
            model.addAttribute("pageNumber",pageable.getPageNumber());
            model.addAttribute("length",length-1);
            model.addAttribute("title",!posts.isEmpty() ?
                    "search by title: '" + title + "'" :
                    "search for '" + title + "' did not yield any results");
            return INDEX;
        }
    }

    @GetMapping("/post/{id}")
    public String onePost(@PathVariable("id")String strId,Model model){
        try {
            Post post = postService.getById(Integer.parseInt(strId));
            if(post == null){
                throw new NumberFormatException();
            }
            model.addAttribute("post",post);
            return POST_DETAIL;
        }catch (NumberFormatException e){
            return "redirect:/";
        }
    }
}