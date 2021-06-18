// -*- mode: java; c-basic-offset: 2; -*-
// Copyright © 2013-2016 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0
/**
 * @license
 * @fileoverview Visual blocks editor for App Inventor
 * Set of drawers for holding factory blocks (blocks that create
 * other blocks when dragged onto the workspace). The set of drawers
 * includes the built-in drawers that we get from the blocks language, as
 * well as a drawer per component instance that was added to this workspace.
 *
 * @author mckinney@mit.edu (Andrew F. McKinney)
 * @author Sharon Perl (sharon@google.com)
 * @author ewpatton@mit.edu (Evan W. Patton)
 */

'use strict';

goog.provide('AI.Blockly.Drawer');

goog.require('Blockly.Flyout');
goog.require('Blockly.Options');
goog.require('goog.object');

//feduss
goog.require('Blockly.RenderedConnection');
goog.require('Blockly.BlockSvg');

// Some block drawers need to be initialized after all the javascript source is loaded because they
// use utility functions that may not yet be defined at the time their source is read in. They
// can do this by adding a field to Blockly.DrawerInit whose value is their initialization function.
// For example, see language/common/math.js.

Blockly.Drawer = function(parentWorkspace, opt_options) {
  if (opt_options instanceof Blockly.Options) {
    this.options = opt_options;
  } else {
    opt_options = opt_options || {};
    this.options = new Blockly.Options(opt_options);
  }
  this.options.languageTree = Blockly.Drawer.buildTree_();
  this.workspace_ = parentWorkspace;
  this.flyout_ = new Blockly.Flyout(this.options);
  var flyoutGroup = this.flyout_.createDom('g'),
      svg = this.workspace_.getParentSvg();
  if (this.workspace_.svgGroup_.nextSibling == null) {
    svg.appendChild(flyoutGroup);
  } else {
    svg.insertBefore(flyoutGroup, this.workspace_.svgGroup_.nextSibling);
  }
  this.flyout_.init(parentWorkspace);
  this.lastComponent = null;
};

/**
 * String to prefix on categories of each potential block in the drawer.
 * Used to prevent collisions with built-in properties like 'toString'.
 * @private
 */
Blockly.Drawer.PREFIX_ = 'cat_';

/**
 * Build the hierarchical tree of block types.
 * Note: taken from Blockly's toolbox.js
 * @return {!Object} Tree object.
 * @private
 */
Blockly.Drawer.buildTree_ = function() {
  var tree = {};
  var formName = Blockly.mainWorkspace.formName;
  var screenName = formName.substring(formName.indexOf("_") + 1);

  // Check to see if a Blocks Toolkit is defined. If so, use that to build the tree.
  if (window.parent.BlocklyPanel_getComponentInstancePropertyValue) {
    var subsetJsonString = window.parent.BlocklyPanel_getComponentInstancePropertyValue(formName, screenName, "BlocksToolkit");
    if (subsetJsonString) {
      var toolkitTree = Blockly.Drawer.buildToolkitTree_(subsetJsonString);
      if (toolkitTree != undefined)
        return toolkitTree;
    }
  }

  // Populate the tree structure.
  for (var name in Blockly.Blocks) {
    if (!Blockly.Blocks.hasOwnProperty(name)) continue;
    var block = Blockly.Blocks[name];
    // Blocks without a category are fragments used by the mutator dialog.
    if (block.category) {
      var cat = Blockly.Drawer.PREFIX_ + window.encodeURI(block.category);
      if (cat in tree) {
        tree[cat].push(name);
      } else {
        tree[cat] = [name];
      }
    }
  }
  return tree;
};

/**
 * Build the hierarchical tree of built-in block types using the JSON property BlocksToolkit
 * @return {!Object} Tree object.
 * @private
 */
Blockly.Drawer.buildToolkitTree_ = function(jsonToolkit) {
  var tree = {};
  var subsetArray = JSON.parse(jsonToolkit);
  var subsetBlockArray = subsetArray["shownBlockTypes"];
  try {
    for (var key in subsetBlockArray) {
      if (key != 'ComponentBlocks') {
        var cat = "cat_" + key;
        var catBlocks = subsetBlockArray[key];
        for (var i = 0; i < catBlocks.length; i++) {
          var block = catBlocks[i];
          var name = block.type;
          if (cat in tree) {
            tree[cat].push(name);
          } else {
            tree[cat] = [name];
          }
        }
      }
    }
  } catch (err) {
    console.log(err);
    return undefined;
  }
  return tree;
};

//feduss
function createBlock(drawerName, index) {
  if(drawerName === "Control" || drawerName === "Logic" || drawerName === "Math" || drawerName === "Text"
      || drawerName === "Lists" || drawerName === "Dictionaries" || drawerName === "Colors"
      || drawerName === "Variables" || drawerName === "Procedures"){
    var type = "Builtin";
  }
  else{
    var type = "Component";
  }
  //alert("A block with drawerName \"" + drawerName +"\", index " + index + " and type \"" + type + "\"is being created." );
  // x -> y means that x the function y
  /*
   * Blockly.Drawer.prototype.showBuiltin = function(drawerName) of drawer.js
   */
  if (type === "Builtin") {
    var drawerName = Blockly.Drawer.PREFIX_ + drawerName;
    var blockSet = this.options.languageTree[drawerName];
    if (drawerName == "cat_Procedures") {
      var newBlockSet = [];
      //comment by feduss, but code is of mit: getting blockset
      for (var i = 0; i < blockSet.length; i++) {
        if (!(blockSet[i] == "procedures_callnoreturn" // Include callnoreturn only if at least one defnoreturn declaration
            && this.workspace_.getProcedureDatabase().voidProcedures == 0)
            &&
            !(blockSet[i] == "procedures_callreturn" // Include callreturn only if at least one defreturn declaration
                && this.workspace_.getProcedureDatabase().returnProcedures == 0)) {
          newBlockSet.push(blockSet[i]);
        }
      }
      blockSet = newBlockSet;
    }

    if (!blockSet) {
      //throw "no such drawer: " + drawerName;
      //alert(blockSet.replace("cat_", "") + " doesn't exist: check the views in Designer tab and try again");
    } else {
      Blockly.hideChaff();
      //comment by feduss, but code is of mit: getting blockset as xmlList
      var xmlList = Blockly.Drawer.prototype.blockListToXMLArray(blockSet);

      return this.flyout_.insertBlock(xmlList, index)
    }
  }
  //Example: buttons
  else if (type === "Component") {
    var instanceName = drawerName;

    var component = this.workspace_.getComponentDatabase().getInstance(instanceName);
    if (component) {
      Blockly.hideChaff();
      //this.flyout_.show(this.instanceRecordToXMLArray(component));
      return this.flyout_.insertBlock(this.instanceRecordToXMLArray(component), index);
      //this.lastComponent = instanceName;
    } else {
      //alert(instanceName + " doesn't exist: check the views in Designer tab and try again");

      console.log("Got call to Blockly.Drawer.showComponent(" + instanceName +
          ") - unknown component name");
    }
  }
}

function checkSubjSyntax(list) {
  var errors = 0;
  for(i = 0; i < list.length; i++){
    var subj = list[i];
    if(subj != null){
      var component = this.workspace_.getComponentDatabase().getInstance(subj);
      if (component == null) {
        errors++;
        //alert(subj + " doesn't exist: check the views in Designer tab and try again");
      }
    }
  }

  return errors;
}

function onMouseDown(block){
  if (block.isInMutator) {
    // Mutator's coordinate system could be out of date because the bubble was
    // dragged, the block was moved, the parent workspace zoomed, etc.
    block.workspace.resize();
  }
  block.workspace.updateScreenCalculationsIfScrolled();
  block.workspace.markFocused();
  //Blockly.terminateDrag_();
  block.select();
  Blockly.hideChaff();
  if (!Blockly.Events.getGroup()) {
    Blockly.Events.setGroup(true);
  }

  // Left-click (or middle click)
  //Blockly.Css.setCursor(Blockly.Css.Cursor.CLOSED);
  block.dragStartXY_ = block.getRelativeToSurfaceXY();
  //block.workspace.startDrag(e, this.dragStartXY_);
  /*** the line above is replaced by this code ***/
  var svg = block.workspace.getParentSvg();

  var matrix = block.workspace.getInverseScreenCTM()
  // Record the starting offset between the bubble's location and the mouse.
  var svgPoint = svg.createSVGPoint();

  var coords = block.workspace.getSvgXY(/** @type {!Element} */ (block.svgGroup_));

  svgPoint.x = coords.x; //e.clientX;
  svgPoint.y = coords.y; //e.clientY;

  if (!matrix) {
    matrix = svg.getScreenCTM().inverse();
  }
  var point = svgPoint.matrixTransform(matrix);
  //end record

  // Fix scale of mouse event.
  point.x /= block.workspace.scale;
  point.y /= block.workspace.scale;
  block.workspace.dragDeltaXY_ = goog.math.Coordinate.difference(block.dragStartXY_, point);

  /******* END *******/

  Blockly.dragMode_ = Blockly.DRAG_STICKY;
  Blockly.BlockSvg.onMouseUpWrapper_ = Blockly.bindEventWithChecks_(document,
      'mouseup', block, block.onMouseUp_);
  Blockly.BlockSvg.onMouseMoveWrapper_ = Blockly.bindEventWithChecks_(
      document, 'mousemove', block, block.onMouseMove_);
  // Build a list of bubbles that need to be moved and where they started.
  block.draggedBubbles_ = [];
  var descendants = block.getDescendants();
  for (var i = 0, descendant; descendant = descendants[i]; i++) {
    var icons = descendant.getIcons();
    for (var j = 0; j < icons.length; j++) {
      var data = icons[j].getIconLocation();
      if (data) {  // icons for collapsed blocks may not have locations
        data.bubble = icons[j];
        block.draggedBubbles_.push(data);
      }
    }
  }
}

