document.querySelector('.menu-abrir').addEventListener('click', function() {
    document.documentElement.classList.add('menu-ativo');
});

document.querySelector('.menu-fechar').addEventListener('click', function() {
    document.documentElement.classList.remove('menu-ativo');
});

document.documentElement.addEventListener('click', function(event) {
    if (event.target === document.documentElement) {
        document.documentElement.classList.remove('menu-ativo');
    }
});