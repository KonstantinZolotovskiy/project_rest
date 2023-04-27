package tests;

import io.qameta.allure.Owner;
import models.requests.RegistrationUserRequestDto;
import models.responses.RegistrationUserResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.BaseSpec.requestSpec;
import static specs.BaseSpec.responseSpec;

@Owner("K.Zolotovskiy")
public class RegistrationTests {

    String email = "eve.holt@reqres.in";
    String password = "pistol";

    @Test
    @DisplayName("Регистрация юзера без указания e-mail")
    void registrationWithoutEmailTest() {
        RegistrationUserRequestDto registrationUserBody = new RegistrationUserRequestDto();
        registrationUserBody.setPassword(password);

        String errorMessage = "Missing email or username";

        RegistrationUserResponseDto response = step("Регистрация без указания e-mail", () ->
                given(requestSpec)
                        .body(registrationUserBody)
                        .post("/register")
                        .then()
                        .spec(responseSpec)
                        .statusCode(400)
                        .extract().as(RegistrationUserResponseDto.class));

        step("Проверка текста сообщения о ошибке", () ->
                Assertions.assertThat(response.getError()).isEqualTo(errorMessage));
    }

    @Test
    @DisplayName("Успешная регистрация юзера")
    void successfulRegistrationTest() {
        RegistrationUserRequestDto registrationUserBody = new RegistrationUserRequestDto();
        registrationUserBody.setEmail(email);
        registrationUserBody.setPassword(password);

        RegistrationUserResponseDto response = step("Успешная регистрация юзера", () ->
                given(requestSpec)
                        .body(registrationUserBody)
                        .post("/register")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract().as(RegistrationUserResponseDto.class));

        step("Проверка, что token не равен null", () ->
                Assertions.assertThat(response.getToken()).isNotNull());

        step("Проверка, что id не равен null", () ->
                Assertions.assertThat(response.getId()).isNotNull());
    }
}
