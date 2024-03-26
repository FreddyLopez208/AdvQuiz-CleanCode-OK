package es.ulpgc.eite.da.quiz.cheat;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import es.ulpgc.eite.da.quiz.R;

public class CheatActivity
    extends AppCompatActivity implements CheatContract.View {

  public static String TAG = CheatActivity.class.getSimpleName();

  private CheatContract.Presenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);

    getSupportActionBar().setTitle(R.string.cheat_title);

    ((TextView) findViewById(R.id.noButton)).setText(R.string.no_button);
    ((TextView) findViewById(R.id.yesButton)).setText(R.string.yes_button);

    ((TextView) findViewById(R.id.warningTextView))
        .setText(R.string.warning_message);

    // do the setup
    CheatScreen.configure(this);

    if(savedInstanceState == null) {
      presenter.onStart();

    } else {
      presenter.onRestart();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();

    // load the answer
    presenter.onResume();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();

    presenter.onBackPressed();
  }


  @Override
  public void onFinish() {
    finish();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    presenter.onDestroy();
  }

  @Override
  public void displayAnswer(CheatViewModel viewModel) {
    //Log.e(TAG, "displayAnswer()");

    // deal with the answer
    ((TextView) findViewById(R.id.answerTextView)).setText(viewModel.answer);
    findViewById(R.id.noButton).setEnabled(viewModel.answerEnabled);
    findViewById(R.id.yesButton).setEnabled(viewModel.answerEnabled);
  }

  @Override
  public void resetAnswer() {
    ((TextView) findViewById(R.id.answerTextView))
        .setText(R.string.empty_answer);
  }


  public void onWarningButtonClicked(View view) {

    int option = Integer.valueOf((String) view.getTag());
    presenter.onWarningButtonClicked(option);
  }

  @Override
  public void injectPresenter(CheatContract.Presenter presenter) {
    this.presenter = presenter;
  }

}
