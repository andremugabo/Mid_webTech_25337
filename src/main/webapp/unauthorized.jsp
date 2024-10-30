<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Unauthorized Access | AUCA Library Management System</title>
    <link rel="stylesheet" href="assets/css/login.css"> 
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="icon" type="image/x-icon" href="assets/images/flav.png">
</head>
<body>
    <!-- Header -->
    <header>
        <nav class="navbar">
            <a class="logo" href="./">
                <img alt="AUCA Library Logo" src="assets/images/flav.png">
                <h1>Auca Library</h1>
            </a>
            <ul class="nav-links">
                <li><a href="./">Home</a></li>
                <li><a href="books.jsp">Books</a></li>
                <li><a href="about.html">About Us</a></li>
                <li><a href="contact.html">Contact</a></li>
            </ul>
            <div class="user-actions">
                <a href="login.html" class="btn">Login</a>
                <a href="signup.html" class="btn">Sign Up</a>
            </div>
        </nav>
    </header>

    <!-- Main Content -->
    <main>
        <section class="access-denied">
            <div class="access-container">
                <h1 class="access-title">Access Denied</h1>
                <p class="access-message">You do not have permission to access this page.</p>
                <a href="login.html" class="btn access-btn">Go to Login</a>
            </div>
        </section>
    </main>

    <!-- Footer -->
    <footer>
        <p>&copy; 2024 AUCA Library Management System | <a href="privacy.html">Privacy Policy</a></p>
    </footer>

</body>
</html>
