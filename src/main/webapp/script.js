const urlForm = document.querySelector(".form");
const inputUrl = document.querySelector("#url").value;

urlForm.addEventListener("submit", (e) => {
  e.preventDefault();
  const formData = new FormData(urlForm);
  const data = new URLSearchParams(formData);

  sendImageLink(data);
});

function sendImageLink(url) {
  fetch("http://localhost:8080", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: JSON.stringify({ link: url }),
  })
    .then((res) => res.json())
    .then((data) => console.log(data))
    .catch((error) => console.log(error));
}
