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
import page.EditingNewsPage;
import page.FilterControlPanelPage;
import page.MainPage;
import page.NavigationPage;
import page.NewsPage;

@Epic("Раздел control panel filter")
@RunWith(AllureAndroidJUnit4.class)
public class FilterControlPanelTest {
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
    FilterControlPanelPage filterControlPanelPage = new FilterControlPanelPage();

    EditingNewsPage editingNewsPage = new EditingNewsPage();

    @Story("ТК-47 Фильтрация с выбранной (валидной) категорией и валидными датами с активными чек боксами")
    @Test
    public void filterControlPanelValidDataTest() {

        String category = Row.getRandomCategory();
        String firstTitle = "Новость 1";
        String firstDescription = "ТЕСТ1";
        String firstPublicationDate = Row.getTodayDate();
        String firstTime = Row.getTime();

        String secondTitle = "Новость 2";
        String secondDescription = "ТЕСТ2";
        String secondPublicationDate = Row.getPlus2DayDate();
        String secondTime = Row.getTime();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание первой новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();

        creatingNewsPage.createNews(category, firstTitle, firstPublicationDate, firstTime, firstDescription);
        creatingNewsPage.clickSaveButton();

        Allure.step("Создание второй новости");
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, secondTitle, secondPublicationDate, secondTime, secondDescription);
        creatingNewsPage.clickSaveButton();

        Allure.step("Изменение активности 2й вести на NOT ACTIVE");
        controlPanelPage.editNewsByTitle(secondTitle);
        editingNewsPage.clickAndCheckUncheckedCheckBox();
        editingNewsPage.clickSaveButton();

        Allure.step("Фильтрация");
        controlPanelPage.goOverFilter();
        filterControlPanelPage.editFilterTab(category, firstPublicationDate, secondPublicationDate);
        filterControlPanelPage.clickFilterButton();

        Allure.step("Проверка отображения");
        controlPanelPage.checkEditAndCreateNewsByTitle(firstTitle, firstDescription, firstPublicationDate, "ACTIVE");
        controlPanelPage.checkEditAndCreateNewsByTitle(secondTitle, secondDescription, secondPublicationDate, "NOT ACTIVE");


        Allure.step("Удаление созданых новостей для чистоты тестового окружения");
        controlPanelPage.deleteNewsByTitle(firstTitle);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(firstTitle);

