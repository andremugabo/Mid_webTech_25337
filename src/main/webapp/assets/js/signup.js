/**
 * 
 */

  document.addEventListener("DOMContentLoaded", function() {
        
        const successMessage = document.querySelector('.success');
        const errorMessage = document.querySelector('.error');

       
        if (successMessage) {
            successMessage.style.display = 'block';
        }
        if (errorMessage) {
            errorMessage.style.display = 'block';
        }

        
        setTimeout(() => {
            if (successMessage) successMessage.remove();
            if (errorMessage) errorMessage.remove();
        }, 2000);
    });