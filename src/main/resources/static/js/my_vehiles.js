console.log("my_vehicles.js loaded");

// Xóa tin xe
function deleteListing(id) {
    if (!confirm("Bạn có chắc muốn xóa tin này?")) return;

    fetch(`/api/member/listings/${id}`, {
        method: "DELETE"
    })
        .then(res => {
            if (res.ok) {
                alert("Đã xóa thành công.");
                location.reload();
            } else {
                alert("Xóa thất bại!");
            }
        });
}

// Chuyển đến trang sửa xe
function editListing(id) {
    window.location.href = `/member/listings/edit/${id}`;
}
