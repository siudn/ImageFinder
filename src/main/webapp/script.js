const urlForm = document.querySelector(".form");
const inputUrl = document.querySelector("#url");
let output = document.querySelector(".output");

urlForm.addEventListener("submit", (e) => {
  e.preventDefault();
  output.innerHTML = ``;
  console.log(inputUrl.value);
  getImages(inputUrl.value);
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
    console.log(data);

    data.forEach((image) => {
      let newSection = document.createElement("div");
      let newImage = document.createElement("img");
      newImage.setAttribute("src", image.imageUrl);
      newImage.setAttribute("alt", image.title);
      newSection.appendChild(newImage);

      if (image.logoOrIcon) {
        let logoOrIcon = document.createElement("p");
        logoOrIcon.innerHTML = image.logoOrIcon
          ? "<h3>The image directly above contains logos or icons.<h3>"
          : null;
        newSection.appendChild(logoOrIcon);
      }

      output.appendChild(newSection);
    });
  } catch (error) {
    console.error("Error: ", error);
  }
}
