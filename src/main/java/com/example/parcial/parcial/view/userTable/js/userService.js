async function getUsers() {
  try {
    const request = await fetch("http://localhost:8080/api/users/all", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });
    if (!request.ok) {
      throw new Error(`HTTP error! status: ${request.status}`);
    }
    const users = await request.json();

    const tableHtml = users
      .map((user) => {
        return `
        <tr>
          <td>${user.id}</td>
          <td>${user.name + " " + user.lastName}</td>
          <td>${user.email}</td>
          <td>${user.phone}</td>
          <td>
            <button class="btn btn-danger" onclick="deleteUser(${
              user.id
            })">Delete</button>
          </td>
        </tr>
      `;
      })
      .join("");

    document.getElementById("userTableBody").innerHTML = tableHtml;

    return users;
  } catch (error) {
    console.error("Error fetching users:", error);
  }
}

async function deleteUser(id) {
  try {
    const request = await fetch(`http://localhost:8080/api/users/${id}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    });
    if (!request.ok) {
      throw new Error(`HTTP error! status: ${request.status}`);
    }
    getUsers();
  } catch (error) {
    console.error("Error deleting user:", error);
  }
}
