</div>
<footer>
    <p>&copy; 2024 Library Management System. All rights reserved.</p>
</footer>
<script type="text/javascript">

    const currentPage = window.location.pathname.split("/").pop();

    
    const navLinks = document.querySelectorAll('.nav-link');

    
    navLinks.forEach(link => {
        
        if (link.getAttribute('href') === currentPage) {
            link.classList.add('active'); 
        }
    });
</script>

</body>
</html>
