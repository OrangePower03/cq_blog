    package com.example.cloudCommon.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override // springBoot的跨域请求
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")   // 允许跨域的路径
                .allowedOriginPatterns("*")     // 允许跨域请求的域名
                .allowCredentials(true)         // 是否允许cookie
                .allowedMethods("GET","POST","DELETE","PUT")  //允许的请求方法
                .allowedHeaders("*")
                .maxAge(3600);                  // 允许跨域时间
    }

    // 全局配置日志格式，还有前后端的json数据交互时的字符串和数字间的转换
    @Bean //使用@Bean注入fastJsonHttpMessageConvert
    public HttpMessageConverter fastJsonHttpMessageConverters() {
        //1.需要定义一个Convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //配置日期的格式
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");

        SerializeConfig.globalInstance.put(Long.class, ToStringSerializer.instance);

        fastJsonConfig.setSerializeConfig(SerializeConfig.globalInstance);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverters());
    }
}
