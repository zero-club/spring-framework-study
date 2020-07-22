
# SpringFrameWork 源码下载编译与DEMO搭建
## 下载

git 地址：[https://github.com/spring-projects/spring-framework](https://github.com/spring-projects/spring-framework)

选择对应版本分支：

![图片](https://uploader.shimo.im/f/Y0haOz3x6o28EsbM.png!thumbnail)

尽量不要选择最新的分支，编译会出现各种问题，文本选用的是5.2.X，将源码clone到本地。

## 编译

参照项目内文件 import-into-idea.md  步骤如下：

![图片](https://uploader.shimo.im/f/zBz0gDRlGM2FgbI7.png!thumbnail)

注：如果需要调试 aop相关功能，还需构建spring-aspects项目。

### 安装Gradle

现在本机安装gradle，具体步骤百度。

在项目文件夹下 cmd 执行 gradlew :spring-oxm:compileTestJava

### 导入IDEA

![图片](https://uploader.shimo.im/f/b1yZ4XWnYMQzpust.png!thumbnail)

![图片](https://uploader.shimo.im/f/Jb7AgkEvnDPcd2Ro.png!thumbnail)

等待构建，可能需要较长时间。

注：为了加快构建速度，可将maven地址更换为国内站点，在 spring-framework-5.2.6.RELEASE\build.gradle 文件下替换成如下内容：

```plain
       repositories {
//       mavenCentral()
//       maven { url "https://repo.spring.io/libs-spring-framework-build" }
         maven { url 'https://maven.aliyun.com/repository/central' }
         maven { url 'https://maven.aliyun.com/repository/spring' }
      }
```
## 调试

### 新建Demo

![图片](https://uploader.shimo.im/f/yE1W35lLAuqnD7IR.png!thumbnail)

![图片](https://uploader.shimo.im/f/8L59w8QSYCDrK8Uf.png!thumbnail)

如上步骤建立一个maven项目。

### 引入spring源码依赖

File-->Project Structure -->Modules，选择Demo项目，添加spring源码依赖，因为用到AOP，所以引入spring-aop与spring-aspects，步骤如下：

![图片](https://uploader.shimo.im/f/MBlzr9nfVt2wyKxu.png!thumbnail)

### 编写测试类

配置类

```java
public class AppConfig {
   @Bean
   public TestDao testDao(){
      return new TestDao();
   }
}
```
Dao
```java
@Component
public class TestDao {
}
```
main入口
```java
public static void main(String[] args) {
   AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
   ctx.register(AppConfig.class);
   ctx.refresh();
   TestDao dao =  ctx.getBean(TestDao.class);
   System.out.println(dao.getClass());
}
```
运行debug，即可在容器中打印我们注入的dao，至此可以在源码中自由调试啦。
### 常见错误

#### CoroutinesUtils 缺失问题

![图片](https://uploader.shimo.im/f/iJTjqXLUJ1J36RkU.png!thumbnail)

kotlin编译的jar包未被项目引入，在spring-core项目里手动加入kotlin-coroutines-5.2.6.RELEASE.jar，步骤如下（本文都以IDEA为例）

File-->Project Structure -->Modules

![图片](https://uploader.shimo.im/f/vnFYVD93wrD2N0we.png!thumbnail)

#### 源码与IDEA版本

如果IDEA版本过低，运行Demo会报一个kotlin的错误，源码与IDEA中kotlin的版本冲突，本文用的是 IDEA ：2019.03，springFramework 5.2.X 

#### 缺少common-logging

调试java.lang.NoClassDefFoundError: org/apache/commons/logging/LogFactory ，在Demo中加入common-logging的依赖

```plain
<dependency>
   <groupId>commons-logging</groupId>
   <artifactId>commons-logging</artifactId>
   <version>1.2</version>
</dependency>
```
#### DefaultNamingPolicy找不到

spring-core缺少了spring-cglib-repack-3.2.5.jar和spring-objenesis-repack-2.6.jar的依赖，在模块中手动添加即可。

File-->Project Structure -->Modules，添加 \spring-core\build\libs\目录下对应的2个jar即可。

![图片](https://uploader.shimo.im/f/gLp0Yek3M7Z1kZDu.png!thumbnail)

## AspectJ 安装

### 下载

下载最新版 AspectJ，地址如下：

[https://www.eclipse.org/aspectj/downloads.php](https://www.eclipse.org/aspectj/downloads.php)

![图片](https://uploader.shimo.im/f/b83I68XQcVLJGhb7.png!thumbnail)

### 安装

下载成功后，在下载目录 运行 java  -jar aspectj-1.9.5.jar，如下图 下一步进行安装即可。

![图片](https://uploader.shimo.im/f/4KFK3BtPLPmfsSWX.png!thumbnail)

![图片](https://uploader.shimo.im/f/Ro9vRZJ9X8Wk1w3g.png!thumbnail)

### IDEA配置

File-->Project Structure -->Modules，引入AspectJ 支持

![图片](https://uploader.shimo.im/f/SdUCiVtjk4B6DUcU.png!thumbnail)

![图片](https://uploader.shimo.im/f/vwwtY10CBPs9n1nZ.png!thumbnail)

选择模块，只需要添加spring-aop.main 与spring-aspects.main即可，添加完成后如下图：

![图片](https://uploader.shimo.im/f/1cuiqlIgmUpzLX1T.png!thumbnail)

然后重新构建rebuild相应的2个 模块即可，至此，AspectJ安装完成。


