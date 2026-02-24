package page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import android.view.View;

import androidx.test.espresso.ViewInteraction;

import org.hamcrest.Matchers;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import data.Helper;


public class AuthorizationPage {

    private final ViewInteraction login_input = onView(allOf(
            withHint("Login"),
            withParent(withParent(withId(R.id.login_text_input_layout)))
    ));
    private final ViewInteraction password_input = onView(allOf(
            withHint("Password"),
            withParent(withParent(withId(R.id.password_text_input_layout)))
    ));
    private final ViewInteraction signIn_input = onView(withId(R.id.enter_button));

    private final ViewInteraction authError = onView(withText
            ("Something went wrong. Try again later."));
    private final ViewInteraction emptyError = onView(withText
            ("Login and password cannot be empty"));

    public void checkInButton() {
        Allure.step("Проверка, что кнопка 'Войти' отображается");
        signIn_input
                .check(matches(isDisplayed()));
    }

    private View decorView;

    private void waitForLoginField() {
        Allure.step("Ожидание появления поля логина");
        onView(isRoot()).perform(Helper.waitDisplayed(R.id.login_text_input_layout, 15_000));
        login_input
                .check(matches(isDisplayed()));
    }

    public void checkIsDisplayed() {
        Allure.step("Проверка отображения экрана авторизации");
        waitForLoginField();
    }

    public void authorization(String loginText, String passwordText) {
        Allure.step("Авторизация с логином: " + loginText + " и паролем: " + (passwordText));
        waitForLoginField();
        login_input
                .perform(replaceText(loginText), closeSoftKeyboard());
        Allure.step("Ввод логина");
        password_input
                .perform(replaceText(passwordText), closeSoftKeyboard());
        Allure.step("Ввод пароля");
        signIn_input
                .perform(click());
        Allure.step("Нажатие кнопки 'Войти'");

        new MainPage();
    }

    public void authorizationError(String loginText, String passwordText) {
        Allure.step("Попытка авторизации с неверными данными");
        login_input
                .perform(replaceText(loginText), closeSoftKeyboard());
        password_input
                .perform(replaceText(passwordText), closeSoftKeyboard());
        signIn_input
                .perform(click());

    }

    public void messageAuthorizationError() {
        Allure.step("Проверка отображения ошибки: 'Something went wrong. Try again later.'");
        authError
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }

    public void messageEmptyFieldError() {
        Allure.step("Проверка отображения ошибки: 'Login and password cannot be empty'");
        emptyError
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }
}








