package com.example.chernenkovit.a.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chernenkovit.a.Const;
import com.example.chernenkovit.a.R;

/** Test tab fragment. */
public class TestFragment extends Fragment implements View.OnClickListener {
    private EditText et_inputField;
    private Button btn_ok;

    public TestFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et_inputField = (EditText) view.findViewById(R.id.et_inputField);
        btn_ok = (Button) view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
    }

    //start App b by filling a link
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                if (!et_inputField.getText().toString().isEmpty()) {
                    try {
                        Intent showImage = new Intent("com.example.chernenkovit.action.showImage");
                        showImage.putExtra("Link", et_inputField.getText().toString());
                        showImage.putExtra("Prev", Const.TEST_FRAGMENT);
                        startActivity(showImage);
                        break;
                    } catch (android.content.ActivityNotFoundException e) {
                        Toast.makeText(getActivity(), "No app for image showing", Toast.LENGTH_SHORT).show();
                    }
                } else  Toast.makeText(getActivity(), "Please, fill a field!", Toast.LENGTH_SHORT).show();
        }
    }
}
