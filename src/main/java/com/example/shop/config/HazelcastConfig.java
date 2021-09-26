package com.example.shop.config;

import com.example.shop.model.dao.Product;
import com.hazelcast.config.*;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching // uruchaiamy cache
public class HazelcastConfig {

    @Bean
    Config configHazelcast() {
        Config config = new Config()
                .setInstanceName("hazelcast-instance")
                .addMapConfig(new MapConfig()
                        .setName("product")
                        .setEvictionConfig(new EvictionConfig() // w jaki sposób usuwamy z cach'a ( Eviction - eksmituj )
                                .setEvictionPolicy(EvictionPolicy.LFU)
                                .setSize(10)
                                .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)) // jak pamięc się zapełni to czy możemy dodać
                        .setTimeToLiveSeconds(60 * 60 * 24));// długość życia obiektów w cache
// .addMapConfig(); - gdybyśmy chcieli więcej

        // rejestracja serializatora IndentyfiedDataSeriazlizable dla klasy product
        config.getSerializationConfig().addDataSerializableFactory(1, id -> id == 1 ? new Product() : null);
        return config;
    }


//    >>> Czy dodanie nowego id Factory nie wystarczy ? <<< <<< <<< <<< <<< <<< <<< <<<


    // LRU - najdawniej używany
    // LFU - najrzadziej używany
}
