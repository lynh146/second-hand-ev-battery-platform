document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("registerForm");
    const alertBox = document.getElementById("regAlert");

    if (!form) return;

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const fullName = document.getElementById("fullName").value.trim();
        const email = document.getElementById("email").value.trim();
        const password = document.getElementById("password").value;
        const confirmPassword = document.getElementById("confirmPassword").value;

        if (!fullName || !email || !password || !confirmPassword) {
            return showAlert("Vui lòng điền đầy đủ thông tin.", "danger");
        }

        if (password !== confirmPassword) {
            return showAlert("Mật khẩu nhập lại không khớp.", "danger");
        }

        const payload = {
            fullName: fullName,
            email: email,
            password: password
            // phoneNumber, address để user tự cập nhật ở trang hồ sơ sau
        };

        try {
            const res = await fetch("/api/users/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            });

            const data = await res.json().catch(() => ({}));

            if (!res.ok) {
                const msg = data.message || "Đăng ký thất bại. Vui lòng thử lại.";
                return showAlert(msg, "danger");
            }

            showAlert("Đăng ký thành công! Đang chuyển đến trang đăng nhập...", "success");

            setTimeout(() => {
                window.location.href = "/login";
            }, 1200);

        } catch (err) {
            console.error("Register error", err);
            showAlert("Có lỗi kết nối server. Vui lòng thử lại.", "danger");
        }
    });

    function showAlert(message, type) {
        alertBox.classList.remove("d-none", "alert-danger", "alert-success");
        alertBox.classList.add("alert-" + type);
        alertBox.textContent = message;
    }
});
