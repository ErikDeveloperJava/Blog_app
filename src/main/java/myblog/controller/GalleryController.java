package myblog.controller;

import myblog.model.Gallery;
import myblog.pages.Pages;
import myblog.service.GalleryService;
import myblog.util.ImageUtil;
import myblog.util.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class GalleryController implements Pages {

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private ImageUtil imageUtil;

    @GetMapping("/galleries")
    public @ResponseBody
    List<Gallery> Galleries(){
        return galleryService.getAll(PageRequest.of(0,6));
    }

    @GetMapping("/gallery")
    public String gallery(Pageable pageable, Model model,
                          @RequestParam(value = "token",required = false,defaultValue = "none")String token){
        int length = PageableUtil.getLength(galleryService.count(),10);
        pageable = PageableUtil.getCheckedPageable(pageable,length);
        model.addAttribute("galleries",galleryService.getAll(PageRequest.of(pageable.getPageNumber(),10,Sort.Direction.DESC,"id")));
        model.addAttribute("pageNumber",pageable.getPageNumber());
        model.addAttribute("length",length-1);
        return token.equals("none") ? GALLERY : GALLERIES;
    }

    @PostMapping("/admin/gallery/image/upload")
    public @ResponseBody
    boolean imageUpload(@RequestParam("image")MultipartFile image){
        if(image.isEmpty() || !imageUtil.isValidFormat(image.getContentType())){
            return false;
        }else {
            Gallery gallery = Gallery.builder()
                    .imgUrl(System.currentTimeMillis() + image.getOriginalFilename())
                    .build();
            galleryService.add(gallery,image);
            return true;
        }
    }

    @PostMapping("/admin/gallery/delete/{id}")
    public @ResponseBody
    boolean delete(@PathVariable("id")int id){
        galleryService.deleteById(id);
        return true;
    }
}