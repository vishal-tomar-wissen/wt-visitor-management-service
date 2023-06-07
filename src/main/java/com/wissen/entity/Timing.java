package com.wissen.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Entity for timing.
 *
 * @author Vishal Tomar
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "timing")
@EqualsAndHashCode
public class Timing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="visitor_id", referencedColumnName = "visitorId")
    @JsonBackReference
    private Visitor visitor;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime inTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = true)
    private LocalDateTime outTime;

    @NotBlank(message = "Point of contact can not be blank.")
    @Column(nullable = true, length = 100)
    private String employeeId; // wissen id of point of contact

    @NotBlank(message = "Visitor type can not be null.")
    @Column(nullable = false, length = 50)
    private String visitorType;

}
