package es.ulpgc.eite.da.quiz;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.ulpgc.eite.da.quiz.question.QuestionActivity;

@SuppressWarnings("ALL")
@LargeTest
@RunWith(AndroidJUnit4.class)
public class ExampleEspressoTest {

  @Rule
  public ActivityTestRule<QuestionActivity> activityTestRule =
      new ActivityTestRule<>(QuestionActivity.class);



  private void rotate() {

    Context context = ApplicationProvider.getApplicationContext();
    int orientation = context.getResources().getConfiguration().orientation;
    Activity activity = activityTestRule.getActivity();

    if(orientation  == Configuration.ORIENTATION_PORTRAIT) {
      activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    } else {
      activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
  }


  @Test
  public void instrumentedTest() throws Exception {


    // GIVEN

    ViewInteraction button = onView(withId(R.id.cheatButton));
    button.perform(click());

    Thread.sleep(700);

    ViewInteraction button2 = onView(withId(R.id.yesButton));
    button2.perform(click());


    pressBack();

    Thread.sleep(700);


    ViewInteraction button5 = onView(withId(R.id.cheatButton));
    button5.perform(click());


    // WHEN


    rotate();
    //rotate();

    ViewInteraction button6 = onView(withId(R.id.yesButton));
    button6.perform(click());


    // THEN

    ViewInteraction textView71 = onView(withId(R.id.answerTextView));
    textView71.check(matches(withText("Canadá"))); // error = ""

    // WHEN

    rotate();
    //rotate();

    // THEN

    ViewInteraction textView7 = onView(withId(R.id.answerTextView));
    textView7.check(matches(withText("Canadá")));

    /*

    // WHEN

    pressBack();

    Thread.sleep(700);

    // THEN

    ViewInteraction textView3 = onView(withId(R.id.questionTextView));
    textView3.check(matches(
        withText("¿Cómo se llama la tercera isla más grande del mundo?")
    ));
    ViewInteraction textView4 = onView(withId(R.id.replyTextView));
    textView4.check(matches(withText("???")));

    */

  }


}
