<div align="center">
<h1 align="center">
<img src="https://github.com/khengyun/FFood-shop/blob/29835c240b656bb24c1ae445327bce14930dfbf8/src/main/webapp/assets/img/favicons/apple-touch-icon.png?raw=true" width="100" />
<br>FFOOD-SHOP</h1>
<h3> FFood is a food e-commerce site that lets customers order food in a quick and convenient way.</h3>
<h3>◦ Developed with the software and tools below. ◦</h3>

<p align="center">
<img src="https://img.shields.io/badge/GNU%20Bash-4EAA25.svg?style=flat-square&logo=GNU-Bash&logoColor=white" alt="GNU%20Bash" />
<img src="https://img.shields.io/badge/Grunt-FAA918.svg?style=flat-square&logo=Grunt&logoColor=white" alt="Grunt" />
<img src="https://img.shields.io/badge/JavaScript-F7DF1E.svg?style=flat-square&logo=JavaScript&logoColor=black" alt="JavaScript" />
<img src="https://img.shields.io/badge/scikitlearn-F7931E.svg?style=flat-square&logo=scikit-learn&logoColor=white" alt="scikitlearn" />
<img src="https://img.shields.io/badge/HTML5-E34F26.svg?style=flat-square&logo=HTML5&logoColor=white" alt="HTML5" />
<img src="https://img.shields.io/badge/YAML-CB171E.svg?style=flat-square&logo=YAML&logoColor=white" alt="YAML" />
<img src="https://img.shields.io/badge/PHP-777BB4.svg?style=flat-square&logo=PHP&logoColor=white" alt="PHP" />
<img src="https://img.shields.io/badge/Python-3776AB.svg?style=flat-square&logo=Python&logoColor=white" alt="Python" />

<img src="https://img.shields.io/badge/Docker-2496ED.svg?style=flat-square&logo=Docker&logoColor=white" alt="Docker" />
<img src="https://img.shields.io/badge/jQuery-0769AD.svg?style=flat-square&logo=jQuery&logoColor=white" alt="jQuery" />
<img src="https://img.shields.io/badge/NumPy-013243.svg?style=flat-square&logo=NumPy&logoColor=white" alt="NumPy" />
<img src="https://img.shields.io/badge/FastAPI-009688.svg?style=flat-square&logo=FastAPI&logoColor=white" alt="FastAPI" />
<img src="https://img.shields.io/badge/JSON-000000.svg?style=flat-square&logo=JSON&logoColor=white" alt="JSON" />
<img src="https://img.shields.io/badge/Wikipedia-000000.svg?style=flat-square&logo=Wikipedia&logoColor=white" alt="Wikipedia" />
<img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=flat-square&logo=openjdk&logoColor=white" alt="java" />
</p>
<img src="https://img.shields.io/github/license/khengyun/FFood-shop?style=flat-square&color=5D6D7E" alt="GitHub license" />
<img src="https://img.shields.io/github/last-commit/khengyun/FFood-shop?style=flat-square&color=5D6D7E" alt="git-last-commit" />
<img src="https://img.shields.io/github/commit-activity/m/khengyun/FFood-shop?style=flat-square&color=5D6D7E" alt="GitHub commit activity" />
<img src="https://img.shields.io/github/languages/top/khengyun/FFood-shop?style=flat-square&color=5D6D7E" alt="GitHub top language" />
</div>

  

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
