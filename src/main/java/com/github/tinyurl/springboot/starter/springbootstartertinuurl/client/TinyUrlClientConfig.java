package com.github.tinyurl.springboot.starter.springbootstartertinuurl.client;

import lombok.Data;

/**
 * TODO 文件说明
 *
 * @author jiquanxi
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
}
