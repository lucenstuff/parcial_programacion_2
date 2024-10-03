document.addEventListener("DOMContentLoaded", () => {
  const loginForm = document.getElementById("loginForm");
  if (loginForm) {
    loginForm.addEventListener("submit", function (event) {
      event.preventDefault();
      const email = document.getElementById("email").value;
      const password = document.getElementById("password").value;
      signIn(email, password);
    });
  }
});

document.addEventListener("DOMContentLoaded", () => {
  const registerForm = document.getElementById("registerForm");
  if (loginForm) {
    registerForm.addEventListener("submit", function (event) {
      event.preventDefault();
      const email = document.getElementById("email").value;
      const password = document.getElementById("password").value;
      signIn(email, password);
    });
  }
});

document.addEventListener("DOMContentLoaded", () => {
  document
    .getElementById("logoutButton")
    .addEventListener("click", function (event) {
      event.preventDefault();
      signOut();
    });
});

function saveTokenToCookie(token, days) {
  const date = new Date();
  date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
  document.cookie = `authToken=${token};expires=${date.toUTCString()};path=/`;
}

async function signOut() {
  try {
    const token = localStorage.getItem("authToken");
    const response = await fetch("http://localhost:8080/api/auth/logout", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      const errorData = await response.json();
      console.error("Failed to log out:", errorData.message);
      return;
    }

    deleteTokenCookie();
    sessionStorage.removeItem("authToken");
    localStorage.removeItem("authToken");
    window.location.href = "/";
  } catch (error) {
    console.error("Error during logout:", error);
  }
}

function deleteTokenCookie() {
  document.cookie =
    "authToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
}

async function signIn(email, password) {
  try {
    const response = await fetch("http://localhost:8080/api/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email: email,
        password: password,
      }),
    });

    if (!response.ok) {
      const errorData = await response.json();
      console.error("Failed to log in:", errorData.message);
      return;
    }

    const data = await response.json();
    sessionStorage.setItem("authToken", data.token);

    if (data.status === "success") {
      console.log("Logged in successfully!");
      console.log("Token", data.token);
      console.log("Expires In", new Date(data.expiresIn));
      localStorage.setItem("authToken", data.token);
      saveTokenToCookie(data.token, 1);
      window.location.href = "/";
    } else {
      console.error("Failed to log in:", data.message);
    }

    console.log("Successfully signed in:", data);
  } catch (error) {
    console.error("Error fetching users:", error);
  }
}

async function signUp(email, password, firstName, lastName, phone) {
  try {
    const response = await fetch("http://localhost:8080/api/auth/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email: email,
        password: password,
        firstName: firstName,
        lastName: lastName,
        phone: phone,
      }),
    });

    const data = await response.json();

    if (data.status === "success") {
      console.log("Registered successfully!");
      console.log("Token", data.token);
      console.log("Expires In", new Date(data.expiresIn));

      saveTokenToCookie(data.token, 1);
    } else {
      console.error("Failed to register:", data.message);
    }

    console.log("Successfully signed up:", data);
  } catch (error) {
    console.error("Error fetching users:", error);
  }
}
