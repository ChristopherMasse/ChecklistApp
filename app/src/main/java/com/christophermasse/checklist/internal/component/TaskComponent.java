package com.christophermasse.checklist.internal.component;

import com.christophermasse.checklist.internal.scope.PerFragment;
import com.christophermasse.checklist.presentation.feature.edit_task.EditTaskFrag;
import com.christophermasse.checklist.presentation.feature.task_list.TaskListFrag;

import dagger.Component;

@Component(dependencies = {AppComponent.class})
@PerFragment
public interface TaskComponent {

    void inject(TaskListFrag frag);

    void inject(EditTaskFrag frag);
}
