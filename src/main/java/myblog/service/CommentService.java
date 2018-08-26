package myblog.service;

import myblog.form.CommentResponseForm;
import myblog.model.Comment;

import java.util.List;

public interface CommentService {

    List<CommentResponseForm> getAllByPostId(int postId);

    int countByPostId(int postId);

    void add(Comment comment);
}
