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
            mainPage.checkMainPage();
            return;
        } catch (Exception ignored) {}

        try {
            authorizationPage.checkIsDisplayed();
            authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        } catch (Exception ignored) {}
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
        navigationPage.goOverTabNews();
        newsPage.checkNewsTab();
    }

    @Story("ТК-9 Переход в раздел about с таба навигации")
    @Test
    public void GoOverAboutTabTest() {
        navigationPage.goOverTabAbout();
        aboutPage.checkOnAboutTab();
    }

    @Story("ТК-10 Переход в раздел love is all с таба навигации")
    @Test
    public void GoOverLoveIsAllTabTest() {
        navigationPage.goOverTabLoveIsAll();
        loveIsAllPage.checkOnLoveIsAllTab();
    }

    @Story("ТК-11 Выход из аккаунта (log out)")
    @Test
    public void GoOverLogOutTest() {
        mainPage.checkMainPage();
        navigationPage.openMenu();
        navigationPage.logOut();

        authorizationPage.checkInButton();
    }

}




