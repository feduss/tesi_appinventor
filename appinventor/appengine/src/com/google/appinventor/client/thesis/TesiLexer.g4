lexer grammar TesiLexer;

WHITESPACE: [ \t\r\n]-> skip;

WHEN: 'when';

IF: 'if';

AND: 'AND';

OR : 'OR';

NOT : 'not';

THEN: 'then';

ELSE: 'else';

THROW: 'throw';

ARTICLE: 'the'|'a';

VERB : 'is';

SET : 'set';

CALL : 'call';

ACTION_WHEN_OBJ : 'after date set'|'after selecting'|'after time set'|
                  'changed'|
                  'got focus'|
                  'is long clicked'|'is clicked'|'after picking'|'before picking'|
                  'is touched down'|'is touched up'
                  'lost focus'|
                  'position changed to'
                  ;

ACTION_IF_OBJ : 'background color'|
                'clickable'|'color left'|'color right'|
                'day'|
                'enabled'|'elements'|
                'font bold'|'font italic'|'font size'|
                'html content'|'has margins'|'height'|'hint'|'hour'|
                'image'|'instant'|'item background color'|'item text color'|
                'month'|'month in text'|'max value'|'min value'|'multi line'|'minute'|
                'numbers only'|
                'on'|
                'picture'|'password visible'|'prompt'|
                'rotation angle'|'read only'|
                'show feedback'|'scaling'|'selection'|'selection index'|'show filter bar'|'selection color'|
                'text'|'text color'|'title'|'thumb enabled'|'thumb position'|'text size'|
                'thumb color active'|'thumb color inactive'|'track color active'|'track color inactive'|
                'visible'|
                'width'|
                'year'
                ;

ACTION_SET_OBJ : 'animation to'|
            'clickable to'|'color left to'|'color right to'|
            'background color to'|
            'enabled to'|'elements to'|'elements from string to'|
            'font size to'|'font bold to'|'font italic to'|
            'hasmargin to'|'height percent to'|'height to'|'hint to'|
            'image to'|'item background color to'|'item text color to'|
            'max value to'|'min value to'|'multi line to'|
            'numbers only to'|
            'on to'|
            'picture to'|'password visible to'|'prompt to'|
            'rotation angle to'|'read only to'|
            'show feedback to'|'scale picture to fit to'|'scaling to'|'selection to'|'selection index to'|'show filter bar to'|'selection color to'|
            'text to'|'text color to'|'title to'|'text size to'|'thumb enabled to'|'thumb position to'|'thumb color active to'|
            'thumb color inactive to'|'track color active to'|'track color inactive to'|
            'visible to'|
            'width to'|'width percent to'
            ;

ACTION_CALL_OBJ : 'display dropdown'|
                 'hide keyboard'|
                 'launch picker'|
                 'open list'|
                 'request focus'|
                 'set date to display'|'set date to display from instance'|
                 'set time to display'|'set time to display from instance'
                 ;

ACTION_OPEN_OBJ: 'another screen with name';

COLON: ',';

SEMICOLON: ';';

OPEN_BRACKET: '(';

CLOSE_BRACKET: ')';

OPEN: 'open';

STRING : [a-zA-Z0-9]+;