package br.com.jeporto.car.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import br.com.jeporto.car.api.Car;

@Repository
@SuppressWarnings({"rawtypes", "unchecked"})
public class CarRepository {

	private ValueOperations<String, Car> opsForValue;
	
	@Autowired
	public CarRepository(RedisTemplate redisTemplate) {
		opsForValue = redisTemplate.opsForValue();
	}
	
	public void save(Car car) {
		opsForValue.set(car.getId(), car);
	}

	public Car findOne(String id) {
		return opsForValue.get(id);
	}
	
	public List<Car> findAll() {
		return opsForValue.getOperations().keys("*")
							.stream()
							.map(this::findOne)
							.collect(Collectors.toList());
	}

	public void delete(String id) {
		opsForValue.getOperations().delete(id);
	}
}
