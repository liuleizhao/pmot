[download]
http://code.google.com/p/jquery-json/

http://code.google.com/p/jquery-json/jQuery-JSON��һ��JSON������ṩ�����ķ�ʽת���ͻ�ԭJSON��ʽ�����ݡ�



var thing = {plugin: 'jquery-json', version: 2.2}; 
var encoded = $.toJSON(thing);              //'{"plugin":"jquery-json","version":2.2}' 
var name = $.evalJSON(encoded).plugin;      //"jquery-json" 
var version = $.evalJSON(encoded).version;  // 2.2