package com.yanwei.platform.common.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 环境
 *
 * @author zhengrj
 * @since 2017-07-28
 */
public enum Env implements IEnumType {
    Dev(0, "测试"),
    Local(1, "本地"),
    Prod(2, "生产"),
    Test(3, "模拟生产");


    private final static Map<Integer, Env> stateMap
            = Arrays.stream(Env.values()).collect(Collectors.toMap(Env::code, code -> code));

    private final static Map<String, Env> stateByNameMap
            = Arrays.stream(Env.values()).collect(Collectors.toMap(code -> code.name().toLowerCase(), code -> code));


    private final int code;
    private final String desc;

    /**
     * @param code 代码
     * @param desc 描述
     */
    Env(final int code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public String desc() {
        return this.desc;
    }

}
