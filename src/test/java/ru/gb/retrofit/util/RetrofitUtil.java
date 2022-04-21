package ru.gb.retrofit.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.okhttp3.AllureOkHttp3;
import lombok.Data;
import lombok.experimental.UtilityClass;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.gb.retrofit.endpoints.CategoryService;
import ru.gb.retrofit.endpoints.ProductService;
import ru.gb.retrofit.log.LoggingInterceptor;
import ru.gb.retrofit.log.PrettyLogger;


import java.util.ArrayList;
import java.util.List;

import static ru.gb.retrofit.config.MiniMarketConfig.miniMarketConfig;
@UtilityClass
public class RetrofitUtil {
    public static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(miniMarketConfig.baseURI())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor(new PrettyLogger()).setLevel(HttpLoggingInterceptor.Level.BODY))
                        .addInterceptor(new AllureOkHttp3())
                        .build())
                .build();
    }
    public static CategoryService getCategoryService(){
        return getRetrofit().create(CategoryService.class);
    }
    public static ProductService getProductsService(){
        return getRetrofit().create(ProductService.class);
    }
}
