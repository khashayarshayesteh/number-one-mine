package com.cydeo.controller;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {
    private final ClientVendorService clientVendorService;

    public ClientVendorController(ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
    }

    @GetMapping("/list")
    public String listAllClientVendor(Model model) {

        model.addAttribute("clientVendors", clientVendorService.listAllClientVendors());

        return "/clientVendor/clientVendor-list";
    }

    @GetMapping("/create")
    public String createClientVendor(Model model) {
        model.addAttribute(("newClientVendor"), new ClientVendorDto());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        return "/clientVendor/clientVendor-create";
    }

    @PostMapping("/create")
    public String insertClientVendor(@Valid @ModelAttribute("newClientVendor") ClientVendorDto clientVendorDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
            return "/clientVendor/clientVendor-create";
        }
        clientVendorService.save(clientVendorDto);
        return "redirect:/clientVendors/list";
    }

    @GetMapping("/update/{id}")
    public String editClientVendor(@PathVariable Long id, Model model) {

        model.addAttribute("clientVendor", clientVendorService.findClientVendorById(id));
        model.addAttribute("address", clientVendorService.findClientVendorAddress(id));
        model.addAttribute("clientVendorTypes", clientVendorService.listAllClientVendorTypes());
        model.addAttribute("clientVendorType.value", clientVendorService.listAllClientVendorTypes());

        return "/clientVendor/clientVendor-update";
    }

    @PostMapping("/update/{id}")
    public String updateClientVendor(@PathVariable("id") Long id, @Valid @ModelAttribute ClientVendorDto clientVendorDto, BindingResult bindingResult, Model model) {
        clientVendorDto.setId(id);
        clientVendorDto.setClientVendorType(clientVendorService.findClientVendorById(id).getClientVendorType());
        if (bindingResult.hasErrors()) {
            model.addAttribute("clientVendorTypes", clientVendorService.listAllClientVendorTypes());
            model.addAttribute("address", clientVendorService.findClientVendorAddress(id));
            model.addAttribute("clientVendor", clientVendorDto);
            return "clientVendor/clientVendor-update";
        }
        clientVendorService.updateClientVendor(clientVendorDto);
        return "redirect:/clientVendors/list";
    }


}
