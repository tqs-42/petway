const BASE_API_URL = "http://127.0.0.1:8000/api/client/cart/"

const loadProducts = () => $.get(BASE_API_URL).done(data => console.log(data))

const addProductEvent = e => $.post(BASE_API_URL, $(e.target).closest('form').serialize()).done(data => console.log(data))

$('#cartBtn').click(() => 1)