package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Data
@Table(name = "api_usage") // Plant
public class ApiUsage implements Serializable {

    @Serial
    private static final long serialVersionUID = 8592895910447049554L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "request_method")
    private String requestMethod; // GET/POST

    @Column(name = "request_url" )
    private String requestUrl;

//    @Lob
    @Column(name = "request_body" , columnDefinition = "TEXT" )
//    @JsonSubTypes.Type(JavaType.class)
//    @ColumnTransformer(write = "?::jsonb")
    private String requestBody;

//    @Lob
//    @Column(name = "response_body" , columnDefinition = "json" ) //, columnDefinition = "json"
//    @JsonSubTypes.Type(JavaType.class)
//    @ColumnTransformer(write = "?::jsonb")
    @Column(name = "response_body" , columnDefinition = "TEXT")
    private String responseBody;

    @Column(name = "response_status")
    private int responseStatus;

    @Column(name = "service_from")
    @Enumerated(EnumType.STRING)
    private ServiceTypes serviceFrom; // (USER/WRITEAPI)

    @Column(name = "service_to")
    @Enumerated(EnumType.STRING)
    private ServiceTypes serviceTo; //(SELF/INFLUX/DASHBOARD/WRITEAPI)

    @Column(name = "start_time")
    private long startTime;

    @Column(name = "end_time")
    private long endTime;

    @Column(name = "execution_time")
    private long execTime;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime createdAt;

}