function onMouseMove(block, supportBlock, secondCoords, type){

  //move block near the destination one

  //block.moveBy(secondCoords.x +  mainWidth, 0);

  var oldXY = block.getRelativeToSurfaceXY();
  /* feduss: block.moveBy(secondCoords.x - 1, 0);

  var newXY = block.getRelativeToSurfaceXY();

  block.moveBy(secondCoords.x + 1, 0);*/

  //var newXY= this.workspace.moveDrag(e);
  /*** the line above is replaced by this code ***/
      //var point = Blockly.utils.mouseToSvg(e, this.getParentSvg(),
      //    this.getInverseScreenCTM());
  var newCoordsX = secondCoords.x;
  var newCoordsY = secondCoords.y;

  // Check to see if any of this block's connections are within range of
  // another block's connection.
  var myConnections = block.getConnections_(false);
  // Also check the last connection on this stack
  var lastOnStack = block.lastConnectionInStack_();
  if (lastOnStack && lastOnStack != block.nextConnection) {
    myConnections.push(lastOnStack);
  }
  var closestConnection = null;
  var localConnection = null;
  var radiusConnection = Blockly.SNAP_RADIUS;
  switch(type){
    case "left":
    case "open":
    case "if":
    case "ifcond":
    case "notcond":
    case "set":
      //alert("case set or ifcond or notcond or if");
      closestConnection = supportBlock.inputList[0].connection;

      if(closestConnection == null && type !== "set"){
        var connections = supportBlock.getConnections_(false);
        var tempConn = null;
        var k = 0;
        //alert("Conn length: " + connections.length)
        while(closestConnection == null){
          tempConn = connections[k];
          closestConnection = tempConn;
          //radiusConnection = tempConn.radius;
          k++;
        }
      }
      //radiusConnection = supportBlock.inputList[0].radius;
      break;
    case "when":
      //alert("case when");
      var connections = supportBlock.getConnections_(false);
      var tempConn = null;
      var k = 0;
      //alert("Conn when length: " + connections.length)
      while(closestConnection == null){
        tempConn = connections[k];
        closestConnection = tempConn;
        //radiusConnection = tempConn.radius;
        k++;
      }
      break;
    case "ifblock":
      //alert("case ifblock");
      closestConnection = supportBlock.inputList[1].connection;
      break;
    case "innerBlock":
    case "nextCondition":
    case "nextAction":
      closestConnection = supportBlock.nextConnection;
      break;
    case "right":
      closestConnection = supportBlock.inputList[1].connection;
      break;
  }

  if(!closestConnection){
    //alert("Error: closestConnection is null.")
  }
  else{
    //alert("closestconnection is not null")
    newCoordsX = closestConnection.x_;
    newCoordsY = closestConnection.y_;
    //alert("PrevCoords: " + secondCoords.x + ", " + secondCoords.y +
    //    "\nNewCoords: " + newCoordsX + ", " + newCoordsY)
    var svg = block.workspace.getParentSvg();
    var matrix = block.workspace.getInverseScreenCTM();
    var svgPoint = svg.createSVGPoint();
    svgPoint.x = newCoordsX;
    svgPoint.y = newCoordsY;

    if (!matrix) {
      matrix = svg.getScreenCTM().inverse();
    }
    var point = svgPoint.matrixTransform(matrix);
    // Fix scale of mouse event.
    point.x /= block.workspace.scale;
    point.y /= block.workspace.scale;
    var newXY = goog.math.Coordinate.sum(block.workspace.dragDeltaXY_, point);

    /******* END *******/

    //alert("SecondCoords: "+ secondCoords + "\noldXY: " + oldXY + "\nnewXY: " + newXY +
    //    "\ndragStartXY: " + block.dragStartXY_)

    if (Blockly.dragMode_ == Blockly.DRAG_STICKY) {
      //alert("sticky");
      // Still dragging within the sticky DRAG_RADIUS.
      var dr = goog.math.Coordinate.distance(oldXY, newXY) * block.workspace.scale;
      if (dr > Blockly.DRAG_RADIUS) {
        //alert("I blocchi sono troppo lontani...dr > drag radius, " + dr +
        //    " > " + Blockly.DRAG_RADIUS);
        // Switch to unrestricted dragging.
        Blockly.dragMode_ = Blockly.DRAG_FREE;
        Blockly.longStop_();
        block.workspace.setResizesEnabled(false);
        if (block.parentBlock_) {
          //alert("first if")
          // Push this block to the very top of the stack.
          block.unplug();
          var group = block.getSvgRoot();
          group.translate_ = 'translate(' + newXY.x + ',' + newXY.y + ')';
          block.disconnectUiEffect();
        }
        block.setDragging_(true);
        //alert("moveToDragSurface")
        block.moveToDragSurface_();
      }
    }
    if (Blockly.dragMode_ == Blockly.DRAG_FREE) {
      // Unrestricted dragging.
      var dxy = goog.math.Coordinate.difference(oldXY, block.dragStartXY_);
      var group = block.getSvgRoot();
      if (block.useDragSurface_) {
        block.workspace.blockDragSurface_.translateSurface(newXY.x, newXY.y);
      } else {
        group.translate_ = 'translate(' + newXY.x + ',' + newXY.y + ')';
        group.setAttribute('transform', group.translate_ + group.skew_);
      }
      // Drag all the nested bubbles.
      for (var i = 0; i < block.draggedBubbles_.length; i++) {
        var commentData = block.draggedBubbles_[i];
        commentData.bubble.setIconLocation(
            goog.math.Coordinate.sum(commentData, dxy));
      }

      // Check to see if any of this block's connections are within range of
      // another block's connection.
      /*var myConnections = block.getConnections_(false);
      // Also check the last connection on this stack
      var lastOnStack = block.lastConnectionInStack_();
      if (lastOnStack && lastOnStack != block.nextConnection) {
        myConnections.push(lastOnStack);
      }
      var closestConnection = null;
      var localConnection = null;
      var radiusConnection = Blockly.SNAP_RADIUS;
      for (var i = 0; i < myConnections.length; i++) {
        var myConnection = myConnections[i];
        var neighbour = myConnection.closest(radiusConnection, dxy);
        if (neighbour.connection) {
          alert("neighbour.connection is NOT null")
          closestConnection = neighbour.connection;
          localConnection = myConnection;
          radiusConnection = neighbour.radius;
        }
      }/*

      // Remove connection highlighting if needed.
      if (Blockly.highlightedConnection_ &&
          Blockly.highlightedConnection_ != closestConnection) {
        Blockly.highlightedConnection_.unhighlight();
        Blockly.highlightedConnection_ = null;
        Blockly.localConnection_ = null;
      }

      //var wouldDeleteBlock = block.updateCursor_(e, closestConnection);
      /*** the line above is replaced by this code ***/
      var wouldDeleteBlock = false;

      var deleteArea = false//block.workspace.isDeleteArea(e);
      var wouldConnect = Blockly.selected && closestConnection &&
          deleteArea != Blockly.DELETE_AREA_TOOLBOX;
      var wouldDelete = deleteArea && !block.getParent() &&
          Blockly.selected.isDeletable();
      var showDeleteCursor = wouldDelete && !wouldConnect;

      if (showDeleteCursor) {
        Blockly.Css.setCursor(Blockly.Css.Cursor.DELETE);
        if (deleteArea == Blockly.DELETE_AREA_TRASH && block.workspace.trashcan) {
          block.workspace.trashcan.setOpen_(true);
        }
        //return true;
        wouldDeleteBlock = true;
      } else {
        //Blockly.Css.setCursor(Blockly.Css.Cursor.CLOSED);
        if (block.workspace.trashcan) {
          block.workspace.trashcan.setOpen_(false);
        }
        //return false;
        wouldDeleteBlock = false;
      }

      /******* END *******/

      for (var i = 0; i < myConnections.length; i++) {
        var localConnection = myConnections[i];
        // Add connection highlighting if needed.
        if (!wouldDeleteBlock && closestConnection &&
            closestConnection != Blockly.highlightedConnection_) {
          closestConnection.highlight();
          Blockly.highlightedConnection_ = closestConnection;
          Blockly.localConnection_ = localConnection;

          //alert("Click ok to connect falseBlock with isHiddenBlock");
          //connectBlocks.call(this, falseBlock, Blockly.OUTPUT_VALUE, isHiddenBlock, Blockly.INPUT_VALUE);
          onMouseUp.call(this, block);
          break;
        }
      }
    }
  }

}

function onMouseUp(block1){
  //alert("in mouseUp")
  Blockly.Touch.clearTouchIdentifier();
  if (Blockly.dragMode_ != Blockly.DRAG_FREE &&
      !Blockly.WidgetDiv.isVisible()) {
    Blockly.Events.fire(
        new Blockly.Events.Ui(block1, 'click', undefined, undefined));
  }
  Blockly.terminateDrag_();
  var oldXY = this.dragStartXY_;

  var deleteArea = false;//block1.workspace.isDeleteArea(e);

  // Connect to a nearby block, but not if it's over the toolbox.
  if (Blockly.selected && Blockly.highlightedConnection_ &&
      deleteArea != Blockly.DELETE_AREA_TOOLBOX) {
    // Connect two blocks together.
    //alert("pre connect");
    Blockly.localConnection_.connect(Blockly.highlightedConnection_);
    if (block1.rendered) {
      //alert("pre connect ui effect")
      // Trigger a connection animation.
      // Determine which connection is inferior (lower in the source stack).
      var inferiorConnection = Blockly.localConnection_.isSuperior() ?
          Blockly.highlightedConnection_ : Blockly.localConnection_;
      inferiorConnection.getSourceBlock().connectionUiEffect();
    }
    if (block1.workspace.trashcan) {
      // Don't throw an object in the trash can if it just got connected.
      block1.workspace.trashcan.close();
    }
  }
  else if (deleteArea && !block1.getParent() && Blockly.selected.isDeletable()) {
    //alert("pre secondo if")
    // We didn't connect the block, and it was over the trash can or the
    // toolbox.  Delete it.
    var trashcan = block1.workspace.trashcan;
    Blockly.confirmDeletion(function(confirmedDelete) {
      if (trashcan) {
        goog.Timer.callOnce(trashcan.close, 100, trashcan);
      }
      if (confirmedDelete) {
        Blockly.selected.dispose(false, true);
      } else {
        //Move block back to original position if trash is canceled
        var group = Blockly.selected.getSvgRoot();
        group.translate_ = 'translate(' + oldXY.x + ',' + oldXY.y + ')';
        group.setAttribute('transform', group.translate_ + group.skew_);
      }
    });
  }
  if (Blockly.highlightedConnection_) {
    Blockly.highlightedConnection_.unhighlight();
    Blockly.highlightedConnection_ = null;
  }
  Blockly.Css.setCursor(Blockly.Css.Cursor.OPEN);
  if (!Blockly.WidgetDiv.isVisible()) {
    Blockly.Events.setGroup(false);
  }
}

function connectBlocks(blockToBeConn, baseBlock, type) {
  var secondCoords = blockToBeConn.workspace.getSvgXY(/** @type {!Element} */ (blockToBeConn.svgGroup_));
  //alert("Press ok to mouse down: \n" + blockToBeConn);
  onMouseDown.call(this, blockToBeConn)

  //alert("Press ok to mouse move");
  onMouseMove.call(this, blockToBeConn, baseBlock, secondCoords, type);

  //alert("Press ok to mouse move2");
  //secondCoords.x += mainWidth;
  //onMouseMove.call(this, falseBlock, isHiddenBlock, secondCoords, -1);
}

