/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.acme.getting.started;

import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

@Entity
public class MyEntity implements Serializable {
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;    
    
    @Column(name = "name", unique = false, nullable = false)
    private String name;
    
    @Column(name = "lastId", unique =false, nullable = true)
    private Long lastId;
    
    @Column(name = "stamp", nullable = false)
    private OffsetDateTime created;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MyEntity setName(String name) {
        this.name = name;
        return this;
    }

    public Long getLastId() {
        return lastId;
    }

    public MyEntity setLastId(Long lastId) {
        this.lastId = lastId;
        return this;
    }

    
    public OffsetDateTime getCreated() {
        return created;
    }

    @PrePersist
    protected void onCreate() {
        created = OffsetDateTime.now();
    }
    
}
