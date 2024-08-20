package ru.gaew.springcourse.managerapp.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import ru.gaew.springcourse.managerapp.controller.payload.NewProductPayload;
import ru.gaew.springcourse.managerapp.controller.payload.UpdateProductPayload;
import ru.gaew.springcourse.managerapp.entity.Product;
import ru.gaew.springcourse.managerapp.myException.BadRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;


@RequiredArgsConstructor
public class ProductsRestClientImpl implements ProductsRestClient {


    private final RestClient restClient;


    private static final ParameterizedTypeReference<List<Product>> PARAMETERIZED_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };

    @Override
    public List<Product> findAllProducts(String filter) {

        return this.restClient.get()
                .uri("/catalogue-api/products?filter={filter}", filter)
                .retrieve()
                .body(PARAMETERIZED_TYPE_REFERENCE);
    }

    @Override
    public Product createProduct(String title, String details) {
        try {
            return this.restClient.post()
                    .uri("/catalogue-api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewProductPayload(title, details))
                    .retrieve()
                    .body(Product.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }

    }

    @Override
    public Optional<Product> findProduct(int productId) {
        try {
            return Optional.ofNullable(this.restClient
                    .get()
                    .uri("/catalogue-api/products/{productId}", productId)
                    .retrieve()
                    .body(Product.class));
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateProduct(Integer productId, String title, String details) {
        try {
            this.restClient.patch()
                    .uri("/catalogue-api/products/{productId}", productId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateProductPayload(title, details))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }

    }

    @Override
    public void deleteProduct(int productId) {

        try {
            this.restClient.delete()
                    .uri("/catalogue-api/products/{productId}", productId)
                    .retrieve();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException("Product not found");
        }


    }
}
