# spring-data-sample
Sample Spring 3 application with basic CRUD using JPA, together with DB migration.

## Using
* `Spring-Boot 3`
* `Flyway`
* `Lombok`
* `Mapstruct`
* `Testcontainers`

## Domains
CRUD operations are done with JPA. More complicated SQL including database specific SQL are done with JPQL or native SQL . 

The field `id` is set automatically using hibernates `@UuidGenerator`. The fields `createdDate` and `modifiedDate` are set automatically using Springs `AuditingEntityListener`  
### Foo
```json
{
  "id": "16c66cff-5076-4136-9b29-28d40aa8b0f7",
  "name": "foo3",
  "createdDate": "2023-01-20T14:07:32.919392Z",
  "modifiedDate": "2023-01-20T14:07:32.919392Z",
  "data": {
    "field1": "field1",
    "field2": 2
  },
  "children": [
    {
      "id": "ecaefaa5-fcc4-4146-9e4a-2b614ab4b232",
      "name": "child2",
      "field": "value"
    },
    {
      "id": "a32a6be2-9a59-42de-bfdf-d00a9494830c",
      "name": "child1",
      "field": "value"
    }
  ]
}
```

#### Operations
| First Header | Second Header                               |
|--------------|---------------------------------------------|
| Get all      | `GET http://localhost:9191/api/foo`         |
| Get one      | `GET http://localhost:9191/api/foo/{id}`    |
| Create       | `POST http://localhost:9191/api/foo/`       |
| Update       | `PUT http://localhost:9191/api/foo/{id}`    |
| Delete       | `DELETE http://localhost:9191/api/foo/{id}` |

### Bar
```json
{
  "id": "a072cfe8-ef50-4d6d-b987-a5e1e12b00c6",
  "name": "bar",
  "createdDate": "2023-01-20T14:08:02.954134Z",
  "modifiedDate": "2023-01-20T14:08:02.954134Z",
  "fooId": "16c66cff-5076-4136-9b29-28d40aa8b0f7"
}
```

#### Operations
| First Header | Second Header                               |
|--------------|---------------------------------------------|
| Get all      | `GET http://localhost:9191/api/bar`         |
| Get one      | `GET http://localhost:9191/api/bar/{id}`    |
| Create       | `POST http://localhost:9191/api/bar/`       |
| Update       | `PUT http://localhost:9191/api/bar/{id}`    |
| Delete       | `DELETE http://localhost:9191/api/bar/{id}` |

## Migrations
Database migrations are done with Flyway.
