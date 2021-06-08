// Copyright 2012 Massachusetts Institute of Technology. All Rights Reserved.

package com.google.appinventor.client.boxes;

import com.google.appinventor.client.explorer.AntRulesExplorer;
import com.google.appinventor.client.explorer.SourceStructureExplorerItem;
import com.google.appinventor.client.widgets.boxes.Box;
import com.google.gwt.dom.client.NativeEvent;

import static com.google.appinventor.client.Ode.MESSAGES;

/**
 * Box implementation for block selector. Shows a tree containing the built-in
 * blocks as well as the components for the current form. Clicking on an item
 * opens the blocks drawer for the item (or closes it if it was already open).
 * This box shares screen real estate with the SourceStructureBox. It uses a
 * SourceStructureExplorer to handle the components part of the tree.
 *
 * @author sharon@google.com (Sharon Perl)
 *
 */
public final class AntRulesSelectorBox extends Box {
  private static class BlockSelectorItem implements SourceStructureExplorerItem {
    BlockSelectorItem() {
    }

    @Override
    public void onSelected(NativeEvent source) {
    }

    @Override
    public void onStateChange(boolean open) {
    }

    @Override
    public boolean canRename() {
      return false;
    }

    @Override
    public void rename() {
    }

    @Override
    public boolean canDelete() {
      return false;
    }

    @Override
    public void delete() {
    }
  }

  // Singleton block selector box instance
  // Starts out not visible. call setVisible(true) to make it visible
  private static final AntRulesSelectorBox INSTANCE = new AntRulesSelectorBox();

  public final AntRulesExplorer antRulesExplorer;

  // Source structure explorer (for components and built-in b

  /**
   * Return the singleton BlockSelectorBox box.
   *
   * @return block selector box
   */

  public static AntRulesSelectorBox getAntRulesSelectorBox() {
    return INSTANCE;
  }

  /**
   * Creates new block selector box.
   */
  private AntRulesSelectorBox() {
    //feduss, change box title
    super(MESSAGES.antLRRulesBoxCaption(),
            300, // height
            false, // minimizable
            false); // removable

    antRulesExplorer = new AntRulesExplorer();

    setContent(antRulesExplorer);
    setVisible(false);
  }

  /**
   * Returns source structure explorer associated with box.
   *
   * @return source structure explorer
   */
  public AntRulesExplorer getAntRulesExplorer() {
    return antRulesExplorer;
  }
}
