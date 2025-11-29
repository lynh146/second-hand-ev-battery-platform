document.addEventListener("DOMContentLoaded", () => {

    const typeSelect = document.getElementById("listingType");
    const formVehicle = document.getElementById("formVehicle");
    const formBattery = document.getElementById("formBattery");

    function updateForm() {
        if (typeSelect.value === "vehicle") {
            formVehicle.classList.remove("d-none");
            formBattery.classList.add("d-none");
        } else {
            formBattery.classList.remove("d-none");
            formVehicle.classList.add("d-none");
        }
    }
    typeSelect.addEventListener("change", updateForm);
    updateForm();

    // ==========================
    // FIXED PREVIEW ẢNH NHIỀU FILE
    // ==========================
    function bindPreview(inputId, previewId) {

        const input = document.getElementById(inputId);
        const preview = document.getElementById(previewId);

        let fileList = [];

        input.addEventListener("change", () => {
            fileList = [...fileList, ...input.files];  // GHÉP FILE

            if (fileList.length > 12) {
                fileList = fileList.slice(0, 12); // max 12 ảnh
                alert("Tối đa 12 ảnh!");
            }

            renderPreview();
        });

        function renderPreview() {
            preview.innerHTML = "";
            const dt = new DataTransfer();

            fileList.forEach((file, index) => {
                dt.items.add(file);

                const url = URL.createObjectURL(file);
                const box = document.createElement("div");
                box.classList.add("preview-box");

                box.innerHTML = `
                    <img src="${url}">
                    <button class="preview-remove">&times;</button>
                `;

                box.querySelector(".preview-remove").onclick = () => {
                    fileList.splice(index, 1);
                    renderPreview();
                };

                preview.appendChild(box);
            });

            input.files = dt.files;
        }
    }

    bindPreview("imagesVehicle", "previewVehicle");
    bindPreview("imagesBattery", "previewBattery");

    // ==========================
    // FORMAT TIỀN
    // ==========================
    document.querySelectorAll(".price-input").forEach(input => {
        input.addEventListener("input", function () {
            let raw = this.value.replace(/\D/g, "");
            this.value = raw ? new Intl.NumberFormat("vi-VN").format(raw) : "";
        });
    });

});
