# thymeleaf

## 依赖导入

```xml
<!-- https://mvnrepository.com/artifact/org.thymeleaf/thymeleaf -->
<dependency>
    <groupId>org.thymeleaf</groupId>
    <artifactId>thymeleaf</artifactId>
    <version>3.0.11.RELEASE</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.thymeleaf/thymeleaf-spring5 -->
<dependency>
    <groupId>org.thymeleaf</groupId>
    <artifactId>thymeleaf-spring5</artifactId>
    <version>3.0.11.RELEASE</version>
</dependency>
```

配置thymeleaf视图解析器：

```xml
<!-- thymeleaf的视图解析器 会与冲突ContentNegotiatingViewResolver-->
<bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
<property name="characterEncoding" value="UTF-8"/>
<property name="templateEngine" ref="templateEngine"/>
</bean>
<!-- 模板引擎 -->
<bean id="templateEngine" class="org.thymeleaf.spring5.SpringTemplateEngine">
<property name="templateResolver" ref="templateResolver"/>
</bean>
<!-- 模板解析器 -->
<bean id="templateResolver" class="org.thymeleaf.templateresolver.ServletContextTemplateResolver">
<constructor-arg ref="servletContext"/>
<property name="prefix" value="/WEB-INF/muke/"/>
<property name="suffix" value=".html"/>
<property name="templateMode" value="HTML5"/>
<property name="cacheable" value="false"/>
<property name="characterEncoding" value="UTF-8"/>
</bean>
```

```html
<!-- 在html页面的head标签需加上的 -->
xmlns:th="http://www.thymeleaf.org"
```



## 资源引入



## 文本



## 表达式



## 条件判断



