package myblog.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageableUtil {

    public static int getLength(int count,int size){
        if(count <= size){
            return 1;
        }else if(count % size == 0){
            return count/size;
        }else {
            return count/size + 1;
        }
    }

    public static Pageable getCheckedPageable(Pageable pageable,int length){
        if(pageable.getPageNumber() >= length){
            return PageRequest.of(0,pageable.getPageSize());
        }else {
           return pageable;
        }
    }
}
