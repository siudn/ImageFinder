const urlForm = document.querySelector(".form");
const inputUrl = document.querySelector("#url");

urlForm.addEventListener("submit", (e) => {
  e.preventDefault();
  console.log(inputUrl.value);
  getImages(inputUrl.value);
});

function getImages(inputUrl) {
  fetch("http://localhost:8080/crawl", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ url: inputUrl }),
  })
    .then((res) => res.json())
    .then((data) => console.log(data))
    .catch((error) => console.log(error));
}
