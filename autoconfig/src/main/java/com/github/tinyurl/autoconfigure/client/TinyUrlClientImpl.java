package com.github.tinyurl.autoconfigure.client;

import com.github.tinyurl.autoconfigure.constant.Constants;
import com.github.tinyurl.autoconfigure.constant.ErrorCode;
import com.github.tinyurl.autoconfigure.exception.TinyUrlException;
import com.github.tinyurl.autoconfigure.lb.BaseLoadBalancer;
import com.github.tinyurl.autoconfigure.lb.LoadBalancer;
import com.github.tinyurl.autoconfigure.lb.Server;
import com.github.tinyurl.autoconfigure.util.NumberUtil;
import com.github.tinyurl.autoconfigure.util.ObjectUtil;
import com.github.tinyurl.autoconfigure.util.SignUtil;
import com.github.tinyurl.autoconfigure.util.StringUtil;
import com.github.tinyurl.client.TinyUrlClient;
import com.github.tinyurl.client.TinyUrlObject;
import com.github.tinyurl.client.TinyUrlParam;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * 短链接客户端
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/15
 */
public class TinyUrlClientImpl implements TinyUrlClient {

    private final TinyUrlClientConfig clientConfig;
    private final RestTemplate restTemplate;
    private final LoadBalancer loadBalancer;

    public TinyUrlClientImpl(final TinyUrlClientConfig clientConfig,
                             final RestTemplate restTemplate) {
        this.clientConfig = clientConfig;
        this.restTemplate = restTemplate;
        loadBalancer = new BaseLoadBalancer();
    }

    public void initialize() {
        if (StringUtil.isEmpty(clientConfig.getHost())) {
            throw new TinyUrlException(ErrorCode.CLIENT_HOST_NOT_CONFIGURE);
        }

        // 解析服务
        String[] servers = clientConfig.getHost().split(Constants.SEMICOLON);
        for (String s : servers) {
            String scheme;
            String hostPort;
            if (s.toLowerCase().startsWith(Constants.HTTP_SCHEMA)) {
                scheme = Constants.HTTP_SCHEMA;
                hostPort = s.substring(scheme.length());
            } else if (s.startsWith(Constants.HTTPS_SCHEMA)) {
                scheme = Constants.HTTPS_SCHEMA;
                hostPort = s.substring(scheme.length());
            } else {
                // 默认方案为http
                scheme = Constants.HTTPS_SCHEMA;
                hostPort = s;
            }

            String[] hps = hostPort.split(":");
            if (hps.length != 2) {
                throw new TinyUrlException(ErrorCode.CLIENT_HOST_FORMAT_ERROR);
            }

            if (!NumberUtil.isNumber(hps[1])) {
                throw new TinyUrlException(ErrorCode.CLIENT_HOST_FORMAT_ERROR);
            }

            int port = Integer.parseInt(hps[1]);

            Server server = new Server(hps[0], port);

            loadBalancer.addServer(server);
        }
    }

    @Override
    public TinyUrlObject shorten(TinyUrlParam tinyUrlParam) {
        Server server = loadBalancer.chooseServer(null);
        if (ObjectUtil.isNull(server)) {
            throw new TinyUrlException(ErrorCode.SYSTEM_ERROR);
        }

        // 组装请求参数
        Map<String, String> requestParam = new TreeMap<>();
        requestParam.put("url", tinyUrlParam.getUrl());
        requestParam.put("type", "DECIMAL");
        requestParam.put("domain", tinyUrlParam.getDomain());
        requestParam.put("timestamp", String.valueOf(System.currentTimeMillis()));
        requestParam.put("appId", clientConfig.getAppId());
        requestParam.put("nonceStr", UUID.randomUUID().toString());
        // 生成签名
        requestParam.put("sign", SignUtil.sign(requestParam, clientConfig.getKey()));

        // 发起请求
        Response response = restTemplate.postForObject(server.getUrl(), requestParam, Response.class);
        if (ObjectUtil.isNull(response)) {
            throw new TinyUrlException(ErrorCode.CLIENT_REMOTE_CALL_ERROR);
        }

        if (ErrorCode.SUCCESS.getCode() != response.getCode()) {
            throw new TinyUrlException(ErrorCode.CLIENT_REMOTE_CALL_ERROR);
        }

        String shortUrl = (String) response.getData();


        // 组装Object 返回
        TinyUrlObject object = new TinyUrlObject();
        object.setUrl(shortUrl);
        return object;
    }
}
