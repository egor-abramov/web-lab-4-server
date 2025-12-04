package server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "points")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NonNull
    private Float x;

    @Column(nullable = false)
    @NonNull
    private Float y;

    @Column(nullable = false)
    @NonNull
    private Float r;

    @Column(nullable = false)
    private boolean isHit;

    @Column(nullable = false)
    private String utcTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @NonNull
    private UserEntity user;
}
