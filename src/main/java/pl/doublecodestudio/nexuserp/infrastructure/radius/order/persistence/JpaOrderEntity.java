package pl.doublecodestudio.nexuserp.infrastructure.radius.order.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "Orders", schema = "order_warehouse_test_db")
public class JpaOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "`Index`", nullable = false)
    private String index;

    @NotNull
    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Quantity")
    private Double quantity;

    @NotNull
    @Column(name = "ProdLine", nullable = false)
    private String prodLine;

    @NotNull
    @Column(name = "OrderDate", nullable = false)
    private Instant orderDate;

    @Column(name = "Comment", nullable = true)
    private String comment;

    @NotNull
    @Column(name = "Status", nullable = false)
    private String status;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "HasComment", nullable = false)
    private Boolean hasComment = false;

    @NotNull
    @Column(name = "Client", nullable = false)
    private String client;

    @NotNull
    @Column(name = "BatchId", nullable = false, unique = true)
    private String batchId;
}