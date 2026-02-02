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

@Epic("Раздел creating news")
@RunWith(AllureAndroidJUnit4.class)
public class CreatingNewsTest {
    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    private View decorView;

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
    MainPage mainPage = new MainPage();
    ControlPanelPage controlPanelPage = new ControlPanelPage();
    CreatingNewsPage creatingNewsPage = new CreatingNewsPage();

    @Story("ТК-33 создание новости с валидными данными")
    @Test
    public void createNewsValidDataTest() {

        String category = Row.getRandomCategory();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publicationDate = Row.getTodayDate();
        String time = Row.getTime();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publicationDate, time, description);
        creatingNewsPage.clickSaveButton();

        Allure.step("Проверка созданной новости");
        controlPanelPage.checkEditAndCreateNewsByTitle(title, description, publicationDate, "ACTIVE");

        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);
    }

    @Story("ТК-34 создание новости с НЕ валидными данными в текстовых полях (спецсимволами)")
    @Test
    public void createNewsInValidDataTest() {

        String publicationDate = Row.getTodayDate();
        String time = Row.getTime();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.manualCreateNews(Row.InvalidCategory, Row.InvalidTitle, publicationDate, time, Row.InvalidDescription);
        creatingNewsPage.clickSaveButton();

        Allure.step("Проверка ошибки saving failed при создании новости");
        creatingNewsPage.checkErrorSavingFailed();
    }

    @Story("ТК-36 создание новости с ручным вводом несуществующей катеогрии")
    @Test
    public void notExistentCategoryTest() {

        String title = Row.getRandomTitle();
        String description = Row.getRandomCategory();
        String publicationDate = Row.getTodayDate();
        String time = Row.getTime();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.manualCreateNews(Row.InvalidsCategory, title, publicationDate, time, description);
        creatingNewsPage.clickSaveButton();

        Allure.step("Проверка ошибки saving failed при создании новости");
        creatingNewsPage.checkErrorSavingFailed();
    }

    @Story("ТК-37 создание новости с НЕ валидными данными в текстовых полях (пробелы)")
    @Test
    public void createNewsInValidTest() {

        String publicationDate = Row.getTodayDate();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.manualCreateNews(Row.EmptyCategory, Row.EmptyTitle, publicationDate, Row.EmptyTime, Row.EmptyDescription);
        creatingNewsPage.clickSaveButton();

        Allure.step("Проверка ошибки empty failed  при создании новости");
        creatingNewsPage.checkErrorEmptyFailed();
    }

    @Story("ТК-38 создание новости с пустыми полями")
    @Test
    public void createNewsEmptyTest() {

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.clickSaveButton();

        Allure.step("Проверка ошибки empty failed  при создании новости");
        creatingNewsPage.checkErrorEmptyFailed();
    }

    @Story("ТК-39 выход из раздела creating news с заполненными полями валидными данными")
    @Test
    public void cancelApplicationTest() {

        String category = Row.getRandomCategory();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publicationDate = Row.getTodayDate();
        String time = Row.getTime();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publicationDate, time, description);

        Allure.step("Выход из раздела creating news с заполненными полями валидными данными");
        creatingNewsPage.clickCancelButtonAndWindowModalOK();
        controlPanelPage.checkControlTab();
    }

    @Story("ТК-40 выход и возврат обратно в раздел creating news с заполненными полями валидными данными")
    @Test
    public void reworkCancelApplicationTest() {
        String category = Row.getRandomCategory();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publicationDate = Row.getTodayDate();
        String time = Row.getTime();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publicationDate, time, description);

        Allure.step("Выход из раздела creating news с заполненными полями валидными данными");
        creatingNewsPage.clickCancelButtonAndWindowModalCancel();
        creatingNewsPage.checkCreatingNewsPage();


    }


}


