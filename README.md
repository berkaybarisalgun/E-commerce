# Spring Boot E-Commerce Project

The project aims to develop an e-commerce website with various features and functionalities. The technologies used in this project include Java, Spring Boot, Hibernate, Thymeleaf, Bootstrap, jQuery, HTML, and RESTful Web services.

## About Project

The project is still under active development, and additional features and improvements are being made. You can contribute to the project by submitting bug reports, feature requests, or pull requests on the project's GitHub repository.

Please note that this is a sample README file, and the actual setup and development process may vary based on your specific requirements and configurations.


## Project Overview

The project consists of two main applications: the Admin application and the Shopping application.

### Admin Application

The Admin application provides administrative functionalities for managing the e-commerce website. The following features are available in the Admin application:

- Users: Manage user accounts and permissions. The admin can create, update, and delete user accounts. User roles and permissions can be assigned to control access to various features.
- Categories: Manage product categories. Admin can create, update, and delete categories, and assign products to specific categories.
- Brands: Manage brands for the products. Admin can create, update, and delete brands and associate them with products.
- Products: Manage the products available for sale. Admin can add new products, update existing products, and remove products from the inventory. Each product has details such as name, description, price, image, and stock availability.
- Customers: Manage customer information. Admin can view and update customer details such as name, address, and contact information.
- Shipping: Manage shipping options and information. Admin can configure different shipping methods, rates, and delivery timeframes.
- Orders: View and manage customer orders. Admin can view and update order statuses, track shipments, and handle customer inquiries related to orders.
- Sales Report: Generate and view sales reports. Admin can generate reports to analyze sales performance over a specific period. The reports can include information such as total sales, popular products, and revenue.
- Reviews: Manage product reviews and ratings. Admin can view and moderate customer reviews for products, including approving or deleting reviews.
- Questions: Handle customer questions and inquiries. Admin can respond to customer inquiries, provide support, and manage customer communication.
- Settings: Configure various settings for the e-commerce website. Admin can customize settings such as website title, logo, payment options, and other preferences.

### Shopping Application

The Shopping application is the front-end of the e-commerce website where customers can browse and purchase products. The following features are available in the Shopping application:

- Categories Listing: Customers can browse products by categories. They can select a specific category and view all products associated with that category.
- Product Details: Customers can view detailed information about a specific product, including its name, description, price, images, and customer reviews.
- Search Products: Customers can search for products based on keywords. The search functionality allows customers to find products quickly and easily.
- Shopping Cart: Customers can add products to the cart for purchase. They can add multiple products, update quantities, and remove items from the cart.
- Checkout: Customers can proceed to the checkout process. They can enter their shipping and billing information, select a shipping method, and review the order before making a payment.
- Manage Orders: Customers can view and manage their orders. They can track the status of their orders, view order history, and request order cancellations or returns.
- Tracking Orders: Customers can track the status of their orders. They can check the current location and estimated delivery date of their shipments.
- Customer Registration: New customers can register on the website. They can create an account, provide their personal information, and set up a password for future logins.

## Additional Technologies

In addition to the main features and functions, I also want to implement the following technologies in the project, but it is not certain yet.

- Spring Data JPA and Hibernate: Persistence and database management. These technologies provide seamless integration with the database, allowing efficient storage and retrieval of data.
- Spring Security: Authentication and authorization. The project uses Spring Security to handle user authentication and authorization, ensuring that only authorized users can access certain features and resources.
- Spring OAuth: Social login with Facebook and Google. Customers can log in to the e-commerce website using their social media accounts, such as Facebook or Google.
- Spring Mail: Sending customer registration confirmation and order confirmation emails. Customers receive confirmation emails after registering on the website and placing orders.
- PayPal Checkout API: Capturing payments from customers. The project integrates with the PayPal Checkout API to securely process payments from customers.
- Google Chart API: Drawing charts for sales reports. The sales reports include visually appealing charts generated using the Google Chart API to help visualize sales data.
- JUnit, AssertJ, and Mockito: Unit testing. The project includes unit tests to ensure the correctness of the implemented functionalities.
- Spring RESTful Web services: Building RESTful APIs. The project provides RESTful APIs for various functionalities, allowing integration with other systems or external applications.
- Bootstrap 4, HTML5, and jQuery 3: Front-end development. The project utilizes Bootstrap, HTML5, and jQuery to create a responsive and user-friendly interface for the e-commerce website.
- Amazon S3 APIs: Storing user-uploaded files. User-generated files, such as product images, are stored securely on the Amazon S3 server.
- Deployment on Heroku (AWS): Hosting the e-commerce web app. The project can be deployed on the Heroku cloud platform, which provides scalability and reliability.

## Deployment and Development

To deploy and run the project, follow these steps:

1. Clone the project repository from [GitHub](https://github.com/your-repo-link).
2. Install the necessary dependencies and ensure you have Java and Maven installed on your system.
3. Set up a database (e.g., MySQL, PostgreSQL) and configure the database connection in the project's configuration files.
4. Build the project using Maven by running the command `mvn clean install` in the project's root directory.
5. Once the build is successful, run the project using the command `java -jar target/your-project-name.jar`.
6. Access the application in your web browser by navigating to `http://localhost:8080`.

## Acknowledgements

I would like to express my gratitude to **Nam Ha Minh** for their valuable contributions and guidance throughout the development of this project. Their tutorials and videos were immensely helpful in understanding and implementing various aspects of the e-commerce website. I highly recommend checking out their content for anyone interested in learning more about Spring Boot and e-commerce application development.

- Nam Ha Minh's GitHub: [Github](https://github.com/namhaminh)
- Nam Ha Minh's YouTube Channel: [Youtube](https://www.youtube.com/@CodeJava)

Thank you, Nam Ha Minh, for sharing your knowledge and helping the community!




