package myblog.repository;

import myblog.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<Gallery,Integer> {
}