//feduss
function getActionBlock(actionType, actionSubj, actionVerb, actionObj){
  var actionBlock = null;
  var index = -1;
  //TODO edit with new set/call
  if(true){
    //alert("Test: " + actionType + " " + actionVerb);
    var temp = actionType + " " + actionVerb;
    switch (temp){
      case "set visible to true":
        index = 14;
        var visibleToBlock = createBlock.call(this, actionSubj, index); // "set actionSubj visibility to" block
        var trueBlock = createBlock.call(this, "Logic", 0); // "true" block
        connectBlocks.call(this, trueBlock, visibleToBlock, "set");
        actionBlock = visibleToBlock;
    }
  }
  else{
    switch(actionVerb){
      case "is hidden":
        if(actionSubj.toLowerCase().includes("label")){
          index = 15;
        }
        else if(actionSubj.toLowerCase().includes("button")){
          index = 28;
        }
        var isHiddenBlock = createBlock.call(this, actionSubj, index); // "set actionSubj visibility to" block
        var falseBlock = createBlock.call(this, "Logic", 1); // "false" block
        connectBlocks.call(this, falseBlock, isHiddenBlock, "set");
        actionBlock = isHiddenBlock;
        break;
      case "is visible":
      case "is shown":
        if(actionSubj.toLowerCase().includes("label")){
          index = 15;
        }
        else if(actionSubj.toLowerCase().includes("button")){
          index = 28;
        }
        var isHiddenBlock = createBlock.call(this, actionSubj, index); // "set actionSubj visibility to" block
        var trueBlock = createBlock.call(this, "Logic", 0); // "true" block
        connectBlocks.call(this, trueBlock, isHiddenBlock, "set");
        actionBlock = isHiddenBlock;
        break;
      case "open":
        //("ACTION case open")
        var openScreenBlock = createBlock.call(this, "Control", 9); //open another screen screename block
        var screenNameBlock = createBlock.call(this, "Text", 0); //it's an empty text block
        //screenNameBlock.comment = actionObj;
        for (var i = 0, input; input = screenNameBlock.inputList[i]; i++) {
          for (var j = 0, field; field = input.fieldRow[j]; j++) {
            if(field.name == "TEXT") {
              field.setText(actionObj);
            }
          }
        }
        connectBlocks.call(this, screenNameBlock, openScreenBlock, "open");
        actionBlock = openScreenBlock;
    }
  }

  return actionBlock;
}

Blockly.Drawer.prototype.deleteBlock = function(id){
  //Blockly.selected.dispose(false, true);
  var block = this.workspace_.blockDB_[id];
  block.dispose(false, true);
  return true;
}

