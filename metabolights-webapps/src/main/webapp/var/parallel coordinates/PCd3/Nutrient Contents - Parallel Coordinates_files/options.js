$(function() {
  if (localStorage['hidden'] === "true") {
	$('.intro').hide();
	$('#show_instructions').show();
  }
  
  $('#hide_instructions').click(function() {
	if (localStorage)
	  localStorage['hidden'] = "true";
    $('.intro').hide();
	$('#show_instructions').show();
	return false;
  });
    
  $('#show_instructions').click(function() {
	  if (localStorage)
	    localStorage['hidden'] = "false";
    $('.intro').fadeIn();
  	$('#show_instructions').hide();
	  return false; 
  });
  
  toggleCSS('inverted');
  toggleCSS('no_ticks');
  toggleCSS('shadows');
  
  function toggleCSS(key, func) {
    // initialize on load
    if (localStorage[key] === "true") {
      $('body').addClass(key);
      $('#' + key).addClass('on');
    }
  
    // alter state
    $('#' + key).click(function() {
      if (localStorage && localStorage[key] != "true") {
	      localStorage[key] = "true";
        $('body').addClass(key);
        $('#' + key).addClass('on');
        if (func)
          func();
    	} else {
	      if (localStorage) {
	      	localStorage[key] = "false";
        }
        $('body').removeClass(key);
        $('#' + key).removeClass('on');
        if (func)
          func();
	    }
    	return false;
    });
  }
  
});
