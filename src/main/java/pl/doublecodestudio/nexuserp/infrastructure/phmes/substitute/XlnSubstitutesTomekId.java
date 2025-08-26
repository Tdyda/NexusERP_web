package pl.doublecodestudio.nexuserp.infrastructure.phmes.substitute;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class XlnSubstitutesTomekId implements Serializable {
    private String baseMaterialId;
    private String substMaterialId;
}