function insertBlockRecursive(rule1) {
  //test statico
  var id = "";
  if(rule1.event != null){

    //alert("Pre check sintassi")
    var list = []//[whenSubject, cond0Subj, action0Subj, ecc];
    var whenSubject = rule1.event.whenSubj; //when whenSubject whenAction do
    list.push(whenSubject)

    for(var i = 0; i < rule1.conditionNumber; i++){
      var condSubj = rule1.conditions["condition" + i] != null ?
          rule1.conditions["condition" + i].condSubj : //if condSubj
          null;
      if(condSubj != null){
        list.push(condSubj);
      }
    }

    for (var i = 0; i < rule1.actionNumber; i++) {
      //alert("OTHER ACTIONS " + t);
      var actionSubj = rule1.actions["action" + i] != null ?
          rule1.actions["action" + i].actionSubj :
          null;
      if (actionSubj != null && actionSubj !== "NoSubj") {
        list.push(actionSubj);
      }
    }

    i = 0;


    //Verifico che i subjects (es. Button1, Label1, ecc) esistano o meno, prima di creare i blocchi
    var errors = checkSubjSyntax.call(this, list)

    //alert("post check sintassi")

    if(errors == 0) {
      //alert("EVENT")
      /*** EVENT ***/
      var whenSubjType = rule1.event.whenSubjType;
      whenSubjType = whenSubjType.substring(0,1).toUpperCase() + whenSubjType.substring(1, whenSubjType.length);
      var whenAction = rule1.event.whenVerbAct;
      var index = -1;
      switch (whenAction) {
        case 'after date':
          switch (whenSubjType){
            case "DatePicker": index = 0; break;
          }; break;
        case 'after picking':
          switch (whenSubjType){
            case "ListView": index = 0; break;
          };  break;
        case 'after selecting':
          switch (whenSubjType){
            case "Spinner": index = 0; break;
          };  break;
        case 'after time set': switch (whenSubjType){
            case "TimePicker": index = 0; break;
          };  break;
        case  'click': switch (whenSubjType){
            case "Image": index = 0; break;
          };  break;
        case 'changed': switch (whenSubjType){
            case "Switch": index = 0; break;
          };  break;
        case 'got focus': switch (whenSubjType){
            case "Button": index = 1; break;
            case "DatePicker": index = 1; break;
            case "Switch": index = 1; break;
            case "PasswordTextBox": index = 0; break;
            case "TextBox": index = 1; break;
          };  break;
        case 'is clicked': switch (whenSubjType){
            case "Button": index = 0; break;
          };  break;
        case 'is long clicked': switch (whenSubjType){
            case "Button": index = 2; break;
          };  break;
        case 'lost focus': switch (whenSubjType){
            case "DatePicker": index = 0; break;
          };  break;
        case 'position changed': switch (whenSubjType){
            case "Slider": index = 0; break;
          };  break;
        case 'touch down': switch (whenSubjType){
          case "DatePicker": index = 3; break;
          case "TimePicker": index = 3; break;
          case "ListPicker": index = 0; break;
          };  break;
        case 'touch up': switch (whenSubjType){
            case "DatePicker": index = 4; break;
            case "TimePicker": index = 4; break;
            case "ListPicker": index = 1; break;
          };  break;
      }
      //alert("Post Event switch...whenSubjType: " + whenSubjType + "...whenAction: " + whenAction + "...index: " + index)
      //Il primo elemento della lista del whenSubject
      // (Esempio: quando, nella palette, clicchi su button1 (whenSubject), si apre una lista (il flyout)...il primo elemento è isClicked (whenAction)
      var whenBlock = createBlock.call(this, whenSubject, index);
      whenBlock.y_ = rule1.prevY_ + 50;
      id = whenBlock.id;
      /******/
      //alert("CONDITION")
      /*** CONDITION ***/
          //if-then
      var mainCondBlock = null;

      //If "IF" condition is not null
      var firstIfBlock = null;
      if (rule1.conditionNumber > 0) {
        mainCondBlock = createBlock.call(this, "Control", 0); //"if-then" block...primo elemento della lista quando si clicca su "Control" nella palette

        var prevIfBlock = null, prevANDORBlock = null;
        for (var f = 0; f < rule1.conditionNumber; f++) {
          //alert("Condition: " + f);
          var condSubj = rule1.conditions["condition" + f] != null ?
              rule1.conditions["condition" + f].condSubj : //if condSubj
              null;
          var condSubjType = rule1.conditions["condition" + f].condSubjType;
          condSubjType = condSubjType.substring(0, 1).toUpperCase() + condSubjType.substring(1);
          var condVerbAct = rule1.conditions["condition" + f].condVerbAct;
          var condNOT = rule1.conditions["condition" + f].condNOT;
          var condANDOR = rule1.conditions["condition" + f].condANDOR;
          var condValue = rule1.conditions["condition" + f].condValue;
          var index = -1;

          //alert("Pre switch: \ncondSubj: " + condSubj + "\ncondSubj: " + condSubj + "\ncondVerbAct: " + condVerbAct +
          //"\ncondNOT: " + condNOT + "\ncondANDOR: " + condANDOR + "\ncondValue: " + condValue);

          switch (condVerbAct) {
            case 'background color':
              switch (condSubjType) {
                case "Label":
                  index = 0;
                  break;
                case "Button":
                  index = 6;
                  break;
                case "DatePicker":
                  index = 8;
                  break;
                case "ListPicker":
                  index = 3;
                  break;
                case "ListView":
                  index = 1;
                  break;
                case "PasswordTextBox":
                  index = 3;
                  break;
                case "Switch":
                  index = 3;
                  break;
                case "TextBox":
                  index = 4;
                  break;
                case "TimePicker":
                  index = 8;
                  break;
              }
              ;
              break;
            case 'clickable':
              switch (condSubjType) {
                case "Image":
                  index = 2;
                  break;
              }
              ;
              break;
            case'color left':
              switch (condSubjType) {
                case "Slider":
                  index = 3;
                  break;
              }
              ;
              break;
            case'color right':
              switch (condSubjType) {
                case "Slider":
                  index = 5;
                  break;
              }
              ;
              break;
            case'day':
              switch (condSubjType) {
                case "DatePicker":
                  index = 10;
                  break;
              }
              ;
              break;
            case 'enabled':
              switch (condSubjType) {
                case "Button":
                  index = 8;
                  break;
                case "DatePicker":
                  index = 11;
                  break;
                case "ListPicker":
                  index = 10;
                  break;
                case "PasswordTextBox":
                  index = 7;
                  break;
                case "Switch":
                  index = 7;
                  break;
                case "Textbox":
                  index = 8;
                  break;
                case "TimePicker":
                  index = 12;
                  break;
              }
              ;
              break;
            case'elements':
              switch (condSubjType) {
                case "ListPicker":
                  index = 7;
                  break;
                case "ListView":
                  index = 5;
                  break;
                case "Spinner":
                  index = 4;
                  break;
              }
              ;
              break;
            case'font bold':
              switch (condSubjType) {
                case "Button":
                  index = 10;
                  break;
                case "DatePicker":
                  index = 13;
                  break;
                case "ListPicker":
                  index = 12;
                  break;
                case "TimePicker":
                  index = 14;
                  break;
              }
              ;
              break;
            case'font italic':
              switch (condSubjType) {
                case "Button":
                  index = 12;
                  break;
                case "DatePicker":
                  index = 15;
                  break;
                case "ListPicker":
                  index = 14;
                  break;
                case "TimePicker":
                  index = 16;
                  break;
              }
              ;
              break;
            case'font size':
              switch (condSubjType) {
                case "Button":
                  index = 14;
                  break;
                case "Label":
                  index = 2;
                  break;
                case "PasswordTextBox":
                  index = 7;
                  break;
                case "DatePicker":
                  index = 17;
                  break;
                case "ListPicker":
                  index = 14;
                  break;
                case "Switch":
                  index = 9;
                  break;
                case "Textbox":
                  index = 10;
                  break;
                case "TimePicker":
                  index = 18;
                  break;
              }
              ;
              break;
            case 'html content':
              switch (condSubjType) {
                case "Label":
                  index = 4;
                  break;
              }
              ;
              break;
            case'has margins':
              switch (condSubjType) {
                case "Label":
                  index = 5;
                  break;
              }
              ;
              break;
            case'height':
              switch (condSubjType) {
                case "Label":
                  index = 7;
                  break;
                case "Button":
                  index = 16;
                  break;
                case "DatePicker":
                  index = 19;
                  break;
                case "Image":
                  index = 4;
                  break;
                case "ListPicker":
                  index = 16;
                  break;
                case "ListView":
                  index = 6;
                  break;
                case "PasswordTextBox":
                  index = 9;
                  break;
                case "Spinner":
                  index = 5;
                  break;
                case "Switch":
                  index = 11;
                  break;
                case "Textbox":
                  index = 12;
                  break;
                case "TimePicker":
                  index = 20;
                  break;
              }
              ;
              break;
            case'hint':
              switch (condSubjType) {
                case "PasswordTextBox":
                  index = 12;
                  break;
                case "Textbox":
                  index = 13;
                  break;
              }
              ;
              break;
            case'hour':
              switch (condSubjType) {
                case "TimePicker":
                  index = 23;
                  break;
              }
              ;
              break;
            case 'image':
              switch (condSubjType) {
                case "Button":
                  index = 19;
                  break;
                case "DatePicker":
                  index = 22;
                  break;
                case "ListPicker":
                  index = 19;
                  break;
                case "TimePicker":
                  index = 22;
                  break;
              }
              ;
              break;
            case'instant':
              switch (condSubjType) {
                case "DatePicker":
                  index = 24;
                  break;
                case "TimePicker":
                  index = 26;
                  break;
              }
              ;
              break;
            case'item background color':
              switch (condSubjType) {
                case "ListPicker":
                  index = 21;
                  break;
              }
              ;
              break;
            case'item text color':
              switch (condSubjType) {
                case "ListPicker":
                  index = 23;
                  break;
              }
              ;
              break;
            case 'month':
              switch (condSubjType) {
                case "DatePicker":
                  index = 25;
                  break;
              }
              ;
              break;
            case'month in text':
              switch (condSubjType) {
                case "DatePicker":
                  index = 26;
                  break;
              }
              ;
              break;
            case'max value':
              switch (condSubjType) {
                case "Slider":
                  index = 6;
                  break;
              }
              ;
              break;
            case'min value':
              switch (condSubjType) {
                case "Slider":
                  index = 8;
                  break;
              }
              ;
              break;
            case'multi line':
              switch (condSubjType) {
                case "Textbox":
                  index = 15;
                  break;
              }
              ;
              break;
            case'minute':
              switch (condSubjType) {
                case "TimePicker":
                  index = 27;
                  break;
              }
              ;
              break;
            case 'numbers only':
              switch (condSubjType) {
                case "Textbox":
                  index = 17;
                  break;
              }
              ;
              break;
            case 'on':
              switch (condSubjType) {
                case "Switch":
                  index = 12;
                  break;
              }
              ;
              break;
            case 'picture':
              switch (condSubjType) {
                case "Image":
                  index = 7;
                  break;
              }
              ;
              break;
            case'password visible':
              switch (condSubjType) {
                case "PasswordTextBox":
                  index = 14;
                  break;
              }
              ;
              break;
            case'prompt':
              switch (condSubjType) {
                case "Spinner":
                  index = 8;
                  break;
              }
              ;
              break;
            case 'rotation angle':
              switch (condSubjType) {
                case "Image":
                  index = 9;
                  break;
              }
              ;
              break;
            case'read only':
              switch (condSubjType) {
                case "Textbox":
                  index = 19;
                  break;
              }
              ;
              break;
            case 'show feedback':
              switch (condSubjType) {
                case "Button":
                  index = 21;
                  break;
                case "DatePicker":
                  index = 27;
                  break;
                case "ListPicker":
                  index = 29;
                  break;
                case "TimePicker":
                  index = 26;
                  break;
              }
              ;
              break;
            case'scaling':
              switch (condSubjType) {
                case "Image":
                  index = 12;
                  break;
              }
              ;
              break;
            case'selection':
              switch (condSubjType) {
                case "ListPicker":
                  index = 25;
                  break;
                case "ListView":
                  index = 9;
                  break;
                case "Spinner":
                  index = 10;
                  break;
              }
              ;
              break;
            case'selection color':
              switch (condSubjType) {
                case "ListView":
                  index = 11;
                  break;
              }
              ;
              break;
            case'selection index':
              switch (condSubjType) {
                case "ListPicker":
                  index = 27;
                  break;
                case "ListView":
                  index = 13;
                  break;
                case "Spinner":
                  index = 12;
                  break;
              }
              ;
              break;
            case'show filter bar':
              switch (condSubjType) {
                case "ListPicker":
                  index = 31;
                  break;
                case "ListView":
                  index = 15;
                  break;
              }
              ;
              break;
            case 'text':
              switch (condSubjType) {
                case "Label":
                  index = 10;
                  break;
                case "Button":
                  index = 23;
                  break;
                case "DatePicker":
                  index = 29;
                  break;
                case "ListPicker":
                  index = 33;
                  break;
                case "PasswordTextBox":
                  index = 16;
                  break;
                case "Switch":
                  index = 14;
                  break;
                case "TimePicker":
                  index = 28;
                  break;
              }
              ;
              break;
            case'text color':
              switch (condSubjType) {
                case "Label":
                  index = 12;
                  break;
                case "Button":
                  index = 25;
                  break;
                case "DatePicker":
                  index = 31;
                  break;
                case "ListPicker":
                  index = 37;
                  break;
                case "ListView":
                  index = 19;
                  break;
                case "PasswordTextBox":
                  index = 20;
                  break;
                case "Switch":
                  index = 16;
                  break;
                case "TimePicker":
                  index = 30;
                  break;
              }
              ;
              break;
            case'text size':
              switch (condSubjType) {
                case "ListView":
                  index = 21;
                  break;
              }
              ;
              break;
            case'title':
              switch (condSubjType) {
                case "ListPicker":
                  index = 39;
                  break;
              }
              ;
              break;
            case'thumb enabled':
              switch (condSubjType) {
                case "Slider":
                  index = 12;
                  break;
              }
              ;
              break;
            case'thumb position':
              switch (condSubjType) {
                case "Slider":
                  index = 14;
                  break;
              }
              ;
              break;
            case 'thumb color active':
              switch (condSubjType) {
                case "Switch":
                  index = 20;
                  break;
              }
              ;
              break;
            case'thumb color inactive':
              switch (condSubjType) {
                case "Switch":
                  index = 22;
                  break;
              }
              ;
              break;
            case'track color active':
              switch (condSubjType) {
                case "Switch":
                  index = 24;
                  break;
              }
              ;
              break;
            case'track color inactive':
              switch (condSubjType) {
                case "Switch":
                  index = 26;
                  break;
              }
              ;
              break;
            case 'visible':
              switch (condSubjType) {
                case "Label":
                  index = 14;
                  break;
                case "Button":
                  index = 27;
                  break;
                case "DatePicker":
                  index = 33;
                  break;
                case "Image":
                  index = 14;
                  break;
                case "ListPicker":
                  index = 41;
                  break;
                case "ListView":
                  index = 23;
                  break;
                case "PasswordTextBox":
                  index = 20;
                  break;
                case "Slider":
                  index = 15;
                  break;
                case "Spinner":
                  index = 15;
                  break;
                case "Switch":
                  index = 26;
                  break;
                case "Textbox":
                  index = 25;
                  break;
                case "TimePicker":
                  index = 34;
                  break;
              }
              ;
              break;
            case 'width':
              switch (condSubjType) {
                case "Label":
                  index = 16;
                  break;
                case "Button":
                  index = 29;
                  break;
                case "DatePicker":
                  index = 35;
                  break;
                case "Image":
                  index = 16;
                  break;
                case "ListPicker":
                  index = 42;
                  break;
                case "ListView":
                  index = 23;
                  break;
                case "PasswordTextBox":
                  index = 22;
                  break;
                case "Slider":
                  index = 16;
                  break;
                case "Spinner":
                  index = 16;
                  break;
                case "Switch":
                  index = 28;
                  break;
                case "Textbox":
                  index = 27;
                  break;
                case "TimePicker":
                  index = 36;
                  break;
              }
              ;
              break;
            case 'year':
              switch (condSubjType) {
                case "DatePicker":
                  index = 38;
                  break;
                  ;
                  break;
              }
              ;

          }

          var condBlock = createBlock.call(this, condSubj, index);

          //alert("cond block creato, index: " + index)


          if (f == 0) {
            firstIfBlock = condBlock;
          }

          //Attach NOT block To ConditionBlock
          if (condNOT != null && condNOT.toLowerCase().includes("not")) {
            //alert("c'è un not")
            var notBlock = createBlock.call(this, "Logic", 2); // "not" block
            connectBlocks.call(this, condBlock, notBlock, "notcond"); //connect not block to condblock

            if(condValue != "NoValue"){
              var equalBlock = createBlock.call(this, "Logic", 3); // == block
              var valueBlock = null;
              if(condVerbAct.includes("color")){
                switch (condValue.toLowerCase()){
                  case "black": valueBlock = createBlock.call(this, "Colors", 0); break;
                  case "white": valueBlock = createBlock.call(this, "Colors", 1); break;
                  case "red": valueBlock = createBlock.call(this, "Colors", 2); break;
                  case "pink": valueBlock = createBlock.call(this, "Colors", 3); break;
                  case "yellow": valueBlock = createBlock.call(this, "Colors", 5); break;
                  case "green": valueBlock = createBlock.call(this, "Colors", 6); break;
                  case "blue": valueBlock = createBlock.call(this, "Colors", 8); break;
                  case "grey": valueBlock = createBlock.call(this, "Colors", 10); break;
                  default:
                    //alert("Selected color not available. Will be use \"black\" as default");
                    valueBlock = createBlock.call(this, "Colors", 0);
                }
              }
              else{
                switch (condValue.toLowerCase()){
                  case "true": valueBlock = createBlock.call(this, "Logic", 0); break;
                  case "false": valueBlock = createBlock.call(this, "Logic", 1); break;
                  default: valueBlock = createBlock.call(this, "Text", 0);
                    for (var k = 0, input; input = valueBlock.inputList[k]; k++) {
                      for (var j = 0, field; field = input.fieldRow[j]; j++) {
                        if(field.name == "TEXT") {
                          field.setText(condValue);
                        }
                      }
                    }
                }
              }


              //alert("Post Condition" + f + "\nswitch...condVerbAct: " + condVerbAct + "\ncondSubjType: "
              //    + condSubjType + "\nvalueBlock: " + valueBlock + "\nindex: " + index)

              connectBlocks.call(this, notBlock, equalBlock, "left");
              connectBlocks.call(this, valueBlock, equalBlock, "right");
              notBlock = equalBlock;
            }
            else{
              //alert("niente value")
            }

            //If is the first condition, assign the cond block to var first if block
            if (f == 0) {
              //alert("primo if")
              firstIfBlock = notBlock;
            //If it's not the first condblock:
            }
            else {
              var ANDORBlock  = null;
              //Creation of ANDOR block
              if(condANDOR == "AND"){
                ANDORBlock  = createBlock.call(this, "Logic", 4); //AND block
              }
              else{
                ANDORBlock  = createBlock.call(this, "Logic", 5); //OR block
              }

              //if if the first ANDOR block, connect the firstifblock to the left, and the new ifblock to the right
              if(f == 1){
                //alert("secondo if")
                connectBlocks.call(this, firstIfBlock, ANDORBlock, "left");
                //alert("connesso a sx")
                connectBlocks.call(this, notBlock, ANDORBlock, "right");
                //alert("connesso a dx")
                firstIfBlock = ANDORBlock;
              }
              //if it's not the first ANDOR block, connect it to the prev ANDOR block and the new cond block to the new ANDOR block
              else if (f < (rule1.conditionNumber - 2)){
                connectBlocks.call(this, notBlock, ANDORBlock, "left");
                connectBlocks.call(this, ANDORBlock, prevANDORBlock, "right");
              }
              //if it's the last condblock/andorblock, connect it to the prev ANDOR block and the first ANDBlock to the if
              else{
                connectBlocks.call(this, notBlock, ANDORBlock, "left");
                connectBlocks.call(this, ANDORBlock, prevANDORBlock, "right");
                connectBlocks.call(this, firstIfBlock, mainCondBlock, "ifcond");
              }
              prevANDORBlock = ANDORBlock;
            }
          }
          else {
            if(condValue != "NoValue"){
              var equalBlock = createBlock.call(this, "Logic", 3); // == block
              var valueBlock = null;
              if(condVerbAct.includes("color")){
                switch (condValue.toLowerCase()){
                  case "black": valueBlock = createBlock.call(this, "Colors", 0); break;
                  case "white": valueBlock = createBlock.call(this, "Colors", 1); break;
                  case "red": valueBlock = createBlock.call(this, "Colors", 2); break;
                  case "pink": valueBlock = createBlock.call(this, "Colors", 3); break;
                  case "yellow": valueBlock = createBlock.call(this, "Colors", 5); break;
                  case "green": valueBlock = createBlock.call(this, "Colors", 6); break;
                  case "blue": valueBlock = createBlock.call(this, "Colors", 8); break;
                  case "grey": valueBlock = createBlock.call(this, "Colors", 10); break;
                  default:
                    //alert("Selected color not available. Will be use \"black\" as default");
                    valueBlock = createBlock.call(this, "Colors", 0);
                }
              }
              else{
                switch (condValue.toLowerCase()){
                  case "true": valueBlock = createBlock.call(this, "Logic", 0); break;
                  case "false": valueBlock = createBlock.call(this, "Logic", 1); break;
                  default: valueBlock = createBlock.call(this, "Text", 0);
                    for (var k = 0, input; input = valueBlock.inputList[k]; k++) {
                      for (var j = 0, field; field = input.fieldRow[j]; j++) {
                        if(field.name == "TEXT") {
                          field.setText(condValue);
                        }
                      }
                    }
                }
              }

              //alert("Post Condition" + f + "\nswitch...condVerbAct: " + condVerbAct + "\ncondSubjType: "
              //    + condSubjType + "\nvalueBlock: " + valueBlock + "\nindex: " + index)

              connectBlocks.call(this, condBlock, equalBlock, "left");
              connectBlocks.call(this, valueBlock, equalBlock, "right");
              condBlock = equalBlock;
            }
            else{
              //alert("niente value")
            }
            //If is the first condition, assign the cond block to var first if block
            if (f == 0) {
              //alert("primo ig")
              firstIfBlock = condBlock;
              //If it's not the first condblock:
            } else {
              var ANDORBlock  = null;
              //Creation of ANDOR block
              if(condANDOR == "AND"){
                ANDORBlock  = createBlock.call(this, "Logic", 4); //AND block
              }
              else{
                ANDORBlock  = createBlock.call(this, "Logic", 5); //OR block
              }

              //if if the first ANDOR block, connect the firstifblock to the left, and the new ifblock to the right
              if(f == 1){
                //alert("secondo if")
                connectBlocks.call(this, firstIfBlock, ANDORBlock, "left");
                //alert("connesso a sx")
                connectBlocks.call(this, condBlock, ANDORBlock, "right");
                //alert("connesso a dx")
                firstIfBlock = ANDORBlock;
              }
              //if it's not the first ANDOR block, connect it to the prev ANDOR block and the new cond block to the new ANDOR block
              else if (f < (rule1.conditionNumber - 2)){
                connectBlocks.call(this, condBlock, ANDORBlock, "left");
                connectBlocks.call(this, ANDORBlock, prevANDORBlock, "right");
              }
              //if it's the last condblock/andorblock, connect it to the prev ANDOR block and the first ANDBlock to the if
              else{
                connectBlocks.call(this, condBlock, ANDORBlock, "left");
                connectBlocks.call(this, ANDORBlock, prevANDORBlock, "right");
                connectBlocks.call(this, firstIfBlock, mainCondBlock, "ifcond");
              }
              prevANDORBlock = ANDORBlock;
            }
          }

        }
      }

      /******/
      //alert("MAIN ACTION")
      /*** ACTION ***/

      var firstActionBlock = null, prevActionBlock = null;
      //alert("rule1.actionNumber: " + rule1.actionNumber);
      for (var m = 0; m < rule1.actionNumber; m++) {
        //alert("ACTIONS " + m);
        var actionSubj = rule1.actions["action" + m] != null ?
            rule1.actions["action" + m].actionSubj :
            null;
        var actionType = rule1.actions["action" + m].actionType;
        var actionSubj = rule1.actions["action" + m].actionSubj;
        var actionVerb = rule1.actions["action" + m].actionVerb;
        var actionObj = rule1.actions["action" + m].actionObj;
        var actionSubjType = rule1.actions["action" + m].actionSubjType;
        var actionValue = rule1.actions["action" + m].actionValue;
        actionSubjType = actionSubjType.substring(0,1).toUpperCase() + actionSubjType.substring(1, actionSubjType.length);

        //alert("actionType: " + actionType + "\nactionValue: " + actionValue + "\nm: " + m);
        if(actionType.toLowerCase().includes("open")){
          var actionBlock = createBlock.call(this, "Control", 9);
        }
        else{
          var formattedVerb = actionVerb.replace("set ", "").replace("call ", "").replace(" to", "");
          switch (formattedVerb) {
            case 'background color':
              switch (actionSubjType) {
                case "Label":
                  index = 1;
                  break;
                case "Button":
                  index = 9;
                  break;
                case "DatePicker":
                  index = 11;
                  break;
                case "ListPicker":
                  index = 36
                  break;
                case "ListView":
                  index = 2;
                  break;
                case "PasswordTextBox":
                  index = 4;
                  break;
                case "Switch":
                  index = 6;
                  break;
                case "TextBox":
                  index = 7;
                  break;
                case "TimePicker":
                  index = 11;
                  break;
              }
              ;
              break;
            case 'animation':
              switch (actionSubjType){
                case "Image":
                  index = 1;
                  break;
              };
              break;
            case 'clickable':
              switch (actionSubjType) {
                case "Image":
                  index = 3;
                  break;
              }
              ;
              break;
            case'color left':
              switch (actionSubjType) {
                case "Slider":
                  index = 4;
                  break;
              }
              ;
              break;
            case'color right':
              switch (actionSubjType) {
                case "Slider":
                  index = 6;
                  break;
              }
              ;
              break;
            case'date to display':
              switch (actionSubjType) {
                case "DatePicker":
                  index = 8;
                  break;
              }
              ;
              break;
            case'date to display from instance':
              switch (actionSubjType) {
                case "DatePicker":
                  index = 9;
                  break;
              }
              ;
              break;
            case'launch picker':
              switch (actionSubjType) {
                case "DatePicker":
                  index = 7;
                  break;
                case "TimePicker":
                  index = 7;
                  break;
              }
              ;
              break;
            case'open':
              switch (actionSubjType) {
                case "ListPicker":
                  index = 2;
                  break;
              }
              ;
              break;
            case'request focus':
              switch (actionSubjType) {
                case "PasswordTextBox":
                  index = 2;
                  break;
                case "TextBox":
                  index = 3;
                  break;
              }
              ;
              break;
            case'display dropdown':
              switch (actionSubjType) {
                case "Spinner":
                  index = 1;
                  break;
              }
              ;
              break;
            case'hide keyboard':
              switch (actionSubjType) {
                case "TextBox":
                  index = 2;
                  break;
              }
              ;
              break;
            case'time to display':
              switch (actionSubjType) {
                case "TimePicker":
                  index = 8;
                  break;
              }
              ;
              break;
            case'time to display from instance':
              switch (actionSubjType) {
                case "TimePicker":
                  index = 9;
                  break;
              }
              ;
              break;
            case 'enabled':
              switch (actionSubjType) {
                case "Button":
                  index = 11;
                  break;
                case "DatePicker":
                  index = 12;
                  break;
                case "ListPicker":
                  index = 11;
                  break;
                case "PasswordTextBox":
                  index = 6;
                  break;
                case "Switch":
                  index = 8;
                  break;
                case "Textbox":
                  index = 9;
                  break;
                case "TimePicker":
                  index = 13;
                  break;
              }
              ;
              break;
            case'elements':
              switch (actionSubjType) {
                case "ListPicker":
                  index = 8;
                  break;
                case "ListView":
                  index = 6;
                  break;
                case "Spinner":
                  index = 5;
                  break;
              }
              ;
              break;
            case'elements from string':
              switch (actionSubjType) {
                case "ListPicker":
                  index = 9;
                  break;
                case "ListView":
                  index = 7;
                  break;
                case "Spinner":
                  index = 6;
                  break;
              }
              ;
              break;
            case'font bold':
              switch (actionSubjType) {
                case "Button":
                  index = 11;
                  break;
                case "DatePicker":
                  index = 14;
                  break;
                case "ListPicker":
                  index = 11;
                  break;
                case "TimePicker":
                  index = 15;
                  break;
              }
              ;
              break;
            case'font italic':
              switch (actionSubjType) {
                case "Button":
                  index = 13;
                  break;
                case "DatePicker":
                  index = 16;
                  break;
                case "ListPicker":
                  index = 13;
                  break;
                case "TimePicker":
                  index = 17;
                  break;
              }
              ;
              break;
            case'font size':
              switch (actionSubjType) {
                case "Button":
                  index = 15;
                  break;
                case "Label":
                  index = 3;
                  break;
                case "PasswordTextBox":
                  index = 8;
                  break;
                case "DatePicker":
                  index = 18;
                  break;
                case "ListPicker":
                  index = 15;
                  break;
                case "Switch":
                  index = 10;
                  break;
                case "Textbox":
                  index = 11;
                  break;
                case "TimePicker":
                  index = 19;
                  break;
              }
              ;
              break;
            case'has margins':
              switch (actionSubjType) {
                case "Label":
                  index = 6;
                  break;
              }
              ;
              break;
            case'height':
              switch (actionSubjType) {
                case "Label":
                  index = 8;
                  break;
                case "Button":
                  index = 17;
                  break;
                case "DatePicker":
                  index = 20;
                  break;
                case "Image":
                  index = 5;
                  break;
                case "ListPicker":
                  index = 17;
                  break;
                case "ListView":
                  index = 7;
                  break;
                case "PasswordTextBox":
                  index = 10;
                  break;
                case "Spinner":
                  index = 6;
                  break;
                case "Switch":
                  index = 12;
                  break;
                case "Textbox":
                  index = 13;
                  break;
                case "TimePicker":
                  index = 21;
                  break;
              }
              ;
              break;
            case'height percent':
              switch (actionSubjType) {
                case "Label":
                  index = 9;
                  break;
                case "Button":
                  index = 18;
                  break;
                case "DatePicker":
                  index = 21;
                  break;
                case "Image":
                  index = 6;
                  break;
                case "ListPicker":
                  index = 18;
                  break;
                case "ListView":
                  index = 8;
                  break;
                case "PasswordTextBox":
                  index = 11;
                  break;
                case "Spinner":
                  index = 7;
                  break;
                case "Switch":
                  index = 13;
                  break;
                case "Textbox":
                  index = 14;
                  break;
                case "TimePicker":
                  index = 22;
                  break;
                case 'Slider':
                  index = 5;
              }
              ;
              break;
            case'hint':
              switch (actionSubjType) {
                case "PasswordTextBox":
                  index = 13;
                  break;
                case "Textbox":
                  index = 14;
                  break;
              }
              ;
              break;
            case 'image':
              switch (actionSubjType) {
                case "Button":
                  index = 20;
                  break;
                case "DatePicker":
                  index = 23;
                  break;
                case "ListPicker":
                  index = 20;
                  break;
                case "TimePicker":
                  index = 23;
                  break;
              }
              ;
              break;
            case'item background color':
              switch (actionSubjType) {
                case "ListPicker":
                  index = 22;
                  break;
              }
              ;
              break;
            case'item text color':
              switch (actionSubjType) {
                case "ListPicker":
                  index = 24;
                  break;
              }
              ;
              break;
            case 'max value':
              switch (actionSubjType) {
                case "Slider":
                  index = 7;
                  break;
              }
              ;
              break;
            case 'min value':
              switch (actionSubjType) {
                case "Slider":
                  index = 9;
                  break;
              }
              ;
              break;
            case 'multi line':
              switch (actionSubjType) {
                case "Textbox":
                  index = 16;
                  break;
              }
              ;
              break;
            case 'numbers only':
              switch (actionSubjType) {
                case "Textbox":
                  index = 18;
                  break;
              }
              ;
              break;
            case 'on':
              switch (actionSubjType) {
                case "Switch":
                  index = 13;
                  break;
              }
              ;
              break;
            case 'picture':
              switch (actionSubjType) {
                case "Image":
                  index = 8;
                  break;
              }
              ;
              break;
            case 'password visible':
              switch (actionSubjType) {
                case "PasswordTextBox":
                  index = 15;
                  break;
              }
              ;
              break;
            case 'prompt':
              switch (actionSubjType) {
                case "Spinner":
                  index = 9;
                  break;
              }
              ;
              break;
            case 'rotation angle':
              switch (actionSubjType) {
                case "Image":
                  index = 10;
                  break;
              }
              ;
              break;
            case 'read only':
              switch (actionSubjType) {
                case "Textbox":
                  index = 20;
                  break;
              }
              ;
              break;
            case 'show feedback':
              switch (actionSubjType) {
                case "Button":
                  index = 22;
                  break;
                case "DatePicker":
                  index = 28;
                  break;
                case "ListPicker":
                  index = 30;
                  break;
                case "TimePicker":
                  index = 27;
                  break;
              }
              ;
              break;
            case'scaling':
              switch (actionSubjType) {
                case "Image":
                  index = 13;
                  break;
              }
              ;
              break;
            case'selection':
              switch (actionSubjType) {
                case "ListPicker":
                  index = 26;
                  break;
                case "ListView":
                  index = 10;
                  break;
                case "Spinner":
                  index = 11;
                  break;
              }
              ;
              break;
            case 'selection color':
              switch (actionSubjType) {
                case "ListView":
                  index = 12;
                  break;
              }
              ;
              break;
            case 'selection index':
              switch (actionSubjType) {
                case "ListPicker":
                  index = 28;
                  break;
                case "ListView":
                  index = 14;
                  break;
                case "Spinner":
                  index = 13;
                  break;
              }
              ;
              break;
            case 'show filter bar':
              switch (actionSubjType) {
                case "ListPicker":
                  index = 32;
                  break;
                case "ListView":
                  index = 16;
                  break;
              }
              ;
              break;
            case 'text':
              switch (actionSubjType) {
                case "Label":
                  index = 11;
                  break;
                case "Button":
                  index = 24;
                  break;
                case "DatePicker":
                  index = 30;
                  break;
                case "ListPicker":
                  index = 34;
                  break;
                case "PasswordTextBox":
                  index = 17;
                  break;
                case "Switch":
                  index = 15;
                  break;
                case "TimePicker":
                  index = 29;
                  break;
              }
              ;
              break;
            case'text color':
              switch (actionSubjType) {
                case "Label":
                  index = 13;
                  break;
                case "Button":
                  index = 26;
                  break;
                case "DatePicker":
                  index = 32;
                  break;
                case "ListPicker":
                  index = 36;
                  break;
                case "ListView":
                  index = 18;
                  break;
                case "PasswordTextBox":
                  index = 19;
                  break;
                case "Switch":
                  index = 17;
                  break;
                case "TimePicker":
                  index = 31;
                  break;
              }
              ;
              break;
            case'text size':
              switch (actionSubjType) {
                case "ListView":
                  index = 22;
                  break;
              }
              ;
              break;
            case'title':
              switch (actionSubjType) {
                case "ListPicker":
                  index = 40;
                  break;
              }
              ;
              break;
            case'thumb enabled':
              switch (actionSubjType) {
                case "Slider":
                  index = 13;
                  break;
              }
              ;
              break;
            case'thumb position':
              switch (actionSubjType) {
                case "Slider":
                  index = 15;
                  break;
              }
              ;
              break;
            case 'thumb color active':
              switch (actionSubjType) {
                case "Switch":
                  index = 21;
                  break;
              }
              ;
              break;
            case'thumb color inactive':
              switch (actionSubjType) {
                case "Switch":
                  index = 23;
                  break;
              }
              ;
              break;
            case'track color active':
              switch (actionSubjType) {
                case "Switch":
                  index = 25;
                  break;
              }
              ;
              break;
            case'track color inactive':
              switch (actionSubjType) {
                case "Switch":
                  index = 27;
                  break;
              }
              ;
              break;
            case 'visible':
              switch (actionSubjType) {
                case "Label":
                  index = 15;
                  break;
                case "Button":
                  index = 28;
                  break;
                case "DatePicker":
                  index = 34;
                  break;
                case "Image":
                  index = 15;
                  break;
                case "ListPicker":
                  index = 39;
                  break;
                case "ListView":
                  index = 21;
                  break;
                case "PasswordTextBox":
                  index = 22;
                  break;
                case "Slider":
                  index = 14;
                  break;
                case "Spinner":
                  index = 14;
                  break;
                case "Switch":
                  index = 28;
                  break;
                case "Textbox":
                  index = 26;
                  break;
                case "TimePicker":
                  index = 32;
                  break;
              }
              ;
              break;
            case 'width':
              switch (actionSubjType) {
                case "Label":
                  index = 17;
                  break;
                case "Button":
                  index = 29;
                  break;
                case "DatePicker":
                  index = 36;
                  break;
                case "Image":
                  index = 17;
                  break;
                case "ListPicker":
                  index = 41;
                  break;
                case "ListView":
                  index = 24;
                  break;
                case "PasswordTextBox":
                  index = 23;
                  break;
                case "Slider":
                  index = 17;
                  break;
                case "Spinner":
                  index = 17;
                  break;
                case "Switch":
                  index = 29;
                  break;
                case "Textbox":
                  index = 28;
                  break;
                case "TimePicker":
                  index = 35;
                  break;
              }
              ;
              break;
            case 'width percent':
              switch (actionSubjType) {
                case "Label":
                  index = 18;
                  break;
                case "Button":
                  index = 31;
                  break;
                case "DatePicker":
                  index = 37;
                  break;
                case "Image":
                  index = 18;
                  break;
                case "ListPicker":
                  index = 43;
                  break;
                case "ListView":
                  index = 25;
                  break;
                case "PasswordTextBox":
                  index = 24;
                  break;
                case "Slider":
                  index = 18;
                  break;
                case "Spinner":
                  index = 18;
                  break;
                case "Switch":
                  index = 30;
                  break;
                case "Textbox":
                  index = 29;
                  break;
                case "TimePicker":
                  index = 36;
                  break;
              }
              ;
              break;
          };

          var actionBlock = createBlock.call(this, actionSubj, index);
        }
        //alert("ok action block\nm: " + m)
        var actionValueBlock = null;
        //alert("actionValue: " + actionValue);
        if(formattedVerb != null && formattedVerb.includes("color")){
          switch (actionValue.toLowerCase()){
            case "black": actionValueBlock = createBlock.call(this, "Colors", 0); break;
            case "white": actionValueBlock = createBlock.call(this, "Colors", 1); break;
            case "red": actionValueBlock = createBlock.call(this, "Colors", 2); break;
            case "pink": actionValueBlock = createBlock.call(this, "Colors", 3); break;
            case "yellow": actionValueBlock = createBlock.call(this, "Colors", 5); break;
            case "green": actionValueBlock = createBlock.call(this, "Colors", 6); break;
            case "blue": actionValueBlock = createBlock.call(this, "Colors", 8); break;
            case "grey": actionValueBlock = createBlock.call(this, "Colors", 10); break;
            default:
              //alert("Selected color not available. Will be use \"black\" as default");
              actionValueBlock = createBlock.call(this, "Colors", 0);
          }
        }
        else{
          switch (actionValue.toLowerCase()){
            case "true": actionValueBlock = createBlock.call(this, "Logic", 0); break;
            case "false": actionValueBlock = createBlock.call(this, "Logic", 1); break;
            default: actionValueBlock = createBlock.call(this, "Text", 0);
              for (var k = 0, input; input = actionValueBlock.inputList[k]; k++) {
                for (var j = 0, field; field = input.fieldRow[j]; j++) {
                  if(field.name == "TEXT") {
                    field.setText(actionValue);
                  }
                }
              }
          }
        }

        //alert("ok action value block\nm: " + m)

        //alert("Post Action" + m + "\nswitch...formattedVerb: " + formattedVerb + "\nactionSubjType: " + actionSubjType +
        //    "\nactionValueBlock:" + actionValueBlock + "\nindex: " + index)
        connectBlocks.call(this, actionValueBlock, actionBlock, "open")


        if(m == 0){
          //alert("m == 0")
          firstActionBlock = actionBlock;
          prevActionBlock = actionBlock;
        }
        else{
          //alert("else")
          connectBlocks.call(this, actionBlock, prevActionBlock, "nextAction");//connect new if after the prev one TODO idk if "ifcond" params is correct or not
          prevActionBlock = actionBlock;
        }
      }
      /******/

      //alert("Pre last connections")

      if (rule1.conditionNumber == null || rule1.conditionNumber == 0) {
        //alert("no if: \n firstActionBlock: " + firstActionBlock + "\nwhenBlock: " + whenBlock);
        //alert("Click ok to connect isHiddenBlock with whenBlock");
        connectBlocks.call(this, firstActionBlock, whenBlock, "when");
      }
      else {
        //alert("Click ok to connect isHiddenBlock to mainCondBlock");
        connectBlocks.call(this, firstIfBlock, mainCondBlock, "ifcond");
        connectBlocks.call(this, firstActionBlock, mainCondBlock, "ifblock");
        //alert("Click ok to connect mainCondBlock to whenBlock");
        connectBlocks.call(this, mainCondBlock, whenBlock, "if");
      }

      return [id, whenBlock.y_];
    }
  }

    return null;
}

