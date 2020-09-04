package com.lambdaschool.countrysearch.controllers;

import com.lambdaschool.countrysearch.model.Country;
import com.lambdaschool.countrysearch.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {
    @Autowired
    CountryRepository countrepos;
    private List<Country> findCountries(List<Country> myList, CheckCountry tester){
        List<Country> tempList = new ArrayList<>();
        for (Country c : myList){
            if(tester.test(c)){
                tempList.add(c);
            }
        }
        return tempList;
    }
    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries(){
        List<Country> myList = new ArrayList<>();
        countrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }
    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> listAllByCountryName(@PathVariable char letter){
        List<Country> myList = new ArrayList<>();
        countrepos.findAll().iterator().forEachRemaining(myList::add);
        List<Country> rtnList = findCountries(myList, c-> c.getName().toLowerCase().charAt(0) == letter);
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }
    @GetMapping(value = "/population/total")
    public ResponseEntity<?> findTotalOfPopulation(){
        List<Country> myList = new ArrayList<>();
        countrepos.findAll().iterator().forEachRemaining(myList::add);
        long totalPopulation = 0;
        for (Country c : myList){
            totalPopulation = totalPopulation + c.getPopulation();
        }
        System.out.println("The Total Population is " + totalPopulation);
        return new ResponseEntity<>("Status OK" ,HttpStatus.OK);
    }
    @GetMapping(value = "/population/min")
    public ResponseEntity<?> findMinPopulation(){
        List<Country> myList = new ArrayList<>();
        countrepos.findAll().iterator().forEachRemaining(myList::add);
        long min = Long.MAX_VALUE;
        Country minCountry = null;
        for(Country c : myList){
            if(c.getPopulation() < min){
                min = c.getPopulation();
                minCountry = c;
            }
        }
        return new ResponseEntity<>(minCountry, HttpStatus.OK);
    }
    @GetMapping(value = "/population/max")
    public ResponseEntity<?> findMaxPopulation(){
        List<Country> myList = new ArrayList<>();
        countrepos.findAll().iterator().forEachRemaining(myList::add);
        long max = 0;
        Country maxCountry = null;
        for(Country c : myList){
            if(c.getPopulation() > max){
                max = c.getPopulation();
                maxCountry = c;
            }
        }
        return new ResponseEntity<>(maxCountry, HttpStatus.OK);
    }
    @GetMapping(value = "/population/median")
    public ResponseEntity<?> findMedianPopulation(){
        List<Country> myList = new ArrayList<>();
        countrepos.findAll().iterator().forEachRemaining(myList::add);
        Country medCountry = null;
        int medId = myList.size() / 2;
        for(Country c : myList){
            if(myList.size() % 2 == 0 && Math.round(medId) == c.getCountryid()){
                medCountry = c;
            } else if(medId == c.getCountryid()){
                medCountry = c;
            }
        }
        return new ResponseEntity<>(medCountry, HttpStatus.OK);
    }
}
