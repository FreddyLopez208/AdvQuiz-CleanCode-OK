package es.ulpgc.eite.da.quiz.question;

public class QuestionModel implements QuestionContract.Model {

  public static String TAG = QuestionModel.class.getSimpleName();

  private final String[] quizArray;
  private int quizIndex;

  public QuestionModel(String[] quizArray) {
    this.quizArray=quizArray;
  }

  @Override
  public String getQuestion() {
    return quizArray[quizIndex];
  }

  @Override
  public String getOption1() {
    return quizArray[quizIndex+1];
  }

  @Override
  public String getOption2() {
    return quizArray[quizIndex+2];
  }

  @Override
  public String getOption3() {
    return quizArray[quizIndex+3];
  }

  @Override
  public boolean isCorrectOption(int option) {
    int answer = Integer.valueOf(quizArray[quizIndex+4]);

    if(answer == option) {
      return true;
    }

    return false;
  }

  @Override
  public int getQuizIndex() {
    return quizIndex;
  }


  @Override
  public void setQuizIndex(int index) {
    quizIndex=index;
  }

  @Override
  public boolean hasQuizFinished(){
    if(quizIndex+4 < quizArray.length-1) {
      return false;
    }

    return true;
  }

  @Override
  public void updateQuizIndex() {
    quizIndex=quizIndex+5;
  }

  @Override
  public String getAnswer() {
    int index= Integer.valueOf(quizArray[quizIndex+4]);
    return quizArray[quizIndex+index];
  }


}
