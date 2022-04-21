package ru.gb.retrofit.endpoints;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.gb.retrofit.config.GetCategoryResponse;
import ru.gb.retrofit.dto.ProductDto;

public interface CategoryService {

    @GET("categories/{id}")
    Call<ProductDto> getCategory(@Path("id") int id);

}
