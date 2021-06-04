// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.explorer;

import com.google.appinventor.client.Ode;
import com.google.appinventor.client.editor.simple.components.MockComponent;
import com.google.appinventor.client.editor.youngandroid.YaBlocksEditor;
import com.google.appinventor.client.thesis.*;
import com.google.appinventor.client.widgets.TextButton;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.*;

import static com.google.appinventor.client.Ode.MESSAGES;

/**
 * This explorer is used to outline the structure of a source file. Note that
 * this explorer is shared by all it's clients. That means that clients (most
 * likely editors) need to update its content upon activation.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public class SourceStructureExplorer extends Composite {
  // UI elements
  private final EventCaptureTree tree;
  private final TextButton renameButton;
  private final TextButton deleteButton;

  //feduss
  VerticalPanel panel2;
  TextArea inputTextBox; //initialization
  Label resultLabel;

  public ListBox whenSubjListBoxGeneric = new ListBox();
  public ListBox whenVerbListBoxGeneric = new ListBox();

  public ListBox ifSubjListBoxGeneric = new ListBox();
  public ListBox ifVerbListBoxGeneric = new ListBox();

  public ListBox thenSubjListBoxGeneric = new ListBox();
  public ListBox thenVerbListBoxGeneric = new ListBox();
  //Screen -> list of views
  public HashMap<String, ArrayList<MockComponent>> userViewsList = new HashMap<>();

  public HashMap<String, ArrayList<Rule>> rulesListBoxes =  new HashMap<String, ArrayList<Rule>>(); //Screen -> list of rules

  public String screenName = null;
  //////

  /**
   * This is a hack to work around the fact that for multiselect we need to have
   * access to the state of the meta/ctrl key but the SelectionHandler doesn't
   * provide access to the original event that caused the selection. We capture
   * the most recent event before the selection event is triggered and then
   * reset once the selection has been updated.
   */
  static class EventCaptureTree extends Tree {

    Event lastEvent = null;

    public EventCaptureTree(Resources resources) {
      super(resources);
    }

    @Override
    public void onBrowserEvent(Event event) {
      lastEvent = event;
      super.onBrowserEvent(event);
    }
  }

  /**
   * Creates a new source structure explorer.
   */
  //feduss, add type to not execute the if(ThesisVariables.enableRules) in some context
  public SourceStructureExplorer(String type) {
    // Initialize UI elements
    tree = new EventCaptureTree(Ode.getImageBundle());

    tree.setAnimationEnabled(true);
    tree.setScrollOnSelectEnabled(false);
    tree.addCloseHandler(new CloseHandler<TreeItem>() {
      @Override
      public void onClose(CloseEvent<TreeItem> event) {
        TreeItem treeItem = event.getTarget();
        if (treeItem != null) {
          Object userObject = treeItem.getUserObject();
          if (userObject instanceof SourceStructureExplorerItem) {
            SourceStructureExplorerItem item = (SourceStructureExplorerItem) userObject;
            item.onStateChange(false);
          }
        }
      }
    });
    tree.addOpenHandler(new OpenHandler<TreeItem>() {
      @Override
      public void onOpen(OpenEvent<TreeItem> event) {
        TreeItem treeItem = event.getTarget();
        if (treeItem != null) {
          Object userObject = treeItem.getUserObject();
          if (userObject instanceof SourceStructureExplorerItem) {
            SourceStructureExplorerItem item = (SourceStructureExplorerItem) userObject;
            item.onStateChange(true);
          }
        }
      }
    });
    tree.addSelectionHandler(new SelectionHandler<TreeItem>() {
      @Override
      public void onSelection(SelectionEvent<TreeItem> event) {
        TreeItem treeItem = event.getSelectedItem();
        if (treeItem != null) {
          Object userObject = treeItem.getUserObject();
          if (userObject instanceof SourceStructureExplorerItem) {
            SourceStructureExplorerItem item = (SourceStructureExplorerItem) userObject;
            enableButtons(item);
            //showBlocks(item);
            item.onSelected(tree.lastEvent);
          } else {
            disableButtons();
            //hideComponent();
          }
        } else {
          disableButtons();
        }
        tree.lastEvent = null;
      }
    });
    tree.addKeyDownHandler(new KeyDownHandler() {
      @Override
      public void onKeyDown(KeyDownEvent event) {
        int keyCode = event.getNativeKeyCode();
        if (keyCode == KeyCodes.KEY_DELETE || keyCode == KeyCodes.KEY_BACKSPACE) {
          event.preventDefault();
          deleteItemFromTree();
        }
      }
    });

    // Put a ScrollPanel around the tree.
    ScrollPanel scrollPanel = new ScrollPanel(tree);
    scrollPanel.setWidth("200px");  // wide enough to avoid a horizontal scrollbar most of the time
    scrollPanel.setHeight("480px"); // approximately the same height as the viewer

    HorizontalPanel buttonPanel = new HorizontalPanel();
    buttonPanel.setStyleName("ode-PanelButtons");
    buttonPanel.setSpacing(4);

    renameButton = new TextButton(MESSAGES.renameButton());
    renameButton.setEnabled(false);
    renameButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        TreeItem treeItem = tree.getSelectedItem();
        if (treeItem != null) {
          Object userObject = treeItem.getUserObject();
          if (userObject instanceof SourceStructureExplorerItem) {
            SourceStructureExplorerItem item = (SourceStructureExplorerItem) userObject;
            item.rename();
          }
        }
      }
    });
    buttonPanel.add(renameButton);
    buttonPanel.setCellHorizontalAlignment(renameButton, HorizontalPanel.ALIGN_RIGHT);

    deleteButton = new TextButton(MESSAGES.deleteButton());
    deleteButton.setEnabled(false);
    deleteButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        deleteItemFromTree();
      }
    });
    buttonPanel.add(deleteButton);
    buttonPanel.setCellHorizontalAlignment(deleteButton, HorizontalPanel.ALIGN_LEFT);

    VerticalPanel panel = new VerticalPanel();
    //feduss, replace Blocks vertical panel with my antlr panel
    if(ThesisVariables.enableRules && !type.equals("SourceStructureBox")){
      panel2 = new VerticalPanel();
      // Put a ScrollPanel around the panel.
      scrollPanel = new ScrollPanel(panel2);
      scrollPanel.setWidth("1000px");  // wide enough to avoid a horizontal scrollbar most of the time
      scrollPanel.setHeight("100%"); // approximately the same height as the viewer

      final VerticalPanel verticalPanel = new VerticalPanel();
      verticalPanel.getElement().setAttribute("cellpadding", "5");

      Button addRule = new Button();
      addRule.setText("Add rule");


      final Button confirmButton = new Button();
      confirmButton.setText("PARSE");
      confirmButton.setEnabled(false);

      addRule.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent clickEvent) {
          //Vertical layout of the rule
          //Window.alert("Screenanme: " + screenName);
          confirmButton.setEnabled(true);
          final VerticalPanel innerVerticalPanel = new VerticalPanel();
          if(rulesListBoxes.size() == 0){
            rulesListBoxes.put(screenName, new ArrayList<Rule>());
          }

          Rule newRule = new Rule(rulesListBoxes.get(screenName).size());
          rulesListBoxes.get(screenName).add(newRule);
          int ruleIndex = rulesListBoxes.get(screenName).size() - 1;
          innerVerticalPanel.setTitle("Rule " + String.valueOf(ruleIndex + 1)); //useful to recognize which rule i'm editing, etc

          rulesListBoxes.get(screenName).get(ruleIndex).setRulePanel(innerVerticalPanel);

          Label whenLabel = new Label();
          whenLabel.setText("When");
          whenLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
          HorizontalPanel whenLabelContainer = new HorizontalPanel();
          whenLabelContainer.getElement().getStyle().setWidth(50, Style.Unit.PX);
          whenLabelContainer.add(whenLabel);

          final ListBox whenSubjListBox = new ListBox();
          whenSubjListBox.addItem("");
          whenSubjListBox.getElement().getStyle().setWidth(150, Style.Unit.PX);
          final ListBox whenVerbListBox = new ListBox();
          whenVerbListBox.addItem("");
          whenVerbListBox.getElement().getStyle().setWidth(150, Style.Unit.PX);

          /*if(rulesListBoxes.get(screenName) == null){
            rulesListBoxes.put(screenName, new ArrayList<Rule>());
          }*/
          rulesListBoxes.get(screenName).get(newRule.getIndex()).setWhenSubj(whenSubjListBox);
          rulesListBoxes.get(screenName).get(newRule.getIndex()).setWhenVerb(whenVerbListBox);

          //These listboxes are populated in YaFormEditor and DesignToolbar
          //When a "when subject" is selected, the list of "when verb" is changed
          whenSubjListBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
              int ruleIndex = Integer.parseInt(innerVerticalPanel.getTitle().split("Rule ")[1]) - 1;
              String viewClickedType = ListBoxWhenSubjFieldClicked(whenSubjListBox, whenVerbListBox);
              rulesListBoxes.get(screenName).get(ruleIndex).setViewClickedType(viewClickedType);
            }
          });

          final HorizontalPanel horizontalPanelWhen = new HorizontalPanel();
          horizontalPanelWhen.add(whenLabelContainer);
          horizontalPanelWhen.add(new HTML("<hr  style=\"width:75px;\" />"));
          horizontalPanelWhen.add(whenSubjListBox);
          horizontalPanelWhen.add(new HTML("<hr  style=\"width:75px;\" />"));
          horizontalPanelWhen.add(whenVerbListBox);

          final ListBox actionTypeListBox = new ListBox();
          actionTypeListBox.getElement().getStyle().setWidth(150, Style.Unit.PX);
          actionTypeListBox.addItem("");
          actionTypeListBox.addItem("Set");
          actionTypeListBox.addItem("Call");
          actionTypeListBox.addItem("Open");

          final ListBox actionSubjListBox = new ListBox();
          //actionSubjListBox.addItem("");
          actionSubjListBox.getElement().getStyle().setWidth(150, Style.Unit.PX);
          final ListBox actionVerbListBox = new ListBox();
          //actionVerbListBox.addItem("");
          actionVerbListBox.getElement().getStyle().setWidth(150, Style.Unit.PX);

          final TextBox thenTextBox = new TextBox();
          thenTextBox.setVisible(false);
          thenTextBox.getElement().getStyle().setWidth(150, Style.Unit.PX);
          String heightStr = actionVerbListBox.getElement().getStyle().getHeight();
          double height = 25.0;
          if(heightStr != null && !heightStr.equals("")){
            height = Double.parseDouble(heightStr);
          }
          thenTextBox.getElement().getStyle().setHeight(height, Style.Unit.PX);

          final Button deleteMainAction = new Button();
          deleteMainAction.setText("DEL");
          deleteMainAction.setEnabled(false);

          if(rulesListBoxes.get(screenName).get(newRule.getIndex()).getActions() == null){
            rulesListBoxes.get(screenName).get(newRule.getIndex()).setActions(new ArrayList<Action>());
          }

          //the first action of the rule has index 0 and is empty
          rulesListBoxes.get(screenName).get(newRule.getIndex()).getActions().add(new Action(0, actionTypeListBox,
                  actionSubjListBox, actionVerbListBox, thenTextBox, deleteMainAction));

          final Label thenLabel = new Label();
          thenLabel.setText("Then");
          thenLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
          HorizontalPanel thenLabelContainer = new HorizontalPanel();
          thenLabelContainer.getElement().getStyle().setWidth(50, Style.Unit.PX);
          thenLabelContainer.add(thenLabel);

          final HorizontalPanel horizontalPanelAction = new HorizontalPanel();
          horizontalPanelAction.setTitle("Action 1");
          horizontalPanelAction.add(thenLabelContainer);
          horizontalPanelAction.add(new HTML("<hr  style=\"width:75px;\" />"));
          horizontalPanelAction.add(actionTypeListBox);
          horizontalPanelAction.add(new HTML("<hr  style=\"width:75px;\" />"));
          horizontalPanelAction.add(actionSubjListBox);
          horizontalPanelAction.add(new HTML("<hr  style=\"width:75px;\" />"));
          horizontalPanelAction.add(actionVerbListBox);
          horizontalPanelAction.add(thenTextBox);
          horizontalPanelAction.add(deleteMainAction);
          //same of when subj
          actionTypeListBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
              int ruleIndex = Integer.parseInt(innerVerticalPanel.getTitle().split("Rule ")[1]) - 1;
              int actionIndex = Integer.parseInt(horizontalPanelAction.getTitle().split("Action ")[1]) - 1;
              //selected is the action type select by the user
              String actionType = rulesListBoxes.get(screenName).get(ruleIndex).getActions().get(actionIndex)
                      .getThenType().getValue(actionTypeListBox.getSelectedIndex());
              String actionSubj = rulesListBoxes.get(screenName).get(ruleIndex).getActions().get(actionIndex)
                      .getThenSubj() != null ?
                        actionSubjListBox.getSelectedIndex() != -1 ?
                        rulesListBoxes.get(screenName).get(ruleIndex).getActions().get(actionIndex)
                                .getThenSubj().getValue(actionSubjListBox.getSelectedIndex()) : null
                      : null;
              boolean actionSubjSelected = actionSubj != null && !actionSubj.equals("");

              if(actionTypeListBox.getSelectedItemText().equals("Open")){
                while (actionSubjListBox.getItemCount() > 0) {
                  actionSubjListBox.removeItem(0);
                }
                while (actionVerbListBox.getItemCount() > 0) {
                  actionVerbListBox.removeItem(0);
                }
                actionSubjListBox.addItem("");
                actionVerbListBox.addItem("");
                actionSubjListBox.addItem("another screen");
              }
              else{
                while (actionSubjListBox.getItemCount() > 0) {
                  actionSubjListBox.removeItem(0);
                }
                actionSubjListBox.addItem("");
                actionVerbListBox.addItem("");
                for(int i = 0; i < thenSubjListBoxGeneric.getItemCount(); i++){
                  actionSubjListBox.addItem(thenSubjListBoxGeneric.getValue(i));
                }
              }

              if(actionSubjSelected){
                  int index = actionTypeListBox.getSelectedIndex() - 1;
                  if (index >= 0) {
                    ListBoxThenSubjFieldClicked(actionSubjListBox, actionVerbListBox, actionType, index);
                  } else {
                    if (actionVerbListBox.getItemCount() > 0) {
                      while (actionVerbListBox.getItemCount() > 0) {
                        actionVerbListBox.removeItem(0);
                      }
                    }
                    thenTextBox.setText("");
                    thenTextBox.setVisible(false);
                  }
              }
            }
          });
          //same of when subj
          actionSubjListBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
              int ruleIndex = Integer.parseInt(innerVerticalPanel.getTitle().split("Rule ")[1]) - 1;
              int actionIndex = Integer.parseInt(horizontalPanelAction.getTitle().split("Action ")[1]) - 1;
              //selected is the action type select by the user
              String actionType = rulesListBoxes.get(screenName).get(ruleIndex).getActions().get(actionIndex)
                      .getThenType().getValue(actionTypeListBox.getSelectedIndex());

              int index = actionSubjListBox.getSelectedIndex() - 1;
              if(index >= 0){
                String viewClickedType = ListBoxThenSubjFieldClicked(actionSubjListBox, actionVerbListBox, actionType, index);
                rulesListBoxes.get(screenName).get(ruleIndex).getActions().get(actionIndex).setViewClickedType(viewClickedType);
              }
              else{
                if(actionVerbListBox.getItemCount() > 0){
                  while(actionVerbListBox.getItemCount() > 0){
                    actionVerbListBox.removeItem(0);
                  }
                }
                thenTextBox.setText("");
                thenTextBox.setVisible(false);
              }
            }
          });

          //same of when subj
          actionVerbListBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
              int ruleIndex = Integer.parseInt(innerVerticalPanel.getTitle().split("Rule ")[1]) - 1;
              int actionIndex = Integer.parseInt(horizontalPanelAction.getTitle().split("Action ")[1]) - 1;
              //selected is the action type select by the user
              String actionVerb = rulesListBoxes.get(screenName).get(ruleIndex).getActions().get(actionIndex)
                      .getThenVerb().getValue(actionVerbListBox.getSelectedIndex());
              ListBoxThenVerbFieldClicked(actionVerb, thenTextBox);
            }
          });

          innerVerticalPanel.add(horizontalPanelWhen);
          innerVerticalPanel.add(horizontalPanelAction);
          rulesListBoxes.get(screenName).get(newRule.getIndex()).getRulesThenPanel().add(horizontalPanelAction);

          Button addCond = new Button();
          addCond.setText("Add condition");

          Button addAction = new Button();
          addAction.setText("Add action");

          Button copyRule = new Button();
          copyRule.setText("Copy rule");

          Button deleteRule = new Button();
          deleteRule.setText("Delete rule");

          HorizontalPanel horizontalPanelButton = new HorizontalPanel();
          horizontalPanelButton.add(addCond);
          horizontalPanelButton.add(addAction);
          horizontalPanelButton.add(copyRule);
          horizontalPanelButton.add(deleteRule);

          innerVerticalPanel.add(horizontalPanelButton);

          deleteMainAction.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
              deleteActionHandler(horizontalPanelAction, innerVerticalPanel);
            }
          });

          addAction.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
              int ruleIndex = Integer.parseInt(innerVerticalPanel.getTitle().split("Rule ")[1]) - 1;
              final int index = rulesListBoxes.get(screenName).get(ruleIndex).getActions().size();
              //Window.alert("Rule: " + ruleIndex + ", Action: " + index);

              final ListBox otherActionTypeListBox = new ListBox();
              otherActionTypeListBox.getElement().getStyle().setWidth(150, Style.Unit.PX);
              otherActionTypeListBox.addItem("");
              otherActionTypeListBox.addItem("Set");
              otherActionTypeListBox.addItem("Call");
              otherActionTypeListBox.addItem("Open");

              final ListBox otherActionSubjListBox = new ListBox();
              //otherActionSubjListBox.addItem("");
              otherActionSubjListBox.getElement().getStyle().setWidth(150, Style.Unit.PX);
              final ListBox otherActionVerbListBox = new ListBox();
              //otherActionVerbListBox.addItem("");
              otherActionVerbListBox.getElement().getStyle().setWidth(150, Style.Unit.PX);
              final TextBox thenTextBox = new TextBox();
              thenTextBox.getElement().getStyle().setWidth(150, Style.Unit.PX);
              String heightStr = otherActionVerbListBox.getElement().getStyle().getHeight();
              double height = 25.0;
              if(heightStr != null && !heightStr.equals("")){
                height = Double.parseDouble(heightStr);
              }
              thenTextBox.getElement().getStyle().setHeight(height, Style.Unit.PX);
              thenTextBox.setVisible(false);

              Button deleteOtherAction = new Button();
              deleteOtherAction.setText("DEL");

              rulesListBoxes.get(screenName).get(ruleIndex).getActions().add(new Action(index, otherActionTypeListBox,
                      otherActionSubjListBox, otherActionVerbListBox, thenTextBox, deleteOtherAction));

              final Label hiddenIndexLabel = new Label();
              hiddenIndexLabel.setVisible(false);
              hiddenIndexLabel.setText(String.valueOf(index));

              final Label thenLabel = new Label();
              thenLabel.setText("Then");
              thenLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
              HorizontalPanel thenLabelContainer = new HorizontalPanel();
              thenLabelContainer.getElement().getStyle().setWidth(50, Style.Unit.PX);
              thenLabelContainer.add(thenLabel);

              final HorizontalPanel horizontalPanelAction_n = new HorizontalPanel();
              horizontalPanelAction_n.setTitle("Action " +
                      String.valueOf(rulesListBoxes.get(screenName).get(ruleIndex).getRulesThenPanel().size() + 1));
              horizontalPanelAction_n.add(hiddenIndexLabel);
              horizontalPanelAction_n.add(thenLabelContainer);
              horizontalPanelAction_n.add(new HTML("<hr  style=\"width:75px;\" />"));
              horizontalPanelAction_n.add(otherActionTypeListBox);
              horizontalPanelAction_n.add(new HTML("<hr  style=\"width:75px;\" />"));
              horizontalPanelAction_n.add(otherActionSubjListBox);
              horizontalPanelAction_n.add(new HTML("<hr  style=\"width:75px;\" />"));
              horizontalPanelAction_n.add(otherActionVerbListBox);

              horizontalPanelAction_n.add(thenTextBox);
              horizontalPanelAction_n.add(deleteOtherAction);

              for(int i = 0; i < thenSubjListBoxGeneric.getItemCount(); i++){
                otherActionSubjListBox.addItem(thenSubjListBoxGeneric.getValue(i));
              }

              //same of when subj
              otherActionTypeListBox.addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent changeEvent) {
                  int ruleIndex = Integer.parseInt(innerVerticalPanel.getTitle().split("Rule ")[1]) - 1;
                  int actionIndex = Integer.parseInt(horizontalPanelAction_n.getTitle().split("Action ")[1]) - 1;
                  //selected is the action type select by the user
                  String actionType = rulesListBoxes.get(screenName).get(ruleIndex).getActions().get(actionIndex)
                          .getThenType().getValue(otherActionTypeListBox.getSelectedIndex());
                  String actionSubj = rulesListBoxes.get(screenName).get(ruleIndex).getActions().get(actionIndex)
                          .getThenSubj() != null ?
                          otherActionSubjListBox.getSelectedIndex() != -1 ?
                          rulesListBoxes.get(screenName).get(ruleIndex).getActions().get(actionIndex)
                                  .getThenSubj().getValue(otherActionSubjListBox.getSelectedIndex()) : null
                          : null;
                  boolean actionSubjSelected = actionSubj != null && !actionSubj.equals("");

                  if(otherActionTypeListBox.getSelectedItemText().equals("Open")){
                    while (otherActionSubjListBox.getItemCount() > 0) {
                      otherActionSubjListBox.removeItem(0);
                    }
                    while (otherActionVerbListBox.getItemCount() > 0) {
                      otherActionVerbListBox.removeItem(0);
                    }
                    otherActionSubjListBox.addItem("");
                    otherActionVerbListBox.addItem("");
                    otherActionSubjListBox.addItem("another screen");
                  }
                  else{
                    while (otherActionSubjListBox.getItemCount() > 0) {
                      otherActionSubjListBox.removeItem(0);
                    }
                    otherActionSubjListBox.addItem("");
                    otherActionVerbListBox.addItem("");
                    for(int i = 0; i < thenSubjListBoxGeneric.getItemCount(); i++){
                      otherActionSubjListBox.addItem(thenSubjListBoxGeneric.getValue(i));
                    }
                  }

                  if(actionSubjSelected){
                    int index = otherActionTypeListBox.getSelectedIndex() - 1;
                    if(index >= 0){
                      ListBoxThenSubjFieldClicked(otherActionSubjListBox, otherActionVerbListBox, actionType, index);
                    }
                    else{
                      if(otherActionVerbListBox.getItemCount() > 0){
                        while(otherActionVerbListBox.getItemCount() > 0){
                          otherActionVerbListBox.removeItem(0);
                        }
                      }
                      thenTextBox.setText("");
                      thenTextBox.setVisible(false);
                    }
                  }
                }
              });

              //same of when subj
              otherActionSubjListBox.addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent changeEvent) {
                  int ruleIndex = Integer.parseInt(innerVerticalPanel.getTitle().split("Rule ")[1]) - 1;
                  int actionIndex = Integer.parseInt(horizontalPanelAction_n.getTitle().split("Action ")[1]) - 1;
                  //selected is the action type select by the user
                  String actionType = rulesListBoxes.get(screenName).get(ruleIndex).getActions().get(actionIndex)
                          .getThenType().getValue(otherActionTypeListBox.getSelectedIndex());
                  String actionSubj = rulesListBoxes.get(screenName).get(ruleIndex).getActions().get(actionIndex)
                          .getThenSubj().getValue(otherActionSubjListBox.getSelectedIndex());

                  int index = otherActionSubjListBox.getSelectedIndex() - 1;
                  if(index >= 0){
                    String viewClickedType = ListBoxThenSubjFieldClicked(otherActionSubjListBox, otherActionVerbListBox, actionType, index);
                    rulesListBoxes.get(screenName).get(ruleIndex).getActions().get(actionIndex).setViewClickedType(viewClickedType);
                  }
                  else{
                    if(otherActionVerbListBox.getItemCount() > 0){
                      while(otherActionVerbListBox.getItemCount() > 0){
                        otherActionVerbListBox.removeItem(0);
                      }
                    }
                    thenTextBox.setText("");
                    thenTextBox.setVisible(false);
                  }
                }
              });

              otherActionVerbListBox.addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent changeEvent) {
                  int ruleIndex = Integer.parseInt(innerVerticalPanel.getTitle().split("Rule ")[1]) - 1;
                  int actionIndex = Integer.parseInt(horizontalPanelAction.getTitle().split("Action ")[1]) - 1;
                  //selected is the action type select by the user
                  String actionVerb = rulesListBoxes.get(screenName).get(ruleIndex).getActions().get(actionIndex)
                          .getThenVerb().getValue(actionVerbListBox.getSelectedIndex());
                  ListBoxThenVerbFieldClicked(actionVerb, thenTextBox);
                }
              });

              int lastThenIndex = rulesListBoxes.get(screenName).get(ruleIndex).getRulesThenPanel().size() - 1;
              int temp = innerVerticalPanel.getWidgetIndex(rulesListBoxes.get(screenName).get(ruleIndex).getRulesThenPanel().get(lastThenIndex)) + 1;
              //Window.alert("Action will be insert in position: " + String.valueOf(temp));
              rulesListBoxes.get(screenName).get(ruleIndex).getRulesThenPanel().add(horizontalPanelAction_n);
              innerVerticalPanel.insert(horizontalPanelAction_n, temp);

              if(deleteMainAction != null){
                deleteMainAction.setEnabled(true);
              }

              deleteOtherAction.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent clickEvent) {
                  deleteActionHandler(horizontalPanelAction_n, innerVerticalPanel);
                }
              });
            }
          });

          addCond.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {

              int ruleIndex = Integer.parseInt(innerVerticalPanel.getTitle().split("Rule ")[1]) - 1;
              int size = rulesListBoxes.get(screenName).get(ruleIndex).getConditions().size();

              HorizontalPanel ifLabelContainer = new HorizontalPanel();
              final ListBox andOrListBox = new ListBox();

              Label ifLabel = new Label();
              ifLabel.setText("If");
              ifLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
              ifLabelContainer.getElement().getStyle().setWidth(50, Style.Unit.PX);
              ifLabelContainer.add(ifLabel);

              if(size != 0){
                ifLabel.setText("");
                andOrListBox.addItem("");
                andOrListBox.addItem("AND");
                andOrListBox.addItem("OR");
                andOrListBox.getElement().getStyle().setWidth(150, Style.Unit.PX);
              }

              final ListBox ifSubjListBox = new ListBox();
              ifSubjListBox.addItem("");
              ifSubjListBox.getElement().getStyle().setWidth(150, Style.Unit.PX);
              final ListBox ifVerbListBox = new ListBox();
              ifVerbListBox.getElement().getStyle().setWidth(150, Style.Unit.PX);

              //rulesListBoxes.get(rulesListBoxesCount - 1).setIfSubj(ifSubjListBox);
              //rulesListBoxes.get(rulesListBoxesCount - 1).setIfVerb(ifVerbListBox);

              final TextBox ifTextBox = new TextBox();
              ifTextBox.getElement().getStyle().setWidth(150, Style.Unit.PX);
              String heightStr = ifVerbListBox.getElement().getStyle().getHeight();
              double height = 25.0;
              if(heightStr != null && !heightStr.equals("")){
                height = Double.parseDouble(heightStr);
              }
              ifTextBox.getElement().getStyle().setHeight(height, Style.Unit.PX);
              ifTextBox.setVisible(false);

              Button deleteCond = new Button();
              deleteCond.setText("DEL");

              rulesListBoxes.get(screenName).get(ruleIndex).addCondition(new Condition(ifSubjListBox, ifVerbListBox, ifTextBox));//, deleteCond));

              final HorizontalPanel horizontalPanelIf = new HorizontalPanel();
              //Window.alert("IfIndex: " +
              //        String.valueOf(rulesListBoxes.get(screenName).get(ruleIndex).getRulesIfPanel().size() + 1));
              horizontalPanelIf.setTitle("Condition " +
                      String.valueOf(rulesListBoxes.get(screenName).get(ruleIndex).getRulesIfPanel().size() + 1));
              horizontalPanelIf.add(ifLabelContainer);
              HTML line = new HTML("<hr  style=\"width:75px;\" />");
              horizontalPanelIf.add(line);
              if(size != 0){
                rulesListBoxes.get(screenName).get(ruleIndex).getConditions().get(size).setANDOR(andOrListBox);
                horizontalPanelIf.add(andOrListBox);
                horizontalPanelIf.add(new HTML("<hr  style=\"width:75px;\" />"));
              }
              horizontalPanelIf.add(ifSubjListBox);
              horizontalPanelIf.add(new HTML("<hr  style=\"width:75px;\" />"));
              horizontalPanelIf.add(ifVerbListBox);
              horizontalPanelIf.add(ifTextBox);

              //Same of whensubj
              ifSubjListBox.addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent changeEvent) {
                  int ruleIndex = Integer.parseInt(innerVerticalPanel.getTitle().split("Rule ")[1]) - 1;
                  int conditionIndex = Integer.parseInt(horizontalPanelIf.getTitle().split("Condition ")[1]) - 1;
                  String viewClickedType = ListBoxIfSubjFieldClicked(ifSubjListBox, ifVerbListBox);
                  rulesListBoxes.get(screenName).get(ruleIndex).getConditions().get(conditionIndex).setViewClickedType(viewClickedType);
                }
              });

              //Some "if verb" require an extra input text field
              ifVerbListBox.addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent changeEvent) {
                  ListBoxIfVerbFieldClicked(ifVerbListBox, ifTextBox);
                }
              });

              for(int i = 0; i < ifSubjListBoxGeneric.getItemCount(); i++){
                ifSubjListBox.addItem(ifSubjListBoxGeneric.getValue(i));
              }
              for(int i = 0; i < ifVerbListBoxGeneric.getItemCount(); i++){
                ifVerbListBox.addItem(ifVerbListBoxGeneric.getValue(i));
              }

              int lastIfIndex = rulesListBoxes.get(screenName).get(ruleIndex).getRulesIfPanel().size() - 1;
              int temp = -1;
              if(lastIfIndex == -1){
                temp = innerVerticalPanel.getWidgetIndex(horizontalPanelWhen) + 1;
              }
              else{
                temp = innerVerticalPanel.getWidgetIndex(rulesListBoxes.get(screenName).get(ruleIndex).getRulesIfPanel().get(lastIfIndex)) + 1;
              }
              //Window.alert("Condition will be insert in position: " + String.valueOf(temp));
              rulesListBoxes.get(screenName).get(ruleIndex).getRulesIfPanel().add(horizontalPanelIf);
              innerVerticalPanel.insert(horizontalPanelIf, temp);

              deleteCond.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent clickEvent) {
                  int indexToRemove = Integer.parseInt(horizontalPanelIf.getTitle().split("Condition ")[1]) - 1;
                  int ifPos = innerVerticalPanel.getWidgetIndex(horizontalPanelIf);
                  //Window.alert("Condition index: " + (indexToRemove) + ", Condition pos: " + ifPos);
                  int ruleIndex = Integer.parseInt(innerVerticalPanel.getTitle().split("Rule ")[1]) - 1;
                  //rulesListBoxes.get(screenName).get(ruleIndex).getConditions().get(indexToRemove).setIfSubj(null);
                  //rulesListBoxes.get(screenName).get(ruleIndex).getConditions().get(indexToRemove).setIfVerb(null);
                  rulesListBoxes.get(screenName).get(ruleIndex).getConditions().remove(indexToRemove);
                  rulesListBoxes.get(screenName).get(ruleIndex).getRulesIfPanel().remove(indexToRemove);
                  boolean res = innerVerticalPanel.remove(ifPos);

                  for(HorizontalPanel ifPanel : rulesListBoxes.get(screenName).get(ruleIndex).getRulesIfPanel()){
                    int i = Integer.parseInt(ifPanel.getTitle().split("Condition ")[1]) - 1;
                    if(i > indexToRemove){
                      ifPanel.setTitle("Condition " + String.valueOf(i - 1));
                    }
                  }
                }
              });

              horizontalPanelIf.add(deleteCond);
            }
          });

          copyRule.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
              Window.alert("WIP");
            }
          });

          deleteRule.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
              int indexToRemove = Integer.parseInt(innerVerticalPanel.getTitle().split("Rule ")[1]) - 1;
              for(Rule rule : rulesListBoxes.get(screenName)){
                VerticalPanel rulePanel = rule.getRulePanel();
                int index = Integer.parseInt(rulePanel.getTitle().split("Rule ")[1]) - 1;
                if(index > indexToRemove){
                  rulePanel.setTitle("Rule " + String.valueOf(index - 1));
                }
              }

              verticalPanel.remove(rulesListBoxes.get(screenName).get(indexToRemove).getRulePanel());
              rulesListBoxes.get(screenName).remove(indexToRemove);

              if(rulesListBoxes.get(screenName).size() == 0){
                confirmButton.setEnabled(false);
              }
            }
          });

          for(int i = 0; i < whenSubjListBoxGeneric.getItemCount(); i++){
            whenSubjListBox.addItem(whenSubjListBoxGeneric.getValue(i));
          }/*
          for(int i = 0; i < whenVerbListBoxGeneric.getItemCount(); i++){
            whenVerbListBox.addItem(whenVerbListBoxGeneric.getValue(i));
          }
          for(int i = 0; i < thenSubjListBoxGeneric.getItemCount(); i++){
            actionSubjListBox.addItem(thenSubjListBoxGeneric.getValue(i));
          }
          for(int i = 0; i < thenVerbListBoxGeneric.getItemCount(); i++){
            actionVerbListBox.addItem(thenVerbListBoxGeneric.getValue(i));
          }*/


          verticalPanel.add(innerVerticalPanel);
        }
      });

      //inputTextBox = new TextArea();
      //DEBUG:
      //inputTextBox.setText("when button1 is clicked if label1 is hidden then label1 is hidden");
      //inputTextBox.setText("when button1 is clicked if label1 is hidden then label1 is shown and open page1");
      //inputTextBox.setText("when button1 is clicked if label1 is hidden then button2 is shown throw (when button2 is
      // clicked then open page1)");
      /*inputTextBox.setText("when button1 is clicked if label1 is hidden then button2 is shown throw (when button2 is" +
              " clicked then open page1) and label1 is shown");
      inputTextBox.setWidth("100%"); //fill width
      inputTextBox.getElement().setPropertyString("placeholder", MESSAGES.inputTextBoxAntLR()); //set a placeholder
      inputTextBox.getElement().setAttribute("style", "width: 100%; box-sizing: border-box;"); //set css style
      inputTextBox.getElement().setAttribute("wrap","on");*/

      resultLabel = new Label();
      //resultLabel.setWidth("100%"); //fill width
      resultLabel.setText(MESSAGES.resultLabelAntLR());
      confirmButton.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent clickEvent) {
                int ruleCount = 0;
                //Rule to formatted string
                for(Rule rule : rulesListBoxes.get(screenName)) {
                  String value = "";
                  //EVENT
                  value += "when " + rule.getWhenSubj().getSelectedItemText() + " " +
                          rule.getViewClickedType() + " " +
                          rule.getWhenVerb().getSelectedItemText() + " ";

                  //CONDITION
                  if(rule.getConditions() != null && rule.getConditions().size() > 0){
                    value += "if ";
                    int i = 0;
                    for(Condition condition : rule.getConditions()){
                      if(i > 0){
                        value += " " + condition.getANDOR().getSelectedItemText().toUpperCase() + " ";
                      }
                      String conditionVerb = condition.getIfVerb().getSelectedItemText().contains("not") ?
                              "not " + condition.getIfVerb().getSelectedItemText().replace("not ", "") :
                              condition.getIfVerb().getSelectedItemText();
                      value += condition.getIfSubj().getSelectedItemText().toLowerCase() + " " +
                              condition.getViewClickedType().toLowerCase() + " " +
                              conditionVerb.toLowerCase() + " " +
                              condition.getIfTextBox().getText().toLowerCase();
                      i++;
                    }
                  }

                  //ACTION
                  value += "then ";
                  int i = 0;
                  for(Action action : rule.getActions()){
                    //Window.alert("Parsing action " + i + " with type " + action.getThenType().getSelectedItemText());
                    if(i > 0){
                      value += " AND ";
                    }
                    if(action.getThenType().getSelectedItemText().contains("Open")) {
                      Window.alert("open: \naction.getThenType().getSelectedItemText(): " + action.getThenType().getSelectedItemText() + "\n" +
                              "action.getThenSubj().getSelectedItemText(): " + action.getThenSubj().getSelectedItemText());
                    }
                    value += action.getThenType().getSelectedItemText().contains("Open") ?
                            action.getThenType().getSelectedItemText().toLowerCase() + " " +
                                    action.getThenSubj().getSelectedItemText().toLowerCase() + " " +
                                    action.getThenVerb().getSelectedItemText().toLowerCase() + " " +
                                    action.getThenTextBox().getText() :
                            action.getThenType().getSelectedItemText().toLowerCase() + " " +
                                    action.getThenSubj().getSelectedItemText().toLowerCase() + " " +
                                    action.getViewClickedType().toLowerCase() + " " +
                                    action.getThenVerb().getSelectedItemText().toLowerCase() + " " +
                                    action.getThenTextBox().getText().toLowerCase();
                    i++;
                  }

                  Window.alert("Rule " + ruleCount + ": \n" + value);
                  ruleCount++;

                  //String input = "se il button1 viene cliccato, allora la lista viene mostrata";
                  ///Custom Lexer ->  A lexer takes the individual characters and transforms them
                  // in tokens, the atoms that the parser uses to create the logical structure
                  TesiLexer tesiLexer = new TesiLexer(CharStreams.fromString(value));
                  tesiLexer.removeErrorListeners();
                  //Create syntax error listener
                  SyntaxErrorListener errorListener = new SyntaxErrorListener();
                  //Add it to lexer
                  tesiLexer.addErrorListener(errorListener);
                  ///https://www.antlr.org/api/Java/org/antlr/v4/runtime/CommonTokenStream.html
                  CommonTokenStream commonTokenStream = new CommonTokenStream(tesiLexer);

                  ///Custom Parser
                  TesiParser tesiParser = new TesiParser(commonTokenStream);
                  tesiParser.removeErrorListeners();
                  //Add prev syntax error listener to parser
                  tesiParser.addErrorListener(errorListener);

                  tesiParser.setBuildParseTree(true);
                  //Set the root of parse tree as upper parser rules i've defined, aka blocks
                  TesiParser.BlockContext treeRoot = tesiParser.block();

                  TesiParserBaseVisitor visitor = new TesiParserBaseVisitor();
                  visitor.visit(treeRoot);

                  //Window.alert("Parsed");
                  if(tesiParser.getNumberOfSyntaxErrors() == 0){
                    //Se ha riconosciuto regole
                    if(tesiParser.getRuleNames() != null || tesiParser.getRuleNames().length == 0) {
                      resultLabel.setText("Rule detected successfully");

                      //Window.alert("before getJson");
                      JSONObject ruleJSON = getJSONBlock(treeRoot);
                      //Window.alert("after getJson");

                      //Window.alert("Json: \n" + rule.toString());

                      //Window.alert( "rule: \n\n" + rule.toString());
                      YaBlocksEditor editor =
                              (YaBlocksEditor) Ode.getInstance().getCurrentFileEditor();
                      editor.insertBlock(ruleJSON.toString());
                    }
                    else{
                      resultLabel.setText("No rules detected.");
                    }
                  }
                  else{
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Check the following " + tesiParser.getNumberOfSyntaxErrors()  + " error/s and try again:");
                    for(String error : errorListener.getSyntaxErrorList()){
                      stringBuilder.append("\n- " + error);
                    }
                    resultLabel.setText(stringBuilder.toString());
                  }
                }



        }
      });

      //add all to the vertical panel
      //panel2.add(inputTextBox);
      panel2.add(verticalPanel);
      panel2.add(addRule);
      panel2.add(confirmButton);
      panel2.add(resultLabel);
      panel.add(scrollPanel);

      panel.setCellHorizontalAlignment(confirmButton, HorizontalPanel.ALIGN_CENTER);
      panel.setWidth("100%");
      panel.setHeight("100%");
    }
    else{
      panel.add(scrollPanel);
      panel.add(new Label());
      panel.add(buttonPanel);
      panel.setCellHorizontalAlignment(buttonPanel, HorizontalPanel.ALIGN_CENTER);
    }
    initWidget(panel);
  }

  //feduss
  private void deleteActionHandler(HorizontalPanel horizontalPanelThen, VerticalPanel innerVerticalPanel) {
    int indexToRemove = Integer.parseInt(horizontalPanelThen.getTitle().split("Action ")[1]) - 1;
    int thenPos = innerVerticalPanel.getWidgetIndex(horizontalPanelThen);
    Window.alert("Action index: " + (indexToRemove) + ", Action pos: " + thenPos);
    int ruleIndex = Integer.parseInt(innerVerticalPanel.getTitle().split("Rule ")[1]) - 1;
    //rulesListBoxes.get(screenName).get(ruleIndex).getActions().get(indexToRemove).setThenSubj(null);
    //rulesListBoxes.get(screenName).get(ruleIndex).getActions().get(indexToRemove).setThenVerb(null);
    rulesListBoxes.get(screenName).get(ruleIndex).getActions().remove(indexToRemove).setThenVerb(null);
    rulesListBoxes.get(screenName).get(ruleIndex).getRulesThenPanel().remove(indexToRemove);
    boolean res = innerVerticalPanel.remove(thenPos);
    for(HorizontalPanel thenPanel : rulesListBoxes.get(screenName).get(ruleIndex).getRulesThenPanel()){
      int i = Integer.parseInt(thenPanel.getTitle().split("Action ")[1]) - 1;
      if(i > indexToRemove){
        thenPanel.setTitle("Action " + String.valueOf(i - 1));
      }
    }

    if(rulesListBoxes.get(screenName).get(ruleIndex).getActions().size() == 1){
      rulesListBoxes.get(screenName).get(ruleIndex).getActions().get(0).getDeleteButton().setEnabled(false);
    }
  }

  //feduss
  private String ListBoxWhenSubjFieldClicked(ListBox subjList, ListBox verbList) {
    int index = subjList.getSelectedIndex() - 1;
    if(index >= 0){
      //Window.alert("Selected: " + userViewsList.get(screenName).get(index).getPropertyValue("Name") + "(" + String.valueOf(index) + ")");

      while(verbList.getItemCount() > 0){
        verbList.removeItem(0);
      }
      verbList.addItem("");

      MockComponent component = userViewsList.get(screenName).get(index);
      String compType = component.getType();

      switch(compType){
        case "Button":
          verbList.addItem("is clicked");
          verbList.addItem("is focused");
          verbList.addItem("is long clicked");
          verbList.addItem("lost focus");
          verbList.addItem("is touched down");
          verbList.addItem("is touched up");
          break;
        case "DatePicker":
          verbList.addItem("is clicked");
          verbList.addItem("got focus");
          verbList.addItem("is long clicked");
          verbList.addItem("lost focus");
          verbList.addItem("is touched down");
          verbList.addItem("is touched up");
          break;
        case "Image":
          verbList.addItem("is clicked");
          break;
        case "ListPicker":
          verbList.addItem("is after picked");
          verbList.addItem("is before picked");
          verbList.addItem("got focus");
          verbList.addItem("lost focus");
          verbList.addItem("is touched down");
          verbList.addItem("is touched up");
          break;
        case "ListView":
          verbList.addItem("is after picked");
          break;
        case "TextBox":
        case "PasswordTextBox":
          verbList.addItem("got focus");
          verbList.addItem("lost focus");
          break;
        case "Slider":
          verbList.addItem("position changed to"); //todo insert position selection
          break;
        case "Spinner":
          verbList.addItem("after selecting"); //todo insert "selection" selection
          break;
        case "Switch":
          verbList.addItem("changed");
          verbList.addItem("got focus");
          verbList.addItem("lost focus");
          break;
        case "TimePicker":
          verbList.addItem("after time set");
          verbList.addItem("got focus");
          verbList.addItem("lost focus");
          verbList.addItem("is touched down");
          verbList.addItem("is touched up");
          break;
        //TODO add notifier?
        default:
          Window.alert("NotHandledCase: " + compType);
          verbList.addItem("is hidden");
          verbList.addItem("is make visible");
          verbList.addItem("is disabled");
          verbList.addItem("is shown");
          verbList.addItem("is visible");
      }

      return compType;
    }
    return "";
  }

  //feduss
  private String ListBoxIfSubjFieldClicked(ListBox subjList, ListBox verbList) {
    int index = subjList.getSelectedIndex() - 1;
    if(index >= 0) {
      //Window.alert("Selected: " + userViewsList.get(screenName).get(index).getPropertyValue("Name") + "(" + String.valueOf(index) + ")");

      while (verbList.getItemCount() > 0) {
        verbList.removeItem(0);
      }
      verbList.addItem("");

      MockComponent component = userViewsList.get(screenName).get(index);
      String compType = component.getType();

      switch (compType) {
        case "Button":
          verbList.addItem("background color is"); //todo add text input //todo in logic there is the equal operator
          verbList.addItem("background color is not"); //todo add text input //todo in logic there is the equal operator
          verbList.addItem("is enabled");
          verbList.addItem("is not enabled");
          verbList.addItem("font is bold");
          verbList.addItem("font is not bold");
          verbList.addItem("font is italic");
          verbList.addItem("font is not italic");
          verbList.addItem("font size is"); //todo add text input
          verbList.addItem("font size is not"); //todo add text input
          verbList.addItem("height is"); //todo add text input
          verbList.addItem("height is not"); //todo add text input
          verbList.addItem("image is"); //?? //todo add text input
          verbList.addItem("image is not");//?? //todo add text input
          verbList.addItem("shows feedback");
          verbList.addItem("doesn't show feedback");
          verbList.addItem("text is"); //todo add text input
          verbList.addItem("text is not"); //todo add text input
          verbList.addItem("text color is"); //todo add text input
          verbList.addItem("text color is not"); //todo add text input
          verbList.addItem("is visible");
          verbList.addItem("is not visible");
          verbList.addItem("width is"); //todo add text input
          verbList.addItem("width is not"); //todo add text input
          break;
        case "Label":
          verbList.addItem("background color is"); //todo add text input //todo in logic there is the equal operator
          verbList.addItem("background color is not"); //todo add text input //todo in logic there is the equal operator
          verbList.addItem("font size is"); //todo add text input
          verbList.addItem("font size is not"); //todo add text input
          verbList.addItem("has margins");
          verbList.addItem("doesn't have margins");
          verbList.addItem("height is"); //todo add text input
          verbList.addItem("height is not"); //todo add text input
          verbList.addItem("text is"); //todo add text input
          verbList.addItem("text is not"); //todo add text input
          verbList.addItem("text color is"); //todo add text input
          verbList.addItem("text color is not"); //todo add text input
          verbList.addItem("is visible");
          verbList.addItem("is not visible");
          verbList.addItem("width is"); //todo add text input
          verbList.addItem("width is not"); //todo add text input
          break;
        case "DatePicker":
          verbList.addItem("background color is"); //todo add text input //todo in logic there is the equal operator
          verbList.addItem("background color is not"); //todo add text input //todo in logic there is the equal operator
          verbList.addItem("day is"); //todo add text input
          verbList.addItem("day is not"); //todo add text input
          verbList.addItem("is enabled");
          verbList.addItem("is not enabled");
          verbList.addItem("font is bold");
          verbList.addItem("font is not bold");
          verbList.addItem("font is italic");
          verbList.addItem("font is not italic");
          verbList.addItem("font size is"); //todo add text input
          verbList.addItem("font size is not"); //todo add text input
          verbList.addItem("height is"); //todo add text input
          verbList.addItem("height is not"); //todo add text input
          verbList.addItem("image is"); //?? //todo add text input
          verbList.addItem("image is not");//?? //todo add text input
          verbList.addItem("instant is"); //todo add text input --> ai2.appinventor.mit.edu/reference/components/userinterface.html
          verbList.addItem("instant is not"); //todo add text input
          verbList.addItem("month is"); //todo add text input
          verbList.addItem("month is not"); //todo add text input
          verbList.addItem("month in text is"); //todo add text input --> ai2.appinventor.mit.edu/reference/components/userinterface.html
          verbList.addItem("month in text is not"); //todo add text input
          verbList.addItem("shows feedback");
          verbList.addItem("doesn't show feedback");
          verbList.addItem("text is"); //todo add text input
          verbList.addItem("text is not"); //todo add text input
          verbList.addItem("text color is"); //todo add text input
          verbList.addItem("text color is not"); //todo add text input
          verbList.addItem("is visible");
          verbList.addItem("is not visible");
          verbList.addItem("width is"); //todo add text input
          verbList.addItem("width is not"); //todo add text input
          verbList.addItem("year is"); //todo add text input
          verbList.addItem("year is not"); //todo add text input
          break;
        case "Image":
          verbList.addItem("is clickable");
          verbList.addItem("is not clickable");
          verbList.addItem("height is"); //todo add text input
          verbList.addItem("height is not"); //todo add text input
          verbList.addItem("picture is"); //todo add text input
          verbList.addItem("picture is not"); //todo add text input
          verbList.addItem("rotation angle is"); //todo add text input
          verbList.addItem("rotation angle is not"); //todo add text input
          verbList.addItem("scaling is"); //todo add text input
          verbList.addItem("scaling is not"); //todo add text input
          verbList.addItem("is visible");
          verbList.addItem("is not visible");
          verbList.addItem("width is"); //todo add text input
          verbList.addItem("width is not"); //todo add text input
          break;
        case "ListPicker":
          verbList.addItem("background color is"); //todo add text input //todo in logic there is the equal operator
          verbList.addItem("background color is not"); //todo add text input //todo in logic there is the equal operator
          verbList.addItem("elements are");
          verbList.addItem("elements are not");
          verbList.addItem("is enabled");
          verbList.addItem("is not enabled");
          verbList.addItem("font is bold");
          verbList.addItem("font is not bold");
          verbList.addItem("font is italic");
          verbList.addItem("font is not italic");
          verbList.addItem("font size is"); //todo add text input
          verbList.addItem("font size is not"); //todo add text input
          verbList.addItem("height is"); //todo add text input
          verbList.addItem("height is not"); //todo add text input
          verbList.addItem("image is"); //?? //todo add text input
          verbList.addItem("image is not");//?? //todo add text input
          verbList.addItem("item background color is"); //todo add text input
          verbList.addItem("item background color is not"); //todo add text input
          verbList.addItem("item text color is"); //?? //todo add text input
          verbList.addItem("item text color is not");//?? //todo add text input
          verbList.addItem("selection is"); //todo add text input
          verbList.addItem("selection is not"); //todo add text input
          verbList.addItem("selection index is"); //?? //todo add text input
          verbList.addItem("selection index is not");//?? //todo add text input
          verbList.addItem("shows feedback");
          verbList.addItem("doesn't show feedback");
          verbList.addItem("shows filter bar");
          verbList.addItem("doesn't show filter bar");
          verbList.addItem("text is"); //?? //todo add text input
          verbList.addItem("text is not");//?? //todo add text input
          verbList.addItem("text color is"); //?? //todo add text input
          verbList.addItem("text color is not");//?? //todo add text input
          verbList.addItem("title is"); //?? //todo add text input
          verbList.addItem("title is not");//?? //todo add text input
          verbList.addItem("is visible");
          verbList.addItem("is not visible");
          verbList.addItem("width is"); //todo add text input
          verbList.addItem("width is not"); //todo add text input
          break;
        case "ListView":
          verbList.addItem("background color is"); //todo add text input //todo in logic there is the equal operator
          verbList.addItem("background color is not"); //todo add text input //todo in logic there is the equal operator
          verbList.addItem("elements are"); //todo add text input
          verbList.addItem("elements are not"); //todo add text input
          verbList.addItem("height is"); //todo add text input
          verbList.addItem("height is not"); //todo add text input
          verbList.addItem("selection is"); //todo add text input
          verbList.addItem("selection is not"); //todo add text input
          verbList.addItem("selection color is");//?? //todo add text input
          verbList.addItem("selection color is not");//?? //todo add text input
          verbList.addItem("selection index is"); //?? //todo add text input
          verbList.addItem("selection index is not");//?? //todo add text input
          verbList.addItem("shows filter bar");
          verbList.addItem("doesn't show filter bar");
          verbList.addItem("text color is"); //?? //todo add text input
          verbList.addItem("text color is not");//?? //todo add text input
          verbList.addItem("text size is"); //?? //todo add text input
          verbList.addItem("text size is not");//?? //todo add text input
          verbList.addItem("is visible");
          verbList.addItem("is not visible");
          verbList.addItem("width is"); //todo add text input
          verbList.addItem("width is not"); //todo add text input
          break;
        case "PasswordTextBox":
          verbList.addItem("background color is"); //todo add text input //todo in logic there is the equal operator
          verbList.addItem("background color is not"); //todo add text input //todo in logic there is the equal operator
          verbList.addItem("is enabled");
          verbList.addItem("is not enabled");
          verbList.addItem("font size is"); //todo add text input
          verbList.addItem("font size is not"); //todo add text input
          verbList.addItem("height is"); //todo add text input
          verbList.addItem("height is not"); //todo add text input
          verbList.addItem("hint is"); //todo add text input
          verbList.addItem("hint is not"); //todo add text input
          verbList.addItem("password is visible"); //todo add text input
          verbList.addItem("password is not visible"); //todo add text input
          verbList.addItem("text is"); //?? //todo add text input
          verbList.addItem("text is not");//?? //todo add text input
          verbList.addItem("text color is"); //?? //todo add text input
          verbList.addItem("text color is not");//?? //todo add text input
          verbList.addItem("is visible");
          verbList.addItem("is not visible");
          verbList.addItem("width is"); //todo add text input
          verbList.addItem("width is not"); //todo add text input
          break;
        case "Slider":
          verbList.addItem("color left is"); //todo add text input
          verbList.addItem("color left is not"); //todo add text input
          verbList.addItem("max value is"); //todo add text input
          verbList.addItem("max value is not"); //todo add text input
          verbList.addItem("thumb is enabled");
          verbList.addItem("thumb is not enabled");
          verbList.addItem("thumb position is"); //todo add text input
          verbList.addItem("thumb position is not"); //todo add text input
          verbList.addItem("is visible");
          verbList.addItem("is not visible");
          verbList.addItem("width is"); //todo add text input
          verbList.addItem("width is not"); //todo add text input
          break;
        case "Spinner":
          verbList.addItem("elements are"); //todo add text input
          verbList.addItem("elements are not"); //todo add text input
          verbList.addItem("height is"); //todo add text input
          verbList.addItem("height is not"); //todo add text input
          verbList.addItem("selection is"); //todo add text input
          verbList.addItem("selection is not"); //todo add text input
          verbList.addItem("prompt is"); //todo add text input
          verbList.addItem("prompt is not"); //todo add text input
          verbList.addItem("selection index is"); //?? //todo add text input
          verbList.addItem("selection index is not");//?? //todo add text input
          verbList.addItem("is visible");
          verbList.addItem("is not visible");
          verbList.addItem("width is"); //todo add text input
          verbList.addItem("width is not"); //todo add text input
          break;
        case "Switch":
          verbList.addItem("background color is"); //todo add text input //todo in logic there is the equal operator
          verbList.addItem("background color is not"); //todo add text input //todo in logic there is the equal operator
          verbList.addItem("is enabled");
          verbList.addItem("is not enabled");
          verbList.addItem("font size is"); //todo add text input
          verbList.addItem("font size is not"); //todo add text input
          verbList.addItem("height is"); //todo add text input
          verbList.addItem("height is not"); //todo add text input
          verbList.addItem("is on");
          verbList.addItem("is not on");
          verbList.addItem("text is");  //todo add text input
          verbList.addItem("text is not"); //todo add text input
          verbList.addItem("text color is"); //todo add text input
          verbList.addItem("text color is not"); //todo add text input
          verbList.addItem("thumb color active is"); //todo add text input
          verbList.addItem("thumb color active is not"); //todo add text input
          verbList.addItem("thumb color inactive is"); //todo add text input
          verbList.addItem("thumb color inactive is not"); //todo add text input
          verbList.addItem("track color active is"); //todo add text input
          verbList.addItem("track color active is not"); //todo add text input
          verbList.addItem("track color inactive is"); //todo add text input
          verbList.addItem("track color inactive is not"); //todo add text input
          verbList.addItem("is visible");
          verbList.addItem("is not visible");
          verbList.addItem("width is"); //todo add text input
          verbList.addItem("width is not"); //todo add text input
          break;
        case "TextBox":
          verbList.addItem("background color is"); //todo add text input //todo in logic there is the equal operator
          verbList.addItem("background color is not"); //todo add text input //todo in logic there is the equal operator
          verbList.addItem("is enabled");
          verbList.addItem("is not enabled");
          verbList.addItem("font size is"); //todo add text input
          verbList.addItem("font size is not"); //todo add text input
          verbList.addItem("height is"); //todo add text input
          verbList.addItem("height is not"); //todo add text input
          verbList.addItem("hint is"); //todo add text input
          verbList.addItem("hint is not"); //todo add text input
          verbList.addItem("is multiline");
          verbList.addItem("is not multiline");
          verbList.addItem("is number only");
          verbList.addItem("is not number only");
          verbList.addItem("is read only");
          verbList.addItem("is not read only");
          verbList.addItem("text is");  //todo add text input
          verbList.addItem("text is not"); //todo add text input
          verbList.addItem("text color is"); //todo add text input
          verbList.addItem("text color is not"); //todo add text input
          verbList.addItem("is visible");
          verbList.addItem("is not visible");
          verbList.addItem("width is"); //todo add text input
          verbList.addItem("width is not"); //todo add text input
          break;
        case "TimePicker":
          verbList.addItem("background color is"); //todo add text input //todo in logic there is the equal operator
          verbList.addItem("background color is not"); //todo add text input //todo in logic there is the equal operator
          verbList.addItem("is enabled");
          verbList.addItem("is not enabled");
          verbList.addItem("font is bold");
          verbList.addItem("font is not bold");
          verbList.addItem("font is italic");
          verbList.addItem("font is not italic");
          verbList.addItem("font size is"); //todo add text input
          verbList.addItem("font size is not"); //todo add text input
          verbList.addItem("height is"); //todo add text input
          verbList.addItem("height is not"); //todo add text input
          verbList.addItem("hour is"); //todo add text input
          verbList.addItem("hour is not"); //todo add text input
          verbList.addItem("image is"); //?? //todo add text input
          verbList.addItem("image is not");//?? //todo add text input
          verbList.addItem("instant is"); //todo add text input
          verbList.addItem("instant is not"); //todo add text input
          verbList.addItem("minute is"); //todo add text input
          verbList.addItem("minute is not"); //todo add text input
          verbList.addItem("shows feedback");
          verbList.addItem("doesn't show feedback");
          verbList.addItem("text is");  //todo add text input
          verbList.addItem("text is not"); //todo add text input
          verbList.addItem("text color is"); //todo add text input
          verbList.addItem("text color is not"); //todo add text input
          verbList.addItem("is visible");
          verbList.addItem("is not visible");
          verbList.addItem("width is"); //todo add text input
          verbList.addItem("width is not"); //todo add text input
          break;
        //TODO add notifier?
        default:
          Window.alert("NotHandledCase: " + compType);
      }

      return compType;
    }
    return "";
  }

  //feduss
  private void ListBoxIfVerbFieldClicked(ListBox verbList, TextBox ifTextBox) {
    int index = verbList.getSelectedIndex() - 1;
    //Window.alert("index: " + String.valueOf(index));
    if(index >= 0) {
      String verb = verbList.getSelectedItemText();
      //Window.alert("verb: " + verb);

      switch (verb) {
        case "background color is": //todo in logic there is the equal operator
        case "background color is not":
        case "font size is":
        case "font size is not":
        case "height is":
        case "height is not":
        case "image is":
        case "image is not":
        case "text is":
        case "text is not":
        case "text color is":
        case "text color is not":
        case "width is":
        case "width is not":
        case "day is":
        case "day is not":
        case "instant is": //todo info --> ai2.appinventor.mit.edu/reference/components/userinterface.html
        case "instant is not":
        case "month is":
        case "month is not":
        case "month in text is":
        case "month in text is not":
        case "year is":
        case "year is not":
        case "picture is":
        case "picture is not":
        case "rotation angle is":
        case "rotation angle is not":
        case "scaling is":
        case "scaling is not":
        case "item background color is":
        case "item background color is not":
        case "item text color is":
        case "item text color is not":
        case "selection is":
        case "selection is not":
        case "selection index is":
        case "selection index is not":
        case "title is":
        case "title is not":
        case "elements are":
        case "elements are not":
        case "selection color is":
        case "selection color is not":
        case "text size is":
        case "text size is not":
        case "hint is":
        case "hint is not":
        case "password is visible":
        case "password is visible not":
        case "color left is":
        case "color left is not":
        case "max value is":
        case "max value is not":
        case "thumb position is":
        case "thumb position is not":
        case "prompt is":
        case "prompt is not":
        case "thumb color active is":
        case "thumb color active is not":
        case "thumb color inactive":
        case "thumb color inactive not":
        case "track color active is":
        case "track color active is not":
        case "track color inactive is":
        case "track color inactive is not":
        case "hour is":
        case "hour is not":
        case "minute is":
        case "minute is not":
          //Window.alert("active");
          ifTextBox.setVisible(true);
          break;
        //TODO add notifier?
        default:
          //Window.alert("inactive");
          ifTextBox.setVisible(false);
      }
    }
  }

  //feduss
  private String ListBoxThenSubjFieldClicked(ListBox subjList, ListBox verbList, String selected, int index) {
    //Window.alert("Selected: " + userViewsList.get(screenName).get(index).getPropertyValue("Name") + "(" + String.valueOf(index) + ")");

    if(selected.toLowerCase().equals("open")){
      //subjList.addItem("another screen");
      verbList.addItem("with name");
    }
    else {
      MockComponent component = userViewsList.get(screenName).get(index);
      String compType = component.getType();
      String view = component.getPropertyValue("Name");

      switch (compType) {
        case "Button":
          if (selected.toLowerCase().equals("set")) {
            verbList.addItem("background color to"); //todo add text input
            verbList.addItem("enabled to"); //todo add text input
            verbList.addItem("font bold to"); //todo add text input
            verbList.addItem("font italic to"); //todo add text input
            verbList.addItem("font size to"); //todo add text input
            verbList.addItem("height to"); //todo add text input
            verbList.addItem("height percent to"); //todo add text input
            verbList.addItem("image to"); //todo add text input
            verbList.addItem("show feedback to"); //todo add text input
            verbList.addItem("text to"); //todo add text input
            verbList.addItem("text color to"); //todo add text input
            verbList.addItem("visible to"); //todo add text input
            verbList.addItem("width to"); //todo add text input
            verbList.addItem("width percent to"); //todo add text input
          }
          break;
        case "Label":
          if (selected.toLowerCase().equals("set")) {
            verbList.addItem("background color to"); //todo add text input
            verbList.addItem("font size to"); //todo add text input
            verbList.addItem("has margins to"); //todo add text input
            verbList.addItem("height to"); //todo add text input
            verbList.addItem("height percent to"); //todo add text input
            verbList.addItem("text to"); //todo add text input
            verbList.addItem("text color to"); //todo add text input
            verbList.addItem("visible to"); //todo add text input
            verbList.addItem("width to"); //todo add text input
            verbList.addItem("width percent to"); //todo add text input
          }
          break;
        case "DatePicker":
          if (selected.toLowerCase().equals("call")) {
            verbList.addItem("call " + view + " launch picker"); //todo add text input
            verbList.addItem("call " + view + " set date to display (year/month/day) to"); //todo add text input
            verbList.addItem("call " + view + " set date to display from instant"); //todo add text input
          } else {
            verbList.addItem("background color to"); //todo add text input
            verbList.addItem("enabled to"); //todo add text input
            verbList.addItem("font bold to"); //todo add text input
            verbList.addItem("font italic to"); //todo add text input
            verbList.addItem("font size to"); //todo add text input
            verbList.addItem("height to"); //todo add text input
            verbList.addItem("height percent to"); //todo add text input
            verbList.addItem("image to"); //todo add text input
            verbList.addItem("show feedback to"); //todo add text input
            verbList.addItem("text to"); //todo add text input
            verbList.addItem("text color to"); //todo add text input
            verbList.addItem("visible to"); //todo add text input
            verbList.addItem("width to"); //todo add text input
            verbList.addItem("width percent to"); //todo add text input
          }
          break;
        case "Image":
          if (selected.toLowerCase().equals("set")) {
            verbList.addItem("animation to"); //todo add text input
            verbList.addItem("clickable to"); //todo add text input
            verbList.addItem("height to"); //todo add text input
            verbList.addItem("height percent to"); //todo add text input
            verbList.addItem("picture to"); //todo add text input
            verbList.addItem("rotation angle to"); //todo add text input
            verbList.addItem("scale picture to fit to"); //todo add text input
            verbList.addItem("scaling to"); //todo add text input
            verbList.addItem("visible to"); //todo add text input
            verbList.addItem("width to"); //todo add text input
            verbList.addItem("width percent to"); //todo add text input
          }
          break;
        case "ListPicker":
          if (selected.toLowerCase().equals("call")) {
            verbList.addItem("open list"); //todo add text input
          } else {
            verbList.addItem("background color to"); //todo add text input
            verbList.addItem("elements to"); //todo add text input
            verbList.addItem("elements from string to"); //todo add text input
            verbList.addItem("enabled to"); //todo add text input
            verbList.addItem("font bold to"); //todo add text input
            verbList.addItem("font italic to"); //todo add text input
            verbList.addItem("font size to"); //todo add text input
            verbList.addItem("height to"); //todo add text input
            verbList.addItem("height percent to"); //todo add text input
            verbList.addItem("image to"); //todo add text input
            verbList.addItem("item background color to"); //todo add text input
            verbList.addItem("item text color to"); //todo add text input
            verbList.addItem("selection to"); //todo add text input
            verbList.addItem("selection index to"); //todo add text input
            verbList.addItem("show feedback to"); //todo add text input
            verbList.addItem("show filter bar to"); //todo add text input
            verbList.addItem("text to"); //todo add text input
            verbList.addItem("text color to"); //todo add text input
            verbList.addItem("title to"); //todo add text input
            verbList.addItem("visible to"); //todo add text input
            verbList.addItem("width to"); //todo add text input
            verbList.addItem("width percent to"); //todo add text input
          }
          break;
        case "ListView":
          if (selected.toLowerCase().equals("set")) {
            verbList.addItem("background color to"); //todo add text input
            verbList.addItem("elements to"); //todo add text input
            verbList.addItem("elements from string to"); //todo add text input
            verbList.addItem("height to"); //todo add text input
            verbList.addItem("height percent to"); //todo add text input
            verbList.addItem("selection to"); //todo add text input
            verbList.addItem("selection color to"); //todo add text input
            verbList.addItem("selection index to"); //todo add text input
            verbList.addItem("show filter bar to"); //todo add text input
            verbList.addItem("text color to"); //todo add text input
            verbList.addItem("text size to"); //todo add text input
            verbList.addItem("visible to"); //todo add text input
            verbList.addItem("width to"); //todo add text input
            verbList.addItem("width percent to"); //todo add text input
          }
          break;
        case "PasswordTextBox":
          if (selected.toLowerCase().equals("set")) {
            verbList.addItem("background color to"); //todo add text input
            verbList.addItem("enabled to"); //todo add text input
            verbList.addItem("font size to"); //todo add text input
            verbList.addItem("heigth to"); //todo add text input
            verbList.addItem("heigth percent to"); //todo add text input
            verbList.addItem("hint to"); //todo add text input
            verbList.addItem("password visible to"); //todo add text input
            verbList.addItem("text to"); //todo add text input
            verbList.addItem("text color to"); //todo add text input
            verbList.addItem("visible to"); //todo add text input
            verbList.addItem("width to"); //todo add text input
            verbList.addItem("width percent to"); //todo add text input
          }
          break;
        case "Slider":
          if (selected.toLowerCase().equals("set")) {
            verbList.addItem("color left to"); //todo add text input
            verbList.addItem("color right to"); //todo add text input
            verbList.addItem("height percent to"); //todo add text input
            verbList.addItem("max value to"); //todo add text input
            verbList.addItem("min value to"); //todo add text input
            verbList.addItem("thumb enabled to"); //todo add text input
            verbList.addItem("thumb position to"); //todo add text input
            verbList.addItem("visible to"); //todo add text input
            verbList.addItem("width to"); //todo add text input
            verbList.addItem("width percent to"); //todo add text input
          }
          break;
        case "Spinner":
          if (selected.toLowerCase().equals("call")) {
            verbList.addItem("display dropdown"); //todo add text input
          } else {
            verbList.addItem("elements to"); //todo add text input
            verbList.addItem("elements from string to"); //todo add text input
            verbList.addItem("height to"); //todo add text input
            verbList.addItem("height percent to"); //todo add text input
            verbList.addItem("prompt to"); //todo add text input
            verbList.addItem("selection to"); //todo add text input
            verbList.addItem("selection index to"); //todo add text input
            verbList.addItem("visible to"); //todo add text input
            verbList.addItem("width to"); //todo add text input
            verbList.addItem("width percent to"); //todo add text input
          }
          break;
        case "Switch":
          if (selected.toLowerCase().equals("set")) {
            verbList.addItem("background color to"); //todo add text input
            verbList.addItem("enabled to"); //todo add text input
            verbList.addItem("font size to"); //todo add text input
            verbList.addItem("height to"); //todo add text input
            verbList.addItem("height percent to"); //todo add text input
            verbList.addItem("on to"); //todo add text input
            verbList.addItem("text to"); //todo add text input
            verbList.addItem("text color to"); //todo add text input
            verbList.addItem("thumb color active to"); //todo add text input
            verbList.addItem("thumb color inactive to"); //todo add text input
            verbList.addItem("track color active to"); //todo add text input
            verbList.addItem("track color inactive to"); //todo add text input
            verbList.addItem("visible to"); //todo add text input
            verbList.addItem("width to"); //todo add text input
            verbList.addItem("width percent to"); //todo add text input
          }
          break;
        case "TextBox":
          if (selected.toLowerCase().equals("call")) {
            verbList.addItem("hide keyboard"); //todo add text input
            verbList.addItem("request focus"); //todo add text input
          } else {
            verbList.addItem("background color to"); //todo add text input
            verbList.addItem("enabled to"); //todo add text input
            verbList.addItem("font size to"); //todo add text input
            verbList.addItem("height to"); //todo add text input
            verbList.addItem("height percent to"); //todo add text input
            verbList.addItem("hint to"); //todo add text input
            verbList.addItem("multiline to"); //todo add text input
            verbList.addItem("numbers only to"); //todo add text input
            verbList.addItem("read only to"); //todo add text input
            verbList.addItem("text to"); //todo add text input
            verbList.addItem("text color to"); //todo add text input
            verbList.addItem("visible to"); //todo add text input
            verbList.addItem("width to"); //todo add text input
            verbList.addItem("width percent to"); //todo add text input
          }
          break;
        case "TimePicker":
          if (selected.toLowerCase().equals("call")) {
            verbList.addItem("launch picker"); //todo add text input
            verbList.addItem("set time to display (hour/minute)"); //todo add text input
            verbList.addItem("set time to display from instant"); //todo add text input
          } else {
            verbList.addItem("background color to"); //todo add text input
            verbList.addItem("enabled to"); //todo add text input
            verbList.addItem("font bold to"); //todo add text input
            verbList.addItem("font italic to"); //todo add text input
            verbList.addItem("font size to"); //todo add text input
            verbList.addItem("height to"); //todo add text input
            verbList.addItem("height percent to"); //todo add text input
            verbList.addItem("image to"); //todo add text input
            verbList.addItem("show feedback to"); //todo add text input
            verbList.addItem("text to"); //todo add text input
            verbList.addItem("text color to"); //todo add text input
            verbList.addItem("visible to"); //todo add text input
            verbList.addItem("width to"); //todo add text input
            verbList.addItem("width percent to"); //todo add text input
          }
          break;
        //TODO add notifier?
        default:
          Window.alert("NotHandledCase: " + compType);
      }
      return compType;
    }
    return "";
  }

  //feduss
  private void ListBoxThenVerbFieldClicked(String verbSelected, TextBox inputBox) {
    if(verbSelected.equals("")){
      inputBox.setVisible(false);
    }
    else{
      inputBox.setVisible(true);
    }
  }

  //feduss
  private static JSONObject getJSONBlock(TesiParser.BlockContext treeRoot) {
    JSONObject json = new JSONObject();
    if(treeRoot != null){
      //treeRoot is the block rules detected
      //the i get the event, and then the string, to get the drawerName

      //Event: when
      //Ex.: button1
      String BlockName = treeRoot.event().subj().getText();
      BlockName = BlockName.substring(0,1).toUpperCase().concat(BlockName.substring(1));

      String BlockType = treeRoot.event().subj_type().getText();

      //Ex.: is clicked
      String EventVerbAction = treeRoot.event().ACTION_WHEN_OBJ().getText();

      JSONObject temp = new JSONObject();
      temp.put("whenSubj", new JSONString(BlockName));
      temp.put("whenSubjType", new JSONString(BlockType));
      temp.put("whenVerbAct", new JSONString(EventVerbAction));
      json.put("event", temp);

      //Window.alert("When ok");

      if(treeRoot.condition() != null){
        //Condition: if
        //Ex.: label1
        String ConditionSubj = treeRoot.condition().condition_statement().subj().getText();
        ConditionSubj = ConditionSubj.substring(0,1).toUpperCase().concat(ConditionSubj.substring(1));

        String ConditionSubjType = treeRoot.condition().condition_statement().subj_type().getText();

        //Ex.: is hidden
        String ConditionVerbAction = /*treeRoot.condition().condition_statement().VERB().getText() + " "
                + */treeRoot.condition().condition_statement().ACTION_IF_OBJ().getText();

        String ConditionNOT = treeRoot.condition().condition_statement().NOT() != null ?
                "NOT" :
                "";

        String CondValue = "NoValue";
        if(treeRoot.condition().condition_statement().value() != null){
          CondValue = treeRoot.condition().condition_statement().value().getText();
        }

        temp = new JSONObject();
        JSONObject temp2 = new JSONObject();
        temp2.put("condSubj", new JSONString(ConditionSubj));
        temp2.put("condSubjType", new JSONString(ConditionSubjType));
        temp2.put("condVerbAct", new JSONString(ConditionVerbAction));
        temp2.put("condNOT", new JSONString(ConditionNOT));
        temp2.put("condANDOR", new JSONString("NOANDOR"));
        temp2.put("condValue", new JSONString(CondValue));
        temp.put("condition" + 0, temp2);

        int count = 1;

        for(int i = 0; i < treeRoot.condition().another_condition().size(); i++){

          String CondANDOR = "NOANDOR";
          CondValue = "NoValue";

          if(treeRoot.condition().another_condition(i).AND() != null){
            CondANDOR = "AND";
          }
          else if(treeRoot.condition().another_condition(i).OR() != null){
            CondANDOR = "OR";
          }

          if(treeRoot.condition().another_condition(i).condition_statement().value() != null){
            CondValue = treeRoot.condition().another_condition(i).condition_statement().value().getText();
          }

          ConditionSubj = treeRoot.condition().another_condition(i).condition_statement().subj().getText();
          ConditionSubj = ConditionSubj.substring(0,1).toUpperCase().concat(ConditionSubj.substring(1));

          //Ex.: is hidden
          ConditionVerbAction = /*treeRoot.condition().another_condition(i).condition_statement().VERB().getText() + " "
                  + */treeRoot.condition().condition_statement().ACTION_IF_OBJ().getText();

          ConditionNOT = treeRoot.condition().another_condition(i).condition_statement().NOT() != null ?
                  "NOT" :
                  "";

          //temp = new JSONObject();
          temp2 = new JSONObject();
          temp2.put("condSubj", new JSONString(ConditionSubj));
          temp2.put("condSubjType", new JSONString(ConditionSubjType));
          temp2.put("condVerbAct", new JSONString(ConditionVerbAction));
          temp2.put("condNOT", new JSONString(ConditionNOT));
          temp2.put("condANDOR", new JSONString(CondANDOR));
          temp2.put("condValue", new JSONString(CondValue));
          temp.put("condition" + (i + 1), temp2);
          count++;
        }
        json.put("conditions", temp);
        json.put("conditionNumber", new JSONString(String.valueOf(count)));
      }

      //Window.alert("Conditions ok");

      int size = treeRoot.another_action().size() + 1;
      System.out.println("Size: " + String.valueOf(size));
      json.put("actionNumber", new JSONString(String.valueOf(size)));
      //Actions
      temp = new JSONObject();

      //ActionType
      String ActionType = null;
      if(treeRoot.action().action_body().action_statement() != null){
        if(treeRoot.action().action_body().action_statement().SET() != null){
          ActionType = treeRoot.action().action_body().action_statement().SET().getText();
        }
        else if(treeRoot.action().action_body().action_statement().CALL() != null){
          ActionType = treeRoot.action().action_body().action_statement().CALL().getText();
        }
        else if(treeRoot.action().action_body().action_statement().OPEN() != null){
          ActionType = treeRoot.action().action_body().action_statement().OPEN().getText();
        }
      }
      else{
        ActionType = "NoType";
      }

      //Window.alert("ActionType: " + ActionType);

      //ActionSubj
      //Ex.: label2
      String ActionSubj = null, ActionSubjType = null;
      if(treeRoot.action().action_body().action_statement() != null && treeRoot.action().action_body().action_statement().OPEN() == null){
        ActionSubj = treeRoot.action().action_body().action_statement().subj().getText();
      }

      if(ActionSubj != null){
        ActionSubj = ActionSubj.substring(0,1).toUpperCase().concat(ActionSubj.substring(1));
      }
      else{
        ActionSubj = "NoSubj";
      }

      //Window.alert("ActionSubj: " + ActionSubj);

      ActionSubjType = treeRoot.action().action_body().action_statement().subj_type() != null ?
              treeRoot.action().action_body().action_statement().subj_type().getText() : "NoSubjType" ;

      //Window.alert("ActionSubjType: " + ActionSubjType);

      ////////////////

      //ActionVerb
      //Ex.: is shown
      String ActionVerb = null, ActionValue = null;
      if(treeRoot.action().action_body().action_statement() != null &&
              treeRoot.action().action_body().action_statement().OPEN() == null){
        ActionVerb = treeRoot.action().action_body().action_statement().ACTION_SET_OBJ() != null ?
                treeRoot.action().action_body().action_statement().ACTION_SET_OBJ().getText() :
                treeRoot.action().action_body().action_statement().ACTION_CALL_OBJ().getText();

        ActionValue = treeRoot.action().action_body().action_statement().value().getText();
      }
      else{
        ActionVerb =treeRoot.action().action_body().action_statement().ACTION_OPEN_OBJ().getText();
        ActionValue = treeRoot.action().action_body().action_statement().value().getText();
      }

      //Window.alert("ActionVerb: " + ActionVerb);

      JSONObject temp2 = new JSONObject();
      temp2.put("actionType", new JSONString(ActionType));
      temp2.put("actionSubj", new JSONString(ActionSubj));
      temp2.put("actionSubjType", new JSONString(ActionSubjType));
      temp2.put("actionVerb", new JSONString(ActionVerb));
      temp2.put("actionValue", new JSONString(ActionValue));
      temp2.put("innerRule", getJSONBlock(treeRoot.action().action_body().block())); //block innestato
      temp.put("action" + 0, temp2);
      //Other_Actions
      for(int i = 0; i < treeRoot.another_action().size(); i++){
        if(treeRoot.another_action(i).action_body().action_statement() != null){
          if(treeRoot.another_action(i).action_body().action_statement().SET() != null){
            ActionType = treeRoot.another_action(i).action_body().action_statement().SET().getText();
          }
          else if(treeRoot.another_action(i).action_body().action_statement().CALL() != null){
            ActionType = treeRoot.another_action(i).action_body().action_statement().CALL().getText();
          }
          else if(treeRoot.another_action(i).action_body().action_statement().OPEN() != null){
            ActionType = treeRoot.another_action(i).action_body().action_statement().OPEN().getText();
          }

          ActionSubj = treeRoot.another_action(i).action_body().action_statement().subj().getText();
          ActionSubj = ActionSubj.substring(0,1).toUpperCase().concat(ActionSubj.substring(1));

          ActionSubjType = treeRoot.another_action(i).action_body().action_statement().subj_type() != null ?
                  treeRoot.another_action(i).action_body().action_statement().subj_type().getText() : "NoSubjType" ;
        }

        if(treeRoot.another_action(i).action_body().action_statement() != null &&
                treeRoot.another_action(i).action_body().action_statement().OPEN() == null){
          ActionVerb = treeRoot.another_action(i).action_body().action_statement().ACTION_SET_OBJ() != null ?
                  treeRoot.another_action(i).action_body().action_statement().ACTION_SET_OBJ().getText() :
                  treeRoot.another_action(i).action_body().action_statement().ACTION_CALL_OBJ().getText();
          ActionValue = treeRoot.another_action(i).action_body().action_statement().value().getText();
        }
        else{
          ActionVerb = treeRoot.another_action(i).action_body().action_statement().ACTION_OPEN_OBJ().getText();
          ActionValue = treeRoot.another_action(i).action_body().action_statement().value().getText();
        }


        temp2 = new JSONObject();
        temp2.put("actionType", new JSONString(ActionType));
        temp2.put("actionSubj", new JSONString(ActionSubj));
        temp2.put("actionSubjType", new JSONString(ActionSubjType));
        temp2.put("actionVerb", new JSONString(ActionVerb));
        temp2.put("actionValue", new JSONString(ActionValue));
        temp2.put("innerRule", getJSONBlock(treeRoot.another_action(i).action_body().block())); //block innestato
        temp.put("action" + (i + 1), temp2);

      }
      json.put("actions", temp);
      //Window.alert("Actions ok");
    }
    return json;
  }

  //feduss: if the type of the block has a block like "when viewname etc etc" (ex.: when button1 is clicked)
  public static boolean HasWhenBlock(String compType) {
    boolean cond = false;
    switch (compType) {
      case "Button": case "DatePicker": case "Image": case "ListPicker": case "ListView": case "TextBox":
      case "PasswordTextBox": case "Slider": case "Spinner": case "Switch": case "TimePicker" : cond = true; break;
      case "Label" : break;
      //TODO add notifier?
      default : ;
    };

    return cond;
  }

  private void deleteItemFromTree() {
    TreeItem treeItem = tree.getSelectedItem();
    if (treeItem != null) {
      Object userObject = treeItem.getUserObject();
      if (userObject instanceof SourceStructureExplorerItem) {
        SourceStructureExplorerItem item = (SourceStructureExplorerItem) userObject;
        item.delete();
      }
    }
  }

  private void enableButtons(SourceStructureExplorerItem item) {
    renameButton.setEnabled(item.canRename());
    deleteButton.setEnabled(item.canDelete());
  }

  private void disableButtons() {
    renameButton.setEnabled(false);
    deleteButton.setEnabled(false);
  }

  
  /* move this logic to declarations of SourceStructureExplorerItem subtypes
  private void showBlocks(SourceStructureExplorerItem item) {
    // are we showing the blocks editor?
    if (Ode.getInstance().getCurrentFileEditor() instanceof YaBlocksEditor) {
      YaBlocksEditor editor = 
          (YaBlocksEditor) Ode.getInstance().getCurrentFileEditor();
      OdeLog.log("Showing item " + item.getItemName());
      if (item.isComponent()) {
        editor.showComponentBlocks(item.getItemName());
      } else {
        editor.showBuiltinBlocks(item.getItemName());
      }
    }
  }

  private void hideComponent() {
    if (Ode.getInstance().getCurrentFileEditor() instanceof YaBlocksEditor) {
      YaBlocksEditor editor =
          (YaBlocksEditor) Ode.getInstance().getCurrentFileEditor();
      OdeLog.log("Hiding selected item");
      editor.hideComponentBlocks();
    }  
  }
   */  

  /**
   * Clears the tree.
   */
  public void clearTree() {
    tree.clear();
    disableButtons();
  }

  /**
   * Updates the tree
   *
   * @param root the new root TreeItem
   * @param itemToSelect item to select, or null for no selected item
   */
  public void updateTree(TreeItem root, SourceStructureExplorerItem itemToSelect) {
    TreeItem items[] = new TreeItem[1];
    items[0] = root;
    updateTree(items, itemToSelect);
  }

  
  /**
   * Updates the tree
   *
   * @param roots An array of root items (all top level)
   * @param itemToSelect item to select, or null for no selected item
   */
  public void updateTree(TreeItem[] roots, SourceStructureExplorerItem itemToSelect) {
    tree.clear();
    for (TreeItem root : roots) {
      tree.addItem(root);
    }
    if (itemToSelect != null) {
      selectItem(itemToSelect, true);
    } else {
      disableButtons();
    }
  }

  /**
   * Select or unselect an item in the tree
   *
   * @param item to select or unselect
   * @param select true to select, false to unselect
   */
  private void selectItem(SourceStructureExplorerItem item, boolean select) {
    Iterator<TreeItem> iter = tree.treeItemIterator();
    while (iter.hasNext()) {
      TreeItem treeItem = iter.next();
      if (item.equals(treeItem.getUserObject())) {
        // NOTE(lizlooney) - It turns out that calling TreeItem.setSelected(true) doesn't actually
        // select the item in the tree. It looks selected, but Tree.getSelectedItem() will return
        // null. Instead, we have to call Tree.setSelectedItem.
        if (select) {
          tree.setSelectedItem(treeItem, false); // false means don't trigger a SelectionEvent
          enableButtons(item);
          //showBlocks(item);
        } else {
          tree.setSelectedItem(null, false); // false means don't trigger a SelectionEvent
          disableButtons();
          //hideComponent();
        }
        break;
      }
    }
  }

  /**
   * Select an item in the tree
   *
   * @param item item to select
   */
  public void selectItem(SourceStructureExplorerItem item) {
    selectItem(item, true);
  }

  /**
   * Select an item in the tree
   *
   * @param item item to unselect
   */
  public void unselectItem(SourceStructureExplorerItem item) {
    selectItem(item, false);
  }
}
