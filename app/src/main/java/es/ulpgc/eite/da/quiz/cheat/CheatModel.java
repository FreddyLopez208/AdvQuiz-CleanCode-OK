package es.ulpgc.eite.da.quiz.cheat;

public class CheatModel implements CheatContract.Model {

  public static String TAG = CheatModel.class.getSimpleName();

  private String answer;

  public CheatModel() {

  }

  @Override
  public String getAnswer() {
    return answer;
  }

  @Override
  public void setAnswer(String answer) {
    this.answer=answer;
  }
}
