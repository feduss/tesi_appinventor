package com.google.appinventor.client.thesis;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.ArrayList;

public class Rule {
    private int index;
    private ListBox whenSubj;
    private ListBox whenVerb;
    private ArrayList<Condition> conditions;
    private ArrayList<Action> actions;
    private VerticalPanel rulePanel; //contains the vertical panel, whose title contains the number of the rule
    private ArrayList<HorizontalPanel> rulesIfPanel; //contains the HorizontalPanel of if(condition)
    private ArrayList<HorizontalPanel> rulesThenPanel; //contains the HorizontalPanel of then(action)


    public Rule(int index){
        this.setIndex(index);
        this.setConditions(new ArrayList<Condition>());
        this.setActions(new ArrayList<Action>());
        this.setRulesIfPanel(new ArrayList<HorizontalPanel>());
        this.setRulesThenPanel(new ArrayList<HorizontalPanel>());
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ListBox getWhenSubj() {
        return whenSubj;
    }

    public void setWhenSubj(ListBox whenSubj) {
        this.whenSubj = whenSubj;
    }

    public ListBox getWhenVerb() {
        return whenVerb;
    }

    public void setWhenVerb(ListBox whenVerb) {
        this.whenVerb = whenVerb;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setConditions(ArrayList<Condition> conditions){
        this.conditions = conditions;
    }

    public void addCondition(Condition condition) {
        this.conditions.add(condition);
    }

    public ArrayList<Condition> getConditions(){ return this.conditions; }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }

    public VerticalPanel getRulePanel() {
        return rulePanel;
    }

    public void setRulePanel(VerticalPanel rulePanel) {
        this.rulePanel = rulePanel;
    }

    public ArrayList<HorizontalPanel> getRulesIfPanel() {
        return rulesIfPanel;
    }

    public void setRulesIfPanel(ArrayList<HorizontalPanel> rulesIfPanel) {
        this.rulesIfPanel = rulesIfPanel;
    }

    public ArrayList<HorizontalPanel> getRulesThenPanel() {
        return rulesThenPanel;
    }

    public void setRulesThenPanel(ArrayList<HorizontalPanel> rulesThenPanel) {
        this.rulesThenPanel = rulesThenPanel;
    }
}
