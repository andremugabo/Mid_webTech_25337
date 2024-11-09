<%@ page contentType="text/html;charset=ISO-8859-1" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Books | AUCA Library Management System</title>
<link rel="stylesheet" href="assets/css/styles.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<link rel="icon" type="image/x-icon" href="assets/images/flav.png">
</head>
<body>
	<header>
		<nav class="navbar">
			<a class="logo" href="./"> <img alt="AUCA Logo"
				src="assets/images/flav.png">
				<h1>Auca Library</h1>
			</a>
			<ul class="nav-links">
				<li><a href="./">Home</a></li>
				<li><a href="books.jsp">Books</a></li>
				<li><a href="about.html">About Us</a></li>
				<li><a href="contact.html">Contact</a></li>
			</ul>
			<div class="user-actions">
				<a href="login.html" class="btn">Login</a> <a href="signup.html"
					class="btn">Sign Up</a>
			</div>
		</nav>
	</header>

	<main>
		<div class="banner books-banner">
			<div class="banner-content">
				<h2>Our Books</h2>
				<p>Explore our collection of books available for borrowing.</p>
			</div>
		</div>

		<section class="books">
			<h2>Available Books</h2>

			<!-- Genre Filter Dropdown -->
			<div class="filter-container">
				<label for="genre-filter">Filter by Genre:</label> <select
					id="genre-filter" onchange="filterBooksByGenre()">
					<option value="all">All Genres</option>
					<c:forEach var="genre" items="${shelfList}">
						<option value="${genre.shelfId}">${genre.bookCategory}</option>
					</c:forEach>
				</select>
			</div>

			<div class="book-list">
				<!-- Loop through available books dynamically -->
				<c:forEach var="book" items="${bookList}">
					<div class="book-item" data-genre="${book.shelf_id}"
						style="background-image: url('${book.imageUrl}');">
						<div class="book-details">
							<h3>${book.title}</h3>
							<p>Author: ${book.author}</p>
							<p>Genre: ${book.genre}</p>
							<p>Available Copies: ${book.availableCopies}</p>
							<a href="borrowBook?bookId=${book.bookId}" class="btn">Borrow</a>
						</div>
					</div>
				</c:forEach>
			</div>
		</section>
	</main>

	<footer>
		<p>
			&copy; 2024 Auca Library Management System | <a href="privacy.html">Privacy
				Policy</a>
		</p>
	</footer>


	<!-- JavaScript for Filter and Modal Logic -->
	<script>
        function filterBooksByGenre() {
            const genre = document.getElementById('genre-filter').value;
            const books = document.querySelectorAll('.book-item');

            books.forEach(book => {
                if (genre === 'all' || book.dataset.genre === genre) {
                    book.style.display = 'block';
                } else {
                    book.style.display = 'none';
                }
            });
        }
    </script>
</body>
</html>
