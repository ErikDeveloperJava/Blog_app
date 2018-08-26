package myblog.form;

import lombok.*;
import myblog.model.Comment;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseForm {

    private Comment comment;

    private List<CommentResponseForm> childrenList;
}