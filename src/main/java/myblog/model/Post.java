package myblog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private String imgUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}