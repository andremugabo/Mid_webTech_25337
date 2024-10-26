/**
 * 
 */
function filterBooksByGenre() {
    const genre = document.getElementById("genre-filter").value.toLowerCase();
    const books = document.querySelectorAll(".book-item");

    books.forEach(book => {
        const bookGenre = book.getAttribute("data-genre").toLowerCase();
        if (genre === "all" || bookGenre === genre) {
            book.style.display = "block";
        } else {
            book.style.display = "none";
        }
    });
}
