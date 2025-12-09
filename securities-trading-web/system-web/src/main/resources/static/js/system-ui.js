// Lightweight Bootstrap-based toast helper
(function(){
    function ensureContainer(){
        let c = document.getElementById('toast-container');
        if (!c) {
            c = document.createElement('div');
            c.id = 'toast-container';
            c.style.position = 'fixed';
            c.style.top = '1rem';
            c.style.right = '1rem';
            c.style.zIndex = 1080;
            document.body.appendChild(c);
        }
        return c;
    }

    window.showToast = function(type, message, timeout){
        timeout = timeout || 3000;
        const container = ensureContainer();
        const toast = document.createElement('div');
        toast.className = 'toast align-items-center text-bg-' + (type === 'error' ? 'danger' : (type === 'warning' ? 'warning' : 'success')) + ' border-0';
        toast.role = 'alert';
        toast.ariaLive = 'assertive';
        toast.ariaAtomic = 'true';
        toast.style.minWidth = '200px';

        toast.innerHTML = `
            <div class="d-flex">
              <div class="toast-body">${message}</div>
              <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        `;

        container.appendChild(toast);
        const bsToast = new bootstrap.Toast(toast, { delay: timeout });
        bsToast.show();
        toast.addEventListener('hidden.bs.toast', function(){
            toast.remove();
        });
        return bsToast;
    };

})();
