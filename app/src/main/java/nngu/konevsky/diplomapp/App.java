package nngu.konevsky.diplomapp;

import android.app.Application;

import nngu.konevsky.diplomapp.pojo.APIService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 31.03.2018.
 */

public class App extends Application {

    private static APIService apiService;
    private Retrofit retrofit;

    public static final String BASE_URL = "http://192.168.1.101:58084/api/";

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        apiService = retrofit.create(APIService.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    public static APIService getApi() {
        return apiService;
    }
}
