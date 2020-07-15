package com.github.tinyurl.springboot.starter.springbootstartertinuurl;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置属性
 *
 * @author jiquanxi
 * @date 2020/07/15
 */
@Data
@Component
@ConfigurationProperties(prefix = "tinyurl")
public class TinyUrlProperties {
    /**
     * 域名
     */
    private String host;

    /**
     * 端口
     */
    private String port;

    /**
     * appid
     */
    private String appId;

    /**
     * app密钥
     */
    private String key;
}
