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
    private View decorView;

    @Before
    public void setUp() {
        mActivityScenarioRule.getScenario().onActivity(activity -> decorView = activity.getWindow().getDecorView());
        try {
            mainPage.checkMainPage();
            mainPage.goToTabAllNews();
            return;
        } catch (Exception ignored) {}
        try {
            authorizationPage.checkIsDisplayed();
            authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
            mainPage.goToTabAllNews();
        } catch (Exception ignored) {}
    }
    AuthorizationPage authorizationPage = new AuthorizationPage();
    NewsPage newsPage = new NewsPage();
    MainPage mainPage = new MainPage();
    ControlPanelPage controlPanelPage = new ControlPanelPage();
    CreatingNewsPage creatingNewsPage = new CreatingNewsPage();

    @Story("ТК-29 Сортировка по возврастанию и убыванию")
    @Test
    public void sortingControlPanelTabAscendingAndDescendingTest() {
        String FirstTitle = "First News";
        String SecondTitle = "Second News";
        String today = Row.getTodayDate();
        String FirstTime = Row.getFirstTime();
        String SecondTime = Row.getTime();

        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(Row.getRandomCategory(), FirstTitle, today, FirstTime, "1-я новость");
        creatingNewsPage.clickSaveButton();

        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(Row.getRandomCategory(), SecondTitle, today, SecondTime, "2-я новость");
        creatingNewsPage.clickSaveButton();

        controlPanelPage.checkNewsOrder(SecondTitle, FirstTitle);

        controlPanelPage.clickSortButton();
        controlPanelPage.checkNewsOrder(FirstTitle, SecondTitle);

        controlPanelPage.clickSortButton();

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

        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publicationDate, time, description);
        creatingNewsPage.clickSaveButton();

        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();

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

        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publicationDate, time, description);
        creatingNewsPage.clickSaveButton();

        controlPanelPage.checkUnfolding(description);

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

        newsPage.buttonEditNews();
        controlPanelPage.goOverCreatingNews();
        creatingNewsPage.createNews(category, title, publicationDate, time, description);
        creatingNewsPage.clickSaveButton();

        controlPanelPage.expandNewsByTitleAndPublicationDate(title, publicationDate);
        controlPanelPage.checkUnfoldingNews(title, description);

        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);


    }
}


















