package es.ulpgc.eite.da.quiz;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import es.ulpgc.eite.da.quiz.cheat.CheatActivity;
import es.ulpgc.eite.da.quiz.question.QuestionActivity;

@RunWith(RobolectricTestRunner.class)
public class ExtraRobolectricTest {

  ActivityController<QuestionActivity> controller1;
  ActivityController<CheatActivity> controller2;

  TextView question, reply;
  TextView warning, answer;
  Button option1, option2, option3, next, cheat;
  Button yes, no;

  String[] quiz;
  String empty_reply, correct, incorrect;
  String empty_answer, sure;

  @Before
  public void setUp(){

    controller1 = Robolectric.buildActivity(QuestionActivity.class);
    controller2 = Robolectric.buildActivity(CheatActivity.class);

    controller1.create().resume().visible().get();
    updateResources1();

  }

  private void updateResources1() {

    QuestionActivity activity1 = controller1.get();

    quiz = activity1.getResources().getStringArray(R.array.quiz_array);

    empty_reply =activity1.getResources().getString(R.string.empty_reply);
    correct=activity1.getResources().getString(R.string.correct_reply);
    incorrect=activity1.getResources().getString(R.string.incorrect_reply);

    question = activity1.findViewById(R.id.questionTextView);
    reply = activity1.findViewById(R.id.replyTextView);
    option1 = activity1.findViewById(R.id.option1Button);
    option2 = activity1.findViewById(R.id.option2Button);
    option3 = activity1.findViewById(R.id.option3Button);
    next = activity1.findViewById(R.id.nextButton);
    cheat = activity1.findViewById(R.id.cheatButton);
  }


  private void updateResources2() {

    CheatActivity activity2 = controller2.get();

    sure =activity2.getResources().getString(R.string.warning_message);
    empty_answer=activity2.getResources().getString(R.string.empty_answer);

    warning = activity2.findViewById(R.id.warningTextView);
    answer = activity2.findViewById(R.id.answerTextView);
    yes = activity2.findViewById(R.id.yesButton);
    no = activity2.findViewById(R.id.noButton);

  }


  private void rotate1() {

    Bundle bundle = new Bundle();

    controller1
        .saveInstanceState(bundle)
        .pause()
        .stop()
        .destroy();

    controller1 = Robolectric.buildActivity(QuestionActivity.class)
        .create(bundle)
        .restoreInstanceState(bundle)
        .resume()
        .visible();

  }

  private void rotate2() {

    Bundle bundle = new Bundle();

    controller2
        .saveInstanceState(bundle)
        .pause()
        .stop()
        .destroy();

    controller2 = Robolectric.buildActivity(CheatActivity.class)
        .create(bundle)
        .restoreInstanceState(bundle)
        .resume()
        .visible();

  }


  @Test
  public void test() {

    // GIVEN

    cheat.performClick();
    controller2.create().resume().visible().visible().get();
    updateResources2();
    yes.performClick();


    // THEN

    assertThat(
        answer.getText().toString(), equalTo("América del Sur")
    );

    // WHEN

    CheatActivity activity2 = controller2.get();
    activity2.onBackPressed();
    controller1.resume();


    // THEN

    updateResources1();
    assertThat(
        question.getText().toString(),
        equalTo("¿Qué país es el segundo más grande del mundo?")
    );
    assertThat(reply.getText().toString(), equalTo("???"));


    // WHEN

    cheat.performClick();
    controller2.pause().stop().destroy();
    controller2 = Robolectric.buildActivity(CheatActivity.class);
    controller2.create().resume().visible();

    updateResources2();
    yes.performClick();

    // THEN

    assertThat(
        answer.getText().toString(), equalTo("Canadá")
    );

    // WHEN

    activity2 = controller2.get();
    activity2.onBackPressed();
    controller1.resume();


    // THEN

    updateResources1();
    assertThat(
        question.getText().toString(),
        equalTo("¿Cómo se llama la tercera isla más grande del mundo?")
    );
    assertThat(reply.getText().toString(), equalTo("???"));


    // WHEN

    cheat.performClick();
    controller2.pause().stop().destroy();
    controller2 = Robolectric.buildActivity(CheatActivity.class);
    controller2.create().resume().visible();

    // THEN

    updateResources2();
    assertThat(
        answer.getText().toString(),
        equalTo("???")
    );

    // WHEN

    rotate1();
    rotate2();

    // THEN

    assertThat(answer.getText().toString(), equalTo("???"));

    // WHEN

    updateResources2();
    yes.performClick();


    // THEN

    updateResources2();
    assertThat(answer.getText().toString(), equalTo("Borneo"));


    // WHEN

    rotate1();
    rotate2();


    // THEN

    updateResources2();
    assertThat(answer.getText().toString(), equalTo("Borneo"));


    // WHEN

    activity2 = controller2.get();
    activity2.onBackPressed();
    controller1.resume();


    // THEN

    updateResources1();
    assertThat(
        question.getText().toString(),
        equalTo("¿Qué porcentaje de la superficie de la Tierra no es agua?")
    );
    assertThat(reply.getText().toString(), equalTo("???"));

  }


}
