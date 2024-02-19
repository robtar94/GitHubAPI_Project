package com.example.githubapi_project.users.controller;


import lombok.Builder;

@Builder
public record HttpError(String status, String message) {

}
