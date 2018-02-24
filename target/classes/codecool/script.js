
function addEventListeners() {

    var elements = [
        document.getElementById("one"),
        document.getElementById("two"),
        document.getElementById("three")
    ];

    for (let i = 0; i < elements.length; i++) {
        addEventListener(elements[i]);
    }
}

function addEventListener(element) {
    element.addEventListener("click",function(e){
        e.stopPropagation();
        alert(this.dataset.name);
    },false);
}

document.addEventListener("DOMContentLoaded", function(event) {
    addEventListeners();
});
