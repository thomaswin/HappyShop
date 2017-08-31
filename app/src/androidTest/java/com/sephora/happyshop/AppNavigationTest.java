/*
 *  Copyright 2017, Tun Lin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.sephora.happyshop;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.sephora.happyshop.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AppNavigationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickOnHomeNavigationItem_ShowsCategoryScreen() {

        onView(withId(R.id.navigation_home))        // withId(R.id.navigation_home) is a ViewMatcher
            .perform(click())                       // click() is a ViewAction
            .check(matches(isDisplayed()));         // matches(isDisplayed()) is a ViewAssertion
    }

    @Test
    public void clickOnCartNavigationItem_ShowsCartScreen() {

        onView(withId(R.id.navigation_cart))        // withId(R.id.navigation_cart) is a ViewMatcher
            .perform(click())                       // click() is a ViewAction
            .check(matches(isDisplayed()));         // matches(isDisplayed()) is a ViewAssertion
    }
}
