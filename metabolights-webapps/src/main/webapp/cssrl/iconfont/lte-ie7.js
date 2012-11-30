/* Use this script if you need to support IE 7 and IE 6. */

window.onload = function() {
	function addIcon(el, entity) {
		var html = el.innerHTML;
		el.innerHTML = '<span style="font-family: \'icomoon\'">' + entity + '</span>' + html;
	}
	var icons = {
			'icon-lab' : '&#x21;',
			'icon-support' : '&#x22;',
			'icon-clock' : '&#x23;',
			'icon-busy' : '&#x24;',
			'icon-locked' : '&#x25;',
			'icon-key' : '&#x26;',
			'icon-stats-up' : '&#x27;',
			'icon-MetaboLightsLogo' : '&#x28;',
			'icon-MSLogo' : '&#x29;',
			'icon-NMRLogo' : '&#x2a;',
			'icon-mail' : '&#x2b;',
			'icon-book' : '&#x2c;',
			'icon-facebook' : '&#x2e;',
			'icon-twitter' : '&#x2f;',
			'icon-blogger' : '&#x2d;'
		},
		els = document.getElementsByTagName('*'),
		i, attr, html, c, el;
	for (i = 0; i < els.length; i += 1) {
		el = els[i];
		attr = el.getAttribute('data-icon');
		if (attr) {
			addIcon(el, attr);
		}
		c = el.className;
		c = c.match(/icon-[^\s'"]+/);
		if (c && icons[c[0]]) {
			addIcon(el, icons[c[0]]);
		}
	}
};