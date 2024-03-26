package es.ulpgc.eite.da.quiz.cheat;

import java.lang.ref.WeakReference;

public interface CheatContract {

  interface View {
    void injectPresenter(Presenter presenter);

    void displayAnswer(CheatViewModel viewModel);
    void resetAnswer();
    void onFinish();
  }

  interface Presenter {
    void injectView(WeakReference<View> view);
    void injectModel(Model model);

    void onResume();
    void onStart();
    void onRestart();
    void onDestroy();
    void onBackPressed();
    void onWarningButtonClicked(int option);
  }

  interface Model {
    String getAnswer();
    void setAnswer(String answer);
  }

}
