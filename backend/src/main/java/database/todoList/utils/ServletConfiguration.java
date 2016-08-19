package database.todoList.utils;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class ServletConfiguration {
	@Bean
	public FilterRegistrationBean registerCORSFilter(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(getCORSFilter());
		filterRegistrationBean.setName("CORS");
		return filterRegistrationBean;
	}

	public Filter getCORSFilter(){
		return new CORSFilter();
	}
}
