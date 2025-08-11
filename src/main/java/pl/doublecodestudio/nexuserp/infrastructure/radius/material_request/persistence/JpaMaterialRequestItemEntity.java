package pl.doublecodestudio.nexuserp.infrastructure.radius.material_request.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "material_request_item")
@Getter
@Setter
@NoArgsConstructor
public class JpaMaterialRequestItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "material_id", nullable = false)
    private String materialId;

    @Column(name = "material_name", nullable = false)
    private String materialName;

    @Column(name = "client", nullable = false)
    private String client;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "material_request_batch_id", nullable = false)
    private JpaMaterialRequestEntity materialRequest;
}
