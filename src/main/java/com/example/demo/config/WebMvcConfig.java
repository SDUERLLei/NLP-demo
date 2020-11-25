package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override 
	public void addCorsMappings(CorsRegistry registry)
	{ registry 
		.addMapping("/**") //配置可以被跨域的路径 
		.allowedMethods("*") //允许所有的请求方法访问该跨域资源服务器如：POST、GET、PUT、DELETE等。
		.allowedOrigins("*") //允许所有的请求域名访问我们的跨域资源 
		.allowedHeaders("*")//允许所有的请求header访问 可以自定义设置任意请求头信息，如："X-YAUTH-TOKEN" 
		.allowCredentials(true);//解决单点登录跨域问题
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		registry.addResourceHandler("*")
				.addResourceLocations("classpath:/webapp/")
		        .addResourceLocations("classpath:/static/");
	}

	/*@Bean
	public InternalResourceViewResolver resourceViewResolver()
	{
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		//请求视图文件的后缀
		//internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setSuffix(".html");
		return internalResourceViewResolver;
	}*/
		@Override
	public void addInterceptors(InterceptorRegistry registry){
			//registry.addInterceptor(new OneInterceptor()).addPathPatterns("/view/**");
			//registry.addInterceptor(new TwoInterceptor()).addPathPatterns("/test/**");
	}
}
