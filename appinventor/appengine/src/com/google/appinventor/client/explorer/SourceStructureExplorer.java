// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.explorer;

import com.google.appinventor.client.Ode;
import com.google.appinventor.client.editor.youngandroid.YaBlocksEditor;
import com.google.appinventor.client.thesis.*;
import com.google.appinventor.client.widgets.TextButton;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

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
  TextBox inputTextBox; //initialization
  Label resultLabel;
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
      scrollPanel.setWidth("200px");  // wide enough to avoid a horizontal scrollbar most of the time
      scrollPanel.setHeight("100%"); // approximately the same height as the viewer

      inputTextBox = new TextBox();
      //inputTextBox.setWidth("100%"); //fill width
      inputTextBox.getElement().setPropertyString("placeholder", MESSAGES.inputTextBoxAntLR()); //set a placeholder
      inputTextBox.getElement().setAttribute("style", "width: 100%; box-sizing: border-box;"); //set css style

      resultLabel = new Label();
      //resultLabel.setWidth("100%"); //fill width
      resultLabel.setText(MESSAGES.resultLabelAntLR());
      Button confirmButton = new Button();
      confirmButton.setText("PARSE");
      confirmButton.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(ClickEvent clickEvent) {
                //String input = "se il button1 viene cliccato, allora la lista viene mostrata";
                ///Custom Lexer ->  A lexer takes the individual characters and transforms them
                // in tokens, the atoms that the parser uses to create the logical structure
                TesiLexer tesiLexer = new TesiLexer(CharStreams.fromString(inputTextBox.getText()));
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

                if(tesiParser.getNumberOfSyntaxErrors() == 0){
                    //Se ha riconosciuto regole
                    if(tesiParser.getRuleNames() != null || tesiParser.getRuleNames().length == 0) {
                        System.out.println("Rules detected: " + tesiParser.getRuleNames().length);

                        //Concatena i nomi delle regole in una string
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String rule : tesiParser.getRuleNames()) {
                            //System.out.println("\n " + rule);
                            stringBuilder.append(rule);
                            stringBuilder.append("; ");
                        }
                        //System.out.println(tree.toStringTree(tesiParser));
                        //E poi la visualizza
                        resultLabel.setText(stringBuilder.toString());

                        JSONObject rule = new JSONObject();

                        //treeRoot is the block rules detected
                        //the i get the event, and then the string, to get the drawerName

                        //Event: when
                        //Ex.: button1
                        String BlockName = treeRoot.event().STRING().getText();
                        BlockName = BlockName.substring(0,1).toUpperCase().concat(BlockName.substring(1));

                        //Ex.: is clicked
                        String EventVerbAction = treeRoot.event().VERB().getText() + " " + treeRoot.event().ACTION().getText();

                        rule.put("blockName", new JSONString(BlockName));
                        rule.put("eventVerbAction", new JSONString(EventVerbAction));


                        if(treeRoot.condition() != null){
                          //Condition: if
                          //Ex.: label1
                          String ConditionSubj = treeRoot.condition().statement().STRING().getText();
                          rule.put("conditionSubj", new JSONString(ConditionSubj));
                          Window.alert("conditionSubj: " + ConditionSubj);
                          //Ex.: is hidden
                          String ConditionVerbAction = treeRoot.condition().statement().VERB().getText() + " "
                                  + treeRoot.condition().statement().ACTION().getText();
                          rule.put("conditionVerbAction", new JSONString(ConditionVerbAction));
                          Window.alert("conditionVerbAction: " + ConditionVerbAction);

                        }
                        //Action: then
                        //Ex.: label2
                        String ActionSubj = treeRoot.action(0).statement().STRING().getText();
                        rule.put("actionSubj", new JSONString(ActionSubj));
                        //Ex.: is shown
                        String ActionVerb = treeRoot.action(0).statement().VERB().getText() + " "
                                + treeRoot.action(0).statement().ACTION().getText();
                        rule.put("actionVerb", new JSONString(ActionVerb));

                        //Window.alert( "rule: \n\n" + rule.toString());
                        YaBlocksEditor editor =
                                (YaBlocksEditor) Ode.getInstance().getCurrentFileEditor();
                        editor.insertBlock(rule.toString(), "Component");
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
      });

      //add all to the vertical panel
      panel2.add(inputTextBox);
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
