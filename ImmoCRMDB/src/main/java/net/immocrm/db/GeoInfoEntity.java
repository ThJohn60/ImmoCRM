package net.immocrm.db;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="GeoInfo")
@Table(name="geo_information")
@NamedQueries(value={
	@NamedQuery(name="GeoInfo.fetchAll", query="SELECT b FROM GeoInfo b"),
})
public class GeoInfoEntity extends AbstractEntity {

	@Id
    @GeneratedValue(generator="geoInfoSeq")
    @SequenceGenerator(name="geoInfoSeq",sequenceName="GEO_INFO_SEQ", allocationSize=1)
	private Integer id;

	@Column(name="quarter", length=64)
    private String quarter;

	private Long immo24QuarterId;

	private Double lon;
	private Double lat;

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

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public Double getLon() {
		return lon;
	}

	public Long getImmo24QuarterId() {
		return immo24QuarterId;
	}

	public void setImmo24QuarterId(Long immo24QuarterId) {
		this.immo24QuarterId = immo24QuarterId;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	@Override
	public Timestamp getCreatedOn() {
		return createdOn;
	}

	@Override
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public boolean isEmpty() {
		return EntityTool.isEmpty(quarter) && EntityTool.isNull(lon, lat);
	}

	@Override
	public String toString() {
		return String.format("%s[%.2f, %.2f]", quarter, lon, lat);
	}

}