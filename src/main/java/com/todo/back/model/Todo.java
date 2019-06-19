package com.todo.back.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "todo")
public class Todo {
	
	private long id;
	
	private String descripcion;
	
	private boolean estado;
	
	private byte[] imagen;
	
	public Todo() {
	  
    }
 
    public Todo(String descripcion, boolean estado, byte[] imagen) {
         this.descripcion = descripcion;
         this.estado = estado;
         this.imagen = imagen;
    }
 
	    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

    
	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "descripcion", nullable = false)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "estado", nullable = false)
	public boolean getEstado() {
		return estado;
	}
	
	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@Lob
	@Column(name="imagen", columnDefinition = "BLOB")
	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	

}
