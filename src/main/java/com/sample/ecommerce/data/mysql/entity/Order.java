package com.sample.ecommerce.data.mysql.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "com.sample.ecommerce.data.mysql.entity.Order")
@Table(name = "orders")
public class Order implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id", nullable = false)
	private Integer orderId;
	@Column(name = "buyer_id", nullable = false)
	private Integer buyerId;
	@Column(name = "merchant_id", nullable = false)
	private Integer merchantId;
	@Column(name = "order_time", nullable = false)
	private Timestamp orderTime;
	@Column(name = "order_status", nullable = false)
	private String orderStatus;
	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;
	@Column(name = "created_by", nullable = false)
	private String createdBy;
	@Column(name = "updated_date", nullable = false)
	private Timestamp updatedDate;
	@Column(name = "updated_by", nullable = false)
	private String updatedBy;

    @Builder.Default
	@OneToMany(mappedBy="order")
	private List<OrderProduct> products = new ArrayList<>();
}