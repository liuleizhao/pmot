// JavaScript Document
/** (function($) {
var printAreaCount = 0;
$.fn.printArea = function()
{
var ele = $(this);
var idPrefix = "printArea_";
removePrintArea( idPrefix + printAreaCount );
printAreaCount++;
var iframeId = idPrefix + printAreaCount;
var iframeStyle = 'position:absolute;width:0px;height:0px;left:-500px;top:-500px;';
iframe = document.createElement('IFRAME');
$(iframe).attr({ style : iframeStyle,
id    : iframeId
});
document.body.appendChild(iframe);
var doc = iframe.contentWindow.document;
$(document).find("link")
.filter(function(){
return $(this).attr("rel").toLowerCase() == "stylesheet";
})
.each(function(){
doc.write('<link type="text/css" rel="stylesheet" href="' +
$(this).attr("href") + '" >');
});
doc.write('<div class="' + $(ele).attr("class") + '">' + $(ele).html() + '</div>');
doc.close();
var frameWindow = iframe.contentWindow;
frameWindow.close();
frameWindow.focus();
frameWindow.print();
}
var removePrintArea = function(id)
{
$( "iframe#" + id ).remove();
};
})(jQuery);
**/




(function($) {
$.fn.printArea = function() {
var ele = $(this);
var printCss = '';
$(document).find("link").filter(function() {
return $(this).attr("rel").toLowerCase() == "stylesheet";
}).each(
function() {
printCss = printCss + '<link type="text/css" rel="stylesheet" href="' + $(this).attr("href") + '" >';
});

var printContent = '<div>' + $(ele).html() + '</div>';
var windowUrl = 'about:blank';
var uniqueName = new Date();
var windowName = 'Print' + uniqueName.getTime();
var newPrintContent = $(printContent);
newPrintContent.find('table').attr('cellpadding','10');
var atr = newPrintContent.find('tr');
atr.first().find('td').removeClass('srbgebg');
newPrintContent.find('tr').css('font-size','20px');

var printWindow = window.open(windowUrl, windowName, 'left=50000,top=50000,width=0,height=0');
var BodyHtml = '<body>';
var BodyEnd = "</body>";
printWindow.document.write('<html>'+ '<head>'+'<style type="text/css">a {color:#333; text-decoration:none;}.spiltPage{page-break-after:always;}</style>' + '</head>' + BodyHtml + $(newPrintContent).html() + BodyEnd + '</html>');
//printWindow.document.write('<html>'+ '<head>'+printCss + '<style type="text/css">.spiltPage{page-break-after:always;}</style>'+'</head>' + BodyHtml + $(newPrintContent).html() + BodyEnd + '</html>');
printWindow.document.close();
printWindow.focus();
printWindow.print();
printWindow.close();
}
})(jQuery);