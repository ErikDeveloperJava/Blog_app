package myblog.form;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequestForm {

    @Length(min = 2,max = 255,message = "in field title wrong data")
    private String title;

    @Length(min = 10,message = "in field description wrong data")
    private String description;

    private int catId;

    private MultipartFile image;
}