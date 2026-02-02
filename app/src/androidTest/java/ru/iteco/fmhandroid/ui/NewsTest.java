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



@Epic("Раздел news")
@RunWith(AllureAndroidJUnit4.class)
public class NewsTest {
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
    ControlPanelPage controlPanelPage = new ControlPanelPage();
    MainPage mainPage = new MainPage();
    CreatingNewsPage creatingNewsPage = new CreatingNewsPage();
    FilterNewsPage filterNewsPage = new FilterNewsPage();



    @Story("ТК-16 Cортировка новостей по возрастанию/убыванию")
    @Test
    public void sortingNewsTabAscendingAndDescendingTest() {

        String FirstTitle = "Первая новость";
        String SecondTitle = "Вторая новость";
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

        Allure.step("Сортировка новостей по возрастанию");
        navigationPage.goOverTabNews();
        newsPage.checkNewsOrderNewsTab(SecondTitle, FirstTitle);
        newsPage.clickSortButtonNewsTab();
        Allure.step("Проверка сортировки по убыванию");
        newsPage.checkNewsOrderNewsTab(FirstTitle, SecondTitle);

        Allure.step("Удаление созданной новости для чистоты тестового окружения");
        newsPage.buttonEditNews();
        controlPanelPage.deleteNewsByTitle(FirstTitle);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(FirstTitle);
        controlPanelPage.deleteNewsByTitle(SecondTitle);
        controlPanelPage.windowModalOKDelete();;
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(SecondTitle);
    }



    @Story("ТК-17 Переход в фильтр новостей")
    @Test
    public void goToFilterNewsTest() {

        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();
        newsPage.clickFilterButtonNewsTab();

        Allure.step("Проверка перехода в фильтр новостей");
        filterNewsPage.checkNewsTab();
    }

    @Story("ТК-18 Переход редактирование новостей")
    @Test
    public void goToControlPanelTest() {

        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        mainPage.goToTabAllNews();
        newsPage.buttonEditNews();

        Allure.step("Проверка перехода в панель управления");
        controlPanelPage.checkControlTab();
    }
}


