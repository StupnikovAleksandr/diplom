package page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.not;


import android.view.View;

import androidx.test.espresso.ViewInteraction;

import org.hamcrest.Matchers;

import ru.iteco.fmhandroid.R;

public class EditingNewsPage {


    private final ViewInteraction firstCheck_NewsEditing_tab = onView(withId(R.id.custom_app_bar_title_text_view));
    private final ViewInteraction secondCheck_NewsEditing_tab = onView(withId(R.id.custom_app_bar_sub_title_text_view));

    private final ViewInteraction category_text_auto_complete_text_view = onView(withId(R.id.news_item_category_text_auto_complete_text_view));
    private final ViewInteraction title_text_auto_complete_text_view = onView(withId(R.id.news_item_title_text_input_edit_text));
    private final ViewInteraction publish_date_text_input = onView(withId(R.id.news_item_publish_date_text_input_edit_text));
    private final ViewInteraction publish_time_text_input = onView(withId(R.id.news_item_publish_time_text_input_edit_text));
    private final ViewInteraction description_text_input = onView(withId(R.id.news_item_description_text_input_edit_text));


    private final ViewInteraction chekBoxActive = onView(withId(R.id.switcher));


    private final ViewInteraction cancel_Button = onView(withText("CANCEL"));
    private final ViewInteraction save_Button = onView(withText("SAVE"));

    private View decorView;
    private final ViewInteraction errorEmptyFailed = onView(withText("Fill empty fields"));
    private final ViewInteraction errorSavingFailed = onView(withText("Saving failed. Try again later."));

    private final ViewInteraction windowModalCancel = onView(withText("Cancel"));
    private final ViewInteraction windowModalOK = onView(withText("OK"));


    public void checkNewsEditingPage() {
        firstCheck_NewsEditing_tab.check(matches(isDisplayed()));
        secondCheck_NewsEditing_tab.check(matches(isDisplayed()));
    }

    public void editNewsTab(String category, String title, String publish_date, String time, String description) {
        category_text_auto_complete_text_view.perform(replaceText(category), closeSoftKeyboard());
        title_text_auto_complete_text_view.perform(replaceText(title), closeSoftKeyboard());
        publish_date_text_input.perform(replaceText(publish_date), closeSoftKeyboard());
        publish_time_text_input.perform(replaceText(time), closeSoftKeyboard());
        description_text_input.perform(replaceText(description), closeSoftKeyboard());
    }


    public void clickAndCheckUncheckedCheckBox() {
        chekBoxActive.check(matches(isDisplayed())).perform(click());
        checkCheckBoxIsUnchecked();
    }

    public void checkCheckBoxIsUnchecked() {
        chekBoxActive.check(matches(not(isChecked())));
    }

    public void checkCheckBoxIsChecked() {
        chekBoxActive.check(matches(isChecked()));
    }


    public void checkErrorEmptyFields() {
        errorEmptyFailed
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }


    public void checkErrorSavingFailed() {
        errorSavingFailed
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }


    public void clickSaveButton() {
        save_Button.check(matches(isDisplayed())).perform(click());
    }

    public void clickCancelButton() {
        cancel_Button.check(matches(isDisplayed())).perform(click());
    }

    public void clickCancelButtonAndWindowModalCancel() {
        clickCancelButton();
        windowModalCancel.check(matches(isDisplayed()))
                .perform(click());
    }

    public void clickCancelButtonAndWindowModalOK() {
        clickCancelButton();
        windowModalOK.check(matches(isDisplayed()))
                .perform(click());
    }


}
