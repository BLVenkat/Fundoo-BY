package com.bridgelabz.fundoo.configuration;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bridgelabz.fundoo.entity.User;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Configuration
public class CacheConfig {

	@Bean
	public HazelcastInstance hazlecastInstance(){
	Config config= new Config();
	config.setInstanceName("hazelcast-instance");
	config.addMapConfig(new MapConfig().setName("NoteCache"));
	return Hazelcast.newHazelcastInstance(config);

	}
	
	@Bean
	public Map<Long, User> noteMap(HazelcastInstance hazelcastInstance) {
		Map<Long,User> cacheMap = hazelcastInstance.getMap("Product");
		return cacheMap;

	}
	
}
