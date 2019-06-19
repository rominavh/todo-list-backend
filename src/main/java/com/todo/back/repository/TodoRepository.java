package com.todo.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.todo.back.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>{
	
	 @Query("SELECT t FROM Todo t where (:id is null or t.id = :id) AND"
	 		+ "(:descripcion is null OR t.descripcion like %:descripcion%) AND"
	 		+ "(:estado is null OR t.estado = :estado)")
	 public List<Todo> findByIdOrDescripcionContainingOrEstado(@Param("id") Long id, @Param("descripcion") String descripcion, @Param("estado") Boolean estado);

}
