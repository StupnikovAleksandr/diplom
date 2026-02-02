package ru.iteco.fmhandroid.ui;
import android.view.View;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
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
import page.NavigationPage;

@Epic("Раздел about")
@RunWith(AllureAndroidJUnit4.class)
public class AboutTest {
    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authorizationPage = new AuthorizationPage();
    AboutPage aboutPage = new AboutPage();
    NavigationPage navigationPage = new NavigationPage();


    private View decorView;

    @Before
    public void setUp() {
        Intents.init();
        mActivityScenarioRule.getScenario().onActivity(activity -> decorView = activity.getWindow().getDecorView());
        try {
            authorizationPage.checkIsDisplayed();
        } catch (Exception e) {
            navigationPage.openMenu();
            navigationPage.logOut();
        }
    }
    @After
    public void upDown() {
        Intents.release();
    }

    @Story("ТК-60 Открытие ссыли политики конфиденциальности")
    @Test
    public void intentPolicyTest() {
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        navigationPage.goOverTabAbout();
        aboutPage.clickUrlPolicy();

        Allure.step("Проверка интента на сылку политики конфиденциальности");
        aboutPage.intentPrivacyPolicy();

    }
    @Story("ТК- 61 Открытие ссыли  условие эксплотации")
    @Test
    public void intentTermsTest() {
        authorizationPage.authorization(Row.ValidLogin, Row.ValidPassword);
        navigationPage.goOverTabAbout();
        aboutPage.clickUrlTerms();

        Allure.step("Проверка интента на сылку условие эксплотации");
        aboutPage.intentTermsOfUse();

    }
}
