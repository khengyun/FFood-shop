

<p align="center"><img src="https://socialify.git.ci/khengyun/FFood-shop/image?description=1&amp;font=Source%20Code%20Pro&amp;forks=1&amp;issues=1&amp;logo=%20%20%20https%3A%2F%2Fcdn-icons-png.flaticon.com%2F512%2F8827%2F8827871.png%20&amp;name=1&amp;pattern=Formal%20Invitation&amp;pulls=1&amp;stargazers=1&amp;theme=Auto" alt="project-image"></p>



![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white) ![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white) ![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E) ![jQuery](https://img.shields.io/badge/jquery-%230769AD.svg?style=for-the-badge&logo=jquery&logoColor=white) ![Bootstrap](https://img.shields.io/badge/bootstrap-%238511FA.svg?style=for-the-badge&logo=bootstrap&logoColor=white)

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white) ![Apache Tomcat](https://img.shields.io/badge/apache%20tomcat-%23F8DC75.svg?style=for-the-badge&logo=apache-tomcat&logoColor=black) ![Apache Netbeans](https://img.shields.io/badge/apache%20netbeans-1B6AC6?style=for-the-badge&logo=apache%20netbeans%20IDE&logoColor=white)
  

## Project ScreenShots:
  <img align="center" src="cover.png" alt="project-screenshot" >

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies](#technologies)
- [Installation](#dockerized-project-)
- [License](#license)

## Introduction

FFood is a food website that lets customers order food in a quick and convenient way.

## Features

- Menu filtering and searching on home page
- Quick and convenient ordering process (Choose Food -> Proceed to Checkout -> Place Order)
- Order without logging in
- Order tracking and auto-fill checkout forms for logged in users
- Optimized UX for customers: forms are contained in modals (pop-ups), users do not have to be redirected to different pages for these actions:
  - Logging in, signing up
  - Viewing Carts
- Food, User, Order management for Admins
- Authorization system prevents unauthorized access to certain pages
  - Non-logged in users cannot access User and Admin pages
  - Logged-in Users can access User pages, but not Admin pages
  - Admins can only access Admin pages, the rest is restricted from access to prevent security risks
- Optimized UX for admins: CRUD actions are prompted using modals (pop-ups) instead of redirecting to specific pages

## Technologies

- Java SE 1.8 (Java 8)
- JSTL 1.2
- Tomcat 10 (Java EE 7)
- JavaScript ES6
- Bootstrap 5.3.0
- jQuery 3.7.0
- jQuery Validation Plugin
- Docker 24.0.6

### Dependencies

- Jakarta Servlet API (5.0.0)
- Microsoft JDBC Driver for SQL Server (10.2.0.jre8)
- Jakarta Standard Tag Library Implementation (Glassfish)

## Dockerized Project 🚀🚀
1. Install Docker on Window [click here](https://desktop.docker.com/win/main/amd64/Docker%20Desktop%20Installer.exe?_gl=1*3jwbnk*_ga*MTU2MzcwNDM2OS4xNjk3NDU2NjY2*_ga_XJWPQMJYHQ*MTY5NzUyNjQ5Mi44LjEuMTY5NzUyODUxMy40My4wLjA.)
2. Check Docker version after install 
> ```bash
> docker --version
> Docker 24.0.6
>```

3. ``cd`` into the cloned FFood-shop folder
>```bash
>git clone https://github.com/khengyun/FFood-shop.git
>cd FFood-shop
>## Run docker
>docker compose up --build
>```

Now, FFood running locally on your machine at: http://localhost:8080

## License

This project is licensed under the [MIT License](https://github.com/khengyun/FFood-shop/blob/main/README.md).
