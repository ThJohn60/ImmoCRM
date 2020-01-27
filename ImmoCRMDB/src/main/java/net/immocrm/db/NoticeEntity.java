package net.immocrm.db;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="Notice")
@Table(name="notice")
public class NoticeEntity extends AbstractEntity {

	@Id
    @GeneratedValue(generator="noticeSeq")
    @SequenceGenerator(name="noticeSeq", sequenceName="NOTICE_SEQ", allocationSize=1)
	private int id;

	@Column(length=6000)
	private String text;

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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public Timestamp getCreatedOn() {
		return createdOn;
	}

	@Override
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "Notiz[" + (text.length() > 20 ? text.substring(0, 20) : text) +  "]";
	}

}
