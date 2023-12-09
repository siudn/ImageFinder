const urlForm = document.querySelector(".form");
const inputUrl = document.querySelector("#url");
let output = document.querySelector(".output");

urlForm.addEventListener("submit", (e) => {
  e.preventDefault();
  console.log(inputUrl.value);
  displayImages(getImages(inputUrl.value));
});

async function getImages(inputUrl) {
  try {
    const res = await fetch("http://localhost:8080/crawl", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ url: inputUrl }),
    });
    const data = await res.json();
    return data;
  } catch (error) {
    console.error("Error: ", error);
  }
}

async function displayImages(imageList) {
  await imageList;
  imageList.forEach((image) => {
    let newImage = document.createElement("img");
    newImage.setAttribute("src", image.imageUrl);
    newImage.setAttribute("alt", image.title);
    output.appendChild(newImage);
  });
}
