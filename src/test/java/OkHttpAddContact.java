import com.google.gson.Gson;
import dto.AuthContactRequestDTO;
import dto.AuthContactResponseDTO;
import okhttp3.*;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpAddContact {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public String newToken;

    @BeforeTest
    public void precondition() throws IOException {
        new OkHttpLoginTest().loginPositiveTest();
        newToken = OkHttpLoginTest.token;
    }

    @Test
    public void AddContactPositiveTest() throws IOException {
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        AuthContactRequestDTO requestDTO = AuthContactRequestDTO.builder()
                .address("Holon")
                .description("Friend")
                .email("luna4@gmail.ru")
                .id(0)
                .lastName("White")
                .name("Oddy")
                .phone("0579589656")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .addHeader("Authorization", newToken)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        String responseJson = response.body().string();
        AuthContactResponseDTO responseDTO = gson.fromJson(responseJson, AuthContactResponseDTO.class);
        System.out.println(responseDTO.getId());

        Assert.assertTrue(response.isSuccessful());
    }


}
