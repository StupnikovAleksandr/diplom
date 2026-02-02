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
import page.FilterNewsPage;
import page.MainPage;
import page.NavigationPage;
import page.NewsPage;


@Epic("Раздел news filter")
@RunWith(AllureAndroidJUnit4.class)
public class FilterNewsTest {
    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authorizationPage = new AuthorizationPage();
    NewsPage newsPage = new NewsPage();
    NavigationPage navigationPage = new NavigationPage();
    ControlPanelPage controlPanelPage = new ControlPanelPage();
    MainPage mainPage = new MainPage();
    CreatingNewsPage creatingNewsPage = new CreatingNewsPage();
    FilterNewsPage filterNewsPage = new FilterNewsPage();

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

    @Story("ТК-20 ФИЛЬТРАЦИЯ ТОЛЬКО ПО КАТЕГОРИИ (даты пустые)")
    @Test
    public void filterNewsChangeCategory() {

        String category = Row.getRandomCategory();
        String time = Row.getTime();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publisher = Row.getTodayDate();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publisher, time, description);
        creatingNewsPage.clickSaveButton();
        navigationPage.goOverTabNews();

        Allure.step("Фильтрация");
        newsPage.clickFilterButtonNewsTab();
        filterNewsPage.editFilterTab(category);
        filterNewsPage.clickFilterButton();

        Allure.step("Проверка отображения");
        filterNewsPage.checkEditFilterTab(title, description, publisher);

        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        newsPage.buttonEditNews();
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);
    }

    @Story("ТК-21 Фильтрация с выбранной (валидной) категорией и валидными датами")

    @Test
    public void filterNewsTest() {

        String category = Row.getRandomCategory();
        String time = Row.getTime();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publisher = Row.getTodayDate();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publisher, time, description);
        creatingNewsPage.clickSaveButton();
        navigationPage.goOverTabNews();

        Allure.step("Фильтрация");
        newsPage.clickFilterButtonNewsTab();
        filterNewsPage.editFilterTab(category, publisher, publisher);
        filterNewsPage.clickFilterButton();

        Allure.step("Проверка отображения");
        filterNewsPage.checkEditFilterTab(title, description, publisher);

        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        newsPage.buttonEditNews();
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);

    }

    @Story("ТК-22 фильтрация с валидной категорией и пустой датой начала")
    @Test
    public void filterNewsEditCategoryAndDateEndTest() {

        String category = Row.getRandomCategory();
        String time = Row.getTime();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publisher = Row.getTodayDate();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publisher, time, description);
        creatingNewsPage.clickSaveButton();

        Allure.step("Фильтрация");
        navigationPage.goOverTabNews();
        newsPage.clickFilterButtonNewsTab();
        filterNewsPage.editFilterCategoryAndDateEnd(category, publisher); // дата окончания пустая
        filterNewsPage.clickFilterButton();

        Allure.step("Проверка отображения");
        filterNewsPage.checkErrorWrongAnClickOK();

        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        filterNewsPage.clickCancelButton();
        newsPage.buttonEditNews();
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);

    }

    @Story("ТК-23 фильтрация с валидной категорией и пустой датой окончания")
    @Test
    public void filterNewsEditDateEndTest() {

        String category = Row.getRandomCategory();
        String time = Row.getTime();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publisher = Row.getTodayDate();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publisher, time, description);
        creatingNewsPage.clickSaveButton();

        Allure.step("Фильтрация");
        navigationPage.goOverTabNews();
        newsPage.clickFilterButtonNewsTab();
        filterNewsPage.editFilterTab(category, publisher); // дата окончания пустая

        filterNewsPage.clickFilterButton();

        Allure.step("Проверка отображения");
        filterNewsPage.checkErrorWrongAnClickOK();
        filterNewsPage.clickCancelButton();

        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        newsPage.buttonEditNews();
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);

    }

    @Story("ТК-24 фильтрация с пустой категорией и датой окончания")
    @Test
    public void filterNewsEditStartTest() {

        String category = Row.getRandomCategory();
        String time = Row.getTime();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publisher = Row.getTodayDate();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publisher, time, description);
        creatingNewsPage.clickSaveButton();
        navigationPage.goOverTabNews();

        Allure.step("Фильтрация");
        newsPage.clickFilterButtonNewsTab();
        filterNewsPage.editFilterTabStart(publisher); // дата начала только, остальные  пустое
        filterNewsPage.clickFilterButton();

        Allure.step("Проверка отображения");
        filterNewsPage.checkErrorWrongAnClickOK();
        filterNewsPage.clickCancelButton();

        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        newsPage.buttonEditNews();
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);
    }

    @Story("ТК-25 фильтрация с пустой категорией и датой начала")
    @Test
    public void filterNewsEditEndDateTest() {

        String category = Row.getRandomCategory();
        String time = Row.getTime();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publisher = Row.getTodayDate();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publisher, time, description);
        creatingNewsPage.clickSaveButton();
        navigationPage.goOverTabNews();

        Allure.step("Фильтрация");
        newsPage.clickFilterButtonNewsTab();
        filterNewsPage.editFilterTabEnd(publisher);
        filterNewsPage.clickFilterButton();

        Allure.step("Проверка отображения");
        filterNewsPage.checkErrorWrongAnClickOK();
        filterNewsPage.clickCancelButton();

        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        newsPage.buttonEditNews();
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);
    }

    @Story("ТК-26 фильтация с пустой категорией (даты заполнены)")
    @Test
    public void filterNewsEmptyCategory() {

        String category = Row.getRandomCategory();
        String time = Row.getTime();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publisher = Row.getTodayDate();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publisher, time, description);
        creatingNewsPage.clickSaveButton();
        navigationPage.goOverTabNews();

        Allure.step("Фильтрация");
        newsPage.clickFilterButtonNewsTab();
        filterNewsPage.editFilterTabRange(publisher, publisher); // только даты, без категории
        filterNewsPage.clickFilterButton();

        Allure.step("Проверка отображения");
        filterNewsPage.checkEditFilterTab(title, description, publisher);


        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        newsPage.buttonEditNews();
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);
    }

    @Story("ТК-27 выход из фильра с пустыми полями")
    @Test
    public void filterExitEmptyTest() {

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Фильтрация");
        newsPage.clickFilterButtonNewsTab();
        filterNewsPage.clickCancelButton();
        newsPage.checkNewsTab();
    }

    @Story("ТК-28 выход из фильра с заполненными полями")
    @Test
    public void filterExitTest() {

        String category = Row.getRandomCategory();
        String publisher = Row.getTodayDate();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Фильтрация");
        newsPage.clickFilterButtonNewsTab();
        filterNewsPage.editFilterTab(category, publisher, publisher);
        filterNewsPage.clickCancelButton();
        newsPage.checkNewsTab();
    }

}
