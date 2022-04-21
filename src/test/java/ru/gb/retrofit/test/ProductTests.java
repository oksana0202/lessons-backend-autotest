package ru.gb.retrofit.test;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.gb.retrofit.base.CategoryType;
import ru.gb.retrofit.dto.ProductDto;
import ru.gb.retrofit.endpoints.ProductService;
import ru.gb.retrofit.util.RetrofitUtil;
import java.io.IOException;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.gb.retrofit.util.RetrofitUtil.getCategoryService;
import static ru.gb.retrofit.util.RetrofitUtil.getProductsService;


public class ProductTests {
    int productID;
    static ProductService productService = RetrofitUtil.getRetrofit()
            .create(ProductService.class);
    ProductDto productDto;
    private boolean bookOrFood;


    @SneakyThrows
    @BeforeEach
    void setUp() throws IOException {
        productDto = new ProductDto()
                .withCategoryTitle(getCategoryService().getCategory(1).execute().body().getTitle())
                .withTitle(new Faker().food().ingredient())
                .withCategoryTitle(bookOrFood ? CategoryType.FOOD.getTitle() : CategoryType.BOOK.getTitle())
                .withPrice(300);
    }
    @SneakyThrows
    @Test
    void createProductTest() {
        Response<ProductDto> productDtoResponse = getProductsService().createProducts(productDto)
                .execute();
        assertThat(productDtoResponse.isSuccessful()).isTrue();
        assertThat(productDtoResponse.body())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(productDto);

        productID = Objects.requireNonNull(productDtoResponse.body()).getId();
    }
    @SneakyThrows
    @AfterEach
    void tearDown() {
        assertThat(getProductsService().deleteProducts(productID).execute().isSuccessful())
                .isNotNull();
    }
    @SneakyThrows
    @Test
    void changeProducts() {
        Response<ProductDto> productDtoResponse = getProductsService().changeProducts(productDto)
                .execute();
        assertThat(productDtoResponse.isSuccessful()).isNotNull();
        assertThat(productDtoResponse.body())
                .isEqualTo(null);
    }

    @SneakyThrows
    @Test
    void deleteProduct() {
        Response<Void> productDtoResponse = getProductsService().deleteProducts(1)
                .execute();
        assertThat(productDtoResponse.code()).isEqualTo(500);
        assertThat(productDtoResponse.isSuccessful())
                .isFalse();
    }
}

