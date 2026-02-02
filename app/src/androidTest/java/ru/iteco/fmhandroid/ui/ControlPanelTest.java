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

@Epic("Раздел controlPanel")
@RunWith(AllureAndroidJUnit4.class)
public class ControlPanelTest {
    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authorizationPage = new AuthorizationPage();
    NewsPage newsPage = new NewsPage();
    NavigationPage navigationPage = new NavigationPage();
    MainPage mainPage = new MainPage();
    ControlPanelPage controlPanelPage = new ControlPanelPage();
    CreatingNewsPage creatingNewsPage = new CreatingNewsPage();

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

    @Story("ТК-29 Сортировка по возврастанию и убыванию")
    @Test
    public void sortingControlPanelTabAscendingAndDescendingTest() {
        String FirstTitle = "First News";
        String SecondTitle = "Second News";
        String today = Row.getTodayDate();
        String FirstTime = Row.getFirstTime();
        String SecondTime = Row.getTime();

        Allure.step("Авторизация и переход в раздел новостей");
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();

        Allure.step("Создание первой новости");
        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(Row.getRandomCategory(), FirstTitle, today, FirstTime, "1-я новость");
        creatingNewsPage.clickSaveButton();

        Allure.step("Создание второй новости");
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(Row.getRandomCategory(), SecondTitle, today, SecondTime, "2-я новость");
        creatingNewsPage.clickSaveButton();

        Allure.step("Проверка порядка новостей: по возрастанию времени");
        controlPanelPage.checkNewsOrder(SecondTitle, FirstTitle);

        controlPanelPage.clickSortButton();
        Allure.step("Проверка порядка новостей: по убыванию времени");
        controlPanelPage.checkNewsOrder(FirstTitle, SecondTitle);

        controlPanelPage.clickSortButton();

        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        controlPanelPage.deleteNewsByTitle(FirstTitle);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(FirstTitle);

        controlPanelPage.deleteNewsByTitle(SecondTitle);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(SecondTitle);

    }


    @Story("ТК-30 Удаление созданной записи")
    @Test
    public void deleteCreatedNewsTest() {
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

        Allure.step("Удаление созданной новости");
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();

        Allure.step("Проверка, что новость удалена");
        controlPanelPage.checkNewsControlPanelIsDelete(title);

    }

    @Story("ТК-31 свёрнутого состояния новости в списке новостей")
    @Test
    public void foldingNewsTest() {
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

        Allure.step("проверка свёрнутого состояния");
        controlPanelPage.checkUnfolding(description);

        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);


    }

    @Story("ТК-32 развёртанного состояния новости")
    @Test
    public void UnfoldingNewsTest() {
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

        Allure.step("действие + проверка развёрнутого состояния");
        controlPanelPage.expandNewsByTitleAndPublicationDate(title , publicationDate);
        controlPanelPage.checkUnfoldingNews(title, description);

        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);


    }
}


















