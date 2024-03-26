package es.ulpgc.eite.da.quiz.question;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.quiz.app.AppMediator;
import es.ulpgc.eite.da.quiz.app.CheatToQuestionState;
import es.ulpgc.eite.da.quiz.app.QuestionToCheatState;

public class QuestionPresenter implements QuestionContract.Presenter {

    public static String TAG = QuestionPresenter.class.getSimpleName();

    private AppMediator mediator;
    private WeakReference<QuestionContract.View> view;
    private QuestionState state;
    private QuestionContract.Model model;

    public QuestionPresenter(AppMediator mediator) {
        this.mediator = mediator;
        state = mediator.getQuestionState();
    }

    @Override
    public void onStart() {
        Log.e(TAG, "onStart()");

        // call the model
        state.question = model.getQuestion();
        state.option1 = model.getOption1();
        state.option2 = model.getOption2();
        state.option3 = model.getOption3();

        // reset state to tests
        state.answerCheated = false;
        state.optionClicked = false;
        state.option = 0;

        // update the view
        disableNextButton();
        view.get().resetReply();
    }


    @Override
    public void onRestart() {
        Log.e(TAG, "onRestart()");
        Log.d("TAG", "Valor de index: " + model.getQuizIndex());
        Log.d("TAG", "Valor de index state restart: " + state.quizIndex);
        Log.d("TAG", "Valor de index restart: " + state.optionClicked);


        //TODO: implementación de gestión tras rotar la pantalla.

        view.get().resetReply();


        if (state.optionClicked) {
            enableNextButton();
            state.cheatEnabled = false;

            if (state.option == 0) {
                view.get().resetReply();

            } else {
                view.get().updateReply(state.optionClicked);

                if (state.quizIndex == 45) {
                    state.nextEnabled = false;

                }
            }

        } else if (!state.optionClicked) {
            view.get().updateReply(state.optionClicked);

            if (state.option == 0) {
                view.get().resetReply();

            } else if (state.quizIndex == 45) {
                state.nextEnabled = false;
                state.cheatEnabled = true;
                state.optionEnabled = false;

            }
        }
    }


    @Override
    public void onResume() {
        Log.e(TAG, "onResume()");
        Log.d("TAG", "Valor de index: " + state.quizIndex);

        //TODO: implementación de gestión tras volver de pantalla cheat.

        // use passed state if is necessary
        CheatToQuestionState savedState = getStateFromCheatScreen();
        if (savedState != null) {

            // fetch the model
            if (savedState.answerCheated) {
                state.optionEnabled = false;

                if (state.quizIndex == 45) {
                    if (state.optionClicked) {
                        view.get().resetReply();

                    } else if (state.option == 0) {
                        view.get().resetReply();

                    } else if (!state.optionClicked) {
                        view.get().updateReply(state.optionClicked);

                    }
                } else {
                    onNextButtonClicked();

                }

            } else {
                view.get().resetReply();

            }
        }

        // update the view
        view.get().displayQuestion(state);
    }


    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy()");
    }


    @Override
    public void onOptionButtonClicked(int option) {
        Log.e(TAG, "onOptionButtonClicked()");

        //TODO: implementación de gestión tras pulsar un botón de opción de las preguntas.

        //Actualizamos el estado de opción en base a la opcion elegida.
        state.option = option;


        // Comprobamos si la opcion elegida es correcta y actualizamos el estado segun el caso.
        state.optionClicked = model.isCorrectOption(option);
        enableNextButton();

        if (state.optionClicked) {
            if (state.quizIndex == 45) {
                state.nextEnabled = false;
                state.cheatEnabled = false;

            } else {
                state.cheatEnabled = false;
                state.nextEnabled = true;

            }
        } else {
            state.nextEnabled = true;
            if (state.quizIndex == 45) {
                state.nextEnabled = false;
                state.cheatEnabled = true;

            }
        }

        // Finalmente actualizamos la vista con el nuevo estado y lo pasamos al mediador.
        view.get().updateReply(state.optionClicked);

        QuestionToCheatState currentState = new QuestionToCheatState();
        currentState.answer = model.getAnswer();

        passStateToCheatScreen(currentState);

        Log.d("TAG", "Valor de option clicked: " + state.optionClicked);

        view.get().displayQuestion(state);
    }


    @Override
    public void onNextButtonClicked() {
        Log.e(TAG, "onNextButtonClicked()");

        //TODO: implementación de gestión tras pulsar el boton next.

        //Entramos a este if si no se han acabado las preguntas.
        if (model.hasQuizFinished() == false) {
            state.answerCheated = false;
            state.optionClicked = false;
            state.option = 0;

            disableNextButton();
            view.get().resetReply();

            //Pasamos a la siguiente pregunta
            model.updateQuizIndex();

            state.question = model.getQuestion();
            ;
            state.option1 = model.getOption1();
            state.option2 = model.getOption2();
            state.option3 = model.getOption3();

            state.quizIndex = model.getQuizIndex();
            view.get().displayQuestion(state);

        } else {
            // Si se han acabado las preguntas empezamos de 0 el quiz.
            model.setQuizIndex(0);

            state.question = model.getQuestion();
            ;
            state.option1 = model.getOption1();
            state.option2 = model.getOption2();
            state.option3 = model.getOption3();

            view.get().displayQuestion(state);

        }
        Log.d("TAG", "Valor de state index al click next: " + state.quizIndex);
    }


    @Override
    public void onCheatButtonClicked() {
        Log.e(TAG, "onCheatButtonClicked()");

        //TODO: implementación de gestión tras pulsar el boton cheat.

        // Creamos un nuevo estado auxiliar connuestra respuesta a la pregunta,
        // lo mandamos al mediador y navegamos a pantalla cheat.
        QuestionToCheatState currentState = new QuestionToCheatState();

        currentState.answer = model.getAnswer();

        passStateToCheatScreen(currentState);

        view.get().navigateToCheatScreen();
    }


    private void passStateToCheatScreen(QuestionToCheatState state) {

        //TODO: implementación de gestión para mandar el estado de question al mediador.

        mediator.setQuestionToCheatState(state);
    }


    private CheatToQuestionState getStateFromCheatScreen() {

        //TODO: implementación de gestión para obtener desde el mediador el estado de la pantalla
        // cheat a question.

        return mediator.getCheatToQuestionState();
    }


    private void disableNextButton() {
        state.optionEnabled = true;
        state.cheatEnabled = true;
        state.nextEnabled = false;
    }


    private void enableNextButton() {
        state.optionEnabled = false;

        if (!model.hasQuizFinished()) {
            state.nextEnabled = true;
        }
    }


    @Override
    public void injectView(WeakReference<QuestionContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(QuestionContract.Model model) {
        this.model = model;
    }

}