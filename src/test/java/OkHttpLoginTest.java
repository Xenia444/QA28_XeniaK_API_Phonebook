import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpLoginTest {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static String token;

    @Test
    public void loginTest() throws IOException {
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .email("sam@gmail.com")
                .password("Sam1234$jh")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        if(response.isSuccessful()) {
            String responseJson = response.body().string();
            AuthResponseDTO responseDTO = gson.fromJson(responseJson, AuthResponseDTO.class);
            System.out.println(responseDTO.getToken());
        }else{
            System.out.println( "Response code: " + response.code());
            ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
            System.out.println( "Error: " +errorDTO.getCode() + " Message: " +errorDTO.getMessage());
        }
    }

    @Test
    public void loginPositiveTest() throws IOException {
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .email("sam@gmail.com")
                .password("Sam1234$")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        String responseJson = response.body().string();
        AuthResponseDTO responseDTO = gson.fromJson(responseJson, AuthResponseDTO.class);
        token = responseDTO.getToken();
        System.out.println(token);

        Assert.assertTrue(response.isSuccessful());

    }

    @Test
    public void loginNegativeEmailFormat() throws IOException {
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .email("samgmail.com")
                .password("Sam1234$")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertEquals(400, response.code());
    }

    @Test
    public void loginNegativePasswordFormat() throws IOException {
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .email("sam@gmail.com")
                .password("sam12345")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertEquals(400, response.code());
    }

    @Test
    public void loginNegativeEmail() throws IOException {
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .email("samuel@gmail.com")
                .password("Sam1234$")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertEquals(401, response.code());
    }

    @Test
    public void loginNegativePassword() throws IOException {
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .email("sam@gmail.com")
                .password("Sam9876$")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertEquals(401, response.code());
    }

    @Test
    public void loginNegativeNewUser() throws IOException {
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .email("Micky@gmail.com")
                .password("Micky9876$")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        Assert.assertEquals(401, response.code());
    }
}
