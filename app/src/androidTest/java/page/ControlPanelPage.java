package page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static data.Helper.clickChildViewWithId;
import static data.Helper.hasItemsInOrder;
import static data.Helper.waitFor;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingRootException;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;

import junit.framework.AssertionFailedError;

import org.hamcrest.Matcher;

import java.util.EnumSet;

import data.Helper;
import ru.iteco.fmhandroid.R;

public class ControlPanelPage {

    private final ViewInteraction sort_news_material_button = onView(withId(R.id.sort_news_material_button));
    private final ViewInteraction filter_news_material_button = onView(withId(R.id.filter_news_material_button));
    private final ViewInteraction add_news_image_view = onView(withId(R.id.add_news_image_view));
    private final ViewInteraction windowModalCancelDelete = onView(withText("Cancel"));


    public CreatingNewsPage goOverCreatingNews() {
        onView(isRoot()).perform(Helper.waitDisplayed(R.id.add_news_image_view, 5_000));
        add_news_image_view
                .perform(click());
        return new CreatingNewsPage();
    }

    public FilterControlPanelPage goOverFilter() {
        onView(isRoot()).perform(Helper.waitDisplayed(R.id.filter_news_material_button, 5_000));
        filter_news_material_button
                .perform(click());
        return new FilterControlPanelPage();
    }

    public void checkControlTab() {
        onView(isRoot()).perform(Helper.waitDisplayed(R.id.add_news_image_view, 5_000));
        add_news_image_view.check(matches(isDisplayed()));
    }


    public void clickSortButton() {
        sort_news_material_button.check(matches(isDisplayed()))
                .perform(click());
    }

    public void windowModalCancelDelete() {
        windowModalCancelDelete.check(matches(isDisplayed()))
                .perform(click());

    }

    public void windowModalOKDelete() {
        try {
            onView(isRoot()).perform(waitFor(1500));
            onView(withText("OK")).check(matches(isDisplayed())).perform(click());
        } catch (NoMatchingViewException ignored) {
            // Кнопки нет — ок
        }
    }

    public void deleteNewsByTitle(String title) {


        onView(withId(R.id.news_list_recycler_view))
                .perform(
                        actionOnItem(
                                hasDescendant(withText(title)),
                                clickChildViewWithId(R.id.delete_news_item_image_view)
                        )
                );

    }

    public EditingNewsPage editNewsByTitle(String title) {


        onView(withId(R.id.news_list_recycler_view))
                .perform(
                        actionOnItem(
                                hasDescendant(withText(title)),
                                clickChildViewWithId(R.id.edit_news_item_image_view)
                        )
                );
        return new EditingNewsPage();

    }


    public void refreshControlPanelList() {
        try {
            onView(isRoot()).perform(Helper.waitDisplayed(R.id.news_control_panel_swipe_to_refresh, 5_000));
            onView(withId(R.id.news_control_panel_swipe_to_refresh))
                    .perform(swipeDown());
        } catch (NoMatchingViewException e) {
        }
    }

    public void checkNewsControlPanelIsDelete(String title) {
        try {
            onView(withText(title)).check(doesNotExist());
        } catch (NoMatchingViewException e) {
        }
    }


    public void checkNewsDoesNotExist(String title, String description) {
        onView(allOf(
                withText(title),
                isDescendantOfA(hasDescendant(withText(description)))
        )).check(doesNotExist());
    }


    public void checkEditAndCreateNewsByTitle(String updateTitle, String updateDescription, String publicationDate, String actualStatus) {

        onView(isRoot()).perform(Helper.waitDisplayed(R.id.news_item_material_card_view, 10_000));
        onView(allOf(
                withId(R.id.news_item_material_card_view),
                hasDescendant(allOf(
                        withId(R.id.news_item_title_text_view),
                        withText(updateTitle),
                        isDisplayed()
                )),
                hasDescendant(allOf(
                        withId(R.id.news_item_publication_date_text_view),
                        withText(publicationDate),
                        isDisplayed()
                )),
                hasDescendant(allOf(
                        withId(R.id.news_item_published_text_view),
                        withText(actualStatus),
                        isDisplayed()
                )),
                isDisplayed()
        )).check(matches(isDisplayed()));

        onView(allOf(
                withId(R.id.news_item_material_card_view),
                hasDescendant(allOf(
                        withId(R.id.news_item_title_text_view),
                        withText(updateTitle)
                )),
                isDisplayed()
        )).perform(click());

        onView(allOf(
                withId(R.id.news_item_description_text_view),
                withText(containsString(updateDescription)),
                isDisplayed()
        )).check(matches(isDisplayed()));
    }

    public void expandNewsByTitleAndPublicationDate(String title, String publicationDate) {
        onView(
                allOf(
                        withId(R.id.news_item_material_card_view),
                        hasDescendant(allOf(
                                withId(R.id.news_item_title_text_view),
                                withText(title)
                        )),
                        hasDescendant(allOf(
                                withId(R.id.news_item_publication_date_text_view),
                                withText(publicationDate)
                        ))
                )
        ).perform(click());
    }

    public void checkUnfoldingNews(String title, String description) {
        onView(allOf(
                withId(R.id.news_item_material_card_view),
                hasDescendant(allOf(
                        withId(R.id.news_item_title_text_view),
                        withText(title)
                )),
                hasDescendant(allOf(
                        withId(R.id.news_item_description_text_view),
                        withText(containsString(description)),
                        isDisplayed()
                ))
        )).check(matches(isDisplayed()));
    }


    public void checkUnfolding(String description) {
        onView(withText(description)).check(matches(not(isDisplayed())));
    }

    private final ViewInteraction recyclerView = onView(withId(R.id.news_list_recycler_view));

    public void scrollNews(String title) {
        recyclerView.perform(scrollTo(hasDescendant(withText(title))));
    }

    public void checkNewsOrder(String firstTitle, String secondTitle) {
        scrollNews(secondTitle);

        onView(withText(firstTitle)).check(matches(isDisplayed()));
        onView(withText(secondTitle)).check(matches(isDisplayed()));

        onView(withId(R.id.news_list_recycler_view))
                .check((view, e) -> {
                    if (e != null) throw e;

                    RecyclerView rv = (RecyclerView) view;

                    int firstPos = findPosition(rv, firstTitle);
                    int secondPos = findPosition(rv, secondTitle);

                    if (firstPos == -1 || secondPos == -1 || firstPos >= secondPos) {
                        throw new AssertionError("Wrong order or news not found");
                    }
                });
    }

    private int findPosition(RecyclerView recyclerView, String title) {
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View child = recyclerView.getChildAt(i);
            View titleView = child.findViewById(R.id.news_item_title_text_view); // Используйте правильный ID
            if (titleView instanceof TextView) {
                String text = ((TextView) titleView).getText().toString();
                if (text.equals(title)) {
                    return i;
                }
            }
        }
        return -1;
    }
}










