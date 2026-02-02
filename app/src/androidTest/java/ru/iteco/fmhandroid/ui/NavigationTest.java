package ru.iteco.fmhandroid.ui;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import data.Row;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Story;
import page.AboutPage;
import page.AuthorizationPage;
import page.LoveIsAllPage;
import page.MainPage;
import page.NavigationPage;
import page.NewsPage;

@Epic("Навигация")
@RunWith(AllureAndroidJUnit4.class)
public class NavigationTest {
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
    NewsPage newsPage = new NewsPage();
    NavigationPage navigationPage = new NavigationPage();
    AboutPage aboutPage = new AboutPage();
    LoveIsAllPage loveIsAllPage = new LoveIsAllPage();
    MainPage mainPage = new MainPage();

    private View decorView;

    @Story("ТК-8 Переход в раздел news с таба навигации")
    @Test
    public void GoOverNewsTabTest() {
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        navigationPage.goOverTabNews();

        Allure.step("Проверка перехода в раздел news");
        newsPage.checkNewsTab();
    }

    @Story("ТК-9 Переход в раздел about с таба навигации")
    @Test
    public void GoOverAboutTabTest() {
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        navigationPage.goOverTabAbout();

        Allure.step("Проверка перехода в раздел about");
        aboutPage.checkOnAboutTab();
    }

    @Story("ТК-10 Переход в раздел love is all с таба навигации")
    @Test
    public void GoOverLoveIsAllTabTest() {
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        navigationPage.goOverTabLoveIsAll();

        Allure.step("Проверка перехода в раздел love is all");
        loveIsAllPage.checkOnLoveIsAllTab();
    }

    @Story("ТК-11 Выход из аккаунта (log out)")
    @Test
    public void GoOverLogOutTest() {
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.checkMainPage();
        navigationPage.openMenu();
        navigationPage.logOut();

        Allure.step("Проверка выхода из аккаунта");
        authorizationPage.checkInButton();
    }

}




