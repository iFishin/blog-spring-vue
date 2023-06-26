package com.fish.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fish.BlogApiApplicationTests;
import com.fish.repository.CategoryRepository;

public class CategoryRepositoryTest extends BlogApiApplicationTests{

	@Autowired
	private CategoryRepository	categoryRepository;

	@Test
	public void test() {
	}
}
