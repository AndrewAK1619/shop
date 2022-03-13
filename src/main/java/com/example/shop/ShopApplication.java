package com.example.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing // włącza 'połowicznie' auditing -> dalej do encji włączyć adutiting
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
// dzięki tej adnotacji będziemy mogli pobierać dane z tabel audytowych
@SpringBootApplication
@EnableAsync(proxyTargetClass = true) // włącza asynchroniczne dziełanie dostarczone przez springa (wielowątkowość)
public class ShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }
}
