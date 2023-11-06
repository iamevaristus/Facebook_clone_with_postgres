const editModal = document.querySelector(".edit_post");
const openEdit = document.querySelector(".open_edit_post");
const closeEdit = document.querySelector(".close_edit_post");
const editInput = document.querySelector(".editPostInput");
const editPostId = document.querySelector(".editPostInputId");
const editP = document.querySelector(".edit-post")

// Open the editModal when "Send Post" div is clicked
openEdit.onclick = function() {
    editModal.style.display = "block";
    editInput.value.trim();

    editInput.value = editP.value.split("***PostId***")[0];
    editPostId.value = editP.value.split("***PostId***")[1];
}

// Close the editModal when the close button is clicked
closeEdit.onclick = function() {
    editModal.style.display = "none";
    // Clear the textarea
    editInput.value = " ";
}

const editCommentModal = document.querySelector(".edit_comment");
const openEditComment = document.querySelector(".open_edit_comment");
const closeEditComment = document.querySelector(".close_edit_comment");
const editInputComment = document.querySelector(".editCommentInput");
const editCommentId = document.querySelector(".editCommentInputId");
const editC = document.querySelector(".edit-comment")

// Open the editModal when "Send Post" div is clicked
openEditComment.onclick = function() {
    editCommentModal.style.display = "block";
    editInputComment.value.trim();

    const array = editC.value.split("***COMMENTED***");

    editInputComment.value = array[0];
    editCommentId.value = array[1];
}

// Close the editCommentModal when the close button is clicked
closeEditComment.onclick = function() {
    editCommentModal.style.display = "none";
    // Clear the textarea
    editInputComment.value = " ";
}

const openProfileEdit = document.getElementById("open_edit_post_profile");

// Open the editModal when "Send Post" div is clicked
openProfileEdit.onclick = function() {
    editModal.style.display = "block";

    editInput.value = openProfileEdit.getAttribute("data-post-content").split("*****PostID")[0];
    editPostId.value = openProfileEdit.getAttribute("data-post-content").split("*****PostID")[1];
}

// Close the editModal when the close button is clicked
closeEdit.onclick = function() {
    editModal.style.display = "none";
    // Clear the textarea
    editInput.value = "";
}

const openEditProfileComment = document.getElementById("open_edit_comment_profile");

// Open the editModal when "Send Post" div is clicked
openEditProfileComment.onclick = function() {
    editCommentModal.style.display = "block";

    editInputComment.value = openEditProfileComment.getAttribute("data-comment-content").split("*****CommentID")[0];
    editCommentId.value = openEditProfileComment.getAttribute("data-comment-content").split("*****CommentID")[1];
}

// Close the editCommentModal when the close button is clicked
closeEditComment.onclick = function() {
    editCommentModal.style.display = "none";
    // Clear the textarea
    editInputComment.value = "";
}