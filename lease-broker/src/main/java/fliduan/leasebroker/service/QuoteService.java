package fliduan.leasebroker.service;

import fliduan.leasebroker.adapter.LeaseCompanyAdapter;
import fliduan.leasebroker.dto.QuoteRequestDto;
import fliduan.leasebroker.dto.QuoteResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
@Service
public class QuoteService {
    private final LeaseCompanyAdapter leaseCompanyAdapter;

    /**
     * Method to request quote for a car lease
     * @param quoteRequestDto quote request
     * @return {@link QuoteResponseDto} quote with lease rate
     */
    public QuoteResponseDto requestQuote(@NotNull QuoteRequestDto quoteRequestDto){
        var carVO = leaseCompanyAdapter.retrieveCarVO(quoteRequestDto);
        if (carVO == null) {
            throw new EntityNotFoundException("Car is not found.");
        }
        var nettPrice = carVO.getNettPrice();
        var interestVO = leaseCompanyAdapter.retrieveInterestVO(quoteRequestDto.getStartDate());
        if (interestVO == null){
            throw new EntityNotFoundException("Interest rate is not found.");
        }
        var percentInterestRate = interestVO.rate();
        var leaseRate = calculateLeaseRate(quoteRequestDto.getMileage()
                , quoteRequestDto.getDuration()
                , percentInterestRate
                , nettPrice);

        return QuoteResponseDto.builder()
                .make(quoteRequestDto.getMake())
                .model(quoteRequestDto.getModel())
                .version(quoteRequestDto.getVersion())
                .mileage(quoteRequestDto.getMileage())
                .duration(quoteRequestDto.getDuration())
                .percentInterestRate(percentInterestRate)
                .nettPrice(nettPrice)
                .leaseRate(leaseRate)
                .build();
    }

    protected BigDecimal calculateLeaseRate(Integer mileage, Integer duration, BigDecimal percentInterestRate, BigDecimal nettPrice){
        //leaseRate = ((( mileage / 12 ) * duration ) / nettPrice) + ((( percentInterestRate / 100 ) * nettPrice) / 12 );

        return BigDecimal.valueOf(mileage/12).multiply(BigDecimal.valueOf(duration)).divide(nettPrice,2, RoundingMode.HALF_UP)
                .add(percentInterestRate.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP).multiply(nettPrice).divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP));
    }
}
