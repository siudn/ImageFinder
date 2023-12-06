const urlForm = document.querySelector(".form");
const inputUrl = document.querySelector("#url").value;

urlForm.addEventListener("submit", (e) => {
  e.preventDefault();
  if (!clientValidate(inputUrl)) {
    alert("Enter a proper url!");
    return;
  }
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

function clientValidate(link) {
  // client-side validation, just for convenience (still validated on server-side)
  const linkRegex =
    /^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z0-9\u00a1-\uffff][a-z0-9\u00a1-\uffff_-]{0,62})?[a-z0-9\u00a1-\uffff]\.)+(?:[a-z\u00a1-\uffff]{2,}\.?))(?::\d{2,5})?(?:[/?#]\S*)?$/i;
  return linkRegex.test(link);
}
