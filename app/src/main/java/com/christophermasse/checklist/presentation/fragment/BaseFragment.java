package com.christophermasse.checklist.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.Unbinder;
import timber.log.Timber;

public abstract class BaseFragment extends Fragment {

    protected Unbinder mUnbinder;

    @Nullable
    @Override
    public abstract View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder == null){
            Timber.w("Unbinder is null, cannot unbind views. Make sure to set mUnbinder within onCreateView");
            return;
        }
        mUnbinder.unbind();
    }

    public void showToastShort(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
