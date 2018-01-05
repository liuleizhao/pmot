[download]
http://code.google.com/p/jquery-json/

http://code.google.com/p/jquery-json/jQuery-JSON是一个JSON插件，提供更简便的方式转换和还原JSON格式的数据。



var thing = {plugin: 'jquery-json', version: 2.2}; 
var encoded = $.toJSON(thing);              //'{"plugin":"jquery-json","version":2.2}' 
var name = $.evalJSON(encoded).plugin;      //"jquery-json" 
var version = $.evalJSON(encoded).version;  // 2.2