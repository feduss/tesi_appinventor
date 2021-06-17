// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.explorer;

import com.google.appinventor.client.Ode;
import com.google.appinventor.client.boxes.BlockSelectorBox;
import com.google.appinventor.client.editor.youngandroid.YaBlocksEditor;
import com.google.appinventor.client.thesis.*;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;
import java.util.HashMap;


public class AntRulesExplorer extends Composite {

  private final VerticalPanel verticalPanel;
  private final HashMap<String, Integer> ruleCount = new HashMap<String, Integer>();
  public AntRulesExplorer(){
    ScrollPanel scrollPanel = new ScrollPanel();
    scrollPanel.setWidth("720px");  // wide enough to avoid a horizontal scrollbar most of the time
    scrollPanel.setHeight("100%"); // approximately the same height as the viewer


    verticalPanel = new VerticalPanel();
    verticalPanel.setWidth("720px");
    verticalPanel.setHeight("100%");
    verticalPanel.add(scrollPanel);
    //Label label = new Label("Questo sarà il panel con le regole");
    //verticalPanel.add(label);
    initWidget(verticalPanel);
  }

  public void changeScreen(ArrayList<Rule> rules){
    for(String screen : ruleCount.keySet()){
      ruleCount.replace(screen, 0);
    }
    if(verticalPanel != null){
      while(verticalPanel.getWidgetCount() > 0){
        verticalPanel.remove(0);
      }
    }
    if(rules != null && rules.size() > 0){
      for(Rule rule : rules){
        insertRule(rule);
      }
    }
  }

