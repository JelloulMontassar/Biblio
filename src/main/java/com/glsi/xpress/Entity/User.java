package com.glsi.xpress.Entity;

import com.glsi.xpress.Entity.Enum.URole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cardId;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private URole role;
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
