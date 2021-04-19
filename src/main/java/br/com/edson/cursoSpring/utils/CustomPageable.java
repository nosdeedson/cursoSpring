package br.com.edson.cursoSpring.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

public class CustomPageable {
	
	public static Pageable customPageable( Integer page, Integer size, //
			String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return pageRequest;
	}
}
