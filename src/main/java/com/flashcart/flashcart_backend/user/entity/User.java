package com.flashcart.flashcart_backend.user.entity;

import com.flashcart.flashcart_backend.enums.user.Login;
import com.flashcart.flashcart_backend.enums.user.Role;
import jakarta.persistence.*;

import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String password;
    @Column(nullable = false ,updatable = false, name = "created_at")
    @CreationTimestamp

    private LocalDateTime createdAt;
    @Column(nullable = false , name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Column(nullable = false , name = "full_name")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
   @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "login_type")
    private Login loginType;
   @Column(unique = true, name = "provider_identifier")
    private String providerIdentifier;
   @Column(nullable = false, name = "email_verified")
    private Boolean emailVerified = false;
   @Column(nullable = false, name = "phone_verified")
    private Boolean phoneVerified = false;
    @Column(unique = true)
   private String email;
    @Column(unique = true, name = "phone_number")
    private String phoneNumber;
    @Column(nullable = false, name = "is_deleted")
    private Boolean isDeleted = false;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


}
