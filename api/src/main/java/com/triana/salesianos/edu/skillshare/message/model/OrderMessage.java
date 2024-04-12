package com.triana.salesianos.edu.skillshare.message.model;

import com.triana.salesianos.edu.skillshare.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class OrderMessage extends Message{
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
}
