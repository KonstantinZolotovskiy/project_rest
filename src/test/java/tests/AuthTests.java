package tests;

import io.qameta.allure.Owner;
import models.requests.AuthRequestDto;
import models.responses.AuthResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.BaseSpec.requestSpec;
import static specs.BaseSpec.responseSpec;

@Owner("K.Zolotovskiy")
public class AuthTests {

    String email = "eve.holt@reqres.in";
    String password = "cityslicka";

    @Test
    @DisplayName("Авторизация без указания пароля")
    void authWithoutPasswordTest() {
        AuthRequestDto loginUserBody = new AuthRequestDto();
        loginUserBody.setEmail(email);

        String errorMessage = "Missing password";

        AuthResponseDto response = step("Авторизация без указания пароля", () ->
                given(requestSpec)
                        .body(loginUserBody)
                        .post("/login")
                        .then()
                        .spec(responseSpec)
                        .statusCode(400)
                        .extract().as(AuthResponseDto.class));

        step("Проверка текста сообщения о ошибке", () ->
                Assertions.assertThat(response.getError()).isEqualTo(errorMessage));
    }

    @Test
    @DisplayName("Успещная авторизация")
    void successfulAuthTest() {
        AuthRequestDto loginUserBody = new AuthRequestDto();
        loginUserBody.setEmail(email);
        loginUserBody.setPassword(password);

        AuthResponseDto response = step("Успешная авторизация", () ->
                given(requestSpec)
                        .body(loginUserBody)
                        .post("/login")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract().as(AuthResponseDto.class));

        step("Проверка, что token не равен null", () ->
                Assertions.assertThat(response.getToken()).isNotNull());
    }
}
