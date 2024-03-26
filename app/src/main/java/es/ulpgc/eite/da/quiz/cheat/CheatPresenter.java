package es.ulpgc.eite.da.quiz.cheat;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.quiz.app.AppMediator;
import es.ulpgc.eite.da.quiz.app.CheatToQuestionState;
import es.ulpgc.eite.da.quiz.app.QuestionToCheatState;

public class CheatPresenter implements CheatContract.Presenter {

    public static String TAG = CheatPresenter.class.getSimpleName();

    private AppMediator mediator;
    private WeakReference<CheatContract.View> view;
    private final CheatState state;
    private CheatContract.Model model;

    public CheatPresenter(CheatState state) {
        this.state = state;
    }

    public CheatPresenter(AppMediator mediator) {
        this.mediator = mediator;
        state = mediator.getCheatState();
    }

    @Override
    public void onStart() {
        Log.e(TAG, "onStart()");

        // reset state to tests
        state.answerEnabled = true;
        state.answerCheated = false;
        state.answer = null;

        // update the view
        view.get().resetAnswer();
    }

    @Override
    public void onRestart() {
        Log.e(TAG, "onRestart()");

        //TODO: implementación de gestión tras rotar la pantalla.

        if (state.answerCheated) {
            onWarningButtonClicked(1);

        } else {
            // Resetamos el estado a sus valores iniciales
            state.answerEnabled = true;
            state.answerCheated = false;
            state.answer = null;

            // Actualizamos la vista
            view.get().resetAnswer();

        }
    }


    @Override
    public void onResume() {
        Log.e(TAG, "onResume()");

        //TODO: implementación de gestión para mostrar la respuesta.

        // use passed state if is necessary
        QuestionToCheatState savedState = getStateFromQuestionScreen();

        if (savedState != null) {
            // update the state
            state.answer = savedState.answer;
        }

        // update the view
        if (state.answerCheated) {
            view.get().displayAnswer(state);

        }
    }


    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy()");
    }


    @Override
    public void onBackPressed() {
        Log.e(TAG, "onBackPressed()");

        //TODO: implementación de gestión tras pulsar el botón back.
        view.get().onFinish();
    }


    @Override
    public void onWarningButtonClicked(int option) {
        Log.e(TAG, "onWarningButtonClicked()");

        //TODO: implementación de gestión tras pulsar Si o No.

        //option=1 => yes, option=0 => no
        if (option == 1) {
            state.answerEnabled = false;
            state.answerCheated = true;

            Log.d("TAG", "Valor de myBoolean 1: " + state.answerCheated);

            view.get().displayAnswer(state);

            // Pasamos al mediador el estado de cheat.
            CheatToQuestionState currentState = new CheatToQuestionState();
            currentState.answerCheated = state.answerCheated;
            passStateToQuestionScreen(currentState);

        } else {
            state.answerCheated = false;
            view.get().onFinish();

        }
    }


    private void passStateToQuestionScreen(CheatToQuestionState state) {

        //TODO: implementación de gestión para pasar el estado de cheat al mediador.

        mediator.setCheatToQuestionState(state);
    }


    private QuestionToCheatState getStateFromQuestionScreen() {

        //TODO: implementación de gestión para obtener el estado de question desde el mediador.

        return mediator.getQuestionToCheatState();
    }


    @Override
    public void injectView(WeakReference<CheatContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(CheatContract.Model model) {
        this.model = model;
    }

}

