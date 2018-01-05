(function($) {
  $.fn.clock = function(options) {
    $.fn.clock.timerID = null;
    $.fn.clock.running = false;
    $.fn.clock.el = null;
    var version = '0.1.1';
    var opts = $.extend({}, $.fn.clock.defaults, options);
         
    return this.each(function() {
      $this = $(this);
      $.fn.clock.el = $this;

      var o = $.meta ? $.extend({}, opts, $this.data()) : opts;

      $.fn.clock.withDate = o.withDate;
	  $.fn.clock.withWeek = o.withWeek;
	  $.fn.clock.timeNotation = o.timeNotation;
      $.fn.clock.am_pm = o.am_pm;
      $.fn.clock.utc = o.utc;

      $this.css({
        fontFamily: o.fontFamily,
        fontSize: o.fontSize,
        backgroundColor: o.background,
        color: o.foreground
      });

      $.fn.clock.startClock();

    });
  };
       
  $.fn.clock.startClock = function() {
    $.fn.clock.stopClock();
    $.fn.clock.displayTime();
  }
  $.fn.clock.stopClock = function() {
    if($.fn.clock.running) {
      clearTimeout(timerID);
    }
    $.fn.clock.running = false;
  }
  $.fn.clock.displayTime = function(el) {
	var date = $.fn.clock.getDate();
	var week = $.fn.clock.getWeek();
	var time = $.fn.clock.getTime();
    $.fn.clock.el.html("时间："+date + week); //+ time
    timerID = setTimeout("$.fn.clock.displayTime()",1000);
  }
  $.fn.clock.getDate = function() {
	if($.fn.clock.withDate == true) {
      var now = new Date();
      var year, month, date;
      
      if($.fn.clock.utc == true) {
	    year = now.getUTCFullYear();
		month = now.getUTCMonth() + 1;
		date = now.getUTCDate();
	  } else {
	    year = now.getFullYear();
        month = now.getMonth() + 1;
        date = now.getDate();
	  }
      
      month = ((month < 10) ? "0" : "") + month;
      date = ((date < 10) ? "0" : "") + date;
      
      var dateNow = year + "年" + month + "月" + date + "日  ";
	} else {
      var dateNow = "";
	}
    return dateNow;
  }
  $.fn.clock.getWeek = function() {
    if($.fn.clock.withWeek == true) {
	  var now = new Date();
	  var week;
	  
	  if($.fn.clock.utc == true) {
	    week = now.getUTCDay();
	  } else {
	    week = now.getDay();
	  }
	  
	  $.each(["日","一","二","三","四","五","六"],function(i,n) {
        if(i == week) {week = n; return;}
	  });
	  
	  var weekNow = "星期" + week + " ";
	} else {
	  var weekNow = "";
	}
	return weekNow;
  }
  $.fn.clock.getTime = function() {
    var now = new Date();
    var hours, minutes, seconds;

    if($.fn.clock.utc == true) {
      hours = now.getUTCHours();
      minutes = now.getUTCMinutes();
      seconds = now.getUTCSeconds();
    } else {
      hours = now.getHours();
      minutes = now.getMinutes();
      seconds = now.getSeconds();
    }

    if ($.fn.clock.timeNotation == '12h') {
      hours = ((hours > 12) ? hours - 12 : hours);
    } else {
      hours   = ((hours <  10) ? "0" : "") + hours;
    }

    minutes = ((minutes <  10) ? "0" : "") + minutes;
    seconds = ((seconds <  10) ? "0" : "") + seconds;

    var timeNow = hours + ":" + minutes + ":" + seconds;
    if ( ($.fn.clock.timeNotation == '12h') && ($.fn.clock.am_pm == true) ) {
      timeNow += (hours >= 12) ? " P.M." : " A.M."
    }

    return timeNow;
};
       
  // plugin defaults
  $.fn.clock.defaults = {
	withDate:false,
	withWeek:false,
    timeNotation: '24h',
    am_pm: false,
    utc: false,
    fontFamily: '',
    fontSize: '',
    foreground: '',
    background: ''
  };
})(jQuery);

$(document).ready(function(){
	$("#jq_time").clock({withDate:true, withWeek:true});
});

