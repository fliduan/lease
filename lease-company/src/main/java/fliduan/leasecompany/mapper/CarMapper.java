package fliduan.leasecompany.mapper;

import fliduan.leasecompany.domain.Car;
import fliduan.leasecompany.dto.CarDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarDto carToCarDto(Car car);
    Car carDtoToCar(CarDto carDto);
}
