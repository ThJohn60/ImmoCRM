package net.immocrm.db;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="Document")
@Table(name="document")
@NamedQueries(value={
	@NamedQuery(name="Document.fetchAll", query="SELECT d FROM Document d "),
	@NamedQuery(name="Document.fetchByOrder", query="SELECT d FROM Document d WHERE d.order.id = :orderId"),
	@NamedQuery(name="Document.fetchByImmobilie", query="SELECT d FROM Document d WHERE d.immobilie.id = :immobilieId"),
})

public class DocumentEntity extends AbstractEntity {

	@Id
    @GeneratedValue(generator="documentSeq")
    @SequenceGenerator(name="documentSeq", sequenceName="IMMO_TRANS_SEQ", allocationSize=1)
	private Integer id;

	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderEntity order;

	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="immobilie_id")
	private ImmobilieEntity immobilie;

	@Column(name="file_name")
	private String fileName;

	@Column(name="doc_name")
	private String docName;

	@Column(name="description")
	private String description;

	@Column(name="created_on")
	private Timestamp createdOn;



	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
	}

	public ImmobilieEntity getImmobilie() {
		return immobilie;
	}

	public void setImmobilie(ImmobilieEntity immobilie) {
		this.immobilie = immobilie;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String filenName) {
		this.fileName = filenName;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Timestamp getCreatedOn() {
		return createdOn;
	}

	@Override
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	@PrePersist
	@PreUpdate
	public void setDefaultValues() {
		setDefaultCreatedOn();
	}


	@Override
	public String toString() {
		return "Document[" + fileName + "]";
	}

}
