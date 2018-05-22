package com.yanwei.platform.common.manager;


import com.yanwei.platform.common.domain.TokenModel;

/**
 * 对Token进行操作的接口 
 * @author luojianhong
 * @version $Id: TokenManager.java, v 0.1 2017年11月15日 下午3:25:04 luojianhong Exp $
 */
public interface TokenManager {

    /** 
     * 创建一个token关联上指定用户 
     * @param userId 指定用户的id 
     * @return 生成的token 
     */
    public TokenModel createToken(String userId,String userName);

    /** 
     * 检查token是否有效 
     * @param model token 
     * @return 是否有效 
     */
    public boolean checkToken(String token);

    /** 
     * token 
     * @param token  
     * @return 
     */
    public TokenModel getToken(String token);

    /** 
     * 清除token 
     * @param token 登录用户的token
     */
    public void deleteToken(String token);

}
