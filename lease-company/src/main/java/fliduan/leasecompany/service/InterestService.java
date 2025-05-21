package fliduan.leasecompany.service;

import fliduan.leasecompany.dto.CarDto;
import fliduan.leasecompany.dto.InterestResponseDto;
import fliduan.leasecompany.repository.InterestRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class InterestService {
    private final InterestRepository interestRepository;

    /**
     * Method to retrieve interest rate
     * @param startDate
     * @return {@link List<CarDto>} list of cars
     */
    public InterestResponseDto getInterestRate(@NotNull LocalDate startDate){
        Sort sort = Sort.by("startDate").descending();
        var interest = interestRepository.findTop1ByStartDateIsBefore (startDate, sort);

        return new InterestResponseDto(interest.getInterestRate());
    }

}
