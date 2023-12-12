const names = [
    "Colombo","Gampaha","Kalutara","Kandy","Matale","Nuwara Eliya","Galle","Matara","Hambantota","Jaffna",
    "Kilinochchi","Mannar","Vavuniya",
    "Mullaitivu","Trincomalee","Batticaloa","Ampara","Kurunegala","Puttalam","Anuradhapura","Polonnaruwa","Badulla","Moneragala","Ratnapura",
    "Kegalle"];

const searchInput = document.getElementById("searchInput");
const suggestionsContainer = document.getElementById("suggestions");

searchInput.addEventListener("input", showSuggestions);

function showSuggestions() {
    const inputValue = searchInput.value.toLowerCase();
    const filteredNames = names.filter(name => name.toLowerCase().includes(inputValue));

    // Clear previous suggestions
    suggestionsContainer.innerHTML = "";

    // Display suggestions
    filteredNames.forEach(name => {
        const suggestionElement = document.createElement("li");
        suggestionElement.textContent = name;
        suggestionElement.addEventListener("click", () => {
            searchInput.value = name;
            suggestionsContainer.style.display = "none";
        });
        suggestionsContainer.appendChild(suggestionElement);
    });

    // Show/hide suggestions container
    suggestionsContainer.style.display = filteredNames.length > 0 ? "block" : "none";
}

// Close suggestions when clicking outside the search container
document.addEventListener("click", function (event) {
    const isClickInside = suggestionsContainer.contains(event.target) || searchInput.contains(event.target);
    if (!isClickInside) {
        suggestionsContainer.style.display = "none";
    }
});
