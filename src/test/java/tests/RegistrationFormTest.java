package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Locale;

import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;


@Feature("Form fill tests")
@Story("Student registration")
public class RegistrationFormTest extends TestBase {


    @BeforeAll
    static void setup() {
        Configuration.startMaximized = true;
    }
    Faker faker = new Faker();
    FakeValuesService fakeValuesService = new FakeValuesService(
            new Locale("en-GB"), new RandomService());

    String firstName = "Di",
            lastName = "M",
            userEmail = "leprekon@ya.ru",
            userNumber = "8006666666",
            gender = "Female",
            dayOfBirth = "22",
            monthOfBirth = "June",
            yearOfBirth = "1993",
            picture = "always.png",
            currentAddress = "St. Patrick Street, 17",
            state = "Haryana",
            city = "Karnal";
    String[] subjects = { "Economics", "Social Studies" };
    String[] hobbies = { "Sports", "Reading", "Music" };

    @Test
    @DisplayName("Successful fill registration form")
    void successfulFillFormTest() {

        step("Open students registration form", () -> {
            open("https://demoqa.com/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });

        step("Fill students registration form", () -> {
            $("#firstName").val(firstName);
            $("#lastName").val(lastName);
            $("#userEmail").val(userEmail);
            $("#genterWrapper").$(byText(gender)).click();
            $("#userNumber").val(userNumber);
            $("#dateOfBirthInput").click();
            $(".react-datepicker__year-select").click();
            $(".react-datepicker__year-select").selectOption(yearOfBirth);
            $(".react-datepicker__month-select").click();
            $(".react-datepicker__month-select").selectOption(monthOfBirth);
            $(".react-datepicker__day--0" + dayOfBirth).click();

            for (String subject : subjects) {
                String prefix = subject.substring(1);
                $("#subjectsInput").val(prefix);
                $(".subjects-auto-complete__menu").$(byText(subject)).click();
            }

            for (String hobby : hobbies) {
                $("#hobbiesWrapper").$(byText(hobby)).click();
            }

            $("#uploadPicture").uploadFile(new File("src/test/resources/" + picture));
            $("#currentAddress").val(currentAddress);
            $("#state").click();
            $("#stateCity-wrapper").$(byText(state)).click();
            $("#city").click();
            $("#stateCity-wrapper").$(byText(city)).click();
            $("#submit").click();
        });

        step("Verify successful form submit", () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            $(".table-responsive").shouldHave(text(firstName + " " + lastName),text(userEmail), text(gender));
            $x("//td[text()= 'Student Name']").parent().shouldHave(text(firstName + " " + lastName));
            $x("//td[text()= 'Student Email']").parent().shouldHave(text(userEmail));
            $x("//td[text()= 'Gender']").parent().shouldHave(text(gender));
            $x("//td[text()= 'Mobile']").parent().shouldHave(text(userNumber));
            $x("//td[text()= 'Date of Birth']").parent().shouldHave(text(dayOfBirth + " " + monthOfBirth + "," + yearOfBirth));
            $x("//td[text()= 'Subjects']").parent().shouldHave(text(String.join(", ", subjects)));
            $x("//td[text()= 'Hobbies']").parent().shouldHave(text(String.join(", ", hobbies)));
            $x("//td[text()= 'Picture']").parent().shouldHave(text(picture));
            $x("//td[text()= 'Address']").parent().shouldHave(text(currentAddress));
            $x("//td[text()= 'State and City']").parent().shouldHave(text(state + " " + city));
         });

        }

    @Test
    @DisplayName("Unsuccessful fill registration form with wrong phone number")
    void UnsuccessfulFillFormTest() {
        step("Open students registration form", () -> {
            open("https://demoqa.com/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });

        step("Fill students registration form", () -> {
            $("#firstName").val(firstName);
            $("#lastName").val(lastName);
            $("#userEmail").val(userEmail);
            $("#genterWrapper").$(byText(gender)).click();
            $("#userNumber").val("800666666");
            $("#dateOfBirthInput").click();
            $(".react-datepicker__year-select").click();
            $(".react-datepicker__year-select").selectOption(yearOfBirth);
            $(".react-datepicker__month-select").click();
            $(".react-datepicker__month-select").selectOption(monthOfBirth);
            $(".react-datepicker__day--0" + dayOfBirth).click();

            for (String subject : subjects) {
                String prefix = subject.substring(1);
                $("#subjectsInput").val(prefix);
                $(".subjects-auto-complete__menu").$(byText(subject)).click();
            }

            for (String hobby : hobbies) {
                $("#hobbiesWrapper").$(byText(hobby)).click();
            }

            $("#uploadPicture").uploadFile(new File("src/test/resources/" + picture));
            $("#currentAddress").val(currentAddress);
            $("#state").click();
            $("#stateCity-wrapper").$(byText(state)).click();
            $("#city").click();
            $("#stateCity-wrapper").$(byText(city)).click();
            $("#submit").click();
        });

        step("Verify wrong form submit", () -> {
            $("#userNumber").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        });

    }

}

