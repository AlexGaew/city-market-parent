package ru.gaew.springcourse.managerapp.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.DefaultReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;
import ru.gaew.springcourse.managerapp.client.ProductsRestClientImpl;
import ru.gaew.springcourse.managerapp.security.OAuthClientHttpRequestInterceptor;


@Configuration
public class ClientBeans {

    @Bean
    public ProductsRestClientImpl restClient(@Value("${city-market.services.catalogue.uri:http://localhost:8081}")
                                             // здесь добавлен урл из конфига application-standalone,
                                             // чтобы не харкодить и дополнительно указан урл по умолчанию
                                             String catalogueBaseUri,
                                             ClientRegistrationRepository clientRegistrationRepository,
                                             OAuth2AuthorizedClientRepository authorizedClientRepository,
                                             @Value("${city-market.services.catalogue.registration-id:keycloak}")
                                             String registrationId

    ) {
        return new ProductsRestClientImpl(RestClient.builder()
                .baseUrl(catalogueBaseUri)
                .requestInterceptor(new OAuthClientHttpRequestInterceptor(
                        new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository,
                                authorizedClientRepository), registrationId))
                .build());
    }
}





