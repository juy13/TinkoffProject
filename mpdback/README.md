# Personal data manager

## Stack

1. PostgresSQL
2. Kotlin
3. Spring Boot
4. ActiveMq
5. Docker
6. Nginx

## Docker setting

### Prepare database

For starting database image use docker container with Postgres:

```shell
docker run -d \
--name postgresKotlin \
-e POSTGRES_DB=tinkoffdb \
-e POSTGRES_USER=tinkoff \
-e POSTGRES_PASSWORD=tinkoff \
-e PGDATA=/var/lib/postgresql/data/pgdata \
-p 5432:5432 \
--net kotlin-net \
-d postgres
```

In addition, as it working on a special port, we need to configure net in docker and use it in containers:

```shell
docker network create kotlin-net
```

### Prepare jar for using

For starting docker with application, it should be prepared its .jar:

```shell
cd mpdback
gradle build
gradle jar
```

### Start using docker with app

First built an image:

```shell
docker build -f Dockerfile -t mpdback:1.0 .
```

After that start container:

```shell
docker run --name mpdback -p 8080:8080 \
--net kotlin-net \
 -e JDBC_URL=jdbc:postgresql://postgresKotlin:5432/tinkoffdb \
 -e UserPassword=tinkoff \
 -e User=tinkoff mpdback:1.0
```

As there are some environment vars in Dockerfile, they should be configured. They are:

1. JDBC_URL - connection path for a database. It works in or net, so postgresKotlin - name of container with DB
2. UserPassword - password for db
3. User - user (^o^)

## Testing

For testing, it was prepared a PostMan collection. It includes paths:

1. Auth part:
   1. /new - create new user
   2. /auth - authentication, returns user's id and token
2. Api part:
   1. /white-api/add-record - add new user record
   2. /white-api/prepare-data-all - preparing all user's records
   3. /white-api/prepare-data-by-subject?subject=... - preparing a special user's records
   4. /white-api/get-all-data - get all user's data that were prepared
   5. /white-api/upload - upload file
   6. white-api/download?name=... - download file by name
   7. white-api/prepare-file-by-name?name=... - prepare for downloading a file
