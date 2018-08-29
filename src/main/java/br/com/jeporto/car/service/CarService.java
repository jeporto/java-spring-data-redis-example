package br.com.jeporto.car.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jeporto.car.api.Car;
import br.com.jeporto.car.dao.CarRepository;

@Service
public class CarService {

	private CarRepository repository;

	@Autowired
	public CarService(CarRepository repository) {
		this.repository = repository;
	}

	public Car save(Car car) {
		repository.save(car);
		return car;
	}

	public List<Car> list() {
		return repository.findAll();
	}

	public void delete(String id) {
		repository.delete(id);
	}
}
