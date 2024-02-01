import io.qameta.allure.restassured.AllureRestAssured;
import lombock.LoginBodyLombokModel;
import lombock.LoginResponseLombokModel;
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
}