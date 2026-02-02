package page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import android.view.View;
import android.widget.Filter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;

import data.Helper;
import ru.iteco.fmhandroid.R;

public class NewsPage {
    private final ViewInteraction sort_news_material_button = onView(withId(R.id.sort_news_material_button));
    private final ViewInteraction filter_news_material_button = onView(withId(R.id.filter_news_material_button));
    private final ViewInteraction edit_news_material_button = onView(withId(R.id.edit_news_material_button));

    private final ViewInteraction newsListSwipeRefresh = onView(withId(R.id.news_list_swipe_refresh));

    public void checkNewsTab() {
        onView(isRoot()).perform(Helper.waitDisplayed(R.id.sort_news_material_button, 5_000));
        sort_news_material_button.check(matches(isDisplayed()));
    }

    public ControlPanelPage buttonEditNews() {
        onView(isRoot()).perform(Helper.waitDisplayed(R.id.edit_news_material_button, 5_000));
        edit_news_material_button.perform(click());
        return new ControlPanelPage();

    }

    public void clickSortButtonNewsTab() {
        sort_news_material_button.check(matches(isDisplayed()))
                .perform(click());
    }

    public FilterNewsPage clickFilterButtonNewsTab() {
        filter_news_material_button.check(matches(isDisplayed()))
                .perform(click());
        return new FilterNewsPage();
    }


    public void checkNewsOrderNewsTab(String firstTitle, String secondTitle) {

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
            View titleView = child.findViewById(R.id.news_item_title_text_view);
            if (titleView instanceof TextView) {
                String text = ((TextView) titleView).getText().toString();
                if (text.equals(title)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void newsListSwipeRefresh() {
        newsListSwipeRefresh.perform(swipeDown());
    }

}




