package com.github.tinyurl.client;

/**
 * tiny url客户端接口定义
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/16
 */
public interface TinyUrlClient {
    /**
     * 生成短连接
     * @param tinyUrlParam 生成短连接参数
     * @return 短连接对象
     */
    TinyUrlObject shorten(TinyUrlParam tinyUrlParam);
}
