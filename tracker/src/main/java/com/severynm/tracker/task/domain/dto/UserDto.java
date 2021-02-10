package com.severynm.tracker.task.domain.dto;

import java.util.Objects;

public class UserDto {

    private Long userId;
    private String firstName;
    private String lastName;
    private String departmentName;
    private int rating;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return rating == userDto.rating && Objects.equals(userId, userDto.userId) &&
                Objects.equals(firstName, userDto.firstName) && Objects.equals(lastName, userDto.lastName) &&
                Objects.equals(departmentName, userDto.departmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, departmentName, rating);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", rating=" + rating +
                '}';
    }
}
