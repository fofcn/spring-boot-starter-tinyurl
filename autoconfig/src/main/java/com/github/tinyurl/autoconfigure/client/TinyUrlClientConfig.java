package com.github.tinyurl.autoconfigure.client;

import lombok.Data;

/**
 * 短链接客户端配置
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/15
 */
@Data
public class TinyUrlClientConfig {
    /**
     * 域名,多个域名以逗号分割
     */
    private String host;

    /**
     * appid
     */
    private String appId;

    /**
     * app密钥
     */
    private String key;

    /**
     * 连接超时时间
     */
    private long connTimeout = 30000L;

    /**
     * 读取超时时间
     */
    private long readTimeout = 30000L;
}