//feduss
Blockly.Drawer.prototype.insertBlock = function (rule){
  //when button1 is clicked then label1 is hidden

  //alert("rule: \n" + rule)
  var rule1 = JSON.parse(rule);
  var index = -1; //index is the number of block of flyout of that drawerName

  return insertBlockRecursive.call(this, rule1);

}

/**
 * Show the contents of the built-in drawer named drawerName. drawerName
 * should be one of Blockly.Msg.VARIABLE_CATEGORY,
 * Blockly.Msg.PROCEDURE_CATEGORY, or one of the built-in block categories.
 * @param drawerName
 */
Blockly.Drawer.prototype.showBuiltin = function(drawerName) {
  drawerName = Blockly.Drawer.PREFIX_ + drawerName;
  var blockSet = this.options.languageTree[drawerName];
  if(drawerName == "cat_Procedures") {
    var newBlockSet = [];
    for(var i=0;i<blockSet.length;i++) {
      if(!(blockSet[i] == "procedures_callnoreturn" // Include callnoreturn only if at least one defnoreturn declaration
          && this.workspace_.getProcedureDatabase().voidProcedures == 0)
          &&
          !(blockSet[i] == "procedures_callreturn" // Include callreturn only if at least one defreturn declaration
              && this.workspace_.getProcedureDatabase().returnProcedures == 0)){
        newBlockSet.push(blockSet[i]);
      }
    }
    blockSet = newBlockSet;
  }

  if (!blockSet) {
    throw "no such drawer: " + drawerName;
  }
  Blockly.hideChaff();
  var xmlList = this.blockListToXMLArray(blockSet);
  this.flyout_.show(xmlList);
};

