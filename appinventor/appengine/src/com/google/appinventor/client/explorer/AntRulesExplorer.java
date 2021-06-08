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
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static com.google.appinventor.client.Ode.MESSAGES;


public class AntRulesExplorer extends Composite {

  public AntRulesExplorer(){
    ScrollPanel scrollPanel = new ScrollPanel();
    scrollPanel.setWidth("800px");  // wide enough to avoid a horizontal scrollbar most of the time
    scrollPanel.setHeight("100%"); // approximately the same height as the viewer


    VerticalPanel panel = new VerticalPanel();
    panel.add(scrollPanel);
    Label label = new Label("Questo sarà il panel con le regole");
    panel.add(label);
    initWidget(panel);
  }
}
