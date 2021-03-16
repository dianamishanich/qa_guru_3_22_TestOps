package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationFormTest {


    @BeforeAll
    static void setup() {
        Configuration.startMaximized = true;
    }

    @Test
    void fillFormTest() {
        String firstName = "Diana",
                lastName = "Mishanich",
                userEmail = "diana.m@ya.ru",
                userNumber = "9066666666",
                gender = "Female",
                dayOfBirth = "22",
                monthOfBirth = "June",
                yearOfBirth = "1993",
                picture = "always.png",
                currentAddress = "Tokareva st. 87",
                state = "Haryana",
                city = "Karnal";
        String[] subjects = { "Economics", "Social Studies" };
        String[] hobbies = { "Sports", "Reading", "Music" };

        open("https://demoqa.com/automation-practice-form");

        SelenideElement wrongResult = $(".col-sm-6").$("#lastName");
        SelenideElement successResult = $(".col-sm-6 #lastName");

        System.out.println(wrongResult);
        System.out.println(successResult);


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


    }



}

