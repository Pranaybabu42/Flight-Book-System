# Developer Task: Airport Count by Country

## Task summary

Implement a feature that lets a user find how many airports are registered in a specified country. The result is a count of airport records currently stored in the Flight Booking System; it is not a count of flights or flight instances.

**Assigned to:** Backend developer  
**Priority:** To be agreed by the product owner  
**Status:** Ready for technical design

## Business context

Users need a quick way to understand the airport coverage available in a country. They should be able to provide or select a country and see the number of airports registered for it, without retrieving and counting every airport themselves.

## Scope

- Add a read-only capability to count registered airports for a requested country.
- Use the existing airport data and application architecture.
- Return the country and its airport count; do not return the full airport list as part of this feature.
- Do not add, edit, or delete airport records as part of this task.

## Functional requirements (FR)

- **FR-1 — Request a count:** The system shall allow a user or client to request the airport count for a country.
- **FR-2 — Calculate the count:** The system shall count all airport records whose country matches the requested country. Each airport record shall contribute one to the count.
- **FR-3 — Return a result:** The system shall return the requested country and the calculated count in a structured response. The exact route and response format are for the developer to align with existing API conventions.
- **FR-4 — No matching airports:** If the country is valid but no airport records match it, the system shall return a count of `0` rather than treating this as a missing-resource error.
- **FR-5 — Validate input:** The system shall reject a missing or blank country value with an appropriate client error and a clear validation message.
- **FR-6 — Consistent matching:** Country matching shall be case-insensitive and ignore leading or trailing whitespace, so equivalent country input returns the same count.
- **FR-7 — Read-only behavior:** The count operation shall not modify airport data.

## Non-functional requirements (NFR)

- **NFR-1 — Performance:** For the expected production dataset and normal service load, the count request should complete within 2 seconds at the 95th percentile. The implementation should count in the data layer rather than load all airport records into application memory.
- **NFR-2 — Accuracy:** The returned count must reflect the airport records available to the request and must not count duplicate representations of the same stored record more than once.
- **NFR-3 — API consistency:** The feature shall follow the project's existing REST, response, validation, and error-handling conventions.
- **NFR-4 — Maintainability:** Keep responsibilities in the existing controller, service, and repository layers. Add or update automated tests for the endpoint and counting behavior.
- **NFR-5 — Security:** Apply the existing application security and access-control rules. This feature shall not expose airport data beyond the access already permitted by the application.
- **NFR-6 — Reliability:** A valid count request shall return a deterministic non-negative integer, including when there are no matching records.

## Acceptance criteria

1. Requesting the count for a country with matching airport records returns the correct total and identifies the country.
2. A valid country with no matching records returns `0`.
3. Country matching behaves consistently across letter case and surrounding whitespace.
4. A missing or blank country is rejected with a clear client error.
5. The operation does not change airport records.
6. Automated tests cover the successful count, zero-count case, and invalid input.

## Implementation notes

The existing airport model stores a country value. The developer should confirm whether existing records use consistent country names and normalize input and stored values as appropriate without changing unrelated airport data. Choose an endpoint that fits the current API style and document it in the API documentation or project README after implementation.

## Out of scope / clarification

This task covers a count for one requested country. A separate endpoint or screen that lists counts for every country is not required unless the product owner confirms that users need an all-countries summary. The user interface, if any, should follow the product's existing presentation approach.
