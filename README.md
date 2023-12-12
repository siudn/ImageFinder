# ImageFinder

Eulerity project: a multithreaded image-scraping web crawler.

> [!NOTE]
> Run `mvn clean package jetty:run` instead of `mvn clean package test jetty:run`.
> <br>

> [!WARNING]
> [Java 8](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html) and [Maven 3.5+](https://www.baeldung.com/install-maven-on-windows-linux-mac) are needed to run this project. It will not work with other versions of Java.
> <br>

Testing links with runtimes are in `test-links.txt`. Project instructions and requirements are in `oldInstructions.md`.

## Setup

### 1. Clone the repository

```bash
git clone git@github.com:siudn/ImageFinder.git
cd ImageFinder
```

### 2. Install dependencies and run

```bash
mvn clean
mvn package
mvn clean package jetty:run
```
