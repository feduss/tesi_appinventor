package com.google.appinventor.client.thesis;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class Condition {

    private HorizontalPanel horizontalPanelIf;
    private ListBox andOR;
    private ListBox ifSubj;
    private ListBox ifVerb;
    private TextBox ifTextBox;
    private String viewClickedType = "";
    //private Button deleteButton;

    public Condition(HorizontalPanel horizontalPanelIf, ListBox ifSubj, ListBox ifVerb, TextBox ifTextBox/*, Button deleteCond*/){
        this.setHorizontalPanelIf(horizontalPanelIf);
        this.setIfSubj(ifSubj);
        this.setIfVerb(ifVerb);
        this.setIfTextBox(ifTextBox);
        //this.setDeleteButton(deleteCond);
    }

    public HorizontalPanel getHorizontalPanelIf() {
        return horizontalPanelIf;
    }

    public void setHorizontalPanelIf(HorizontalPanel horizontalPanelIf) {
        this.horizontalPanelIf = horizontalPanelIf;
    }

    public ListBox getIfSubj() {
        return ifSubj;
    }

    public void setIfSubj(ListBox ifSubj) {
        this.ifSubj = ifSubj;
    }

    public ListBox getIfVerb() {
        return ifVerb;
    }

    public void setIfVerb(ListBox ifVerb) {
        this.ifVerb = ifVerb;
    }

    public TextBox getIfTextBox() {
        return ifTextBox;
    }

    public void setIfTextBox(TextBox ifTextBox) {
        this.ifTextBox = ifTextBox;
    }

    public ListBox getANDOR() {
        return andOR;
    }

    public void setANDOR(ListBox andOR) {
        this.andOR = andOR;
    }

    public String getViewClickedType() {
        return viewClickedType;
    }

    public void setViewClickedType(String viewClickedType) {
        this.viewClickedType = viewClickedType;
    }

    /*public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }*/
}
