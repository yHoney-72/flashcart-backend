package com.flashcart.flashcart_backend.driver.mapper;

import com.flashcart.flashcart_backend.driver.dto.DriverProfileResponse;
import com.flashcart.flashcart_backend.driver.dto.DriverRegistrationRequest;
import com.flashcart.flashcart_backend.driver.dto.DriverResponse;
import com.flashcart.flashcart_backend.driver.dto.UpdateDriverProfileRequest;
import com.flashcart.flashcart_backend.driver.entity.Driver;
import com.flashcart.flashcart_backend.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    @Mapping(source = "fullName", target = "name")
    User toUserEntity(DriverRegistrationRequest request);

    Driver toDriverEntity(DriverRegistrationRequest request);

    @Mapping(source = "user.name", target = "fullName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phoneNumber", target = "phoneNumber")
    DriverResponse toDriverResponse(Driver driver);

    @Mapping(source = "user.name", target = "fullName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phoneNumber", target = "phoneNumber")
    DriverProfileResponse toDriverProfileResponse(Driver driver);
    void updateDriverFromRequest(UpdateDriverProfileRequest request,@MappingTarget Driver driver);
}

// @MappingTarget => Naya object create nahi karta.
// Database se aaye existing object ko hi update karta hai.
// Sirf request me jo fields hain unhi ko update karega.


// @Mapping => Source object ki field ko Target object ki field me map karta hai.
// Agar field names same hain to MapStruct automatically map karta hai.
// Agar names alag hain ya nested object hai to @Mapping likhna padta hai.

// @Mapping
// "KYA copy karna hai?"

// @MappingTarget
// "KIS object me copy karna hai?"