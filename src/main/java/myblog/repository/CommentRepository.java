package myblog.repository;

import myblog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> findAllByParentIsNullAndPostId(int postId);

    List<Comment> findAllByParentId(int parentId);

    int countByPostId(int postId);
}