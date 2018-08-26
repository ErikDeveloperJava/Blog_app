package myblog.service;

import myblog.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    boolean existsByUsername(String username);

    void add(User user, MultipartFile image);

    List<User> getAll(Pageable pageable);

    int count();

    void deleteById(int id);
}