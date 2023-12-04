function deleteCookie(name) {
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

function handleLogout() {
    deleteCookie('userId');
    window.location.reload(); // 또는 로그인 페이지로 리디렉션: window.location.href = './Login.html';
}

function getUserIdFromCookie() {
    let cookies = document.cookie.split(';');
    for(let i = 0; i < cookies.length; i++) {
        let cookie = cookies[i].trim().split('=');
        if(cookie[0] === 'userId') return cookie[1];
    }
    return null;
}

function toggleLoginLogout() {
    let userId = getUserIdFromCookie();
    let loginLink = document.querySelector('.nav-link[href="./Login.html"]');
    if(userId) {
        loginLink.innerHTML = '<span class="navbar-brand mb-0 h">Logout</span>';
        loginLink.addEventListener('click', handleLogout); // 로그아웃 이벤트 핸들러 추가
    } else {
        loginLink.innerHTML = '<span class="navbar-brand mb-0 h">Login</span>';
        loginLink.removeEventListener('click', handleLogout); // 이전 이벤트 핸들러 제거
    }
}

function toggleMenuItems() {
    let userId = getUserIdFromCookie();
    if(userId) {
        let isManager = userId.startsWith('MA');
        let menuItems = document.querySelectorAll('.nav li');
        menuItems.forEach(item => {
            if(isManager || item.classList.contains('ShowMenu') || item.classList.contains('ShowMenu_item')) {
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
