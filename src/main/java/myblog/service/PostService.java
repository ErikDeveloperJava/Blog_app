package myblog.service;

import myblog.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    List<Post> getAll(Pageable pageable);

    int count();

    List<Post> getAllPopulars(Pageable pageable);

    void add(Post post, MultipartFile image);

    void deleteById(int id);

    List<Post> getAllByCategoryId(int catId,Pageable pageable);

    int countByCategoryId(int catId);

    List<Post> getAllByTitleContains(String title,Pageable pageable);

    int countByTitleContains(String title);

    Post getById(int id);
}