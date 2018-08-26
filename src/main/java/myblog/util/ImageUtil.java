package myblog.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
public class ImageUtil {

    private static final String[] DIR_NAMES = {"posts","users","galleries"};
    private static final String[] IMAGE_FORMATS = {"image/jpeg","image/png"};

    @Value("${images.root.path}")
    private String rootPath;


    @PostConstruct
    public void init(){
        File file = new File(rootPath);
        if(!file.exists()){
            file.mkdirs();
        }
        for (String dir : DIR_NAMES) {
            createDir(new File(file,dir));
        }
    }

    public boolean isValidFormat(String format){
        for (String imageFormat : IMAGE_FORMATS) {
            if(imageFormat.equals(format)){
                return true;
            }
        }
        return false;
    }

    private void createDir(File dir){
        if(!dir.exists()){
            dir.mkdir();
        }
    }

    public void save(String dir, String img, MultipartFile multipartFile){
        File file = new File(rootPath,dir);
        if(!file.exists()){
            if(!file.mkdir()){
                throw new RuntimeException("file '" + file +"' failed created");
            }
        }
        try {
            multipartFile.transferTo(new File(file,img));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void delete(String fileName){
        File file = new File(rootPath,fileName);
        if(file.exists()){
            delete(file);
        }
    }

    private void delete(File file){
        if(file.isDirectory()){
            for (File f : file.listFiles()) {
                delete(f);
            }
            file.delete();
        }else {
            file.delete();
        }
    }
}
