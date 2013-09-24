package org.energyos.espi.thirdparty.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "data_custodians")
@NamedQueries(value = {
        @NamedQuery(name = DataCustodian.QUERY_FIND_ALL,
                query = "SELECT custodian FROM DataCustodian custodian")
})
public class DataCustodian {
    public static final String QUERY_FIND_ALL = "DataCustodian.findAll";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
