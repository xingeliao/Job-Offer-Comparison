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
public class CurrentJobTest {
    dbManager dbManager;

    @Rule
    public ActivityScenarioRule<MainActivity> tActivityRule = new ActivityScenarioRule<>(
            MainActivity.class);

    @Test
    public void currentJobTest1() {
        ArrayList<String> listToEnter = new ArrayList<String>(Arrays.asList("AnyText", "AnyText", "AnyText,AnyText", "1000", "2", "3", "15", "10", "30"));
        currentJobTest(listToEnter);
    }

    @Test
    public void currentJobTest2() {
        ArrayList<String> listToEnter = new ArrayList<String>(Arrays.asList("Teacher", "xxSchool", "Austin,Texas", "1000", "2", "3", "15", "15", "30"));
        currentJobTest(listToEnter);
    }

    @Test
    public void currentJobTest3() {
        ArrayList<String> listToEnter = new ArrayList<String>(Arrays.asList("Accountant", "AAA Company", "Miami,Florida", "1000", "2", "3", "15", "10", "0"));
        currentJobTest(listToEnter);
    }

    @Test
    public void currentJobTest4() {
        ArrayList<String> listToEnter = new ArrayList<String>(Arrays.asList("Baker", "GE Panaderia", "San Antonio,Texas", "1000", "2", "3", "15", "10", "0"));
        currentJobTest(listToEnter);
    }

    public void currentJobTest(ArrayList<String> listToEnter){
        onView(withId(R.id.buttonEditCurrentJob)).perform(click());
        onView(withId(R.id.idCurrTitle)).perform(clearText(), replaceText(listToEnter.get(0)));
        onView(withId(R.id.idCurrCompany)).perform(clearText(), replaceText(listToEnter.get(1)));
        onView(withId(R.id.idCurrCity)).perform(clearText(), replaceText(listToEnter.get(2).split(",")[0]));
        onView(withId(R.id.idCurrState)).perform(clearText(), replaceText(listToEnter.get(2).split(",")[1]));
        onView(withId(R.id.idCurrAYS)).perform(clearText(), replaceText(listToEnter.get(3)));
        onView(withId(R.id.idCurrAYB)).perform(clearText(), replaceText(listToEnter.get(4)));
        onView(withId(R.id.idCurrLT)).perform(clearText(), replaceText(listToEnter.get(5)));
        onView(withId(R.id.idCurrCSO)).perform(clearText(), replaceText(listToEnter.get(6)));
        onView(withId(R.id.idCurrHBP)).perform(clearText(), replaceText(listToEnter.get(7)));
        onView(withId(R.id.idCurrWF)).perform(clearText(), replaceText(listToEnter.get(8)));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.idSaveButton)).perform(click());

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dbManager = new dbManager(context);
        dbManager.open();
        ArrayList<String> list = dbManager.getJobFromDB(1);
        Assert.assertEquals(listToEnter, list);
        Espresso.pressBack();
    }

    @Test
    public void currentJobErrorTest1() {
        onView(withId(R.id.buttonEditCurrentJob)).perform(click());
        onView(withId(R.id.idCurrTitle)).perform(clearText(), replaceText(""));
        onView(withId(R.id.idCurrCompany)).perform(clearText(), replaceText(""));
        onView(withId(R.id.idCurrState)).perform(clearText(), replaceText(""));
        onView(withId(R.id.idCurrCity)).perform(clearText(), replaceText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.idSaveButton)).perform(click());
        onView(withId(R.id.idCurrTitle)).check(matches(hasErrorText("Invalid Empty Input")));
        onView(withId(R.id.idCurrCompany)).check(matches(hasErrorText("Invalid Empty Input")));
        onView(withId(R.id.idCurrState)).check(matches(hasErrorText("Invalid Empty Input")));
        onView(withId(R.id.idCurrCity)).check(matches(hasErrorText("Invalid Empty Input")));
        Espresso.pressBack();
    }

    @Test
    public void currentJobErrorTest2() {
        onView(withId(R.id.buttonEditCurrentJob)).perform(click());
        onView(withId(R.id.idCurrAYS)).perform(clearText(), replaceText("30000"));
        onView(withId(R.id.idCurrHBP)).perform(clearText(), replaceText("4800"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.idSaveButton)).perform(click());
        onView(withId(R.id.idCurrHBP)).check(matches(hasErrorText("Invalid Input")));
        Espresso.pressBack();
    }

    @Test
    public void currentJobErrorTest3() {
        onView(withId(R.id.buttonEditCurrentJob)).perform(click());
        onView(withId(R.id.idCurrWF)).perform(clearText(), replaceText("5001"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.idSaveButton)).perform(click());
        onView(withId(R.id.idCurrWF)).check(matches(hasErrorText("Invalid Input")));
        Espresso.pressBack();
    }
}
