package ru.gb.retrofit.endpoints;

import retrofit2.Call;
import retrofit2.http.*;
import ru.gb.retrofit.dto.ProductDto;

import java.util.List;

public interface ProductService {

    @GET("products/{id}")
    Call<ProductDto> getProduct();

    @GET("products")
    Call<List<ProductDto>> getProducts();

    @POST("products")
    Call<ProductDto> createProducts(@Body ProductDto productDto);

    @PUT("products/{id}")
    Call<ProductDto> changeProducts(@Body ProductDto productDto);

    @DELETE("products/{id}")
    Call<Void> deleteProducts(@Path("id") int id);

}
