package fliduan.leasecompany.service;

import fliduan.leasecompany.domain.Car;
import fliduan.leasecompany.dto.CarDto;
import fliduan.leasecompany.dto.CarRequestDto;
import fliduan.leasecompany.mapper.CarMapper;
import fliduan.leasecompany.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CarService {
    private final CarMapper carMapper;
    private final CarRepository carRepository;

    /**
     * Method to retrieve cars information
     * @param carRequestDto
     * @return {@link List<CarDto>} list of cars
     */
    public List<CarDto> retrieveCars(@NotNull CarRequestDto carRequestDto){
        List<Car> cars = carRepository.retrieveCarsByMakeAndModelAndVersion(carRequestDto.getMake()
                , carRequestDto.getModel(), carRequestDto.getVersion());

        List<CarDto> carDtoList = new ArrayList<>();
        for (Car car: cars){
            carDtoList.add(carMapper.carToCarDto(car));
        }
        return carDtoList;
    }

    /**
     * Method to save car information
     * @param @{@link CarDto} car information
     */
    public void saveCar(@NotNull CarDto carDto){
        Car car = carMapper.carDtoToCar(carDto);
        carRepository.save(car);
    }

    /**
     * Method to update car information
     * @param @{@link CarDto} car information
     */
    public void updateCar(@NotNull CarDto carDto){
        var cars = carRepository.retrieveCarsByMakeAndModelAndVersion(carDto.getMake(), carDto.getModel(), carDto.getVersion());
        if (!cars.isEmpty()) {
            var car = cars.getFirst();
            car.setNumberOfDoors(carDto.getNumberOfDoors());
            car.setGrossPrice(carDto.getGrossPrice());
            car.setNettPrice((carDto.getNettPrice()));
            car.setEmission(carDto.getEmission());
            carRepository.save(car);
        } else {
            throw new EntityNotFoundException("Car information is not found.");
        }
    }

    /**
     * Method to delete car information
     * @param @{@link CarRequestDto} request to delete car informaiton
     */
    public void deleteCar(@NotNull CarRequestDto carRequestDto){
        var cars = carRepository.retrieveCarsByMakeAndModelAndVersion(carRequestDto.getMake(), carRequestDto.getModel(), carRequestDto.getVersion());
        if (!cars.isEmpty()) {
            var car = cars.getFirst();
            carRepository.delete(car);
        } else {
            throw new EntityNotFoundException("Car information is not found.");
        }
    }

}
