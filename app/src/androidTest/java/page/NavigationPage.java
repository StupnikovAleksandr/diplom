package page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;

import data.Helper;
import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class NavigationPage {
    private final ViewInteraction main_menu_button = onView(withId(R.id.main_menu_image_button));
    private final ViewInteraction our_mission_button = onView(withId(R.id.our_mission_image_button));
    private final ViewInteraction menuButton = onView(withId(R.id.authorization_image_button));
    private final ViewInteraction logOutButton = onView(withText("Log out"));

    private final ViewInteraction newsButton = onView(withText("News"));
    private final ViewInteraction aboutButton = onView(withText("About"));
    private final ViewInteraction mainButton = onView(withText("Main"));


    public void openMenu() {
        Allure.step("Открытие бокового меню");
        menuButton
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void logOut() {
        Allure.step("Выход из аккаунта");
        logOutButton
                .check(matches(isDisplayed()))
                .perform(click());

    }

    public NewsPage goOverTabNews() {
        Allure.step("Переход в раздел 'News'");
        onView(isRoot()).perform(Helper.waitDisplayed(R.id.main_menu_image_button, 5_000));
        main_menu_button
                .perform(click());
        newsButton
                .check(matches(isDisplayed()))
                .perform(click());
        return new NewsPage();
    }

    public AboutPage goOverTabAbout() {
        Allure.step("Переход в раздел 'About'");
        onView(isRoot()).perform(Helper.waitDisplayed(R.id.main_menu_image_button, 5_000));
        main_menu_button
                .perform(click());
        aboutButton
                .check(matches(isDisplayed()))
                .perform(click());
        return new AboutPage();
    }

    public LoveIsAllPage goOverTabLoveIsAll() {
        Allure.step("Переход в раздел 'Love is All'");
        onView(isRoot()).perform(Helper.waitDisplayed(R.id.main_menu_image_button, 5_000));
        our_mission_button
                .perform(click());
        return new LoveIsAllPage();

    }

    public MainPage goOverTabMain() {
        Allure.step("Переход в раздел 'Main'");
        onView(isRoot()).perform(Helper.waitDisplayed(R.id.main_menu_image_button, 5_000));
        main_menu_button.perform(click());
        mainButton.check(matches(isDisplayed()))
                .perform(click());
        return new MainPage();
    }

}
