package page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.intent.matcher.UriMatchers.hasHost;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;


import android.content.Intent;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class AboutPage {

    private final ViewInteraction urlPolicy = onView(withId(R.id.about_privacy_policy_value_text_view));

    private final ViewInteraction urlTermsOfUse = onView(withId(R.id.about_terms_of_use_value_text_view));


    public void checkOnAboutTab() {
        Allure.step("Проверка отображения раздела 'О приложении'");
        urlPolicy
                .check(matches(isDisplayed()));
        urlPolicy
                .check(matches(withText("https://vhospice.org/#/privacy-policy/")));


    }


    public void clickUrlPolicy() {
        Allure.step("Клик по ссылке политики конфиденциальности");
        urlPolicy
                .perform(click());
    }

    public void clickUrlTerms() {
        Allure.step("Клик по ссылке условий использования");
        urlTermsOfUse
                .perform(click());
    }

    public void intentPrivacyPolicy() {
        Allure.step("Проверка интента на открытие политики конфиденциальности");
        intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(hasHost("vhospice.org"))
        ));
    }

    public void intentTermsOfUse() {
        Allure.step("Проверка интента на открытие условий использования");
        intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(hasHost("vhospice.org"))
        ));
    }
}
