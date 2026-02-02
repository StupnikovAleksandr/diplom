package page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

import androidx.test.espresso.ViewInteraction;

import data.Helper;
import ru.iteco.fmhandroid.R;


public class FilterNewsPage {

    private final ViewInteraction check_filter_news_tab = onView(withId(R.id.filter_news_title_text_view));
    private final ViewInteraction category_text_auto_complete_text_view = onView(withId(R.id.news_item_category_text_auto_complete_text_view));
    private final ViewInteraction date_start_text_input = onView(withId(R.id.news_item_publish_date_start_text_input_edit_text));
    private final ViewInteraction date_end_text_input = onView(withId(R.id.news_item_publish_date_end_text_input_edit_text));
    private final ViewInteraction filter_Button = onView(withText("FILTER"));
    private final ViewInteraction error_datePeriod = onView(withText("Wrong period"));
    private final ViewInteraction cancel_Button = onView(withText("CANCEL"));
    private final ViewInteraction window_ok_Button = onView(withText("OK"));


    public void checkNewsTab() {
        onView(isRoot()).perform(Helper.waitDisplayed(R.id.filter_news_title_text_view, 5_000));
        check_filter_news_tab.check(matches(isDisplayed()));
    }

    public void editFilterTab(String category, String date_start, String date_end) {
        category_text_auto_complete_text_view.perform(replaceText(category), closeSoftKeyboard());
        date_start_text_input.perform(replaceText(date_start), closeSoftKeyboard());
        date_end_text_input.perform(replaceText(date_end), closeSoftKeyboard());
    }

    public void editFilterTab(String category) {
        editFilterTab(category, "", "");
    }

    public void editFilterTab(String category, String date_start) {
        editFilterTab(category, date_start, "");
    }

    public void editFilterCategoryAndDateEnd(String category, String date_end) {
        editFilterTab(category, "", date_end);
    }

    public void editFilterTabStart(String date_start) {
        editFilterTab("", date_start, "");
    }

    public void editFilterTabRange(String date_start, String date_end) {
        editFilterTab("", date_start, date_end);
    }

    public void editFilterTabEnd(String date_end) {
        editFilterTab("", "", date_end);
    }


    public void clickFilterButton() {
        filter_Button.check(matches(isDisplayed()))
                .perform(click());
    }

    public void clickCancelButton() {
        cancel_Button.check(matches(isDisplayed()))
                .perform(click());
    }

    public void checkErrorWrongAnClickOK() {
        error_datePeriod.check(matches(isDisplayed()));
        window_ok_Button.check(matches(isDisplayed())).perform(click());

    }


    public void checkEditFilterTab(String title, String description, String date) {
        onView(isRoot()).perform(Helper.waitDisplayed(R.id.news_item_material_card_view, 10_000));

        onView(allOf(
                withId(R.id.news_item_material_card_view),
                hasDescendant(allOf(
                        withId(R.id.news_item_title_text_view),
                        withText(title),
                        isDisplayed()
                )),
                hasDescendant(allOf(
                        withId(R.id.news_item_date_text_view),
                        withText(date),
                        isDisplayed()
                )),
                isDisplayed()
        )).check(matches(isDisplayed()));

        onView(allOf(
                withId(R.id.news_item_material_card_view),
                hasDescendant(withText(title)),
                isDisplayed()
        )).perform(click());

        onView(allOf(
                withId(R.id.news_item_description_text_view),
                withText(containsString(description)),
                isDisplayed()
        )).check(matches(isDisplayed()));
    }
}
