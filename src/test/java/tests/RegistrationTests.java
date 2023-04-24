package tests;

import com.github.javafaker.Faker;
import models.requests.RegistrationUserRequestDto;
import models.responses.RegistrationUserUnsuccessfulResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.BaseSpec.requestSpec;
import static specs.BaseSpec.responseSpec;

public class RegistrationTests {

    Faker faker = new Faker();

    String password;

    @BeforeEach
    public void initTestData() {
        password = faker.internet().password();
    }

    @Test
    @DisplayName("Регистрация без указания e-mail")
    void registrationWithoutEmailTest() {
        RegistrationUserRequestDto registrationUserBody = new RegistrationUserRequestDto();
        registrationUserBody.setPassword(password);

        String errorMessage = "Missing email or username";

        RegistrationUserUnsuccessfulResponseDto response = step("Регистрация без указания e-mail", () ->
                given(requestSpec)
                        .body(registrationUserBody)
                        .post("/register")
                        .then()
                        .spec(responseSpec)
                        .statusCode(400)
                        .extract().as(RegistrationUserUnsuccessfulResponseDto.class));

        step("Проверка текста сообщения о ошибке", () ->
                Assertions.assertThat(response.getError()).isEqualTo(errorMessage));
    }
}
