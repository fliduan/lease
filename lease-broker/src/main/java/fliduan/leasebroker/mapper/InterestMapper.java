package fliduan.leasebroker.mapper;

import fliduan.leasebroker.vo.InterestVO;
import fliduan.model.leasecompany.LcInterestResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InterestMapper {
    InterestVO  interestResponseDtoToInterestVO (LcInterestResponseDto interestResponseDto);
}
