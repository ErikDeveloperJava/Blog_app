package myblog.service.impl;

import myblog.model.Post;
import myblog.repository.PostRepository;
import myblog.service.PostService;
import myblog.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImageUtil imageUtil;

    @Override
    public List<Post> getAll(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

    @Override
    public int count() {
        return (int) postRepository.count();
    }

    @Override
    public List<Post> getAllPopulars(Pageable pageable) {
        return postRepository.findAllPopulars(pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(Post post, MultipartFile image) {
        postRepository.save(post);
        String imgUrl = System.currentTimeMillis() + image.getOriginalFilename();
        post.setImgUrl(post.getId() + "/" + imgUrl);
        try {
            imageUtil.save("posts\\" + post.getId(),imgUrl,image);
        }catch (Exception e){
            imageUtil.delete("posts\\" + post.getId());
            throw new RuntimeException(e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(int id) {
        imageUtil.delete("posts\\" + id);
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getAllByCategoryId(int catId, Pageable pageable) {
        return postRepository.findAllByCategoryId(catId,pageable);
    }

    @Override
    public int countByCategoryId(int catId) {
        return postRepository.countByCategoryId(catId);
    }

    @Override
    public List<Post> getAllByTitleContains(String title, Pageable pageable) {
        return postRepository.findAllByTitleContains(title,pageable);
    }

    @Override
    public int countByTitleContains(String title) {
        return postRepository.countByTitleContains(title);
    }

    @Override
    public Post getById(int id) {
        Optional<Post> optPost = postRepository.findById(id);
        return optPost.isPresent() ? optPost.get() : null;
    }
}