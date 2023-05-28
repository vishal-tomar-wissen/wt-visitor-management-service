package com.wissen.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
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

}
