package com.github.tinyurl.springboot.starter.springbootstartertinuurl.client;

/**
 * 短链接客户端
 *
 * @author jiquanxi
 * @date 2020/07/15
 */
public class TinyUrlClientImpl {

    private final TinyUrlClientConfig clientConfig;

    public TinyUrlClientImpl(final TinyUrlClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    public TinyUrlObject shorten(TinyUrlParam tinyUrlParam) {
        return null;
    }
}
