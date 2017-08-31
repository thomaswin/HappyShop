package com.sephora.happyshop;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.sephora.happyshop.ui.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkArgument;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Thomas Win on 31/8/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CategoryScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> mTasksActivityTestRule =
        new ActivityTestRule<MainActivity>(MainActivity.class) {

            @Override
            protected void beforeActivityLaunched() {
                super.beforeActivityLaunched();

                // TODO prepare screen
            }
        };

    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

    private Matcher<View> withItemText(final String itemText) {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty");
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                return allOf(
                    isDescendantOfA(isAssignableFrom(ListView.class)),
                    withText(itemText)).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA LV with text " + itemText);
            }
        };
    }

    @Test
    public void clickCategory_opensAddCategoryUi() {

        onView(withId(R.id.navigation_home))        // withId(R.id.navigation_home) is a ViewMatcher
            .perform(click())                       // click() is a ViewAction
            .check(matches(isDisplayed()));         // matches(isDisplayed()) is a ViewAssertion

        /*
        onView(withId(R.id.list)).perform(
            RecyclerViewActions.actionOnItemAtPosition(0, CategoryScreenTest.clickChildViewWithId(R.id.categoryView)));
            onView(withId(R.layout.fragment_category)).check(matches(isDisplayed()));
        */
    }
}
