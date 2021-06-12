# Gallery Creator
Collects all files from a directory and lists them in an html file. Uses the [Apache Velocity](https://velocity.apache.org/) engine. 

## Requirements:
- Java 15
- git, maven (optional)

## Preparation
- Clone the project
- run `mvn clean install`
- grab `gallery-creator`from the `dist` directory 

**OR**

- Download `gallery-creator.jar` from releases

## Usage
- Create a working directory.
- Copy/paste the template `sample.vm`, edit it according to your requirements, and put it into the working directory.
- Move `gallery-creator.jar` into the same working directory.
- Put all other desired files/images into the same working directory.
- Run `java -jar gallery-creator.jar`.
- Check the output in `out.html`