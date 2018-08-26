package myblog.service.impl;

import myblog.model.User;
import myblog.repository.UserRepository;
import myblog.service.UserService;
import myblog.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ImageUtil imageUtil;

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(User user, MultipartFile image) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        String imgUrl = System.currentTimeMillis() + image.getOriginalFilename();
        user.setImgUrl(user.getId() + "/" + imgUrl);
        try {
            imageUtil.save("users\\" + user.getId(),imgUrl,image);
        }catch (Exception e){
            imageUtil.delete("users\\" + user.getId());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).getContent();
    }

    @Override
    public int count() {
        return (int) userRepository.count();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(int id) {
        imageUtil.delete("users\\" + id);
        userRepository.deleteById(id);
    }
}
