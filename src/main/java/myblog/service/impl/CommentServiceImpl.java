package myblog.service.impl;

import myblog.form.CommentResponseForm;
import myblog.model.Comment;
import myblog.repository.CommentRepository;
import myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<CommentResponseForm> getAllByPostId(int postId) {
        List<Comment> comments = commentRepository.findAllByParentIsNullAndPostId(postId);
        List<CommentResponseForm> forms = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponseForm form = CommentResponseForm.builder()
                    .comment(comment)
                    .build();
            setChildrenList(form);
            forms.add(form);
        }
        return forms;
    }

    @Override
    public int countByPostId(int postId) {
        return commentRepository.countByPostId(postId);
    }

    @Transactional
    public void add(Comment comment) {
        commentRepository.save(comment);
    }

    private void setChildrenList(CommentResponseForm commentForm){
        List<Comment> childrenList = commentRepository.findAllByParentId(commentForm.getComment().getId());
        if(!childrenList.isEmpty()){
            List<CommentResponseForm> comments = new ArrayList<>();
            for (Comment comment : childrenList) {
                CommentResponseForm form = CommentResponseForm.builder()
                        .comment(comment)
                        .build();
                setChildrenList(form);
                comments.add(form);
            }
            commentForm.setChildrenList(comments);
        }else {
            commentForm.setChildrenList(new ArrayList<>());
        }
    }
}
