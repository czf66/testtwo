# 添加 Java 8 镜像来源
FROM openjdk:8

# 添加 Spring Boot 包
ADD target/gateway-1.0-SNAPSHOT.jar gateway.jar

#暴露容器端口为8889 Docker镜像告知Docker宿主机应用监听了8889端口
EXPOSE 8889

# 执行启动命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/gateway.jar"]
