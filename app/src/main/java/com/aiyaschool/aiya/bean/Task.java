package com.aiyaschool.aiya.bean;

import java.util.List;

/**
 * Created by EGOISTK21 on 2017/5/26.
 */

public class Task {

    private List<String> task;

    public List<String> getTask() {
        return task;
    }

    public void setTask(List<String> task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "Task{" +
                "task=" + task +
                '}';
    }
}
