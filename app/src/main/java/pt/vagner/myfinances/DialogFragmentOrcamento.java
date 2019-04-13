package pt.vagner.myfinances;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogFragmentOrcamento extends AppCompatDialogFragment {

    private EditText editTextInput;
    private DialogFragmentOrcamento.ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DialogFragmentOrcamento.ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void setValue(double orcamento);
    }

    private void goToMainActivity(){
        Intent i = new Intent(getContext(), MainActivity.class);
        startActivity(i);
    }
}