  public void insertRule(final Rule sourceRule) {

    SourceStructureExplorer sourceStructureExplorer = BlockSelectorBox.getBlockSelectorBox().getSourceStructureExplorer();
    if(ruleCount.get(sourceStructureExplorer.screenName) == null){
      ruleCount.put(sourceStructureExplorer.screenName, 0);
    }
    Integer value = ruleCount.get(sourceStructureExplorer.screenName) + 1;
    ruleCount.replace(sourceStructureExplorer.screenName, value);
    Rule newRule = new Rule(ruleCount.get(sourceStructureExplorer.screenName));
    final VerticalPanel innerVerticalPanel = new VerticalPanel();
    final Label title = new Label("Rule" + ruleCount.get(sourceStructureExplorer.screenName) + ":");
    title.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
    newRule.setTitle(title);
    innerVerticalPanel.add(title);
    innerVerticalPanel.setTitle("Rule " + ruleCount.get(sourceStructureExplorer.screenName));
    newRule.setInnerVerticalPanel(innerVerticalPanel);
    //Window.alert("Title: " + innerVerticalPanel.getTitle());
    /***Restore when***/
    final ListBox whenSubjListBox = new ListBox();
    for(int i = 0; i < sourceRule.getWhenSubj().getItemCount(); i++){
      whenSubjListBox.addItem(sourceRule.getWhenSubj().getItemText(i));
    }
    whenSubjListBox.setSelectedIndex(sourceRule.getWhenSubj().getSelectedIndex());
    //Window.alert("sourceRule.getWhenSubj().selected: " + sourceRule.getWhenSubj().getSelectedItemText());

    whenSubjListBox.getElement().getStyle().setWidth(125, Style.Unit.PX);
    final ListBox whenVerbListBox = new ListBox();
    for(int i = 0; i < sourceRule.getWhenVerb().getItemCount(); i++){
      whenVerbListBox.addItem(sourceRule.getWhenVerb().getItemText(i));
    }
    whenVerbListBox.setSelectedIndex(sourceRule.getWhenVerb().getSelectedIndex());
    whenVerbListBox.getElement().getStyle().setWidth(125, Style.Unit.PX);

    final HorizontalPanel horizontalPanelWhen = new HorizontalPanel();
    horizontalPanelWhen.setTitle("Event 1");
    Label whenLabel = new Label("When");
    whenLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
    HorizontalPanel whenLabelContainer = new HorizontalPanel();
    whenLabelContainer.getElement().getStyle().setWidth(50, Style.Unit.PX);
    whenLabelContainer.add(whenLabel);
    horizontalPanelWhen.add(whenLabelContainer);
    horizontalPanelWhen.add(new HTML("<hr  style=\"width:40px;\" />"));
    horizontalPanelWhen.add(whenSubjListBox);
    horizontalPanelWhen.add(new HTML("<hr  style=\"width:40px;\" />"));
    horizontalPanelWhen.add(whenVerbListBox);
    innerVerticalPanel.add(horizontalPanelWhen);

    /******/
    /***Restore ifs***/
    int i = 0;
    for(Condition condition : sourceRule.getConditions()){
      HorizontalPanel ifLabelContainer = new HorizontalPanel();
      final ListBox andOrListBox = new ListBox();

      Label ifLabel = new Label("If");
      ifLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
      ifLabelContainer.getElement().getStyle().setWidth(50, Style.Unit.PX);
      ifLabelContainer.add(ifLabel);

      if(i != 0){
        ifLabel.setText("");
        andOrListBox.addItem("");
        andOrListBox.addItem("AND");
        andOrListBox.addItem("OR");
        andOrListBox.getElement().getStyle().setWidth(125, Style.Unit.PX);
        andOrListBox.setSelectedIndex(condition.getANDOR().getSelectedIndex());
      }

      final ListBox ifSubjListBox = new ListBox();
      for(int j = 0; j < sourceRule.getConditions().get(i).getIfSubj().getItemCount(); j++){
        ifSubjListBox.addItem(sourceRule.getConditions().get(i).getIfSubj().getItemText(j));
      }
      ifSubjListBox.setSelectedIndex(condition.getIfSubj().getSelectedIndex());
      ifSubjListBox.getElement().getStyle().setWidth(125, Style.Unit.PX);
      final ListBox ifVerbListBox = new ListBox();
      for(int j = 0; j < sourceRule.getConditions().get(i).getIfVerb().getItemCount(); j++){
        ifVerbListBox.addItem(sourceRule.getConditions().get(i).getIfVerb().getItemText(j));
      }
      ifVerbListBox.setSelectedIndex(condition.getIfVerb().getSelectedIndex());
      ifVerbListBox.getElement().getStyle().setWidth(125, Style.Unit.PX);

      final TextBox ifTextBox = new TextBox();
      ifTextBox.setText(condition.getIfTextBox().getText());
      ifTextBox.getElement().getStyle().setWidth(125, Style.Unit.PX);
      String heightStr = ifVerbListBox.getElement().getStyle().getHeight();
      double height = 25.0;
      if(heightStr != null && !heightStr.equals("")){
        height = Double.parseDouble(heightStr);
      }
      ifTextBox.getElement().getStyle().setHeight(height, Style.Unit.PX);
      if(condition.getIfTextBox().getText() != null && !condition.getIfTextBox().getText().equals("")){
        ifTextBox.setVisible(true);
        ifTextBox.setText(condition.getIfTextBox().getText());
      }
      else{
        ifTextBox.setVisible(false);
      }

      Button deleteCond = new Button();
      deleteCond.setText("DEL");

      final HorizontalPanel horizontalPanelIf = new HorizontalPanel();
      //Window.alert("IfIndex: " +
      //        String.valueOf(rulesListBoxes.get(screenName).get(ruleIndex).getRulesIfPanel().size() + 1));
      horizontalPanelIf.setTitle("Condition " + sourceRule.getRulesIfPanel().size() + 1);
      horizontalPanelIf.add(ifLabelContainer);
      HTML line = new HTML("<hr  style=\"width:40px;\" />");
      horizontalPanelIf.add(line);
      if(i != 0){
        //rulesListBoxes.get(screenName).get(ruleIndex).getConditions().get(size).setANDOR(andOrListBox);
        horizontalPanelIf.add(andOrListBox);
        horizontalPanelIf.add(new HTML("<hr  style=\"width:40px;\" />"));
      }
      horizontalPanelIf.add(ifSubjListBox);
      horizontalPanelIf.add(new HTML("<hr  style=\"width:40px;\" />"));
      horizontalPanelIf.add(ifVerbListBox);
      horizontalPanelIf.add(ifTextBox);

      innerVerticalPanel.add(horizontalPanelIf);
      i++;
    }
    /******/

    i = 0;
    /***Restore thens***/
    //Window.alert("rule.getActions().size(): " + rule.getActions().size());
    for(Action action : sourceRule.getActions()){
      final ListBox actionTypeListBox = new ListBox();
      for(int j = 0; j < sourceRule.getActions().get(i).getThenType().getItemCount(); j++){
        actionTypeListBox.addItem(sourceRule.getActions().get(i).getThenType().getItemText(j));
      }
      actionTypeListBox.getElement().getStyle().setWidth(125, Style.Unit.PX);
      actionTypeListBox.setSelectedIndex(action.getThenType().getSelectedIndex());

      final ListBox actionSubjListBox = new ListBox();
      for(int j = 0; j < sourceRule.getActions().get(i).getThenSubj().getItemCount(); j++){
        actionSubjListBox.addItem(sourceRule.getActions().get(i).getThenSubj().getItemText(j));
      }
      actionSubjListBox.setSelectedIndex(action.getThenSubj().getSelectedIndex());
      actionSubjListBox.getElement().getStyle().setWidth(125, Style.Unit.PX);
      final ListBox actionVerbListBox = new ListBox();
      for(int j = 0; j < sourceRule.getActions().get(i).getThenVerb().getItemCount(); j++){
        actionVerbListBox.addItem(sourceRule.getActions().get(i).getThenVerb().getItemText(j));
      }
      actionVerbListBox.setSelectedIndex(action.getThenVerb().getSelectedIndex());
      actionVerbListBox.getElement().getStyle().setWidth(125, Style.Unit.PX);

      final TextBox thenTextBox = new TextBox();
      thenTextBox.setText(action.getThenTextBox().getText());
      if(thenTextBox.getText() == null || thenTextBox.getText().equals("")){
        thenTextBox.setVisible(false);
      }
      else{
        thenTextBox.setVisible(true);
      }
      thenTextBox.getElement().getStyle().setWidth(125, Style.Unit.PX);
      String heightStr = actionVerbListBox.getElement().getStyle().getHeight();
      double height = 25.0;
      if(heightStr != null && !heightStr.equals("")){
        height = Double.parseDouble(heightStr);
      }
      thenTextBox.getElement().getStyle().setHeight(height, Style.Unit.PX);

      final Button deleteMainAction = new Button();
      deleteMainAction.setText("DEL");
      deleteMainAction.setEnabled(false);

      final Label thenLabel = new Label("Then");
      thenLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
      HorizontalPanel thenLabelContainer = new HorizontalPanel();
      thenLabelContainer.getElement().getStyle().setWidth(50, Style.Unit.PX);
      thenLabelContainer.add(thenLabel);

      final HorizontalPanel horizontalPanelAction = new HorizontalPanel();
      horizontalPanelAction.setTitle("Action " + (i + 1));
      horizontalPanelAction.add(thenLabelContainer);
      horizontalPanelAction.add(new HTML("<hr  style=\"width:40px;\" />"));
      horizontalPanelAction.add(actionTypeListBox);
      horizontalPanelAction.add(new HTML("<hr  style=\"width:40px;\" />"));
      horizontalPanelAction.add(actionSubjListBox);
      horizontalPanelAction.add(new HTML("<hr  style=\"width:40px;\" />"));
      horizontalPanelAction.add(actionVerbListBox);
      horizontalPanelAction.add(thenTextBox);
      horizontalPanelAction.add(deleteMainAction);
      innerVerticalPanel.add(horizontalPanelAction);

      Button deleteRule = new Button();
      deleteRule.setText("Delete rule");

      HorizontalPanel horizontalPanelButton = new HorizontalPanel();
      horizontalPanelButton.add(deleteRule);

      deleteRule.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent clickEvent) {
          YaBlocksEditor editor =
                  (YaBlocksEditor) Ode.getInstance().getCurrentFileEditor();
          //TODO cambiare l'id con quello del file di salvataggio
          boolean res = editor.deleteBlock(sourceRule.getBlock_id());
          if(res){
            removeRuleLayout(innerVerticalPanel, verticalPanel);
          }
          else{
            Window.alert("An error occurs during rule deleting.");
          }
        }
      });
      innerVerticalPanel.add(horizontalPanelButton);
      //End of the rule
      innerVerticalPanel.add(new HTML("<hr  style=\"width:720px;\" />"));
      verticalPanel.add(innerVerticalPanel);
      i++;
    }

  }


  //feduss
  private void removeRuleLayout(VerticalPanel innerVerticalPanel, VerticalPanel verticalPanel) {
    //Window.alert("innerVerticalPanel: " + innerVerticalPanel + ", verticalPanel: " + verticalPanel);
    int indexToRemove = Integer.parseInt(innerVerticalPanel.getTitle().split("Rule ")[1]);
    //Window.alert("indexToRemove: " + indexToRemove);
    SourceStructureExplorer sourceStructureExplorer = BlockSelectorBox.getBlockSelectorBox().getSourceStructureExplorer();
    //Window.alert("sourceStructureExplorer.rulesListBoxes.get(sourceStructureExplorer.screenName).size: "
    //        + sourceStructureExplorer.rulesListBoxes.get(sourceStructureExplorer.screenName).size());
    for(Rule rule : sourceStructureExplorer.rulesListBoxes.get(sourceStructureExplorer.screenName)){
      VerticalPanel rulePanel = rule.getInnerVerticalPanel();
      int index = Integer.parseInt(rulePanel.getTitle().split("Rule ")[1]);
      //Window.alert("Current index: " + index + "\nIndexToRemove: " + indexToRemove);
      if(index > indexToRemove){
        rulePanel.setTitle("Rule " + (index - 1));
        rule.getTitle().setText("Rule " + (index - 1));
      }
    }

    verticalPanel.remove(innerVerticalPanel);
    sourceStructureExplorer.rulesListBoxes.get(sourceStructureExplorer.screenName).remove(indexToRemove - 1);
    Integer value = ruleCount.get(sourceStructureExplorer.screenName) - 1;
    ruleCount.replace(sourceStructureExplorer.screenName, value);

    /*if(rulesListBoxes.get(screenName).size() == 0){
      confirmButton.setEnabled(false);
    }*/
  }
}
