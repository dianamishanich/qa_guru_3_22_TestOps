package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

    @Feature("Form fill tests")
    @Story("Text box")
    public class TextBoxTest extends TestBase {
        String userName = "Di",
                lastName = "M",
                userEmail = "leprekon@ya.ru",
                currentAddress = "Dublin",
                permanentAddress = "St. Patrick Street, 17";

        @Test
        @DisplayName("Successful fill text box form")
        void fillFormTest() {

            step("Open Text Box form", () -> {
                open("https://demoqa.com/text-box");
                $(".main-header").shouldHave(text("Text Box"));
            });

            step("Fill Text Box form", () -> {
                $("#userName").val(userName);
                $("#userEmail").val(userEmail);
                $("#currentAddress").val(currentAddress);
                $("#permanentAddress").val(permanentAddress);
                $("#submit").click();
            });

            step("Verify successful form submit", () -> {
                $("#output").shouldHave(text("Name:Di\n" +
                        "Email:leprekon@ya.ru\n" +
                        "Current Address :Dublin\n" +
                        "Permananet Address :St. Patrick Street, 17"));
            });
        }

        @Test
        @DisplayName("Unsuccessful fill text box form with wrong email")
        void wrongEmailTest() {
            step("Open Text Box form", () -> {
                open("https://demoqa.com/text-box");
                $(".main-header").shouldHave(text("Text Box"));
            });

            step("Fill Text Box form", () -> {
                $("#userName").val(userName);
                $("#userEmail").val(currentAddress);
                $("#currentAddress").val(currentAddress);
                $("#permanentAddress").val(permanentAddress);
                $("#submit").click();
            });

            step("Verify unsuccessful form submit", () -> {
                $("#userEmail").shouldHave(cssClass("field-error"));
            });
        }

}
