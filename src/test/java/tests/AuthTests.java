package tests;

import com.github.javafaker.Faker;
import io.qameta.allure.Owner;
import models.requests.LoginUserRequestDto;
import models.responses.LoginUserUnsuccessfulResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.BaseSpec.requestSpec;
import static specs.BaseSpec.responseSpec;

@Owner("K.Zolotovskiy")
public class AuthTests {

    Faker faker = new Faker();

    String email;

    @BeforeEach
    public void initTestData() {
        email = faker.internet().emailAddress();
    }

    @Test
    @DisplayName("Авторизация без указания пароля")
    void AuthWithoutPasswordTest() {
        LoginUserRequestDto loginUserBody = new LoginUserRequestDto();
        loginUserBody.setEmail(email);

        String errorMessage = "Missing password";

        LoginUserUnsuccessfulResponseDto response = step("Авторизация без указания пароля", () ->
                given(requestSpec)
                        .body(loginUserBody)
                        .post("/login")
                        .then()
                        .spec(responseSpec)
                        .statusCode(400)
                        .extract().as(LoginUserUnsuccessfulResponseDto.class));

        step("Проверка текста сообщения о ошибке", () ->
                Assertions.assertThat(response.getError()).isEqualTo(errorMessage));
    }
}
