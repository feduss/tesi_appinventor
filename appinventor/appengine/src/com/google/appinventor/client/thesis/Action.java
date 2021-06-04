package com.google.appinventor.client.thesis;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class Action {
    private int index;
    private ListBox thenType;
    private ListBox thenSubj;
    private ListBox thenVerb;
    private TextBox thenTextBox;
    private Button deleteButton;
    private String viewClickedType = "";

    public Action(int index, ListBox thenType, ListBox thenSubj, ListBox thenVerb, TextBox thenTextBox, Button deleteButton){
        this.setIndex(index);
        this.setThenType(thenType);
        this.setThenSubj(thenSubj);
        this.setThenVerb(thenVerb);
        this.setThenTextBox(thenTextBox);
        this.setDeleteButton(deleteButton);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ListBox getThenType() {
        return thenType;
    }

    public void setThenType(ListBox thenType) {
        this.thenType = thenType;
    }

    public ListBox getThenSubj() {
        return thenSubj;
    }

    public void setThenSubj(ListBox thenSubj) {
        this.thenSubj = thenSubj;
    }

    public ListBox getThenVerb() {
        return thenVerb;
    }

    public void setThenVerb(ListBox thenVerb) {
        this.thenVerb = thenVerb;
    }

    public TextBox getThenTextBox() {
        return thenTextBox;
    }

    public void setThenTextBox(TextBox thenTextBox) {
        this.thenTextBox = thenTextBox;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    public String getViewClickedType() {
        return viewClickedType;
    }

    public void setViewClickedType(String viewClickedType) {
        this.viewClickedType = viewClickedType;
    }
}
