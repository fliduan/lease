package fliduan.leasebroker.adapter;

import fliduan.api.leasecompany.CarControllerApi;
import fliduan.api.leasecompany.InterestControllerApi;
import fliduan.leasebroker.dto.QuoteRequestDto;
import fliduan.leasebroker.exception.LeaseBrokerException;
import fliduan.leasebroker.logging.LogUtil;
import fliduan.leasebroker.mapper.CarMapper;
import fliduan.leasebroker.mapper.InterestMapper;
import fliduan.leasebroker.vo.CarVO;
import fliduan.leasebroker.vo.InterestVO;
import fliduan.model.leasecompany.LcCarDto;
import fliduan.model.leasecompany.LcInterestResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import java.net.SocketTimeoutException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static fliduan.leasebroker.LeaseBrokerConstants.CORRELATION_ID;

@Component
@RequiredArgsConstructor
@Log4j2
public class LeaseCompanyAdapter {
    private static final String LEASE_COMPANY_API = "lease-company";
    private static final long RETRY_MAX_ATTEMPTS = 3L;
    private static final long RETRY_DELAY_IN_MS = 1000;
    private static final String TIMEOUT_RETRY_EXCEEDED = "Service temporary not available: number timeout-retry exceeded";
    private static final String DATA_NOT_FOUND_EXCEPTION = "Retrieving %s information: data is not found.";
    private static final String INTERNAL_SERVER_ERROR = "Retrieving %s information: internal server error.";
    private static final String GENERAL_ERROR = "Retrieving %s information: een error has occurred.";

    private final CarControllerApi carControllerApi;
    private final InterestControllerApi interestControllerApi;
    private final CarMapper carMapper;
    private final InterestMapper interestMapper;
    private final LogUtil logUtil;

    public CarVO retrieveCarVO(QuoteRequestDto quoteRequestDto){
        CarVO carVO = null;
        try {
            logUtil.logApiCall(LEASE_COMPANY_API, "getCars"
                    ,quoteRequestDto.getMake()+ "-" +quoteRequestDto.getModel()+ "-" + quoteRequestDto.getVersion());
            var carRequestDto = carMapper.quoteRequestDtoToLcCarRequestDto(quoteRequestDto);
            ResponseEntity<List<LcCarDto>> resp = carControllerApi.getCarsWithHttpInfo(carRequestDto.getMake(), getCorrelationId(), carRequestDto.getModel(), carRequestDto.getVersion())
                    .retryWhen(Retry.backoff(RETRY_MAX_ATTEMPTS, Duration.ofMillis(RETRY_DELAY_IN_MS))
                            .doBeforeRetry(retrySignal -> {
                                logUtil.logApiCallRetry(LEASE_COMPANY_API, "getCars");
                            })
                            .filter(SocketTimeoutException.class::isInstance)
                            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                                throw new LeaseBrokerException(TIMEOUT_RETRY_EXCEEDED, HttpStatus.SERVICE_UNAVAILABLE);
                            }))
                    .block();
            if (resp != null && resp.getBody() != null) {
                carVO = carMapper.lcCarDtoToCarVO(resp.getBody().getFirst());
            }
        }catch (WebClientResponseException e) {
            handleWebClientResponseException(e, "car");
        }
        return carVO;
    }

    public InterestVO retrieveInterestVO(LocalDate startDate){
        InterestVO interestVO = null;
        try {
            logUtil.logApiCall(LEASE_COMPANY_API, "getInterestRate", startDate.toString());

            ResponseEntity<LcInterestResponseDto> resp = interestControllerApi.getInterestRateWithHttpInfo(startDate, getCorrelationId())
                    .retryWhen(Retry.backoff(RETRY_MAX_ATTEMPTS, Duration.ofMillis(RETRY_DELAY_IN_MS))
                            .doBeforeRetry(retrySignal -> {logUtil.logApiCallRetry(LEASE_COMPANY_API, "getCars");})
                            .filter(SocketTimeoutException.class::isInstance)
                            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                                throw new LeaseBrokerException(TIMEOUT_RETRY_EXCEEDED, HttpStatus.SERVICE_UNAVAILABLE);
                            }))
                    .block();
            if (resp != null && resp.getBody() != null) {
                interestVO = interestMapper.interestResponseDtoToInterestVO(resp.getBody());
            }
        }catch (WebClientResponseException e) {
            handleWebClientResponseException(e, "interest");
        }
        return interestVO;
    }

    /**
     * Haal het CorrelationId op uit MDC.
     * Deze call is in een aparte method gezet ivm unit-test testbaarheid
     * @return {@link String} correlationId
     */
    protected String getCorrelationId() {
        return MDC.get(CORRELATION_ID);
    }

    protected void handleWebClientResponseException(WebClientResponseException e, String soort) {
        if (e.getStatusCode() == HttpStatus.NOT_FOUND){
            log.error(String.format(DATA_NOT_FOUND_EXCEPTION, soort));
            throw new LeaseBrokerException(String.format(DATA_NOT_FOUND_EXCEPTION, soort), HttpStatus.NOT_FOUND);
        }else if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
            log.error(String.format(INTERNAL_SERVER_ERROR, soort));
            throw new LeaseBrokerException(String.format(INTERNAL_SERVER_ERROR, soort) + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            log.error(String.format(GENERAL_ERROR, soort));
            throw new LeaseBrokerException(String.format(GENERAL_ERROR, soort) + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
