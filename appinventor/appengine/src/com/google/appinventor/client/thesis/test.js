insertBlock = function static (drawerName) {
    if(drawerName){
        alert("Sei nella funzione insertBlock di test.js. DrawerName: " + drawerName);
    }
    else{
        alert("Sei nella funzione insertBlock di test.js. DrawerName: null");
    }
    /*drawerName = Blockly.Drawer.PREFIX_ + drawerName;
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
    var xmlList = this.blockListToXMLArray(blockSet);*/

    /*****************/

    /*if (xmlList == Blockly.Variables.NAME_TYPE) {
        // Special category for variables.
        xmlList =
            Blockly.Variables.flyoutCategory(this.workspace_.targetWorkspace);
    } else if (xmlList == Blockly.Procedures.NAME_TYPE) {
        // Special category for procedures.
        xmlList =
            Blockly.Procedures.flyoutCategory(this.workspace_.targetWorkspace);
    }

    var contents = [];
    var gaps = [];
    this.permanentlyDisabled_.length = 0;
    for (var i = 0, xml; xml = xmlList[i]; i++) {
        if (xml.tagName) {
            var tagName = xml.tagName.toUpperCase();
            var default_gap = this.horizontalLayout_ ? this.GAP_X : this.GAP_Y;
            if (tagName == 'BLOCK') {
                try {
                    var curBlock = Blockly.Xml.domToBlock(xml, this.workspace_);
                    if (curBlock.disabled) {
                        // Record blocks that were initially disabled.
                        // Do not enable these blocks as a result of capacity filtering.
                        this.permanentlyDisabled_.push(curBlock);
                    }
                    contents.push({type: 'block', block: curBlock});
                    var gap = parseInt(xml.getAttribute('gap'), 10);
                    gaps.push(isNaN(gap) ? default_gap : gap);
                } catch(e) {
                    console.error(e);
                }
            } else if (xml.tagName.toUpperCase() == 'SEP') {
                // Change the gap between two blocks.
                // <sep gap="36"></sep>
                // The default gap is 24, can be set larger or smaller.
                // This overwrites the gap attribute on the previous block.
                // Note that a deprecated method is to add a gap to a block.
                // <block type="math_arithmetic" gap="8"></block>
                var newGap = parseInt(xml.getAttribute('gap'), 10);
                // Ignore gaps before the first block.
                if (!isNaN(newGap) && gaps.length > 0) {
                    gaps[gaps.length - 1] = newGap;
                } else {
                    gaps.push(default_gap);
                }
            } else if (tagName == 'BUTTON' || tagName == 'LABEL') {
                // Labels behave the same as buttons, but are styled differently.
                var isLabel = tagName == 'LABEL';
                var curButton = new Blockly.FlyoutButton(this.workspace_,
                    this.targetWorkspace_, xml, isLabel);
                contents.push({type: 'button', button: curButton});
                gaps.push(default_gap);
            }
        }
    }

    //Test
    var block = contents[0]; //if then
    var root = block.getSvgRoot();
    var blockHW = block.getHeightWidth();*/

};