/**
 * Show the blocks drawer for the component with give instance name. If no
 * such component is found, currently we just log a message to the console
 * and do nothing.
 */
Blockly.Drawer.prototype.showComponent = function(instanceName) {
  var component = this.workspace_.getComponentDatabase().getInstance(instanceName);
  if (component) {
    Blockly.hideChaff();
    this.flyout_.show(this.instanceRecordToXMLArray(component));
    this.lastComponent = instanceName;
  } else {
    console.log("Got call to Blockly.Drawer.showComponent(" +  instanceName +
        ") - unknown component name");
  }
};

/**
 * Show the contents of the generic component drawer named drawerName. (This is under the
 * "Any components" section in App Inventor). drawerName should be the name of a component type for
 * which we have at least one component instance in the blocks workspace. If no such component
 * type is found, currently we just log a message to the console and do nothing.
 * @param {!string} typeName
 */
Blockly.Drawer.prototype.showGeneric = function(typeName) {
  if (this.workspace_.getComponentDatabase().hasType(typeName)) {
    Blockly.hideChaff();
    var xmlList = this.componentTypeToXMLArray(typeName);
    this.flyout_.show(xmlList);
  } else {
    console.log("Got call to Blockly.Drawer.showGeneric(" +  typeName +
        ") - unknown component type name");
  }
};

