/* Utility functions that are used across many
    HTML documents and JS files. */


/* Shows an element as 'inline' */
function show(element){
    element.style.display = "inline";
}

/* Shows an element as 'none', hiding the element */
function hide(element){
    element.style.display = "none";
}

/* Loads an html page into the 'dynamicContainer' on
    the index.html page. */
function switchPage(page){
    $("#dynamicContainer").load(page);
}