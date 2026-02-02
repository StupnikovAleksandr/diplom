package page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.not;

import androidx.test.espresso.ViewInteraction;

import data.Helper;
import ru.iteco.fmhandroid.R;

public class MainPage {
    private final ViewInteraction expand_material_button = onView(withId(R.id.expand_material_button));
    private final ViewInteraction all_news_text_view = onView(withId(R.id.all_news_text_view));
    private final ViewInteraction news_list_recycler_view = onView(withId(R.id.news_list_recycler_view));


    public void checkMainPage() {
        onView(isRoot()).perform(Helper.waitDisplayed(R.id.all_news_text_view, 5_000));
        all_news_text_view.check(matches(isDisplayed()));
    }

    public NewsPage goToTabAllNews() {
        onView(isRoot()).perform(Helper.waitDisplayed(R.id.all_news_text_view, 5_000));
        all_news_text_view.perform(click());
        return new NewsPage();
    }

    public void intentUnfolding() {
        onView(isRoot()).perform(Helper.waitDisplayed(R.id.expand_material_button, 5_000));

        expand_material_button
                .perform(click());

    }

    public void checkFolding() {
        onView(isRoot()).perform(Helper.waitDisplayed(R.id.news_list_recycler_view, 5_000));
        news_list_recycler_view
                .check(matches(isDisplayed()));

    }

    public void checkUnfolding() {
        news_list_recycler_view.check(matches(not(isDisplayed())));
    }

    public void checkNewsMainPage(String title, String description) {
        onView(withText(title)).perform(click());

        onView(withText(description)).check(matches(isDisplayed()));
    }

}





