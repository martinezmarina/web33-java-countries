package com.lambdaschool.countrysearch.repositories;

import com.lambdaschool.countrysearch.model.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long> {
}
