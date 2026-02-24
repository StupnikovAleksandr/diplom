package ru.iteco.fmhandroid.ui;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import data.Row;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Story;
import page.AuthorizationPage;
import page.MainPage;
import page.NavigationPage;


@Epic("Авторизация")
@RunWith(AllureAndroidJUnit4.class)


public class AuthorizationTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        mActivityScenarioRule.getScenario().onActivity(activity -> decorView = activity.getWindow().getDecorView());
        try {
            authorizationPage.checkIsDisplayed();
        } catch (Exception e) {
            navigationPage.openMenu();
            navigationPage.logOut();
        }
    }

    AuthorizationPage authorizationPage = new AuthorizationPage();
    NavigationPage navigationPage = new NavigationPage();
    MainPage mainPage = new MainPage();
    private View decorView;


    @Story("ТК-2 Авторизация С валидным логином и паролем")
    @Test
    public void happyAuthorizationTest() {
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.checkMainPage();
    }

    @Story("ТК-3 Авторизация с НЕ валидным логином")
    @Test
    public void unhappyInValidLoginAuthorizationTest() {
        authorizationPage.authorizationError(Row.InValidLogin, Row.ValidPassword);
        authorizationPage.messageAuthorizationError();
    }

    @Story("ТК-4 Авторизация с НЕ валидным паролем")
    @Test
    public void unhappyInValidPasswordAuthorizationTest() {
        authorizationPage.authorizationError(Row.ValidLogin, Row.InValidPassword);
        authorizationPage.messageAuthorizationError();
    }

    @Story("ТК-5 Авторизация с пустым логином")
    @Test
    public void unhappyEmptyLoginAuthorizationTest() {
        authorizationPage.authorizationError(Row.EmptyLogin, Row.ValidPassword);
        authorizationPage.messageEmptyFieldError();
    }

    @Story("ТК-6 Авторизация с пустым паролем")
    @Test
    public void unhappyEmptyPasswordAuthorizationTest() {
        authorizationPage.authorizationError(Row.ValidLogin, Row.EmptyPassword);
        authorizationPage.messageEmptyFieldError();
    }

    @Story("ТК-7 Авторизаци с валидным логином и паролем в верхнем регистре")
    @Test
    public void unhappyUpperCaseAuthorizationTest() {
        authorizationPage.authorizationError(Row.UpperCaseLogin, Row.UpperCasePassword);
        authorizationPage.messageAuthorizationError();
    }

    @Story("ТК-8 SQL- инъекция  по логину")
    @Test
    public void sqlInjectionLoginTest() {
        authorizationPage.authorization(Row.SqlInjectionLogin, Row.ValidPassword);
        authorizationPage.messageAuthorizationError();

    }

    @Story("ТК-9 SQL- инъекция  по паролю")
    @Test
    public void sqlInjectionPasswordTest() {
        authorizationPage.authorization(Row.ValidLogin, Row.SqlInjectionPassword);
        authorizationPage.messageAuthorizationError();
    }


}



