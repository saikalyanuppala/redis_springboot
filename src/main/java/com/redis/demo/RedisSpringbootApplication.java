package com.redis.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

import com.redis.demo.model.User;

import redis.clients.jedis.Jedis;

@SpringBootApplication
public class RedisSpringbootApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RedisSpringbootApplication.class, args);
	}

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public void run(String... args) throws Exception {

		Jedis jedis = new Jedis("localhost", 6379);
		jedis.set("myKey", "sai sandra");
		System.out.println("getting value from redis using jedis " + jedis.get("myKey"));
		jedis.close();

		System.out.println("------------------------------");

		redisTemplate.opsForValue().set("obj1", new User(10L, "narasimha", "ram", "narasimha.ram@gmail.com", 1000));

	}
}
