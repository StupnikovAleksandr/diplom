package page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.not;


import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class FilterControlPanelPage {
    private final ViewInteraction category_text_auto_complete_text_view = onView(withId(R.id.news_item_category_text_auto_complete_text_view));
    private final ViewInteraction date_start_text_input = onView(withId(R.id.news_item_publish_date_start_text_input_edit_text));
    private final ViewInteraction date_end_text_input = onView(withId(R.id.news_item_publish_date_end_text_input_edit_text));

    private final ViewInteraction check_box_active = onView(withId(R.id.filter_news_active_material_check_box));
    private final ViewInteraction check_box_inActive = onView(withId(R.id.filter_news_inactive_material_check_box));

    private final ViewInteraction filter_Button = onView(withText("FILTER"));
    private final ViewInteraction cancel_Button = onView(withText("CANCEL"));


    private final ViewInteraction error_datePeriod = onView(withText("Wrong period"));
    private final ViewInteraction window_ok_Button = onView(withText("OK"));


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

    public void editFilterTabRange(String date_start, String date_end) {
        editFilterTab("", date_start, date_end);
    }

    public void clickFilterButton() {
        filter_Button.check(matches(isDisplayed()))
                .perform(click());
    }

    public void clickCancelButton() {
        cancel_Button.check(matches(isDisplayed()))
                .perform(click());
    }

    public void clickAndCheckBoxActive() {
        check_box_active.check(matches(isDisplayed())).perform(click());
        checkBoxActive();
    }

    public void checkBoxActive() {
        check_box_active.check(matches(not(isChecked())));
    }

    public void clickAndCheckBoxInActive() {
        check_box_inActive.check(matches(isDisplayed())).perform(click());
        checkBoxInActive();
    }

    public void checkBoxInActive() {
        check_box_inActive.check(matches(not(isChecked())));
    }


    public void checkErrorWrongAnClickOK() {
        error_datePeriod.check(matches(isDisplayed()));
        window_ok_Button.check(matches(isDisplayed())).perform(click());

    }

}
