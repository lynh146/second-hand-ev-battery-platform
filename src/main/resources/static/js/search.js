document.addEventListener("DOMContentLoaded", () => {

    const minRange = document.getElementById("minRange");
    const maxRange = document.getElementById("maxRange");
    const minInput = document.getElementById("minPriceInput");
    const maxInput = document.getElementById("maxPriceInput");

    const minField = document.getElementById("minPriceField");
    const maxField = document.getElementById("maxPriceField");
    const brandField = document.getElementById("brandField");
    const brandInput = document.getElementById("brandInput");

    const filterForm = document.getElementById("filterForm");
    const btnApply = document.getElementById("btnApplyFilter");
    const btnClear = document.getElementById("btnClearFilter");
const categorySelect = document.querySelector("select[name='category']");
if (categorySelect) {
    categorySelect.addEventListener("change", () => {
        document.getElementById("filterForm").submit();
    });
}

    const MAX = 100000000;

    // ====== INIT ======
    let initMin = Number(minField.value || 0);
    let initMax = Number(maxField.value || MAX);

    minRange.value = initMin;
    maxRange.value = initMax;
    minInput.value = initMin || "";
    maxInput.value = initMax === MAX ? "" : initMax;

    updateTrack();

    // ====== FUNCTION PAINT TRACK ======
    function updateTrack() {
        let minVal = Number(minRange.value);
        let maxVal = Number(maxRange.value);

        const percentMin = (minVal / MAX) * 100;
        const percentMax = (maxVal / MAX) * 100;

        minRange.style.background = `linear-gradient(90deg,
            #ddd ${percentMin}%,
            #FFD400 ${percentMin}%,
            #FFD400 ${percentMax}%,
            #ddd ${percentMax}%)`;
        maxRange.style.background = minRange.style.background;
    }

    // ====== PREVENT OVERLAP ======
    minRange.addEventListener("input", () => {
        let minVal = Number(minRange.value);
        let maxVal = Number(maxRange.value);

        if (minVal > maxVal) minRange.value = maxVal;

        minInput.value = minRange.value;
        updateTrack();
    });

    maxRange.addEventListener("input", () => {
        let minVal = Number(minRange.value);
        let maxVal = Number(maxRange.value);

        if (maxVal < minVal) maxRange.value = minVal;

        maxInput.value = maxRange.value;
        updateTrack();
    });

    // ====== sync input box -> range ======
    minInput.addEventListener("change", () => {
        let val = Number(minInput.value || 0);
        if (val < 0) val = 0;
        if (val > Number(maxRange.value)) val = Number(maxRange.value);

        minRange.value = val;
        updateTrack();
    });

    maxInput.addEventListener("change", () => {
        let val = Number(maxInput.value || MAX);

        if (val < Number(minRange.value)) val = Number(minRange.value);
        if (val > MAX) val = MAX;

        maxRange.value = val;
        updateTrack();
    });

    // ====== CLEAR ======
    btnClear.addEventListener("click", () => {
        minField.value = "";
        maxField.value = "";
        brandField.value = "";
        filterForm.submit();
    });

    // ====== APPLY ======
    btnApply.addEventListener("click", () => {
        minField.value = Number(minRange.value) || "";
        maxField.value = Number(maxRange.value) || "";
        brandField.value = brandInput.value || "";

        filterForm.submit();
    });

});
