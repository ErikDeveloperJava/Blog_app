package myblog.service.impl;

import myblog.model.Gallery;
import myblog.repository.GalleryRepository;
import myblog.service.GalleryService;
import myblog.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GalleryServiceImpl implements GalleryService {

    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    private ImageUtil imageUtil;

    @Override
    public List<Gallery> getAll(Pageable pageable) {
        return galleryRepository.findAll(pageable).getContent();
    }

    @Override
    public int count() {
        return (int) galleryRepository.count();
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(Gallery gallery, MultipartFile multipartFile) {
        galleryRepository.save(gallery);
        try {
            imageUtil.save("galleries\\" + gallery.getId(),gallery.getImgUrl(),multipartFile);
            gallery.setImgUrl(gallery.getId() + "/" + gallery.getImgUrl());
        }catch (Exception e){
            imageUtil.delete("galleries\\" + gallery.getId());
            throw new RuntimeException(e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(int id) {
        imageUtil.delete("galleries\\" + id);
        galleryRepository.deleteById(id);
    }
}
