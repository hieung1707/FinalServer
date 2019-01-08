/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class Answer implements Serializable {
    static final long serialVersionUID = 2L;
    Student student;
    Object[] answers;
    boolean[] isRights;
    boolean alreadyRegistration;

    public Answer(Student student, Object[] answers, boolean[] isRights, boolean alreadyRegistration) {
        this.student = student;
        this.answers = answers;
        this.isRights = isRights;
        this.alreadyRegistration = alreadyRegistration;
    }

    public Answer() {
    }

    public long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Student getStudent() {
        return student;
    }

    public Object[] getAnswers() {
        return answers;
    }

    public boolean[] getIsRights() {
        return isRights;
    }

    public boolean isAlreadyRegistration() {
        return alreadyRegistration;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setAnswers(Object[] answers) {
        this.answers = answers;
    }

    public void setIsRights(boolean[] isRights) {
        this.isRights = isRights;
    }

    public void setAlreadyRegistration(boolean alreadyRegistration) {
        this.alreadyRegistration = alreadyRegistration;
    }
    
    
}
