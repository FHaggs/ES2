# ES2 Project

This project consists of two main services: `mdm` and `dem`.

- `mdm` (Master Data Management): A service for managing country data.
- `dem` (Data Extraction and Manipulation): A service that performs ETL operations, fetching data, transforming it, and loading it into the `mdm` service.

## Running Locally

The easiest way to run the project is by using Docker.

1.  **Build and run the services:**
    ```bash
    docker-compose up --build
    ```
    This command will build the Docker images for both `mdm` and `dem` services and start them along with their respective databases.

    -   `mdm` service will be available at `http://localhost:8081`
    -   `dem` service will be available at `http://localhost:8082`

## API Routes

You can interact with the services using the following API routes.

### MDM Service (`http://localhost:8081`)

-   **GET `/api/countries`**: Get a list of all countries.
-   **POST `/api/countries`**: Create a new country.
    -   **Body (example)**:
        ```json
        {
          "name": "argentina2",
          "code": "arg",
          "region": "Americas",
          "subregion": "South America",
          "capital": "Buenos Aires",
          "population": 93490988
        }
        ```
-   **DELETE `/api/countries/{id}`**: Delete a country by its ID.

### DEM Service (`http://localhost:8082`)

-   **POST `/api/etl/start`**: Starts the ETL process (extraction and transformation).
-   **POST `/api/etl/load/{id}`**: Loads the transformed data for a given transaction ID into the MDM service.
-   **GET `/api/etl/transactions`**: Get a list of all ETL transactions.

There is also a `request.http` file that you can use with a REST client like the one in VS Code to send requests to these endpoints.

### Demo ETL Script

A shell script `demo_etl.sh` is provided to demonstrate a typical ETL workflow. You can run it from your terminal:

```bash
./demo_etl.sh
```

This script will:
1.  Start the ETL process in the `dem` service.
2.  List the ETL transactions.
3.  Load the transformed data into the `mdm` service.
4.  List the countries in the `mdm` service to verify the data has been loaded.
5.  List the ETL transactions again to show the final status.

## Testing

Each service has its own set of tests. To run them, you need to have Maven installed.

Navigate to the directory of the service you want to test and run the following command:

-   **For the `mdm` service:**
    ```bash
    cd mdm
    mvn test
    ```

-   **For the `dem` service:**
    ```bash
    cd dem
    mvn test
    ``` 