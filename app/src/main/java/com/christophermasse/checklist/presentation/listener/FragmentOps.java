package com.christophermasse.checklist.presentation.listener;

import com.christophermasse.checklist.entities.Task;

import androidx.annotation.NonNull;

public interface FragmentOps {

    void requestEditTask(@NonNull Task task);
}
