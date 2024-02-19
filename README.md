# GitHub API Project

## Table of Contents
- [Java Version](#java-version)
- [Used Libraries](#used-libraries)
- [Available Endpoints](#available-endEndpoints)

## Java Version
This project uses Java version: `21.0.2`

## Used Libraries
List of libraries used in this project along with their versions:
- spring-boot-starter-web:3.2.2"
- spring-cloud-starter-openfeign:4.1.0
- lombok:1.18.30
- junit-jupiter-api:5.10.2

## Available Endpoints

- **URL**: `/users/{username}/repos`
- **Method**: `GET`
- **Auth required**: No

#### Success Response
- **Code**: `200 OK`
- **Content**:
```json
[
    {
        "repositoryName": "repositoryName",
        "owner": "ownerName",
        "branches": [
            {
                "name": "branchName",
                "lastCommitSha": "last_commit_sha"
            }
        ]
```
### Error Response
- **Code**: `404 NOT FOUND`
- **Content**:
```json

{
    "status": "404",
    "message": "Specified user has been not found"
}
```
All responses are in application/json format.
