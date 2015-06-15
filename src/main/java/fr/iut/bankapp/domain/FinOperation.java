package fr.iut.bankapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;

import fr.iut.bankapp.domain.util.CustomLocalDateSerializer;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A FinOperation.
 */
@Entity
@Table(name = "T_FINOPERATION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FinOperation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @Column(name = "value_date", nullable = false)
    private LocalDate valueDate;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    private Portfolio fromPortfolio;

    @ManyToOne
    private Portfolio toPortfolio;

    @Column(name = "quantity", precision=10, scale=2)
    private BigDecimal quantity;

    @ManyToOne
    private Instrument instrument;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDate valueDate) {
        this.valueDate = valueDate;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

	public void setFromPortfolio(Portfolio from) {
		this.fromPortfolio = from;
	}

	public Portfolio getToPortfolio() {
		return toPortfolio;
	}

	public void setToPortfolio(Portfolio to) {
		this.toPortfolio = to;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FinOperation finOperation = (FinOperation) o;

        if (id != null ? !id.equals(finOperation.id) : finOperation.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "FinOperation{" +
                "id=" + id +
                ", creationDate='" + creationDate + "'" +
                ", valueDate='" + valueDate + "'" +
                ", quantity='" + quantity + "'" +
                ", instrument='" + ((instrument != null)? instrument.getName() : "") + "'" +
                ", from='" + ((fromPortfolio != null)? fromPortfolio.getName() : "") + "'" +
                ", to='" + ((toPortfolio != null)? toPortfolio.getName() : "") + "'" +
                ", comment='" + comment + "'" +
                '}';
    }
}
