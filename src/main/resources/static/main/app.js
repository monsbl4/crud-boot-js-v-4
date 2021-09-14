$(document).ready(function () {
    showAllUsers();
    $('.AddBtn').on('click', function () {
        let user = {
            username: $("#username").val(),
            name: $("#name").val(),
            lastName: $("#lastName").val(),
            email: $("#email").val(),
            age: $("#age").val(),
            password: $("#password").val(),
            roles: getRole("#selectRole")
        }
        console.log(user);
        fetch("api/newUser", {
            method: "POST",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body: JSON.stringify(user)
        }).then(() => openTabById('nav-home'))
            .then(() => showAllUsers());
        $('input').val('');
    });
});

function createTableRow(u) {

    return `<tr id="user_table_row">
            <td>${u.id}</td>
            <td>${u.username}</td>
            <td>${u.name}</td>
            <td>${u.lastName}</td>
            <td>${u.email}</td>
            <td>${u.age}</td>
            <td>${u.rolesUsers}</td>
            <td>
            <a  href="/api/${u.id}" class="btn btn-primary eBtn" >Edit</a>
            </td>
            <td>
            <a  href="/api/delete/${u.id}" class="btn btn-danger delBtn">Delete</a>
            </td>
        </tr>`;
}

function getRole(address) {
    let data = [];
    $(address).find("option:selected").each(function () {
        data.push({id: $(this).val(), role: $(this).attr("name"), authority: $(this).attr("name")})
    });
    console.log(data)
    return data;
}

function showAllUsers() {
    console.log('start')
    let UserTableBody = $("#user_table_body")

    UserTableBody.empty();


    fetch("/api/allUsers")
        .then((response) => {
            console.log(response)
            response.json().then(data => data.forEach(function (user) {
                let TableRow = createTableRow(user);
                UserTableBody.append(TableRow);

            }));
        }).catch(error => {
        console.log(error);
    });
}

document.addEventListener('click', function (event) {
    event.preventDefault()
    if ($(event.target).hasClass('delBtn')) {
        let href = $(event.target).attr("href");
        delModalButton(href)
    }


    if ($(event.target).hasClass('eBtn')) {
        let href = $(event.target).attr("href");
        $(".editUser #exampleModal").modal();

        $.get(href, function (user) {
            $(".editUser #id").val(user.id);
            $(".editUser #usernameEdit").val(user.username);
            $(".editUser #nameEdit").val(user.name);
            $(".editUser #lastNameEdit").val(user.lastName);
            $(".editUser #emailEdit").val(user.email);
            $(".editUser #ageEdit").val(user.age);
            $(".editUser #passwordEdit").val(user.password);
            $(".editUser #selectRoleEdit").val(user.roles);
        });
    }
    if ($(event.target).hasClass('editButton')) {
        let user = {
            id: $('#id').val(),
            username: $('#usernameEdit').val(),
            name: $("#nameEdit").val(),
            lastName: $("#lastNameEdit").val(),
            email: $('#emailEdit').val(),
            age: $("#ageEdit").val(),
            password: $('#passwordEdit').val(),
            roles: getRole("#selectRoleEdit")

        }
        editModalButton(user)
        console.log(user);
    }

    if ($(event.target).hasClass('logout')) {
        logout();
    }
    if ($(event.target).hasClass('btnUserTable')) {
        userTable();
    }

});

function delModalButton(href) {
    fetch(href, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        }
    }).then(() => showAllUsers());
}

function editModalButton(user) {
    fetch("api/edit", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(user)
    }).then(function (response) {
        $('input').val('');
        $('.editUser #exampleModal').modal('hide');
        showAllUsers();
    })
}

function openTabById(tab) {
    $('.nav-tabs a[href="#' + tab + '"]').tab('show');
}

function logout() {
    document.location.replace("/logout");
}

function userTable() {
    document.location.replace("/user");
}


