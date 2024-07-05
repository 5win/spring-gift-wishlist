function addOne() {
     event.preventDefault();

    var formData = {
        'id' : $('#id').val(),
        'name' : $('#name').val(),
        'price' : $('#price').val(),
        'imageUrl' : $('#imageUrl').val()
    };

    $.ajax({
        url: '/api/products/product',
        method: 'POST',
        data: JSON.stringify(formData),
        contentType: 'application/json',
        processData: false,

        success: function (response) {
            alert(response);
            location.href = '/api/products';
        },
        error: function (request, status, error) {
            alert(request.responseText);            
        }
    });
}

function deleteOne(id) {
   $.ajax({
       method: 'DELETE',
       url: `/api/products/product/${id}`,
       success: function(response) {
            alert(response); 
            location.href = '/api/products';
        },
        error: function (request, status, error) {
            alert(request.responseText);            
        }
   });
}

function editOne() {
    event.preventDefault();

    var formData = {
        'id' : $('#id').val(),
        'name' : $('#name').val(),
        'price' : $('#price').val(),
        'imageUrl' : $('#imageUrl').val()
    };

    $.ajax({
        url: '/api/products/product',
        method: 'PUT',
        data: JSON.stringify(formData),
        contentType: 'application/json',
        processData: false,
        success: function (response) {
            alert(response);
            location.href = '/api/products';
        },
        error: function (request, status, error) {
            alert(request.responseText);            
        }
    });
}

function addWishlist(id) {
    event.preventDefault();

    $.ajax({
        url: `/api/members/member/wishlist/${id}`,
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        },
        success: function (response) {
            alert(response);
            location.href = '/api/products';
        },
        error: function (request, status, error) {
            alert(request.responseText);            
        }
    });
}