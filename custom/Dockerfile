# 基础镜像
FROM java:8
#维护者信息
MAINTAINER viwcy <viwcy4611@gamil.com>
# 复制打包完成后的jar文件到/home目录下
ADD target/custom.jar app.jar
EXPOSE  9001
# 启动容器时执行
#为了缩短 Tomcat 的启动时间，添加java.security.egd的系统属性指向/dev/urandom作为 ENTRYPOINT
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]