/**
 * Hide the Drawer flyout
 */
Blockly.Drawer.prototype.hide = function() {
  this.lastComponent = null;
  this.flyout_.hide();
};

/**
 * @returns {boolean} true if the Drawer flyout is currently open, false otherwise.
 */
Blockly.Drawer.prototype.isShowing = function() {
  return this.flyout_.isVisible();
};

Blockly.Drawer.prototype.blockListToXMLArray = function(blockList) {
  var xmlArray = [];
  for(var i=0;i<blockList.length;i++) {
    Array.prototype.push.apply(xmlArray, this.blockTypeToXMLArray(blockList[i],null));
  }
  return xmlArray;
};

/**
 * @param {{name: string, typeName: string}} instanceRecord
 */
Blockly.Drawer.prototype.instanceRecordToXMLArray = function(instanceRecord) {
  var xmlArray = [];
  var typeName = instanceRecord.typeName;
  var componentInfo = this.workspace_.getComponentDatabase().getType(typeName);

  var formName = Blockly.mainWorkspace.formName;
  var screenName = formName.substring(formName.indexOf("_") + 1);
  var subsetJsonString = "";
  if (window.parent.BlocklyPanel_getComponentInstancePropertyValue) {
    subsetJsonString = window.parent.BlocklyPanel_getComponentInstancePropertyValue(formName, screenName, "BlocksToolkit");
  }
  if (subsetJsonString.length > 0) {
    var subsetArray = [];
    var subsetBlocks = [];
    subsetArray = JSON.parse(subsetJsonString);
    var subsetBlockArray = subsetArray["shownBlockTypes"]["ComponentBlocks"][typeName];
    // The component type might not be in the json string if it was removed from the blocks toolkit
    // after an instance was already created in the Designer. It's not entirely clear what behavior
    // one would expect in this situation. I'm going to leave the flyout blank.
    if (subsetBlockArray !== undefined) {
      for (var i = 0; i < subsetBlockArray.length; i++) {
        var obj = subsetBlockArray[i];
        obj['mutatorNameToValue']['instance_name'] = instanceRecord.name;
        obj['fieldNameToValue']['COMPONENT_SELECTOR'] = instanceRecord.name;
        console.log("added obj");
        console.log(obj);
        var xml = bd.toolbox.ctr.blockObjectToXML(bd.toolbox.ctr.blockInfoToBlockObject(obj));
        xmlArray.push(xml);
      }
      //create component literal block
      var obj = {type: "component_component_block"};
      var mutatorAttributes = {component_type: typeName, instance_name: instanceRecord.name};
      obj['mutatorNameToValue'] = mutatorAttributes;
      var xml = bd.toolbox.ctr.blockObjectToXML(bd.toolbox.ctr.blockInfoToBlockObject(obj));
      //console.log(xml);
      xmlArray.push(xml);
    }
  } else {

    //create event blocks
    goog.object.forEach(componentInfo.eventDictionary, function (event, name) {
      if (event.deprecated != 'true' && event.deprecated !== true) {
        Array.prototype.push.apply(xmlArray, this.blockTypeToXMLArray('component_event', {
          'component_type': typeName, 'instance_name': instanceRecord.name, 'event_name': name
        }));
      }
    }, this);

    //create non-generic method blocks
    goog.object.forEach(componentInfo.methodDictionary, function (method, name) {
      if (method.deprecated != 'true' && method.deprecated !== true) {
        Array.prototype.push.apply(xmlArray, this.blockTypeToXMLArray('component_method', {
          'component_type': typeName, 'instance_name': instanceRecord.name, 'method_name': name
        }));
      }
    }, this);

    //for each property
    goog.object.forEach(componentInfo.properties, function (property, name) {
      if (property.deprecated != 'true' && property.deprecated !== true) {
        var params = {
          'component_type': typeName, 'instance_name': instanceRecord.name,
          'property_name': name
        };
        if ((property.mutability & Blockly.PROPERTY_READABLE) == Blockly.PROPERTY_READABLE) {
          params['set_or_get'] = 'get';
          Array.prototype.push.apply(xmlArray, this.blockTypeToXMLArray('component_set_get', params));
        }
        if ((property.mutability & Blockly.PROPERTY_WRITEABLE) == Blockly.PROPERTY_WRITEABLE) {
          params['set_or_get'] = 'set';
          Array.prototype.push.apply(xmlArray, this.blockTypeToXMLArray('component_set_get', params));
        }
      }
    }, this);

    //create component literal block
    var mutatorAttributes = {component_type: typeName, instance_name: instanceRecord.name};
    Array.prototype.push.apply(xmlArray, this.blockTypeToXMLArray("component_component_block", mutatorAttributes));
  }
  return xmlArray;
};

Blockly.Drawer.prototype.componentTypeToXMLArray = function(typeName) {
  var xmlArray = [];
  var componentInfo = this.workspace_.getComponentDatabase().getType(typeName);

  //create generic event blocks
  goog.object.forEach(componentInfo.eventDictionary, function(event, name){
    if(!event.deprecated){
      Array.prototype.push.apply(xmlArray, this.blockTypeToXMLArray('component_event', {
        component_type: typeName, event_name: name, is_generic: 'true'
      }));
    }
  }, this);

  //create generic method blocks
  goog.object.forEach(componentInfo.methodDictionary, function(method, name) {
    if (!method.deprecated) {
      Array.prototype.push.apply(xmlArray, this.blockTypeToXMLArray('component_method', {
        component_type: typeName, method_name: name, is_generic: "true"
      }));
    }
  }, this);

  //for each property
  goog.object.forEach(componentInfo.properties, function(property, name) {
    if (!property.deprecated) {
      var params = {component_type: typeName, property_name: name};
      if ((property.mutability & Blockly.PROPERTY_READABLE) == Blockly.PROPERTY_READABLE) {
        params.set_or_get = 'get';
        Array.prototype.push.apply(xmlArray, this.blockTypeToXMLArray('component_set_get', params));
      }
      if ((property.mutability & Blockly.PROPERTY_WRITEABLE) == Blockly.PROPERTY_WRITEABLE) {
        params.set_or_get = 'set';
        Array.prototype.push.apply(xmlArray, this.blockTypeToXMLArray('component_set_get', params));
      }
    }
  }, this);

  return xmlArray;
};

Blockly.Drawer.prototype.blockTypeToXMLArray = function(blockType,mutatorAttributes) {
  var xmlString = Blockly.Drawer.getDefaultXMLString(blockType,mutatorAttributes);
  if(xmlString == null) {
    // [lyn, 10/23/13] Handle procedure calls in drawers specially
    if (blockType == 'procedures_callnoreturn' || blockType == 'procedures_callreturn') {
      xmlString = this.procedureCallersXMLString(blockType == 'procedures_callreturn');
    } else {
      xmlString = '<xml><block type="' + blockType + '">';
      if(mutatorAttributes) {
        if (mutatorAttributes['is_generic'] === undefined) {
          mutatorAttributes['is_generic'] = !mutatorAttributes['instance_name']
        }
        xmlString += Blockly.Drawer.mutatorAttributesToXMLString(mutatorAttributes);
      }
      xmlString += '</block></xml>';
    }
  }
  var xmlBlockArray = [];
  var xmlFromString = Blockly.Xml.textToDom(xmlString);
  // [lyn, 11/10/13] Use goog.dom.getChildren rather than .children or .childNodes
  //   to make this code work across browsers.
  var children = goog.dom.getChildren(xmlFromString);
  for(var i=0;i<children.length;i++) {
    xmlBlockArray.push(children[i]);
  }
  return xmlBlockArray;
};

Blockly.Drawer.mutatorAttributesToXMLString = function(mutatorAttributes){
  var xmlString = '<mutation ';
  for(var attributeName in mutatorAttributes) {
    if (!mutatorAttributes.hasOwnProperty(attributeName)) continue;
    xmlString += attributeName + '="' + mutatorAttributes[attributeName] + '" ';
  }
  xmlString += '></mutation>';
  return xmlString;
};

// [lyn, 10/22/13] return an XML string including one procedure caller for each procedure declaration
// in main workspace.
// [jos, 10/18/15] if we pass a proc_name, we only want one procedure returned as xmlString
Blockly.Drawer.prototype.procedureCallersXMLString = function(returnsValue, proc_name) {
  var xmlString = '<xml>';  // Used to accumulate xml for each caller
  var decls = this.workspace_.getProcedureDatabase().getDeclarationBlocks(returnsValue);

  if (proc_name) {
    for (var i = 0; i < decls.length; i++) {
      if (decls[i].getFieldValue('NAME').toLocaleLowerCase() == proc_name){
        xmlString += Blockly.Drawer.procedureCallerBlockString(decls[i]);
        break;
      }
    }
  }
  else {
    decls.sort(Blockly.Drawer.compareDeclarationsByName); // sort decls lexicographically by procedure name
    for (var i = 0; i < decls.length; i++) {
      xmlString += Blockly.Drawer.procedureCallerBlockString(decls[i]);
    }
  }
  xmlString += '</xml>';
  return xmlString;
};

Blockly.Drawer.compareDeclarationsByName = function (decl1, decl2) {
  var name1 = decl1.getFieldValue('NAME').toLocaleLowerCase();
  var name2 = decl2.getFieldValue('NAME').toLocaleLowerCase();
  return name1.localeCompare(name2);
};

