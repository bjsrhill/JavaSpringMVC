/*
JavaScript for pagesList.htm
*/
window.onload = writeMessage;

function writeMessage()
{
	document.getElementById("headingMessage").innerHTML = "Java web application code sample";
    detectMobile();
}

/*
 * Checks for a mobile device and redirects to a mobile page
*/
function detectMobile() {
	if (navigator.userAgent.match(/Android/i) ||
	    navigator.userAgent.match(/webOS/i) ||
	    navigator.userAgent.match(/iPhone/i) ||
	    navigator.userAgent.match(/iPod/i) ||
	    navigator.userAgent.match(/BlackBerry/) || 
	    navigator.userAgent.match(/Windows Phone/i) || 
	    navigator.userAgent.match(/ZuneWP7/i)
	    ) {
	       	location.replace('pagesListMobile.htm');
	    }
}