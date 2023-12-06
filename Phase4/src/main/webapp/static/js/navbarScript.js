function getLoginHref() {
    // 현재 페이지의 경로를 가져옵니다.
    var path = window.location.pathname;
    // 경로에 'jsp'가 포함되어 있으면 JSP 페이지로 간주하고 상대 경로를 조정합니다.
    var isJspPage = path.indexOf('jsp') !== -1;
    // 조건에 따라 적절한 경로를 반환합니다.
    return isJspPage ? './../static/html/Login.html' : './Login.html';
}

function deleteCookie(name) {
    document.cookie = name + '=; path=/Phase4; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

function handleLogout() {
    deleteCookie('userId');
	window.location.href = getLoginHref();
    //window.location.reload(); // 또는 로그인 페이지로 리디렉션: window.location.href = './Login.html';
}

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
    let loginLink = document.querySelector('.right-links a:nth-child(1)');
    let loginHref = getLoginHref();

    if (userId) {
        loginLink.textContent = 'Logout';
        loginLink.href = '#';
        loginLink.removeEventListener('click', handleLogout);
        loginLink.addEventListener('click', handleLogout);
    } else {
        loginLink.textContent = 'Login';
        loginLink.href = loginHref; // 동적으로 설정된 경로를 사용합니다.
        loginLink.removeEventListener('click', handleLogout);
    }
}

function toggleMenuItems() {
    let userId = getUserIdFromCookie();
    let navItems = document.querySelectorAll('.navbar a:not(.logo a, .right-links a)');
    
    navItems.forEach(item => {
        item.classList.remove('hidden-link'); // 숨김 클래스 제거

        if(userId) {
            let isManager = userId.startsWith('MA');
            if(!(isManager || item.classList.contains('ShowMenu') || item.classList.contains('ShowMenu_item') || item.classList.contains('SearchOrder'))) {
                item.classList.add('hidden-link');
			}
        } else {
            // 비로그인 상태에서는 SearchOrder, ShowMenu, ShowMenu_item 클래스를 가진 항목을 숨깁니다.
            if(!(item.classList.contains('SearchOrder') || item.classList.contains('ShowMenu') || item.classList.contains('ShowMenu_item'))) {
                item.classList.add('hidden-link');
            }
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

function redirectToLoginIfNotLoggedIn() {
    let userId = getUserIdFromCookie(); // 기존에 작성된 쿠키에서 userId 가져오는 함수를 사용
    let currentPath = window.location.pathname;
    let isMypage = currentPath.includes('Mypage.jsp'); // 현재 페이지가 Mypage인지 확인
	let isSearchOrder = currentPath.includes('OrderSearch.jsp');
    
    // Mypage에 있고, userId가 없으면 로그인 페이지로 리디렉션
    if ((isMypage || isSearchOrder) && !userId) {
        window.location.href = getLoginHref(); // 로그인 페이지로 리디렉션하는 함수를 사용
    }
}

window.onload = function() {
	toggleLoginLogout();
	toggleMenuItems();
	toggleCustomerId();
	redirectToLoginIfNotLoggedIn();
};
