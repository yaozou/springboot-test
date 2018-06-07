/**
 * created by yaozou on 2018/6/7
 */

import com.yaozou.platform.common.utils.ApiPost;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yaozou
 * @create 2018-06-07 17:27
 **/
public class SpringTest {

    private static String url = "http://127.0.0.1:7501/wallet";
    private static String API_VER = "1.0.0";


    @Test
    public void transaction() {
        //已调通
        url = url+"/spring/transaction/test";
        Map map = ApiPost.sendPost(url, new HashMap<>(), API_VER);
        System.out.println("result:"+map);
    }
}
