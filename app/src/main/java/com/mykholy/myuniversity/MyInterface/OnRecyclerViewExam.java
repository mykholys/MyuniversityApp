package com.mykholy.myuniversity.MyInterface;

import com.mykholy.myuniversity.model.Exam;
import com.mykholy.myuniversity.model.Lecture;

public interface OnRecyclerViewExam {
    void OnClickItemListener(Exam exam,String exam_type);
}
