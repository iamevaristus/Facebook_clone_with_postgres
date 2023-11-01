function showLoadingPopup() {
    document.getElementById("loading-popup").style.display = "block";
}

// Function to hide the loading popup
function hideLoadingPopup() {
    document.getElementById("loading-popup").style.display = "none";
}

// Add an event listener to the login form
document.getElementById("login-form").addEventListener("submit", function () {
    // Show the loading popup when the form is submitted
    showLoadingPopup();
});