# Gallery Creator
Collects all files from a directory and lists them in an output file, which is based on a [Apache Velocity](https://velocity.apache.org/) template 

## Requirements:
- Java 15
- git, maven (optional)

## Preparation
Clone the project and run `mvn clean install` 

**OR**

Download `GalleryCreator.jar` from releases

## Usage
- create a working directory
- copy/paste the template `sample.vm`, edit it according to your requirements, and put it into the working directory
- move `GalleryCreator.jar` into the same working directory
- put all other files/images you want to reference to into te same working directory
- run `java -jar Gallery-Creator.jar`
- check the output in `out.html`