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
import page.AuthorizationPage;
import page.ControlPanelPage;
import page.CreatingNewsPage;
import page.MainPage;
import page.NavigationPage;
import page.NewsPage;

@Epic("Раздел Main")
@RunWith(AllureAndroidJUnit4.class)
public class MainTest {
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
    NavigationPage navigationPage = new NavigationPage();
    MainPage mainPage = new MainPage();
    NewsPage newsPage = new NewsPage();

    ControlPanelPage controlPanelPage = new ControlPanelPage();
    CreatingNewsPage creatingNewsPage = new CreatingNewsPage();
    private View decorView;

    @Story("ТК-12 Проверка функционала сворачивания  новостного блока")
    @Test
    public void foldingTest() {
        mainPage.checkFolding();

    }

    @Story("ТК-13 Проверка функционала разворачивания  новостного блока")
    @Test
    public void unfoldingTest() {
        mainPage.intentUnfolding();
        mainPage.checkUnfolding();
    }


    @Story("ТК-14 Переход в раздел News")
    @Test
    public void goOverNewsTab() {
        mainPage.goToTabAllNews();
        newsPage.checkNewsTab();
    }


    @Story("ТК-15 Проверка отображения новости в табе News")
    @Test
    public void createNewsAndCheckTabMainTest() {

        String category = Row.getRandomCategory();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publicationDate = Row.getTodayDate();
        String time = Row.getTime();

        mainPage.goToTabAllNews();

        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publicationDate, time, description);
        creatingNewsPage.clickSaveButton();

        navigationPage.goOverTabMain();

        mainPage.checkNewsMainPage(title, description);


        mainPage.goToTabAllNews();
        newsPage.buttonEditNews();
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);
    }


}
