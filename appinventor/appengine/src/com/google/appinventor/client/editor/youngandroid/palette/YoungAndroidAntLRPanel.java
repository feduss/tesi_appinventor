package com.google.appinventor.client.editor.youngandroid.palette;

import com.google.appinventor.client.editor.youngandroid.YaFormEditor;
import com.google.appinventor.client.thesis.TesiLexer;
import com.google.appinventor.client.thesis.TesiParser;
import com.google.appinventor.client.thesis.TesiParserListener;
import com.google.appinventor.client.thesis.TesiParserListenerC;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import static com.google.appinventor.client.Ode.MESSAGES;

public class YoungAndroidAntLRPanel extends Composite {

    ///the panel the will contain all the following views
    VerticalPanel panel;

    ///Input that user fills and antlr parses
    TextBox inputTextBox;

    Button confirmButton;

    ///Label that shows the antlr parsing result
    Label resultLabel;

    public YoungAndroidAntLRPanel(YaFormEditor myFormEditor) {

        panel = new VerticalPanel();
        panel.setWidth("100%");

        inputTextBox = new TextBox(); //initialization
        inputTextBox.setWidth("100%"); //fill width
        inputTextBox.getElement().setPropertyString("placeholder", MESSAGES.inputTextBoxAntLR()); //set a placeholder
        inputTextBox.getElement().setAttribute("style", "width: 100%; box-sizing: border-box;"); //set css style

        resultLabel = new Label();
        resultLabel.setWidth("100%"); //fill width
        resultLabel.getElement().setPropertyString("placeholder", MESSAGES.resultLabelAntLR()); //set a placeholder
        resultLabel.getElement().setAttribute("style", "width: 100%; box-sizing: border-box;"); //set css style

        //TODO aggiungere una label con gli error di parsing?

        confirmButton = new Button();
        confirmButton.setWidth("20%");
        confirmButton.setText("PARSE");
        confirmButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                ///Il nostro lexer
                TesiLexer tesiLexer = new TesiLexer(CharStreams.fromString(inputTextBox.getText()));
                ///
                ///https://www.antlr.org/api/Java/org/antlr/v4/runtime/CommonTokenStream.html
                CommonTokenStream commonTokenStream = new CommonTokenStream(tesiLexer);

                ///Il nostro parser
                TesiParser tesiParser = new TesiParser(commonTokenStream);

                //Ottengo il parserTree dal nostro parser
                ParseTree tree = tesiParser.blocks();

                //Creo un nuovo listener per l'input string
                TesiParserListener listener = new TesiParserListenerC();
                //E creo anche quello per gli errori
                //TesiParserErrorListener errorListener = new TesiParserErrorListener();

                //E li aggiungo al nostro parser
                tesiParser.addParseListener(listener);
                //tesiParser.addErrorListener(errorListener);

                //Navigo nel parser tree usando il listener appena creato
                ParseTreeWalker walker = new ParseTreeWalker();
                walker.walk(listener, tree);

                if(tesiParser.getNumberOfSyntaxErrors() == 0){
                    if(tesiParser.getRuleNames() != null || tesiParser.getRuleNames().length == 0) {
                        System.out.println("Rules detected: " + tesiParser.getRuleNames().length);

                        StringBuilder stringBuilder = new StringBuilder();
                        for (String rule : tesiParser.getRuleNames()) {
                            //System.out.println("\n " + rule);
                            stringBuilder.append(rule);
                            stringBuilder.append(" ;");
                        }
                        //System.out.println(tree.toStringTree(tesiParser));
                        resultLabel.setText(stringBuilder.toString());
                    }
                    else{
                        resultLabel.setText("No rules detected.");
                    }
                }
                else{
                    resultLabel.setText("Check the errors and try again.");
                }

            }
        });

        //add all to the vertical panel
        panel.add(inputTextBox);
        panel.add(confirmButton);
        panel.add(resultLabel);

        initWidget(panel);
    }
}
