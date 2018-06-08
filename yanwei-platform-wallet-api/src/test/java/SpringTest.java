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
        /**
         * switch (type){
         case 1 : updatePrivate("alipay",1); break;
         case 2 : updatePublic("alipay",1); break;
         case 3 : updateAToBForPrivate("alipay",1); break;
         case 4 : updateAToBForPublic("alipay",1); break;
         case 5 : updateNotThrowException("alipay",2); break;
         }
         */
        //已调通
        url = url+"/spring/transaction/test";
        Map param = new HashMap<>();
        param.put("type",4);
        Map map = ApiPost.sendPost(url,param , API_VER);
        System.out.println("result:"+map);
    }
}
