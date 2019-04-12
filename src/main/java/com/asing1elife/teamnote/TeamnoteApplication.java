package com.asing1elife.teamnote;

import com.asing1elife.teamnote.core.repository.BaseRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.asing1elife.teamnote.*")
@EntityScan(basePackages = "com.asing1elife.teamnote.*")
@EnableJpaRepositories(basePackages = "com.asing1elife.teamnote.*", repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class TeamnoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamnoteApplication.class, args);
    }

}

