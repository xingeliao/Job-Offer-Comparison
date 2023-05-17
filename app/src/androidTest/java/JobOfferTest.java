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
public class JobOfferTest {
    edu.gatech.seclass.jobcompare6300.module.dbManager dbManager;

    @Rule
    public ActivityScenarioRule<MainActivity> tActivityRule = new ActivityScenarioRule<>(
            MainActivity.class);

    @Test
    public void enterJobOfferTest1() {
        ArrayList<String> listToEnter = new ArrayList<String>(Arrays.asList("Psychiatrist", "III Hospital", "Detroit,Michigan", "20000", "10000", "26", "15000", "2400", "3000"));
        currentJobOfferTest(listToEnter);
    }

    @Test
    public void enterJobOfferTest2() {
        ArrayList<String> listToEnter = new ArrayList<String>(Arrays.asList("Neurologist", "Gran Hospital", "Baltimore,Maryland", "40000", "5000", "50", "8000", "2400", "2000"));
        currentJobOfferTest(listToEnter);
    }

    public void currentJobOfferTest(ArrayList<String> listToEnter) {
        onView(withId(R.id.buttonEnterOffer)).perform(click());
        onView(withId(R.id.idJobTitle)).perform(clearText(), replaceText(listToEnter.get(0)));
        onView(withId(R.id.idJobCompany)).perform(clearText(), replaceText(listToEnter.get(1)));
        onView(withId(R.id.idJobCity)).perform(clearText(), replaceText(listToEnter.get(2).split(",")[0]));
        onView(withId(R.id.idJobState)).perform(clearText(), replaceText(listToEnter.get(2).split(",")[1]));
        onView(withId(R.id.idJobAYS)).perform(clearText(), replaceText(listToEnter.get(3)));
        onView(withId(R.id.idJobAYB)).perform(clearText(), replaceText(listToEnter.get(4)));
        onView(withId(R.id.idJobLT)).perform(clearText(), replaceText(listToEnter.get(5)));
        onView(withId(R.id.idJobCSO)).perform(clearText(), replaceText(listToEnter.get(6)));
        onView(withId(R.id.idJobHBP)).perform(clearText(), replaceText(listToEnter.get(7)));
        onView(withId(R.id.idJobWF)).perform(clearText(), replaceText(listToEnter.get(8)));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.idButtonSaveJobOffer)).perform(click());

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dbManager = new dbManager(context);
        dbManager.open();
        int a = dbManager.getJobNumberFromDB();
        ArrayList<String> list = dbManager.getJobFromDB(a);
        Assert.assertEquals(listToEnter, list);
        Espresso.pressBack();
    }
    @Test
    public void jobOfferErrorTest1() {
        onView(withId(R.id.buttonEnterOffer)).perform(click());
        onView(withId(R.id.idJobAYS)).perform(clearText(), replaceText("30000"));
        onView(withId(R.id.idJobHBP)).perform(clearText(), replaceText("4800"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.idButtonSaveJobOffer)).perform(click());
        onView(withId(R.id.idJobHBP)).check(matches(hasErrorText("Invalid Input")));
        Espresso.pressBack();
    }

    @Test
    public void jobOfferErrorTest2() {
        onView(withId(R.id.buttonEnterOffer)).perform(click());
        onView(withId(R.id.idJobWF)).perform(clearText(), replaceText(""));
        onView(withId(R.id.idJobHBP)).perform(clearText(), replaceText(""));
        onView(withId(R.id.idJobCSO)).perform(clearText(), replaceText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.idButtonSaveJobOffer)).perform(click());
        onView(withId(R.id.idJobWF)).check(matches(hasErrorText("Invalid Empty Input")));
        onView(withId(R.id.idJobHBP)).check(matches(hasErrorText("Invalid Empty Input")));
        onView(withId(R.id.idJobCSO)).check(matches(hasErrorText("Invalid Empty Input")));
        Espresso.pressBack();
    }
}
