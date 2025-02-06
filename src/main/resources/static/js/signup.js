const showPasswordError = () => {
    const passwordError = document.getElementById("passwordError");
    passwordError.classList.remove("hidden");
};

const hidePasswordError = () => {
    const passwordError = document.getElementById("passwordError");
    passwordError.classList.add("hidden");
};

const showErrorModal = (message) => {
    document.getElementById("errorMessage").innerText = message;
    document.getElementById("errorModal").classList.remove("hidden");
};

const closeErrorModal = () => {
    document.getElementById("errorModal").classList.add("hidden");
};

const signup = async () => {

    const name = document.getElementById('name').value;
    const userId = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const passwordConfirm = document.getElementById('confirm_password').value;

    if(password !== passwordConfirm) {
        showPasswordError();
        return;
    }

    hidePasswordError();

    const data = {
        name,
        userId,
        email,
        password
    }

    try {
        const response = await fetch('/auth/signup', {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data),
        });

        const responseText = await response.text();

        if (!response.ok) {
            showErrorModal(responseText);
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        window.location.href = "/login";

    }catch (error) {
        console.error('Error fetching customer data:', error);
    }
}
