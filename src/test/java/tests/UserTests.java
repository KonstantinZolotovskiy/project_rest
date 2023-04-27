package tests;

import com.github.javafaker.Faker;
import io.qameta.allure.Owner;
import models.requests.CreateUserRequestDto;
import models.requests.UpdateUserRequestDto;
import models.responses.CreateUserResponseDto;
import models.responses.UpdateUserResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.BaseSpec.requestSpec;
import static specs.BaseSpec.responseSpec;

@Owner("K.Zolotovskiy")
public class UserTests {

    Faker faker = new Faker();

    String name = faker.name().firstName(),
            job = faker.job().position();


    @Test
    @DisplayName("Создание юзера")
    void createUserTest() {
        CreateUserRequestDto createUserBody = new CreateUserRequestDto();
        createUserBody.setName(name);
        createUserBody.setJob(job);

        CreateUserResponseDto response = step("Создание юзера", () ->
                given(requestSpec)
                        .body(createUserBody)
                        .post("/users")
                        .then()
                        .spec(responseSpec)
                        .statusCode(201)
                        .extract().as(CreateUserResponseDto.class));

        step("Проверка поля \"name\"", () ->
                Assertions.assertThat(response.getName()).isEqualTo(name));

        step("Проверка поля \"job\"", () ->
                Assertions.assertThat(response.getJob()).isEqualTo(job));
    }

    @Test
    @DisplayName("Редактирование юзера")
    void updateUserTest() {
        UpdateUserRequestDto updateUserBody = new UpdateUserRequestDto();
        updateUserBody.setName(name);
        updateUserBody.setJob(job);

        UpdateUserResponseDto response = step("Редактирование юзера", () ->
                given(requestSpec)
                        .body(updateUserBody)
                        .put("/users/2")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract().as(UpdateUserResponseDto.class));

        step("Проверка поля \"name\"", () ->
                Assertions.assertThat(response.getName()).isEqualTo(name));

        step("Проверка поля \"job\"", () ->
                Assertions.assertThat(response.getJob()).isEqualTo(job));
    }

    @Test
    @DisplayName("Удаление юзера")
    void deleteUserTest() {
        step("Удаление юзера", () ->
                given(requestSpec)
                        .delete("/users/2")
                        .then()
                        .statusCode(204));
    }
}
