console.log("my_batteries.js loaded");

// Xóa tin pin
function deleteBattery(id) {
    if (!confirm("Bạn có chắc muốn xóa tin pin này?")) return;

    fetch(`/api/member/listings/${id}`, {
        method: "DELETE"
    })
        .then(res => {
            if (res.ok) {
                alert("Xóa thành công.");
                location.reload();
            } else {
                alert("Xóa thất bại!");
            }
        });
}

// Chuyển đến trang sửa pin
function editBattery(id) {
    window.location.href = `/member/listings/edit/${id}`;
}
