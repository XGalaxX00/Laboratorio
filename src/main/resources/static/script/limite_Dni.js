const input2 = document.getElementById("dni");
const errordni = document.getElementById("errorDNI");


input2.addEventListener("input", () => {
    const value = input2.value;
    input2.value = value.replace(/\D/g, "");

    if (value.length > 8) {
        errordni.textContent = "solo 8 digitos";
        input2.value = value.slice(0, 8);
    } else if (value.length < 8 && value !== "") {
        errordni.textContent = "complete los 8 digitos";
    } else {
        errordni.textContent = "";
    }
});