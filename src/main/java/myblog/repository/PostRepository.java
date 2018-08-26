package myblog.repository;

import myblog.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findAllByOrderByCreatedDateDesc(Pageable pageable);

    @Query("select p from Post p order by size(p.comments) desc")
    List<Post> findAllPopulars(Pageable pageable);

    List<Post> findAllByTitleContains(String title,Pageable pageable);

    int countByTitleContains(String title);

    List<Post> findAllByCategoryId(int catId,Pageable pageable);

    int countByCategoryId(int catId);
}