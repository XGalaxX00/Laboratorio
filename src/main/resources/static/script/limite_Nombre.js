
const input3 = document.getElementById("nombre");
const errorNom = document.getElementById("errorNombre");

input3.addEventListener("input", () => {
    const value = input3.value;

// Eliminar números y símbolos
    input3.value = value.replace(/[^A-Za-zÁÉÍÓÚáéíóúÑñ\s]/g, "");

    
    if (input3.value === "") {
        errorNom.textContent = "Solo se permiten letras.";
    } else {
        errorNom.textContent = "";
    }
});
