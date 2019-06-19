package com.todo.back.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.sun.jersey.multipart.FormDataParam;
import com.todo.back.exception.ResourceNotFoundException;
import com.todo.back.model.Todo;
import com.todo.back.repository.TodoRepository;

@RestController
@RequestMapping("/api/v1")
public class TodoController {
	
	@Autowired
    private TodoRepository todoRepository;

	@GetMapping("/todos")
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
	
	@GetMapping("/todos/filter")
	public List<Todo> searchTodos(@RequestParam(required = false) Long id,
			@RequestParam(required = false) String descripcion, @RequestParam(required = false) Boolean estado) {
		return todoRepository.findByIdOrDescripcionContainingOrEstado(id, descripcion, estado);
	}

    @PostMapping("/todos")
    public Todo createTodo(@FormDataParam("descripcion") String descripcion, @FormDataParam("file") MultipartFile file) {
    	Todo todo = new Todo();
    	try {
	    	todo.setDescripcion(descripcion);
	    	todo.setEstado(Boolean.FALSE);
	    	if(null != file) {
	    		byte [] byteArr=file.getBytes();
				todo.setImagen(byteArr);
	    	}
			
			return todoRepository.save(todo);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
    }
    

    @PutMapping("/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable(value = "id") Long todoId,
         @Valid @RequestBody Todo todoDetails) {
        try {
        	 Todo todo = todoRepository.findById(todoId)
						.orElseThrow(() -> new ResourceNotFoundException("Todo not found for this id :: " + todoId));
	        todo.setEstado(todoDetails.getEstado());
	        final Todo updatedTodo = todoRepository.save(todo);
	        return ResponseEntity.ok(updatedTodo);
        } catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
    }

//    @DeleteMapping("/todos/{id}")
//    public Map<String, Boolean> deleteTodo(@PathVariable(value = "id") Long todoId)
//         throws ResourceNotFoundException {
//    	
//    	 try {
//			Todo todo = todoRepository.findById(todoId)
//					 .orElseThrow(() -> new ResourceNotFoundException("Todo not found for this id :: " + todoId));
//			todoRepository.delete(todo);
//	        Map<String, Boolean> response = new HashMap<>();
//	        response.put("deleted", Boolean.TRUE);
//	        return response;
//    	 } catch (Exception e) {
// 			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
// 		}
//        
//    }
//    
//    @GetMapping("/todos/{id}")
//    public ResponseEntity<Todo> getTodoById(@PathVariable(value = "id") Long todoId)
//        throws ResourceNotFoundException {
//        Todo todo = todoRepository.findById(todoId)
//          .orElseThrow(() -> new ResourceNotFoundException("Todo not found for this id :: " + todoId));
//        return ResponseEntity.ok().body(todo);
//    }
}

