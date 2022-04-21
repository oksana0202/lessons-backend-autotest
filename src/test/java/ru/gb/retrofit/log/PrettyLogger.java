package ru.gb.retrofit.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;

public class PrettyLogger implements HttpLoggingInterceptor.Logger {
    @SneakyThrows
    @Override
    public void log(@NotNull String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        String trimmedString = s.trim();
        if ((trimmedString.startsWith("{") && trimmedString.endsWith("}"))
                 || (trimmedString.startsWith("[") && trimmedString.endsWith("]"))) {
            Object object = objectMapper.readValue(s, Object.class);
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } else {
            Platform.get().log(s, Platform.INFO,null);

        }
    }
}
