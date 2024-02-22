package com.example.cloudGateway.filter;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Deprecated
@Component
public class PortSwitchGatewayFilterFactory extends AbstractGatewayFilterFactory {
    public static final PortMapping ARTICLE_PORT = new PortMapping(6666,"/article");
    public static final PortMapping USER_PORT = new PortMapping(6667,"/user");
    public static final PortMapping COMMENT_PORT = new PortMapping(6668,"/comment");
    public static final PortMapping LINK_PORT = new PortMapping(6669,"/link");

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        Map<String, String> uriVariables = ServerWebExchangeUtils.getUriTemplateVariables(exchange);
        String uri = request.getURI().toString();
        if(uri.startsWith(ARTICLE_PORT.uriPrefix)) {

        }
        return chain.filter(exchange);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return null;
    }



    @Data
    public static class Config {
        private String port;
    }

    static class PortMapping {
        int port;

        String uriPrefix;

        public PortMapping(int port, String uriPrefix) {
            this.port = port;
            this.uriPrefix = uriPrefix;
        }
    }
}
