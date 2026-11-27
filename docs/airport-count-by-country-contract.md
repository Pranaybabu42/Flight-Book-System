# Airport Count by Country — API Contract

## Purpose

Return the requested country, the airports registered in it (airport name and city), and the total number of matching airport records.

> **Scope clarification:** The supplied task brief says the count response must not include the full airport list. This contract follows the later clarification requesting airport names and cities as well as the count, so it expands that original scope. Confirm this response shape with the product owner before implementation if the original count-only scope is still authoritative.

## Request

- **Method:** `GET`
- **Route:** `/airport/count`
- **Parameter:** `country` (required query parameter, string)
- **Request body:** None

Example:

```http
GET /airport/count?country=USA
```

Country matching trims leading/trailing whitespace and ignores letter case. The response's `country` value is the trimmed value supplied by the caller; matching should also work for equivalent forms such as `usa`, `USA`, or ` USA `.

## Successful response — HTTP 200

```json
{
  "country": "USA",
  "airports": [
    {
      "name": "Example International Airport",
      "city": "Example City"
    }
  ],
  "totalCount": 1
}
```

### Response fields

| Field | Type | Description |
| --- | --- | --- |
| `country` | string | Trimmed country value from the request. |
| `airports` | array | Matching airport records, each with `name` and `city`. Empty when no records match. |
| `airports[].name` | string | Registered airport name. |
| `airports[].city` | string | City stored for that airport. |
| `totalCount` | integer | Number of matching stored airport records; must equal `airports.length` for this response shape. |

A valid country with no matching records returns HTTP 200 with `airports: []` and `totalCount: 0`; this is not a not-found error.

## Invalid request — HTTP 400

A missing, empty, whitespace-only, or absent `country` parameter is invalid. The API should return the project's standard error response with a clear message, for example:

```json
{
  "message": "country must not be blank"
}
```

The exact error envelope should follow the existing global exception-handling convention.

## Minimum request and boundary test cases

| Case | Example request | Expected result |
| --- | --- | --- |
| Matching records | `/airport/count?country=USA` | HTTP 200; matching airport names/cities and correct `totalCount`. |
| Case and whitespace normalization | `/airport/count?country=%20usa%20` | Same matching records/count as `USA`; response country is trimmed to `usa`. |
| No matches | `/airport/count?country=Atlantis` | HTTP 200; `airports` is empty and `totalCount` is `0`. |
| Missing parameter | `/airport/count` | HTTP 400 with a clear country validation message. |
| Empty value | `/airport/count?country=` | HTTP 400 with a clear country validation message. |
| Whitespace-only value | `/airport/count?country=%20%20%20` | HTTP 400 with a clear country validation message. |

The operation is read-only and must not create, update, or delete airport records. Apply the existing access-control rules to this route.

## Example fixtures

See [airport-count-by-country-fixtures.json](airport-count-by-country-fixtures.json) for two input/expected-response fixtures: a country with matches and a valid country with no matches.
