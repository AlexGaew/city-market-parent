package ru.gaew.springcourse.managerapp.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;
import ru.gaew.springcourse.managerapp.client.ProductsRestClientImpl;


@Configuration
public class ClientBeans {

    @Bean
    public ProductsRestClientImpl restClient(@Value("${city-market.services.catalogue.uri:http://localhost:8081}")
                                             // здесь добавлен урл из конфига application-standalone,
                                             // чтобы не харкодить и дополнительно указан урл по умолчанию
                                             String catalogueBaseUri,
                                             @Value("${city-market.services.catalogue.user:}")
                                             String catalogueUserName,
                                             @Value("${city-market.services.catalogue.password:}")
                                             String catalogueUserPassword

    ) {
        return new ProductsRestClientImpl(RestClient.builder()
                .baseUrl(catalogueBaseUri)
                .requestInterceptor(new BasicAuthenticationInterceptor(catalogueUserName, catalogueUserPassword))
                .build());
    }
}