        controlPanelPage.deleteNewsByTitle(secondTitle);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(secondTitle);
    }

    @Story("ТК-48 Фильтрация с выбранной (валидной) категорией и валидными датами с активным чек чек боксом NOT ACTIVE")
    @Test
    public void CheckBoxInActiveTest() {

        String category = Row.getRandomCategory();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publicationDate = Row.getTodayDate();
        String time = Row.getTime();

        String secondPublicationDate = Row.getPlus2DayDate();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание  новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publicationDate, time, description);
        creatingNewsPage.clickSaveButton();

        Allure.step("Изменение активности  вести на NOT ACTIVE");
        controlPanelPage.editNewsByTitle(title);
        editingNewsPage.clickAndCheckUncheckedCheckBox();
        editingNewsPage.clickSaveButton();

        Allure.step("Фильтрация");
        controlPanelPage.goOverFilter();
        filterControlPanelPage.editFilterTab(category, publicationDate, secondPublicationDate);
        filterControlPanelPage.clickAndCheckBoxActive();
        filterControlPanelPage.clickFilterButton();

        Allure.step("Проверка отображения");
        controlPanelPage.checkEditAndCreateNewsByTitle(title, description, publicationDate, "NOT ACTIVE");


        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);

    }

    @Story("ТК-49 Фильтрация с выбранной (валидной) категорией и валидными датами с активным чек чек боксом ACTIVE")
    @Test
    public void CheckBoxActiveTest() {

        String category = Row.getRandomCategory();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publicationDate = Row.getTodayDate();
        String time = Row.getTime();
        String secondPublicationDate = Row.getPlus2DayDate();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание  новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publicationDate, time, description);
        creatingNewsPage.clickSaveButton();

        Allure.step("Фильтрация");
        controlPanelPage.goOverFilter();
        filterControlPanelPage.editFilterTab(category, publicationDate, secondPublicationDate);
        filterControlPanelPage.clickAndCheckBoxInActive();
        filterControlPanelPage.clickFilterButton();

        Allure.step("Проверка отображения");
        controlPanelPage.checkEditAndCreateNewsByTitle(title, description, publicationDate, "ACTIVE");

        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);
    }

    @Story("ТК-50 Фильтрация с пустыми полем катьегория и активированым чек боксом")
    @Test
    public void CategoryEmptyFilterTest() {

        String category = Row.getRandomCategory();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publicationDate = Row.getTodayDate();
        String time = Row.getTime();
        String secondPublicationDate = Row.getPlus2DayDate();


        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание  новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publicationDate, time, description);
        creatingNewsPage.clickSaveButton();

        Allure.step("Фильтрация");
        controlPanelPage.goOverFilter();
        filterControlPanelPage.editFilterTabRange(publicationDate, secondPublicationDate);
        filterControlPanelPage.clickFilterButton();

        Allure.step("Проверка отображения");
        controlPanelPage.checkEditAndCreateNewsByTitle(title, description, publicationDate, "ACTIVE");

        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);

    }

    @Story("ТК-51 Фильтрация с пустой датой начала")
    @Test
    public void InvalidDateStartPeriodTest() {

        String category = Row.getRandomCategory();
        String publicationDate = Row.getTodayDate();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("переход в редактирование новостей");
        newsPage.buttonEditNews();
        controlPanelPage.goOverFilter();
        filterControlPanelPage.editFilterTab(category, publicationDate);
        filterControlPanelPage.clickFilterButton();

        Allure.step("Проверка отображения ошибки Error wrong");
        filterControlPanelPage.checkErrorWrongAnClickOK();

    }

    @Story("ТК-52 Фильтрация с пустыми полем катьегория и ДЕактивированым и  чек боксом")
    @Test
    public void FilterEmptyBoxActiveTest() {

        String category = Row.getRandomCategory();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publicationDate = Row.getTodayDate();
        String time = Row.getTime();
        String secondPublicationDate = Row.getPlus2DayDate();


        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publicationDate, time, description);
        creatingNewsPage.clickSaveButton();

        Allure.step("Фильтрация");
        controlPanelPage.goOverFilter();
        filterControlPanelPage.editFilterTab(category, publicationDate, secondPublicationDate);
        filterControlPanelPage.clickAndCheckBoxInActive();
        filterControlPanelPage.clickAndCheckBoxActive();
        filterControlPanelPage.clickFilterButton();

        Allure.step("Проверка отображения");
        controlPanelPage.checkEditAndCreateNewsByTitle(title, description, publicationDate, "ACTIVE");


        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);
    }

    @Story("ТК-53 фильтрация с пустой датой окончания")
    @Test
    public void InvalidDateEndPeriodTest() {

        String category = Row.getRandomCategory();
        String secondPublicationDate = Row.getPlus2DayDate();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("переход в фильтр новостей");
        newsPage.buttonEditNews();
        controlPanelPage.goOverFilter();
        filterControlPanelPage.editFilterCategoryAndDateEnd(category, secondPublicationDate);
        filterControlPanelPage.clickFilterButton();

        Allure.step("Проверка отображения ошибки Error wrong");
        filterControlPanelPage.checkErrorWrongAnClickOK();

    }

    @Story("ТК-54 фильтрация с пустой датой начала и окончания")
    @Test
    public void DataEndAndDataStartEmptyTest() {

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

        Allure.step("Фильтрация");
        controlPanelPage.goOverFilter();
        filterControlPanelPage.editFilterTab(category);
        filterControlPanelPage.clickFilterButton();

        Allure.step("Проверка отображения");
        controlPanelPage.checkEditAndCreateNewsByTitle(title, description, publicationDate, "ACTIVE");

        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);

    }

    @Story("ТК-55 фильтрация c будующей датой")
    @Test
    public void DateCreatedFromTheFutureTest() {

        String category = Row.getRandomCategory();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publicationDate = Row.getPlus30DayDate();
        String time = Row.getTime();
        String secondPublicationDate = Row.getTodayDate();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publicationDate, time, description);
        creatingNewsPage.clickSaveButton();

        Allure.step("Фильтрация");
        controlPanelPage.goOverFilter();
        filterControlPanelPage.editFilterTab(category, secondPublicationDate, publicationDate); // сначала сегодня потом +30
        filterControlPanelPage.clickAndCheckBoxInActive();
        filterControlPanelPage.clickFilterButton();

        Allure.step("Проверка отображения");
        controlPanelPage.checkEditAndCreateNewsByTitle(title, description, publicationDate, "ACTIVE");


        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);

    }

    @Story("ТК-56 фильтрация прошлой датой")
    @Test
    public void DateCreatedFromThePastTest() {

        String category = Row.getRandomCategory();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publicationDate = Row.getTodayDate();
        String time = Row.getTime();
        String secondPublicationDate = Row.getMinus30DayDate();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publicationDate, time, description);
        creatingNewsPage.clickSaveButton();

        Allure.step("Фильтрация");
        controlPanelPage.goOverFilter();
        filterControlPanelPage.editFilterTab(category, secondPublicationDate, publicationDate); // сначала 30 дней назад потом сегодня
        filterControlPanelPage.clickAndCheckBoxInActive();
        filterControlPanelPage.clickFilterButton();

        Allure.step("Проверка отображения");
        controlPanelPage.checkEditAndCreateNewsByTitle(title, description, publicationDate, "ACTIVE");

        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);

    }

    @Story("ТК-57 выход с фильра")
    @Test
    public void ExitFilterTabTest() {

        String category = Row.getRandomCategory();
        String publicationDate = Row.getTodayDate();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("переход в фильтр новостей");
        newsPage.buttonEditNews();
        controlPanelPage.goOverFilter();
        filterControlPanelPage.editFilterTab(category, publicationDate);

        Allure.step("Выход из фильтра");
        filterControlPanelPage.clickCancelButton();
        controlPanelPage.checkControlTab();


    }


}




