package myblog.service;

import myblog.model.Gallery;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GalleryService {

    List<Gallery> getAll(Pageable pageable);

    int count();

    void add(Gallery gallery, MultipartFile multipartFile);

    void deleteById(int id);
}