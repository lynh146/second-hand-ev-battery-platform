document.addEventListener("DOMContentLoaded", () => {
    initGallery();
    initCompare();
    initFavorite();
    initBuyNow();
});
/* ========== COMPARE ========== */
function initCompare() {
    const btnCompare = document.getElementById("btnCompare");
    if (!btnCompare) return;

    const listingId = btnCompare.dataset.listingId;
    const type = btnCompare.dataset.type;
    const title = btnCompare.dataset.title;
    const thumb = btnCompare.dataset.thumb;

    btnCompare.addEventListener("click", () => {
        let items = JSON.parse(localStorage.getItem("compare") || "[]");

        if (items.length >= 3) {
            alert("Bạn chỉ có thể so sánh tối đa 3 sản phẩm!");
            return;
        }

        items.push({ id: listingId, type, title, thumb });
        localStorage.setItem("compare", JSON.stringify(items));

        alert("Đã thêm vào so sánh!");
    });
}

/* ========== GALLERY ========== */
function initGallery() {
    const bigImage = document.getElementById("bigImage");
    const thumbs = document.querySelectorAll(".thumb");
    const btnPrev = document.getElementById("btnPrev");
    const btnNext = document.getElementById("btnNext");

    if (!bigImage || thumbs.length === 0) return;

    let currentIndex = 0;

    function updateBigImage(index) {
        currentIndex = index;
        bigImage.src = thumbs[index].src;
        thumbs.forEach(t => t.classList.remove("active"));
        thumbs[index].classList.add("active");
    }

    thumbs.forEach((thumb, index) => {
        thumb.addEventListener("click", () => updateBigImage(index));
    });

    btnPrev && (btnPrev.onclick = () => {
        const next = (currentIndex - 1 + thumbs.length) % thumbs.length;
        updateBigImage(next);
    });

    btnNext && (btnNext.onclick = () => {
        const next = (currentIndex + 1) % thumbs.length;
        updateBigImage(next);
    });
}

/* ========== FAVORITE ========== */
function initFavorite() {
    const btnSave = document.getElementById("btnSaveListing");
    if (!btnSave) return; // KHÔNG HIỂN THỊ KHI SOLD

    const listingId = btnSave.dataset.listingId;
    const isAuth = btnSave.dataset.authenticated === "true";
    const textSpan = btnSave.querySelector(".fav-text");

    function setUI(saved) {
        if (saved) {
            btnSave.classList.add("saved");
            textSpan.textContent = "Đã lưu";
        } else {
            btnSave.classList.remove("saved");
            textSpan.textContent = "Lưu tin";
        }
    }

    if (isAuth) {
        fetch(`/api/favorites/check/${listingId}`)
            .then(res => res.json())
            .then(data => setUI(data.saved))
            .catch(() => {});
    }

    btnSave.addEventListener("click", async () => {
        if (!isAuth) {
            alert("Bạn cần đăng nhập!");
            window.location.href = "/login";
            return;
        }
const res = await fetch(`/api/favorites/toggle/${listingId}`, { method: "POST" });
        const data = await res.json();
        setUI(data.saved);
    });
}

/* ========== BUY NOW ========== */
function initBuyNow() {
    const btnBuy = document.getElementById("btnBuyNow");
    if (!btnBuy) return; // KHÔNG HIỂN THỊ KHI SOLD

    const listingId = btnBuy.dataset.listingId;
    const isAuth = btnBuy.dataset.authenticated === "true";

    btnBuy.addEventListener("click", () => {
        if (!isAuth) {
            alert("Bạn cần đăng nhập để mua!");
            window.location.href = "/login";
            return;
        }
        window.location.href = `/payment/checkout/${listingId}`;
    });
}