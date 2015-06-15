package fr.iut.bankapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A AuthorizedPortfolioOperation.
 */
@Entity
@Table(name = "T_AUTHORIZEDPORTFOLIOOPERATION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuthorizedPortfolioOperation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "oper_type")
    private String operType;

    @Column(name = "min_quantity", precision=10, scale=2)
    private BigDecimal minQuantity;

    @Column(name = "max_quantity", precision=10, scale=2)
    private BigDecimal maxQuantity;

    @ManyToOne
    private Instrument instrument;

    @ManyToOne
    private Portfolio fromPortfolio;

    @ManyToOne
    private Portfolio toPortfolio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public BigDecimal getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(BigDecimal minQuantity) {
        this.minQuantity = minQuantity;
    }

    public BigDecimal getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(BigDecimal maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
    
    public Portfolio getFromPortfolio() {
		return fromPortfolio;
	}

	public void setFromPortfolio(Portfolio fromPortfolio) {
		this.fromPortfolio = fromPortfolio;
	}

	public Portfolio getToPortfolio() {
		return toPortfolio;
	}

	public void setToPortfolio(Portfolio toPortfolio) {
		this.toPortfolio = toPortfolio;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuthorizedPortfolioOperation authorizedPortfolioOperation = (AuthorizedPortfolioOperation) o;

        if (id != null ? !id.equals(authorizedPortfolioOperation.id) : authorizedPortfolioOperation.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "AuthorizedPortfolioOperation{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", operType='" + operType + "'" +
                ", minQuantity='" + minQuantity + "'" +
                ", maxQuantity='" + maxQuantity + "'" +
                '}';
    }
}
