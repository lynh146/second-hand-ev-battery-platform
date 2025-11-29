document.addEventListener("DOMContentLoaded", () => {
    loadFeatured();
});

function loadFeatured() {
    fetch("/api/public/featured")
        .then(res => {
            if (!res.ok) {
                console.warn("Featured API lỗi:", res.status);
                return [];
            }
            return res.json();
        })
        .then(list => renderFeatured(list))
        .catch(err => console.error("Load featured error", err));
}

function renderFeatured(list) {
    const box = document.getElementById("featuredList");
    if (!list || list.length === 0) {
        box.innerHTML = "<p class='text-muted'>Chưa có dữ liệu.</p>";
        return;
    }

    box.innerHTML = list.map(item => `
        <div class="col-6 col-md-3">
            <div class="card h-100">
                <img src="${item.thumbnail}" class="card-img-top" alt="">
                <div class="card-body">
                    <h6 class="fw-bold">${item.title}</h6>
                    <p class="text-danger fw-bold">${item.price.toLocaleString()} đ</p>
                </div>
            </div>
        </div>
    `).join("");
}
