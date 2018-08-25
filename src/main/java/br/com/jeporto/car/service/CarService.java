package br.com.jeporto.car.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import br.com.jeporto.car.api.Car;

@Service
public class CarService {

	private StringRedisTemplate template;
	private ValueOperations<String, String> opsForValue;

	@Autowired
	public CarService(StringRedisTemplate template) {
		this.template = template;
		this.opsForValue = template.opsForValue();
	}

	public Car save(Car car) {
		String id = car.getId().toString();
		opsForValue.set(id, car.getName());
		
		return new Car(car.getId(), opsForValue.get(id));
	}

	public List<Car> list() {
		Set<String> redisKeys = template.keys("*");

		return redisKeys.stream()
						.map(this::buildCar)
						.collect(Collectors.toList());
	}

	private Car buildCar(String key) {
		return new Car(Long.parseLong(key), opsForValue.get(key));
	}

	public void delete(Long id) {
		template.delete(id.toString());
	}
}
