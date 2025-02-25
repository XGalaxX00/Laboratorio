const input = document.getElementById("telefono");
const errorTel = document.getElementById("errorTelefono");


input.addEventListener("input", () => {
    const value = input.value;
    input.value = value.replace(/\D/g, "");

    if (value.length > 9) {
        errorTel.textContent = "solo 9 digitos";
        input.value = value.slice(0, 9);
    } else if (value.length < 9 && value !== "") {
        errorTel.textContent = "complete los 9 digitos";
    } else {
        errorTel.textContent = "";
    }
});