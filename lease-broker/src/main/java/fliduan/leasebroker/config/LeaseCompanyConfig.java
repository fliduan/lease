package fliduan.leasebroker.config;

import fliduan.api.ApiClient;
import fliduan.api.leasecompany.CarControllerApi;
import fliduan.api.leasecompany.InterestControllerApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LeaseCompanyConfig {
    private final String apiKey;

    public LeaseCompanyConfig(@Value("${lease-company.apikey}") String apiKey){
        this.apiKey = apiKey;
    }

    @Bean
    public CarControllerApi carControllerApi() {
        return new CarControllerApi(leaseCompanyApiClient());
    }

    @Bean
    public InterestControllerApi interrestControllerApi() {
        return new InterestControllerApi(leaseCompanyApiClient());
    }

    @Bean
    public ApiClient leaseCompanyApiClient() {
        var apiClient = new ApiClient();
        apiClient.setApiKey(apiKey);
        return  apiClient;
    }
}