// [lyn, 10/22/13] return an XML string for a caller block for the give procedure declaration block
// Here's an example:
//   <block type="procedures_callnoreturn" inline="false">
//     <title name="PROCNAME">p2</title>
//     <mutation name="p2">
//       <arg name="b"></arg>
//       <arg name="c"></arg>
//    </mutation>
//  </block>
Blockly.Drawer.procedureCallerBlockString = function(procDeclBlock) {
  var declType = procDeclBlock.type;
  var callerType = (declType == 'procedures_defreturn') ? 'procedures_callreturn' : 'procedures_callnoreturn';
  var blockString = '<block type="' + callerType + '" inline="false">'
  var procName = procDeclBlock.getFieldValue('NAME');
  blockString += '<title name="PROCNAME">' + procName + '</title>';
  var mutationDom = procDeclBlock.mutationToDom();
  mutationDom.setAttribute('name', procName); // Decl doesn't have name attribute, but caller does
  blockString += Blockly.Xml.domToText(mutationDom);
  blockString += '</block>';
  return blockString;
};

/**
 * Given the blockType and a dictionary of the mutator attributes
 * either return the xml string associated with the default block
 * or return null, since there are no default blocks associated with the blockType.
 */
Blockly.Drawer.getDefaultXMLString = function(blockType,mutatorAttributes) {
  //return null if the
  if(Blockly.Drawer.defaultBlockXMLStrings[blockType] == null) {
    return null;
  }

  if(Blockly.Drawer.defaultBlockXMLStrings[blockType].xmlString != null) {
    //return xml string associated with block type
    return Blockly.Drawer.defaultBlockXMLStrings[blockType].xmlString;
  } else if(Blockly.Drawer.defaultBlockXMLStrings[blockType].length != null){
    var possibleMutatorDefaults = Blockly.Drawer.defaultBlockXMLStrings[blockType];
    var matchingAttributes;
    var allMatch;
    //go through each of the possible matching cases
    for(var i=0;i<possibleMutatorDefaults.length;i++) {
      matchingAttributes = possibleMutatorDefaults[i].matchingMutatorAttributes;
      //if the object doesn't have a matchingAttributes object, then skip it
      if(!matchingAttributes) {
        continue;
      }
      //go through each of the mutator attributes.
      //if one attribute does not match then move to the next possibility
      allMatch = true;
      for(var mutatorAttribute in matchingAttributes) {
        if (!matchingAttributes.hasOwnProperty(mutatorAttribute)) continue;
        if(mutatorAttributes[mutatorAttribute] != matchingAttributes[mutatorAttribute]){
          allMatch = false;
          break;
        }
      }
      //if all of the attributes matched, return the xml string given the appropriate mutator
      if(allMatch) {
        return possibleMutatorDefaults[i].mutatorXMLStringFunction(mutatorAttributes);
      }
    }
    //if the mutator attributes did not match for all of the possibilities, return null
    return null;
  }

};

Blockly.Drawer.defaultBlockXMLStrings = {
  controls_if: {xmlString:
        '<xml>' +
        '<block type="controls_if">' +
        '</block>' +
        '<block type="controls_if">' +
        '<mutation else="1"></mutation>' +
        '</block>' +
        '<block type="controls_if">' +
        '<mutation elseif="1" else="1"></mutation>' +
        '</block>' +
        '</xml>' },

  controls_forRange: {xmlString:
        '<xml>' +
        '<block type="controls_forRange">' +
        '<value name="START"><block type="math_number"><title name="NUM">1</title></block></value>' +
        '<value name="END"><block type="math_number"><title name="NUM">5</title></block></value>' +
        '<value name="STEP"><block type="math_number"><title name="NUM">1</title></block></value>' +
        '</block>' +
        '</xml>' },

  math_random_int: {xmlString:
        '<xml>' +
        '<block type="math_random_int">' +
        '<value name="FROM"><block type="math_number"><title name="NUM">1</title></block></value>' +
        '<value name="TO"><block type="math_number"><title name="NUM">100</title></block></value>' +
        '</block>' +
        '</xml>'},
  color_make_color: {xmlString:
        '<xml>' +
        '<block type="color_make_color">' +
        '<value name="COLORLIST">' +
        '<block type="lists_create_with" inline="false">' +
        '<mutation items="3"></mutation>' +
        '<value name="ADD0"><block type="math_number"><title name="NUM">255</title></block></value>' +
        '<value name="ADD1"><block type="math_number"><title name="NUM">0</title></block></value>' +
        '<value name="ADD2"><block type="math_number"><title name="NUM">0</title></block></value>' +
        '</block>' +
        '</value>' +
        '</block>' +
        '</xml>'},
  lists_create_with: {xmlString:
        '<xml>' +
        '<block type="lists_create_with">' +
        '<mutation items="0"></mutation>' +
        '</block>' +
        '<block type="lists_create_with">' +
        '<mutation items="2"></mutation>' +
        '</block>' +
        '</xml>'},
  lists_lookup_in_pairs: {xmlString:
        '<xml>' +
        '<block type="lists_lookup_in_pairs">' +
        '<value name="NOTFOUND"><block type="text"><title name="TEXT">not found</title></block></value>' +
        '</block>' +
        '</xml>'},
  lists_join_with_separator: {xmlString:
        '<xml>' +
        '<block type="lists_join_with_separator">' +
        '<value name="SEPARATOR"><block type="text"><title name="TEXT"></title></block></value>' +
        '</block>' +
        '</xml>'},
  dictionaries_create_with: {xmlString:
        '<xml>' +
        '<block type="dictionaries_create_with">' +
        '<mutation items="0"></mutation>' +
        '</block>' +
        '<block type="dictionaries_create_with">' +
        '<mutation items="2"></mutation>' +
        '<value name="ADD0"><block type="pair"></block></value>' +
        '<value name="ADD1"><block type="pair"></block></value>' +
        '</block>' +
        '</xml>'},
  dictionaries_lookup: {xmlString:
        '<xml>' +
        '<block type="dictionaries_lookup">' +
        '<value name="NOTFOUND"><block type="text"><title name="TEXT">not found</title></block></value>' +
        '</block>' +
        '</xml>'},
  dictionaries_recursive_lookup: {xmlString:
        '<xml>' +
        '<block type="dictionaries_recursive_lookup">' +
        '<value name="NOTFOUND"><block type="text"><title name="TEXT">not found</title></block></value>' +
        '</block>' +
        '</xml>'},

  component_method: [
    {matchingMutatorAttributes:{component_type:"TinyDB", method_name:"GetValue"},
      mutatorXMLStringFunction: function(mutatorAttributes) {
        return '' +
            '<xml>' +
            '<block type="component_method">' +
            //mutator generator
            Blockly.Drawer.mutatorAttributesToXMLString(mutatorAttributes) +
            '<value name="ARG1"><block type="text"><title name="TEXT"></title></block></value>' +
            '</block>' +
            '</xml>';}},

    {matchingMutatorAttributes:{component_type:"FirebaseDB", method_name:"GetValue"},
      mutatorXMLStringFunction: function(mutatorAttributes) {
        return '' +
            '<xml>' +
            '<block type="component_method">' +
            //mutator generator
            Blockly.Drawer.mutatorAttributesToXMLString(mutatorAttributes) +
            '<value name="ARG1"><block type="text"><title name="TEXT"></title></block></value>' +
            '</block>' +
            '</xml>';}},

    // Notifer.ShowTextDialog has cancelable default to TRUE
    {matchingMutatorAttributes:{component_type:"Notifier", method_name:"ShowTextDialog"},
      mutatorXMLStringFunction: function(mutatorAttributes) {
        return '' +
            '<xml>' +
            '<block type="component_method">' +
            //mutator generator
            Blockly.Drawer.mutatorAttributesToXMLString(mutatorAttributes) +
            '<value name="ARG2"><block type="logic_boolean"><title name="BOOL">TRUE</title></block></value>' +
            '</block>' +
            '</xml>';}},

    // Notifer.ShowChooseDialog has cancelable default to TRUE
    {matchingMutatorAttributes:{component_type:"Notifier", method_name:"ShowChooseDialog"},
      mutatorXMLStringFunction: function(mutatorAttributes) {
        return '' +
            '<xml>' +
            '<block type="component_method">' +
            //mutator generator
            Blockly.Drawer.mutatorAttributesToXMLString(mutatorAttributes) +
            '<value name="ARG4"><block type="logic_boolean"><title name="BOOL">TRUE</title></block></value>' +
            '</block>' +
            '</xml>';}},

    // Canvas.DrawCircle has fill default to TRUE
    {matchingMutatorAttributes:{component_type:"Canvas", method_name:"DrawCircle"},
      mutatorXMLStringFunction: function(mutatorAttributes) {
        return '' +
            '<xml>' +
            '<block type="component_method">' +
            //mutator generator
            Blockly.Drawer.mutatorAttributesToXMLString(mutatorAttributes) +
            '<value name="ARG3"><block type="logic_boolean"><title name="BOOL">TRUE</title></block></value>' +
            '</block>' +
            '</xml>';}},

    // Canvas.DrawShape has fill default to TRUE
    {matchingMutatorAttributes:{component_type:"Canvas", method_name:"DrawShape"},
      mutatorXMLStringFunction: function(mutatorAttributes) {
        return '' +
            '<xml>' +
            '<block type="component_method">' +
            //mutator generator
            Blockly.Drawer.mutatorAttributesToXMLString(mutatorAttributes) +
            '<value name="ARG1"><block type="logic_boolean"><field name="BOOL">TRUE</field></block></value>' +
            '</block>' +
            '</xml>';}},

    // Canvas.DrawArc has useCenter default to FALSE and fill default to TRUE
    {matchingMutatorAttributes:{component_type:"Canvas", method_name:"DrawArc"},
      mutatorXMLStringFunction: function(mutatorAttributes) {
        return '' +
            '<xml>' +
            '<block type="component_method">' +
            //mutator generator
            Blockly.Drawer.mutatorAttributesToXMLString(mutatorAttributes) +
            '<value name="ARG6"><block type="logic_boolean"><field name="BOOL">FALSE</field></block></value>' +
            '<value name="ARG7"><block type="logic_boolean"><field name="BOOL">TRUE</field></block></value>' +
            '</block>' +
            '</xml>';}},

    // Clock.FormatDate has pattern default to MMM d, yyyy
    {matchingMutatorAttributes:{component_type:"Clock", method_name:"FormatDate"},
      mutatorXMLStringFunction: function(mutatorAttributes) {
        return '' +
            '<xml>' +
            '<block type="component_method">' +
            //mutator generator
            Blockly.Drawer.mutatorAttributesToXMLString(mutatorAttributes) +
            '<value name="ARG1"><block type="text"><field name="TEXT">MMM d, yyyy</field></block></value>' +
            '</block>' +
            '</xml>';}},

    // Clock.FormatDateTime has pattern default to MM/dd/yyyy hh:mm:ss a
    {matchingMutatorAttributes:{component_type:"Clock", method_name:"FormatDateTime"},
      mutatorXMLStringFunction: function(mutatorAttributes) {
        return '' +
            '<xml>' +
            '<block type="component_method">' +
            //mutator generator
            Blockly.Drawer.mutatorAttributesToXMLString(mutatorAttributes) +
            '<value name="ARG1"><block type="text"><field name="TEXT">MM/dd/yyyy hh:mm:ss a</field></block></value>' +
            '</block>' +
            '</xml>';}}
  ]
};
