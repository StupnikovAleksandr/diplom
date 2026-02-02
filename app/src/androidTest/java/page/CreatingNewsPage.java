package page;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import androidx.test.espresso.ViewInteraction;
import org.hamcrest.Matchers;
import ru.iteco.fmhandroid.R;

public class CreatingNewsPage {

    private final ViewInteraction choiceСategory =
            onView(withId(R.id.news_item_category_text_auto_complete_text_view));

    private final ViewInteraction choiceTitle =
            onView(withId(R.id.news_item_title_text_input_edit_text));
    private final ViewInteraction choicePublisher =
            onView(withId(R.id.news_item_publish_date_text_input_edit_text));
    private final ViewInteraction choiceTime =
            onView(withId(R.id.news_item_publish_time_text_input_edit_text));
    private final ViewInteraction choiceDescription =
            onView(withId(R.id.news_item_description_text_input_edit_text));
    private final ViewInteraction saveButton = onView(withId(R.id.save_button));
    private final ViewInteraction cancel_button = onView(withId(R.id.cancel_button));
    private final ViewInteraction windowModalCancel = onView(withText("Cancel"));
    private final ViewInteraction windowModalOK = onView(withText("OK"));
    private final ViewInteraction CheckTitleCreatingNewsPage1 = onView(withText("Creating"));
    private final ViewInteraction CheckTitleCreatingNewsPage2 = onView(withText("News"));
    private final ViewInteraction errorSavingFailed = onView(withText("Saving failed. Try again later."));
    private final ViewInteraction errorEmptyFailed = onView(withText("Fill empty fields"));
    private View decorView;


    public void manualCreateNews(String category, String title, String publisher, String time, String description) {

        choiceСategory.perform(click())
                .perform(replaceText(category), closeSoftKeyboard());
        choiceTitle.perform(replaceText(title), closeSoftKeyboard());
        choicePublisher.perform(replaceText(publisher), closeSoftKeyboard());
        choiceTime.perform(replaceText(time), closeSoftKeyboard());
        choiceDescription.perform(replaceText(description), closeSoftKeyboard());

    }

    public void createNews(String category, String title, String publisher, String time, String description) {

        choiceСategory.perform(click());

        onData(allOf(is(instanceOf(String.class)), is(category)))
                .inRoot(isPlatformPopup())
                .perform(click());

        choiceTitle.perform(replaceText(title), closeSoftKeyboard());
        choicePublisher.perform(replaceText(publisher), closeSoftKeyboard());
        choiceTime.perform(replaceText(time), closeSoftKeyboard());
        choiceDescription.perform(replaceText(description), closeSoftKeyboard());
    }


    public void clickSaveButton() {
        saveButton
                .check(matches(isDisplayed()))
                .perform(click());
    }

    public void clickCancelButton() {
        cancel_button
                .check(matches(isDisplayed()))
                .perform(click());
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

    public void checkCreatingNewsPage() {
        CheckTitleCreatingNewsPage1
                .check(matches(isDisplayed()));
        CheckTitleCreatingNewsPage2
                .check(matches(isDisplayed()));

    }

    public void checkErrorSavingFailed() {
        errorSavingFailed
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }

    public void checkErrorEmptyFailed() {
        errorEmptyFailed
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }

}




