package com.se.petstore.api;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(AllureJunit5.class)
public class PetStoreApiTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    @DisplayName("Тест добавления питомца в магазин")
    @Description("Тест добавления питомца с использованием RestAssured")
    public void testAddNewPet() {
        PostPet postPet = new PostPet(1, "Собака", "Лает");
        Response response = addPet(postPet);

        verifyPetResponse(response, postPet);
    }

    @Test
    @DisplayName("Тест получения информации о питомце по ID")
    @Description("Тест получения информации о питомце по его ID")
    public void testGetPetById() {
        PostPet postPet = new PostPet(2, "Кошка", "Мяукает");
        addPet(postPet);

        Response response = getPetById(postPet.getId());

        verifyPetResponse(response, postPet);
    }

    @Test
    @DisplayName("Тест обновления информации о питомце")
    @Description("Тест обновления информации о питомце на сервере")
    public void testUpdatePet() {
        PostPet postPet = new PostPet(3, "Попугай", "Говорит");
        addPet(postPet);

        PostPet updatedPet = new PostPet(postPet.getId(), "Попугай Кеша", "Молчит");
        Response response = updatePet(updatedPet);

        verifyPetResponse(response, updatedPet);
    }

    @Test
    @DisplayName("Тест удаления питомца из магазина")
    @Description("Тест удаления питомца из магазина с последующей проверкой")
    public void testDeletePet() {
        PostPet postPet = new PostPet(4, "Хомяк", "Бегает");
        addPet(postPet);

        deletePet(postPet.getId());

        Response response = getPetById(postPet.getId());
        verifyPetDeleted(response);
    }

    // Шаги для Allure отчетности

    @Step("Добавление питомца")
    private Response addPet(PostPet pet) {
        return given()
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @Step("Получение информации о питомце с id = {petId}")
    private Response getPetById(long petId) {
        return given()
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}")
                .then()
                .extract()
                .response();
    }

    @Step("Обновление информации о питомце")
    private Response updatePet(PostPet pet) {
        return given()
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @Step("Удаление питомца с id = {petId}")
    private void deletePet(long petId) {
        given()
                .pathParam("petId", petId)
                .when()
                .delete("/pet/{petId}")
                .then()
                .statusCode(200);
    }

    @Step("Проверка информации о питомце")
    private void verifyPetResponse(Response response, PostPet expectedPet) {
        assertEquals(expectedPet.getId(), response.jsonPath().getLong("id"));
        assertEquals(expectedPet.getName(), response.jsonPath().getString("name"));
        assertEquals(expectedPet.getStatus(), response.jsonPath().getString("status"));
    }

    @Step("Проверка, что питомец удален")
    private void verifyPetDeleted(Response response) {
        assertEquals(404, response.getStatusCode());
    }
}