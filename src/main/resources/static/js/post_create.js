const modal = document.getElementById("create_post");
const openModalButton = document.getElementById("open_create_post");
const closeModalButton = document.getElementById("close_create_post");
const postInput = document.getElementById("postInput");

// Open the modal when "Send Post" div is clicked
openModalButton.onclick = function() {
    modal.style.display = "block";
    postInput.value.trim();
}

// Close the modal when the close button is clicked
closeModalButton.onclick = function() {
    modal.style.display = "none";
    // Clear the textarea
    postInput.value = " ";
}