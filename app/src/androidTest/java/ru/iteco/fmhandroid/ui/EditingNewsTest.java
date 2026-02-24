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
import page.ControlPanelPage;
import page.CreatingNewsPage;
import page.EditingNewsPage;
import page.MainPage;
import page.NavigationPage;
import page.NewsPage;


@Epic("Раздел editing news")
@RunWith(AllureAndroidJUnit4.class)
public class EditingNewsTest {
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
            newsPage.buttonEditNews();
            controlPanelPage.goOverCreatingNews();
            return;
        } catch (Exception ignored) {}
        try {
            authorizationPage.checkIsDisplayed();
            authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
            mainPage.goToTabAllNews();
            newsPage.buttonEditNews();
            controlPanelPage.goOverCreatingNews();
        } catch (Exception ignored) {}
    }

    AuthorizationPage authorizationPage = new AuthorizationPage();
    NewsPage newsPage = new NewsPage();
    MainPage mainPage = new MainPage();
    ControlPanelPage controlPanelPage = new ControlPanelPage();
    CreatingNewsPage creatingNewsPage = new CreatingNewsPage();
    EditingNewsPage editingNewsPage = new EditingNewsPage();

    @Story("ТК 41 - Редактирование созданной записи валидными данными")
    @Test
    public void editNewsValidDataTest() {

        String category = Row.getRandomCategory();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publicationDate = Row.getTodayDate();
        String time = Row.getTimePlus2Hours();

        String updateCategory = Row.getRandomCategory();
        String updateTitle = Row.getRandomTitle();
        String updateDescription = Row.getRandomDescription();
        String updatePublicationDate = Row.getPlus2DayDate();
        String updateTime = Row.getTime();

        creatingNewsPage.createNews(category, title, publicationDate, time, description);
        creatingNewsPage.clickSaveButton();

        controlPanelPage.editNewsByTitle(title);
        editingNewsPage.editNewsTab(updateCategory, updateTitle, updatePublicationDate, updateTime, updateDescription);
        editingNewsPage.clickSaveButton();
        controlPanelPage.checkEditAndCreateNewsByTitle(updateTitle, updateDescription, updatePublicationDate, "ACTIVE");

        controlPanelPage.deleteNewsByTitle(updateTitle);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(updateTitle);
    }

    @Story("42-Редактирование созданной записи валидными данными с пустыми (ПРОБЕЛАМИ) полями описание титул и категории")
    @Test
    public void editNewsEmptyTest() {

        String category = Row.getRandomCategory();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publicationDate = Row.getTodayDate();
        String time = Row.getTime();

        String updateCategory = Row.EmptyCategory;
        String updateTitle = Row.EmptyTitle;
        String updateDescription = Row.EmptyDescription;
        String updatePublicationDate = Row.getPlus2DayDate();
        String updateTime = Row.getTimePlus2Hours();

        creatingNewsPage.createNews(category, title, publicationDate, time, description);
        creatingNewsPage.clickSaveButton();

        controlPanelPage.editNewsByTitle(title);
        editingNewsPage.editNewsTab(updateCategory, updateTitle, updatePublicationDate, updateTime, updateDescription);
        editingNewsPage.clickSaveButton();
        editingNewsPage.checkErrorEmptyFields();
        editingNewsPage.clickCancelButtonAndWindowModalOK();

        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);
    }

    @Story("ТК-43 Редактирование созданной записи валидными данными с деактивированным ЧБ")
    @Test
    public void editCheckBoxTest() {

        String category = Row.getRandomCategory();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publicationDate = Row.getTodayDate();
        String time = Row.getTime();

        String updateCategory = Row.getRandomCategory();
        String updateTitle = Row.getRandomTitle();
        String updateDescription = Row.getRandomDescription();
        String updatePublicationDate = Row.getPlus2DayDate();
        String updateTime = Row.getTimePlus2Hours();

        creatingNewsPage.createNews(category, title, publicationDate, time, description);
        creatingNewsPage.clickSaveButton();

        controlPanelPage.editNewsByTitle(title);
        editingNewsPage.editNewsTab(updateCategory, updateTitle, updatePublicationDate, updateTime, updateDescription);
        editingNewsPage.clickAndCheckUncheckedCheckBox();
        editingNewsPage.clickSaveButton();
        controlPanelPage.checkEditAndCreateNewsByTitle(updateTitle, updateDescription, updatePublicationDate, "NOT ACTIVE");

        controlPanelPage.deleteNewsByTitle(updateTitle);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(updateTitle);
    }


    @Story("ТК-44 Выход из таба редактирование созданной записи")
    @Test
    public void exitEditTabTest() {

        String category = Row.getRandomCategory();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publicationDate = Row.getTodayDate();
        String time = Row.getTime();

        String updateCategory = Row.getRandomCategory();
        String updateTitle = Row.getRandomTitle();
        String updateDescription = Row.getRandomDescription();
        String updatePublicationDate = Row.getPlus2DayDate();
        String updateTime = Row.getTimePlus2Hours();

        creatingNewsPage.createNews(category, title, publicationDate, time, description);
        creatingNewsPage.clickSaveButton();

        controlPanelPage.editNewsByTitle(title);
        editingNewsPage.editNewsTab(updateCategory, updateTitle, updatePublicationDate, updateTime, updateDescription);
        editingNewsPage.clickCancelButtonAndWindowModalOK();
        controlPanelPage.checkNewsDoesNotExist(updateTitle, updateDescription);

        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);
    }

    @Story("ТК-45 Редактирование созданной записи с заполнение спеецсимволами поля описание титул и категории ")
    @Test
    public void editInvalidDataTest() {

        String category = Row.getRandomCategory();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publicationDate = Row.getTodayDate();
        String time = Row.getTime();

        String updateCategory = Row.InvalidCategory;
        String updateTitle = Row.InvalidTitle;
        String updateDescription = Row.InvalidDescription;
        String updatePublicationDate = Row.getPlus2DayDate();
        String updateTime = Row.getTimePlus2Hours();

        creatingNewsPage.createNews(category, title, publicationDate, time, description);
        creatingNewsPage.clickSaveButton();

        controlPanelPage.editNewsByTitle(title);
        editingNewsPage.editNewsTab(updateCategory, updateTitle, updatePublicationDate, updateTime, updateDescription);
        editingNewsPage.clickSaveButton();

        editingNewsPage.checkErrorSavingFailed();
        editingNewsPage.clickCancelButtonAndWindowModalOK();

        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);
    }

    @Story("ТК-46  Выход+ возврат из таба редактирование созданной записи")
    @Test
    public void exitAndBackEditTabTest() {

        String category = Row.getRandomCategory();
        String title = Row.getRandomTitle();
        String description = Row.getRandomDescription();
        String publicationDate = Row.getTodayDate();
        String time = Row.getTime();

        String updateCategory = Row.getRandomCategory();
        String updateTitle = Row.getRandomTitle();
        String updateDescription = Row.getRandomDescription();
        String updatePublicationDate = Row.getPlus2DayDate();
        String updateTime = Row.getTimePlus2Hours();

        creatingNewsPage.createNews(category, title, publicationDate, time, description);
        creatingNewsPage.clickSaveButton();

        controlPanelPage.editNewsByTitle(title);
        editingNewsPage.editNewsTab(updateCategory, updateTitle, updatePublicationDate, updateTime, updateDescription);
        editingNewsPage.clickCancelButtonAndWindowModalCancel();

        editingNewsPage.checkNewsEditingPage();

        editingNewsPage.clickCancelButtonAndWindowModalOK();
        controlPanelPage.deleteNewsByTitle(title);
        controlPanelPage.windowModalOKDelete();
        controlPanelPage.refreshControlPanelList();
        controlPanelPage.checkNewsControlPanelIsDelete(title);
    }

}
