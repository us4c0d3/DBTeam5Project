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
	let currentUrl = window.location.href;
	let fileExtension = currentUrl.split('.').pop();
	if (userId) {
		if (fileExtension.startsWith('html')) {
			document.querySelector('.nav-link[href="./Login.html"]').innerHTML = '<span class="navbar-brand mb-0 h">Logout</span>';
		}
		else {
			document.querySelector('.nav-link[href="./../static/html/Login.html"]').innerHTML = '<span class="navbar-brand mb-0 h">Logout</span>';
		}
	} else {
		if (fileExtension.startsWith('html')) {
			document.querySelector('.nav-link[href="./Login.html"]').innerHTML = '<span class="navbar-brand mb-0 h">Login</span>';
		}
		else {
			document.querySelector('.nav-link[href="./../static/html/Login.html"]').innerHTML = '<span class="navbar-brand mb-0 h">Login</span>';
		}
	}
}

function toggleMenuItems() {
	let userId = getUserIdFromCookie();
	if (userId == null)
		userId = '';
	let isManager = userId.startsWith('MA');
	let menuItems = document.querySelectorAll('.nav li');
	menuItems.forEach(item => {
		if (isManager || item.classList.contains('ShowMenu') || item.classList.contains('ShowMenu_item') || item.classList.contains('SearchOrder')) {
			item.style.display = '';
		} else {
			item.style.display = 'none';
		}
	});
}

function toggleCustomerId() {
	let userId = getUserIdFromCookie();
	if (userId == null) userId = "";
	var currentPath = window.location.pathname;
	var pathSegments = currentPath.split('/');
	var fileName = pathSegments[pathSegments.length - 1];
	if (fileName === 'OrderSearch.jsp') {
		let isManager = userId.startsWith('MA');
		let isCustomer = userId.startsWith('CU');
		let Form = document.querySelector('form');
		if (isCustomer) {
			Form.innerHTML = '<label for="id">Customer ID:' + userId + '</label> ' + '<input type="text" id="id" style="display: none;" name="id" value="' + userId + '"/>' + ' <input type="submit" value="Search"/>';
		}
		else if (isManager) {
			Form.innerHTML = '<label for="id">Customer ID:</label> <input type="text" id="id" name="id"> <input type="submit" value="Search">';
		}
		else {
			Form.innerHTML = '<label for="id">Customer ID:</label>';
		}
	}
}

window.onload = function() {
	toggleLoginLogout();
	toggleMenuItems();
	toggleCustomerId();
};
