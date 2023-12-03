function getUserIdFromCookie() {
	let cookies = document.cookie.split(';');
	for (let i = 0; i < cookies.length; i++) {
		let cookie = cookies[i].trim().split('=');
		if (cookie[0] === 'userId') return cookie[1];
	}
	return null;
}

function toggleLoginLogout() {
	let userId = getUserIdFromCookie();
	if (userId) {
		var currentUrl = window.location.href;
		var fileExtension = currentUrl.split('.').pop();
		if (fileExtension === 'html') {
			document.querySelector('.nav-link[href="./Login.html"]').innerHTML = '<span class="navbar-brand mb-0 h">Logout</span>';
		}
		else {
			document.querySelector('.nav-link[href="./../static/html/Login.html"]').innerHTML = '<span class="navbar-brand mb-0 h">Logout</span>';
		}
	} else {
		var currentUrl = window.location.href;
		var fileExtension = currentUrl.split('.').pop();
		if (fileExtension === 'html') {
			document.querySelector('.nav-link[href="./Login.html"]').innerHTML = '<span class="navbar-brand mb-0 h">Login</span>';
		}
		else {
			document.querySelector('.nav-link[href="./../static/html/Login.html"]').innerHTML = '<span class="navbar-brand mb-0 h">Login</span>';
		}
	}
}

function toggleMenuItems() {
	let userId = getUserIdFromCookie();
	if (userId) {
		let isManager = userId.startsWith('MA');
		let menuItems = document.querySelectorAll('.nav li');
		menuItems.forEach(item => {
			if (isManager || item.classList.contains('ShowMenu') || item.classList.contains('ShowMenu_item')) {
				item.style.display = '';
			} else {
				item.style.display = 'none';
			}
		});
	}
}

window.onload = function() {
	toggleLoginLogout();
	toggleMenuItems();
};
