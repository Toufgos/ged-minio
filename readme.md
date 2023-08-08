# GED Minio Management Application

The GED Minio Management Application is a Spring Boot application designed to manage GED operations related to Minio. It provides a set of endpoints for interacting with Minio and performing various operations.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Postman Collection](#postman-collection)

## Prerequisites

Before you begin, ensure you have the following software installed on your system:

- Docker: [Install Docker](https://docs.docker.com/get-docker/)

## Getting Started

To run the GED Minio Management Application along with its dependencies, follow these steps:

1. Clone the repository to your local machine:

   ```bash
   git clone <repository_url>

2. Navigate to the root directory of the project:

    ```bash
   cd <project_directory>

3. Build and start the application using Docker Compose:

   ```bash
   docker-compose up --build
This command will build the necessary Docker images and start the application along with its dependencies.

## Usage

Once the application is up and running, you can access the API documentation and interact with the endpoints.

Open your web browser and navigate to the Swagger API documentation:

http://localhost:8037/swagger-ui/index.html

The Swagger UI provides an interactive interface for exploring and testing the available endpoints.
API Documentation
The API documentation is generated using Swagger and provides detailed information about the available endpoints, r
equest parameters, response structures, and more. You can access the documentation through the Swagger UI as mentioned in the [Usage](#usage) section.

## Postman Collection

A Postman collection is also included in the project directory. You can use this collection to import the available API endpoints into your Postman application for easy testing and interaction.

To import the collection:

1. Open Postman.
2. Click on the "Import" button.
3. Select the "Upload Files" tab and upload the ged-minio.postman_collection.json file from the project directory.
4. The collection will be imported into Postman, and you can start sending requests to the API endpoints.