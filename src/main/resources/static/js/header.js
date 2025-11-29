console.log("HEADER JS LOADED");

async function fetchCurrentUser() {
    try {
        const res = await fetch("/api/users/me");

        if (res.status === 401) return null;
        if (!res.ok) {
            console.warn("User API error:", res.status);
            return null;
        }

        return await res.json();
    } catch (e) {
        console.warn("User API failure", e);
        return null;
    }
}

// ===== USER MENU =====
async function renderUserMenu() {
    const user = await fetchCurrentUser();

    const loginBtn = document.getElementById("loginBtn");
    const userMenu = document.getElementById("userMenuList");
    const dropdownToggle = document.getElementById("userMenuBtn");

    if (!userMenu || !dropdownToggle) return;
    bootstrap.Dropdown.getOrCreateInstance(dropdownToggle);

    if (!user) {
        // GUEST
        loginBtn?.classList.remove("d-none");

        userMenu.innerHTML = `
            <li><a class="dropdown-item" href="/register">Tạo tài khoản</a></li>
            <li><a class="dropdown-item" href="/login">Đăng nhập</a></li>
            <hr class="dropdown-divider">
        `;
        return;
    }

    // LOGGED IN
    loginBtn?.classList.add("d-none");

    userMenu.innerHTML = `
        <li><a class="dropdown-item" href="/member/profile">👤 ${user.fullName}</a></li>
        <li><a class="dropdown-item" href="/member/change_password">🔑 Đổi mật khẩu</a></li>
        <li><a class="dropdown-item" href="/member/vehicles/my">🚗 Xe của tôi</a></li>
        <li><a class="dropdown-item" href="/member/batteries/my">🔋 Pin của tôi</a></li>
        <li><a class="dropdown-item" href="/member/transactions">🛒 Lịch sử giao dịch</a></li>
        <li><a class="dropdown-item" href="/member/favorites">❤️ Tin đăng đã lưu</a></li>
        <li><a class="dropdown-item" href="/member/wallet">💳 Ví của tôi</a></li>
        <li><a class="dropdown-item" href="/member/reviews">⭐ Đánh giá của tôi</a></li>
        <hr class="dropdown-divider">
        <li><a class="dropdown-item text-danger" href="/logout">🚪 Đăng xuất</a></li>
    `;
}

// ===== REQUIRE LOGIN HINT =====
function setupRequireLogin() {
    document.querySelectorAll(".require-login").forEach(btn => {
        btn.addEventListener("click", async (e) => {
            e.preventDefault();
            const user = await fetchCurrentUser();
            if (!user) {
                alert("Vui lòng đăng nhập để sử dụng tính năng này.");
                window.location.href = "/login";
            }
        });
    });
}

// ===== SEARCH + ACTION BUTTONS =====
function setupHeaderActions() {

    const searchBtn = document.querySelector(".ct-search-btn");
    const searchInput = document.getElementById("headerSearchInput");

    const doSearch = () => {
        if (!searchInput) {
            window.location.href = "/search";
            return;
        }
        const keyword = searchInput.value.trim();
        const url = keyword
            ? `/search?keyword=${encodeURIComponent(keyword)}`
            : "/search";
        window.location.href = url;
    };

    // Click kính lúp
    searchBtn?.addEventListener("click", doSearch);

    // Enter trong ô input
    searchInput?.addEventListener("keydown", (e) => {
        if (e.key === "Enter") {
            e.preventDefault();
            doSearch();
        }
    });

    // Nút thông báo
    document.getElementById("notifyBtn")?.addEventListener("click", async () => {
        const user = await fetchCurrentUser();
        if (!user) return alert("Bạn cần đăng nhập!");
        window.location.href = "/member/notifications";
    });

    // Nút Đăng tin
    document.querySelector(".ct-post-btn")?.addEventListener("click", async (e) => {
        e.preventDefault();
        const user = await fetchCurrentUser();
        if (!user) return window.location.href = "/login";
        window.location.href = "/member/listings/create";
    });
}

// ===== GIỮ LẠI KEYWORD TRÊN HEADER =====
function initHeaderSearchValueFromUrl() {
    const input = document.getElementById("headerSearchInput");
    if (!input) return;

    const params = new URLSearchParams(window.location.search);
    const kw = params.get("keyword");

    if (kw) {
        input.value = kw;
    }
}

// ===== FAVORITE POPUP =====
let popupOpen = false;

// Hàm này cho chỗ khác (listing_public.js) gọi lại sau khi Lưu tin
function refreshFavoritePopup(autoOpen = false) {
    const popup = document.getElementById("favoritePopup");
    if (!popup) return;

    if (autoOpen) {
        popup.classList.remove("d-none");
        popupOpen = true;
    }

    loadFavoritePopupItems();
}

document.getElementById("favoriteBtn")?.addEventListener("click", async (e) => {
    e.stopPropagation();

    const user = await fetchCurrentUser();
    if (!user) {
        alert("Bạn cần đăng nhập để xem tin đã lưu");
        window.location.href = "/login";
        return;
    }

    toggleFavoritePopup();
});

function toggleFavoritePopup() {
    const popup = document.getElementById("favoritePopup");

    if (popupOpen) {
        popup.classList.add("d-none");
        popupOpen = false;
        return;
    }

    popup.classList.remove("d-none");
    popupOpen = true;

    loadFavoritePopupItems();
}

// Load 3 tin mới nhất
async function loadFavoritePopupItems() {
    const box = document.getElementById("favoritePopupList"); // 👈 ID ĐÃ FIX
    if (!box) return;

    box.innerHTML = `<div class="text-center p-2 text-muted">Đang tải...</div>`;

    try {
        const res = await fetch("/api/favorites/my");

        if (res.status === 401) {
            box.innerHTML = `<div class="text-center p-2 text-muted">Bạn cần đăng nhập.</div>`;
            return;
        }

        if (!res.ok) {
            box.innerHTML = `<div class="text-center p-2 text-muted">Không tải được dữ liệu</div>`;
            return;
        }

        const all = await res.json();

        if (!all || all.length === 0) {
            box.innerHTML = `<div class="text-center p-2 text-muted">Chưa có tin lưu</div>`;
            return;
        }

        const top3 = all.slice(0, 3);

        box.innerHTML = top3.map(item => {
            const img = item.thumbnail || "/images/no-image.png";
            const price = Number(item.price).toLocaleString("vi-VN");

            return `
                <a href="/listings/${item.listingID}" class="fav-item">
                    <img src="${img}" alt="">
                    <div>
                        <div class="title text-truncate">${item.title}</div>
                        <div class="price">${price} đ</div>
                    </div>
                </a>
            `;
        }).join("");

    } catch (e) {
        console.error(e);
        box.innerHTML = `<div class="text-center p-2 text-muted">Lỗi khi tải dữ liệu</div>`;
    }
}

// Close popup khi bấm ra ngoài
document.addEventListener("click", (e) => {
    const popup = document.getElementById("favoritePopup");
    const btn = document.getElementById("favoriteBtn");

    if (!popupOpen || !popup || !btn) return;
    if (popup.contains(e.target) || btn.contains(e.target)) return;

    popup.classList.add("d-none");
    popupOpen = false;
});

document.addEventListener("DOMContentLoaded", () => {
    renderUserMenu();
    setupHeaderActions();
    setupRequireLogin();
    initHeaderSearchValueFromUrl();
});
