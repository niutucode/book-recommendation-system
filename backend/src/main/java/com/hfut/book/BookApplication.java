package com.hfut.book;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * spring boot启动类
 *
 * @author Chenzh
 * @email imchenzh@foxmail.com
 */
@EnableCaching
@MapperScan("com.hfut.book.mapper")
@SpringBootApplication
public class BookApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }
}
