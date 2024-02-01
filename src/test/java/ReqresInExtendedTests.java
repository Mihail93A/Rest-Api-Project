import io.qameta.allure.restassured.AllureRestAssured;
import lombock.LoginBodyLombokModel;
import lombock.LoginResponseLombokModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static specs.LoginSpecs.LoginRequestSpec;
import static specs.LoginSpecs.loginResponseSpec;

public class ReqresInExtendedTests {


    @Test
    @DisplayName("1. LOGIN SUCCESSFUL -  АВТОРИЗАЦИЯ УСПЕШНА")
    void LoginWithCustomSpecsTest() {
        LoginBodyLombokModel data = new LoginBodyLombokModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("cityslicka");

        LoginResponseLombokModel response = given(LoginRequestSpec)
                .filter(withCustomTemplates())
                .body(data)
                .when()
                .post("/login")
                .then()
                .spec(loginResponseSpec)
                .extract().as(LoginResponseLombokModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @DisplayName("2.LOGIN - UNSUCCESSFUL - ВОЙТИ - НЕУДАЧНО")
    void LoginWithBadPracticeTest2() {
        String data = "{ \"email\": \"sydney@fife\"}";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
    @Test
    @DisplayName("3. REGISTER UNSUCCESSFUL - РЕГИСТРАЦИЯ НЕУСПЕШНО")
    void LoginWithBadPracticeTest3() {
        String data = "{ \"email\": \"sydney@fife\"}";

        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
    @Test
    @DisplayName("4. SINGLE USER NOT FOUND - ОДИН ПОЛЬЗОВАТЕЛЬ НЕ НАЙДЕН")
    void LoginWithBadPracticeTest4() {


        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users23")
                .then()
                .log().status()
                .log().body()
                .statusCode(200);
    }
    @Test
    @DisplayName ("5. LIST USERS - получение списка пользователей")
    void LoginWithBadPracticeTest5() {

        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("page", is(2));

    }
}