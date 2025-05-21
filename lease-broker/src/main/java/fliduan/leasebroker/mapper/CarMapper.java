package fliduan.leasebroker.mapper;

import fliduan.leasebroker.dto.QuoteRequestDto;
import fliduan.leasebroker.vo.CarVO;
import fliduan.model.leasecompany.LcCarDto;
import fliduan.model.leasecompany.LcCarRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {
    LcCarRequestDto quoteRequestDtoToLcCarRequestDto(QuoteRequestDto quoteRequestDto);
    CarVO  lcCarDtoToCarVO (LcCarDto carDto);
}
