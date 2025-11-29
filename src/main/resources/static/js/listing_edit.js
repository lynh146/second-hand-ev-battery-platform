document.querySelectorAll(".price-input").forEach(input => {
    input.addEventListener("input", function () {
        let raw = this.value.replace(/\D/g, "");
        this.value = raw ? new Intl.NumberFormat("vi-VN").format(raw) : "";
    });
});

function deleteImage(imageId) {
    if (!confirm("Xoá ảnh này?")) return;

    fetch(`/member/listings/image/delete/${imageId}`, {
        method: "DELETE"
    })
        .then(r => r.text())
        .then(res => {
            if (res === "OK") {
                location.reload();
            } else {
                alert("Lỗi: " + res);
            }
        });
}
