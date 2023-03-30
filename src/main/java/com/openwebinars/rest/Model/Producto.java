package com.openwebinars.rest.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "precio")
	private float precio;

	@Column(name = "imagen")
	private String imagen;

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

}
