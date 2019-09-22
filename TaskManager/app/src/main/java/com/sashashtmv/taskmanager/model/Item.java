package com.sashashtmv.taskmanager.model;

public interface Item {
    //нужен, чтобы различать задачи от разделителей на экране(сепараторов)
    boolean isTask();
}
