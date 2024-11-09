# 添加 Java 8 镜像来源
FROM openjdk:8

# 添加 Spring Boot 包
ADD target/user-1.0-SNAPSHOT.jar user.jar

#暴露容器端口为8890 Docker镜像告知Docker宿主机应用监听了8890端口
EXPOSE 8890

# 执行启动命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/user.jar"]
