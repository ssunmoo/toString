package com.shop.tostring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  // 일자 자동으로 넣어주는 어노테이션
public class ToStringStart {

    public static void main(String[] args) {
        SpringApplication.run(ToStringStart.class, args);
    }

}
