package com.udacity.boogle.maps;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maps")
public class MapsController {

    @GetMapping
    public Address get(@RequestParam Double lat, @RequestParam Double lon) {
        return MockAddressRepository.getRandom();
    }

    @GetMapping("/byId")
    public Address getById(@RequestParam long id) {
        System.out.println("Id : " + id);
        return MockAddressRepository.getAddress(id);
    }

    @GetMapping("/refresh")
    public Address refreshAddress(@RequestParam long id) {
         return  MockAddressRepository.refreshAddress(id);
    }
}
