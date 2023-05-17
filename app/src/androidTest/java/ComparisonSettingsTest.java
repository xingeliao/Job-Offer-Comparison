package edu.gatech.seclass.jobcompare6300;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import edu.gatech.seclass.jobcompare6300.module.dbManager;

@RunWith(AndroidJUnit4.class)
public class ComparisonSettingsTest {
    dbManager dbManager;

    @Rule
    public ActivityScenarioRule<MainActivity> tActivityRule = new ActivityScenarioRule<>(
            MainActivity.class);

    @Test
    public void comparisonSettingsTest1() {
        onView(withId(R.id.buttonAdjustSettings)).perform(click());
        onView(withId(R.id.weightAYS)).perform(clearText(), replaceText(""));
        onView(withId(R.id.weightAYB)).perform(clearText(), replaceText(""));
        onView(withId(R.id.weightLT)).perform(clearText(), replaceText(""));
        onView(withId(R.id.weightCSO)).perform(clearText(), replaceText(""));
        onView(withId(R.id.weightHBP)).perform(clearText(), replaceText(""));
        onView(withId(R.id.weightWF)).perform(clearText(), replaceText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonSavedACS)).perform(click());
        onView(withId(R.id.weightAYS)).check(matches(hasErrorText("Invalid Empty Input")));
        onView(withId(R.id.weightAYB)).check(matches(hasErrorText("Invalid Empty Input")));
        onView(withId(R.id.weightLT)).check(matches(hasErrorText("Invalid Empty Input")));
        onView(withId(R.id.weightCSO)).check(matches(hasErrorText("Invalid Empty Input")));
        onView(withId(R.id.weightHBP)).check(matches(hasErrorText("Invalid Empty Input")));
        onView(withId(R.id.weightWF)).check(matches(hasErrorText("Invalid Empty Input")));
        Espresso.pressBack();
    }

    @Test
    public void comparisonSettingTest2() {
        ArrayList<Float> weights = new ArrayList<Float>(Arrays.asList((float)1.0, (float)1.0, (float)1.0, (float)1.0, (float)1.0, (float)1.0));
        comparisonSettingTest(weights);
    }

    @Test
    public void comparisonSettingTest3() {
        ArrayList<Float> weights = new ArrayList<Float>(Arrays.asList((float)2.0, (float)1.0, (float)3.0, (float)4.0, (float)1.0, (float)5.0));
        comparisonSettingTest(weights);
    }

    public void comparisonSettingTest(ArrayList<Float> listToEnter){
        onView(withId(R.id.buttonAdjustSettings)).perform(click());
        onView(withId(R.id.weightAYS)).perform(clearText(), replaceText(String.valueOf(listToEnter.get(0))));
        onView(withId(R.id.weightAYB)).perform(clearText(), replaceText(String.valueOf(listToEnter.get(1))));
        onView(withId(R.id.weightLT)).perform(clearText(), replaceText(String.valueOf(listToEnter.get(2))));
        onView(withId(R.id.weightCSO)).perform(clearText(), replaceText(String.valueOf(listToEnter.get(3))));
        onView(withId(R.id.weightHBP)).perform(clearText(), replaceText(String.valueOf(listToEnter.get(4))));
        onView(withId(R.id.weightWF)).perform(clearText(), replaceText(String.valueOf(listToEnter.get(5))));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonSavedACS)).perform(click());

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dbManager = new dbManager(context);
        dbManager.open();
        ArrayList<Float> weight = dbManager.getWeightFromDB(1);

        Assert.assertEquals(listToEnter, weight);
        Espresso.pressBack();
    }
}
