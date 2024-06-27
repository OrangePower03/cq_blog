## 分布式博客

本博客使用了分布式技术，具体框架使用了一下组件

1. nacos：服务治理，主要用到了注册中心的功能
2. dubbo：RPC远程通信框架
3. seata：分布式事务
4. SCG：网关服务，用于接收前端请求并分发请求到后端

具体版本依赖

1. JDK 11.0.20
2. Spring Boot 2.6.3
3. nacos-client 1.4.2
4. dubbo 2.7.15
5. seata-client 1.4.2
6. spring-cloud-gateway 3.1.1


由于dubbo 2.7.13以后被alibaba移除了，所以无法直接找到适配的版本，但根据dubbo官方的文档描述，nacos和dubbo的配合是1.x配合2.x还有2.x配合3.x。所以在配合好版本后再根据时间来导入依赖


### 遇到的问题

1. Dubbo传输的参数必须实现`java.io.Serializable`接口，并且mp中的wrapper对象无法作为参数传输，mp中传输wrapper数据会报以下错

   ```java
   java.lang.invode.SerializedLambda’ culd not be instantiated
   ```

2. 多模块下可能会打包失败，出现一种诡异的运行错误：在代码不报错的情况下报错说找不到这个包，这是因为打包并没有将依赖的本地包一连打包，导致依赖的本地包的class并没有被加载进去，需要添加以下的插件即可

   ```xml
   <build>
       <plugins>
           <plugin>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-maven-plugin</artifactId>
           </plugin>

           <plugin>
               <groupId>org.apache.maven.plugins</groupId>
               <artifactId>maven-compiler-plugin</artifactId>
               <version>3.1</version>
               <configuration>
                   <source>${java.version}</source>
                   <target>${java.version}</target>
                   <encoding>${project.build.sourceEncoding}</encoding>
               </configuration>
           </plugin>
       </plugins>
   </build>
   ```

3. 多模块下需要在main函数中添加扫描的包，否则无法扫描到公共模块下的一些bean，这不会直接导致错误，但会在执行业务时无法获取到对应的bean而报一些以前学习时没遇见到的错。比如，假如没有扫描项目中的`com.example.cloudAdminCommon`包会无法将jwtFilter过滤器纳入导致会走Security默认的验证

4. Dubbo和nacos正常运行下加入Seata后导致无法创建Dubbo代理，这是Dubbo和Seata版本不兼容导致的，在项目中seata-client 1.4.2不兼容dubbo 3.0.x

5. 使用网关跨域时记得要删掉重复的跨域请求头，这是因为后端一般也会有跨域的请求头，若是不删除会有两个跨域，最后浏览器会报错：脚本无法获取响应主体(原因：[CORS Multiple Origin Not Allowed])。解决方法就是添加一个默认过滤器

   ```yaml
   default-filters:
     - DedupeResponseHeader=Access-Control-Allow-Credentials Access-Control-Allow-Origin
   ```

6. 使用seata的XA事务后数据库可能会在执行sql时报以下错，这是因为seata和mysql驱动的版本不兼容导致的，需要将驱动降级到`8.0.11`才行，并且这个版本需要添加时区和useSSL的参数，否则会报红

   ```java
   com.mysql.cj.conf.PropertySet.getBooleanReadableProperty(java.lang.String)
     
   // 数据库参数：  database_uri?serverTimezone=Asia/Shanghai&useSSL=false  
   ```

7. jdk版本高的会经常有说到反射的问题，需要添加以下vm参数

   ```java
   --add-opens java.base/java.lang=ALL-UNNAMED
   --add-opens java.base/java.lang.reflect=ALL-UNNAMED
   ```

   其中有一个加了上面的参数仍然无法运行，是因为jdk版本太高了，使用17会导致错误，改为11即可

   ```java
   Unable to make protected final java.lang.Class java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain) throws java.lang.ClassFormatError accessible: module java.base does not "opens java.lang" to unnamed module 
   ```

8. SCG无法集成到nacos注册中心中，集成后会报以下的错。偶尔会导致500，无法找到服务错误，若是直连不会有任何错误。在服务中使用的dubbo后报以下错，这个错可能是指走http不成功导致的，而dubbo3.x以后默认已经不支持http协议了，所以可能就删除了对应http的依赖包，当更改为2.7.15后并未报错

   ```java
   java.lang.IllegalArgumentException: invalid version format: UNSUPPORTED
   	at io.netty.handler.codec.http.HttpVersion.<init>(HttpVersion.java:116) ~[netty-codec-http-4.1.73.Final.jar:4.1.73.Final]
   	Suppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException: 
   Error has been observed at the following site(s):
   	*__checkpoint ⇢org.springframework.cloud.gateway.filter.WeightCalculatorWebFilter [DefaultWebFilterChain]
   	*__checkpoint ⇢ HTTP GET "/article/hotArticleList" [ExceptionHandlingWebHandler]
   ```

   ​

   ​



### 未解决问题

dubbo日志报警告

```java
log4j:WARN No appenders could be found for logger 
       								(org.apache.dubbo.common.logger.LoggerFactory).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
```

