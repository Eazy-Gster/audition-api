# Audition API - Updates

This document outlines changes that have been made to the Audition API application.

## Endpoint changes

### /posts

new query parameter 'userId' allows for in code filtering by user.

### /posts/{id}

new query parameter 'comments' will return all comments for the given post. Default is no comments included.

### /posts/{id}/comments

returns all comments for the given post

## Additional changes

#### - Improved error handling

#### - Logging of request and response at debug level via interceptor

#### - Coverage and style checks added and with 80% coverage

#### - Unit tests added

#### - Nebula Linter allows for detection of unused dependencies




